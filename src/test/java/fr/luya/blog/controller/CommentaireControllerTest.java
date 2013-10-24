package fr.luya.blog.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.List;

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

import fr.luya.blog.document.Commentaire;
import fr.luya.blog.service.CommentaireService;
import fr.luya.blog.utils.IntegrationTestUtil;
import fr.luya.blog.utils.MockBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/resources/spring/web.xml" })
public class CommentaireControllerTest {

    private CommentaireController commentaireControlleur;

    private MockMvc mockMvc;

    private List<Commentaire> commentaires;
    private Commentaire commentaireTmp;

    /**
     * Creation des objets virtuelles via La classe {@link MockBuilder} et le service {@link CommentaireService} utilisé
     * dans le controlleur {@link CommentaireControlleur} est mocké via Mockito
     */
    @Before
    public void setup() {
        commentaires = MockBuilder.mockCommentaires();
        commentaireTmp = new Commentaire();
        commentaireTmp.setCommentaire("Titre5");
//        commentaireTmp.setId("5");
        commentaireTmp.setDateCreation(new Date());
        commentaireTmp.setEmail("mail5");

        final CommentaireService commentaireService = mock(CommentaireService.class);
        when(commentaireService.findAllCommentaires()).thenReturn(commentaires);
        when(commentaireService.findById("1")).thenReturn(commentaires.get(1));
        when(commentaireService.delete(commentaires.get(1))).thenAnswer(new Answer<Boolean>() {

            @Override
            public Boolean answer(InvocationOnMock invocation) throws Throwable {
                commentaires.remove(1);
                return true;
            }
        });

        when(commentaireService.create(commentaireTmp)).thenAnswer(new Answer<Boolean>() {
            @Override
            public Boolean answer(InvocationOnMock invocation) throws Throwable {
                commentaires.add(commentaireTmp);
                return true;
            }
        });

        commentaireControlleur = new CommentaireController();
        ReflectionTestUtils.setField(commentaireControlleur, "service", commentaireService);

        this.mockMvc = MockMvcBuilders.standaloneSetup(commentaireControlleur).build();

    }

    /**
     * Test de l'action /commentaire en GET, lors de l'appel a cette action, la liste de tous les commentaires doivent
     * être retournées
     * 
     * @throws Exception exceptions
     */
    @Test
    public void checkGetAllCommentaires() throws Exception {
        mockMvc.perform(get("/commentaire")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().bytes(IntegrationTestUtil.convertObjectToJsonBytes(commentaires)));

    }

    /**
     * Test de l'action /commentaire/{id} en GET, lors de l'appel a cette action, l'commentaire avec l'id {id} doit être
     * retourné
     * 
     * @throws Exception exceptions
     */
    @Test
    public void checkGetById() throws Exception {
        mockMvc.perform(get("/commentaire/1")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().bytes(IntegrationTestUtil.convertObjectToJsonBytes(commentaires.get(1))));

    }

    /**
     * Test de l'action /commentaire/{id} en DELTE, lors de l'appel a cette action, l'commentaire avec l'id {id} doit
     * être supprimé
     * 
     * @throws Exception exceptions
     */
    @Test
    public void checkDeleteById() throws Exception {
        mockMvc.perform(delete("/commentaire/1"));
        Assert.assertEquals(commentaires.size(), 2);

    }

    /**
     * Test de l'action /commentaire/ en PUT, lors de l'appel a cette action, l'commentaire avec passee en parametre
     * doit etre creer
     * 
     * @throws Exception exceptions
     */
    @Test
    public void checkCreateCommentaire() throws Exception {
        mockMvc.perform(
                put("/commentaire").contentType(IntegrationTestUtil.APPLICATION_JSON_UTF8).content(
                        IntegrationTestUtil.convertObjectToJsonBytes(commentaireTmp))).andExpect(status().isOk())
                .andExpect(content().contentType(IntegrationTestUtil.APPLICATION_JSON_UTF8))
                .andExpect(content().bytes(IntegrationTestUtil.convertObjectToJsonBytes(commentaireTmp)));
        Assert.assertEquals(commentaires.size(), 4);

    }

}
