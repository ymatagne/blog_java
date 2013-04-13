package fr.luya.blog.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import fr.luya.blog.document.Categorie;
import fr.luya.blog.service.CategorieService;
import fr.luya.blog.utils.IntegrationTestUtil;
import fr.luya.blog.utils.MockBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/resources/spring/web.xml" })
public class CategorieControllerTest {

    private CategorieController categorieControlleur;

    private MockMvc mockMvc;

    private List<Categorie> categories;
    private Categorie categorieTmp;

    /**
     * Creation des objets virtuelles via La classe {@link MockBuilder} et le service {@link CategorieService} utilisé
     * dans le controlleur {@link CategorieControlleur} est mocké via Mockito
     */
    @Before
    public void setup() {
        categories = MockBuilder.mockCategories();
        categorieTmp = new Categorie();
        categorieTmp.setNom("Titre5");
        categorieTmp.setId("5");

        final CategorieService categorieService = mock(CategorieService.class);
        when(categorieService.findAllCategories()).thenReturn(categories);
        when(categorieService.findById("1")).thenReturn(categories.get(1));
        when(categorieService.delete(categories.get(1))).thenAnswer(new Answer<Boolean>() {

            @Override
            public Boolean answer(InvocationOnMock invocation) throws Throwable {
                categories.remove(1);
                return true;
            }
        });

        when(categorieService.create(categorieTmp)).thenAnswer(new Answer<Boolean>() {
            @Override
            public Boolean answer(InvocationOnMock invocation) throws Throwable {
                categories.add(categorieTmp);
                return true;
            }
        });

        categorieControlleur = new CategorieController();
        ReflectionTestUtils.setField(categorieControlleur, "service", categorieService);

        this.mockMvc = MockMvcBuilders.standaloneSetup(categorieControlleur).build();

    }

    /**
     * Test de l'action /categorie en GET, lors de l'appel a cette action, la liste de tous les categories doivent être
     * retournées
     * 
     * @throws Exception exceptions
     */
    @Test
    public void checkGetAllCategories() throws Exception {
        mockMvc.perform(get("/categorie")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().bytes(IntegrationTestUtil.convertObjectToJsonBytes(categories)));

    }

    /**
     * Test de l'action /categorie/{id} en GET, lors de l'appel a cette action, l'categorie avec l'id {id} doit être
     * retourné
     * 
     * @throws Exception exceptions
     */
    @Test
    public void checkGetById() throws Exception {
        mockMvc.perform(get("/categorie/1")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().bytes(IntegrationTestUtil.convertObjectToJsonBytes(categories.get(1))));

    }

    /**
     * Test de l'action /categorie/{id} en DELTE, lors de l'appel a cette action, l'categorie avec l'id {id} doit être
     * supprimé
     * 
     * @throws Exception exceptions
     */
    @Test
    public void checkDeleteById() throws Exception {
        mockMvc.perform(delete("/categorie/1"));
        Assert.assertEquals(categories.size(), 2);

    }

    /**
     * Test de l'action /categorie/ en PUT, lors de l'appel a cette action, l'categorie avec passee en parametre doit
     * etre creer
     * 
     * @throws Exception exceptions
     */
    @Test
    public void checkCreateCategorie() throws Exception {
        mockMvc.perform(
                put("/categorie").contentType(IntegrationTestUtil.APPLICATION_JSON_UTF8).content(
                        IntegrationTestUtil.convertObjectToJsonBytes(categorieTmp))).andExpect(status().isOk())
                .andExpect(content().contentType(IntegrationTestUtil.APPLICATION_JSON_UTF8))
                .andExpect(content().bytes(IntegrationTestUtil.convertObjectToJsonBytes(categorieTmp)));
        Assert.assertEquals(categories.size(), 4);

    }

}
