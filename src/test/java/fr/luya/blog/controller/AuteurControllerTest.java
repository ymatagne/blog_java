package fr.luya.blog.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import fr.luya.blog.document.Auteur;
import fr.luya.blog.exceptions.DuplicateUserEmail;
import fr.luya.blog.service.AuteurService;
import fr.luya.blog.utils.IntegrationTestUtil;
import fr.luya.blog.utils.MockBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/resources/spring/web.xml"})
public class AuteurControllerTest {

    private AuteurController auteurControlleur;

    private MockMvc mockMvc;

    private List<Auteur> auteurs;
    private Auteur auteurTmp;

    /**
     * Creation des objets virtuelles via La classe {@link MockBuilder} et le service {@link AuteurService} utilisé dans
     * le controlleur AuteurController est mocké via Mockito
     */
    @Before
    public void setup() {
        auteurs = MockBuilder.mockAuteurs();
        auteurTmp = new Auteur();
        auteurTmp.setNom("Titre5");
        auteurTmp.setAdmin(true);
        auteurTmp.setEmail("mail");
        auteurTmp.setPassword("pass");
        auteurTmp.setPrenom("prenom");

        final AuteurService auteurService = mock(AuteurService.class);
        when(auteurService.findAllAuteurs()).thenReturn(auteurs);
        when(auteurService.findById("1")).thenReturn(auteurs.get(1));
        when(auteurService.delete(auteurs.get(1))).thenAnswer(new Answer<Boolean>() {

            @Override
            public Boolean answer(InvocationOnMock invocation) throws Throwable {
                auteurs.remove(1);
                return true;
            }
        });

        try {
            when(auteurService.saveOrUpdate(auteurTmp)).thenAnswer(new Answer<Auteur>() {
                @Override
                public Auteur answer(InvocationOnMock invocation) throws Throwable {
                    auteurs.add(auteurTmp);
                    auteurTmp.setId("5");
                    return auteurTmp;
                }
            });
        } catch (DuplicateUserEmail e) {
            Assert.fail(e.getMessage());
        }

        auteurControlleur = new AuteurController();
        ReflectionTestUtils.setField(auteurControlleur, "service", auteurService);

        this.mockMvc = MockMvcBuilders.standaloneSetup(auteurControlleur).build();

    }

    /**
     * Test de l'action /auteur en GET, lors de l'appel a cette action, la liste de tous les auteurs doivent être
     * retournées
     *
     * @throws Exception exceptions
     */
    @Test
    public void checkGetAllAuteurs() throws Exception {
        mockMvc.perform(get("/auteur")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().bytes(IntegrationTestUtil.convertObjectToJsonBytes(auteurs, JsonSerialize.Inclusion.ALWAYS)));

    }

    /**
     * Test de l'action /auteur/{id} en GET, lors de l'appel a cette action, l'auteur avec l'id {id} doit être retourné
     *
     * @throws Exception exceptions
     */
    @Test
    public void checkGetById() throws Exception {
        mockMvc.perform(get("/auteur/1")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().bytes(IntegrationTestUtil.convertObjectToJsonBytes(auteurs.get(1), JsonSerialize.Inclusion.NON_NULL)));

    }

    /**
     * Test de l'action /auteur/{id} en DELTE, lors de l'appel a cette action, l'auteur avec l'id {id} doit être
     * supprimé
     *
     * @throws Exception exceptions
     */
    @Test
    public void checkDeleteById() throws Exception {
        mockMvc.perform(delete("/auteur/1"));
        Assert.assertEquals(auteurs.size(), 2);

    }

    /**
     * Test de l'action /auteur/ en PUT, lors de l'appel a cette action, l'auteur avec passee en parametre doit etre
     * creer
     *
     * @throws Exception exceptions
     */
    @Test
    public void checkCreateAuteur() throws Exception {
        final Auteur auteurTmpTest = auteurTmp;
        auteurTmpTest.setId("5");
        mockMvc.perform(
                put("/auteur").contentType(IntegrationTestUtil.APPLICATION_JSON_UTF8).content(
                        IntegrationTestUtil.convertObjectToJsonBytes(auteurTmp, JsonSerialize.Inclusion.NON_NULL))).andExpect(status().isOk())
                .andExpect(content().contentType(IntegrationTestUtil.APPLICATION_JSON_UTF8))
                .andExpect(content().bytes(IntegrationTestUtil.convertObjectToJsonBytes(auteurTmpTest, JsonSerialize.Inclusion.NON_NULL)));
        Assert.assertEquals(auteurs.size(), 4);

    }

}
