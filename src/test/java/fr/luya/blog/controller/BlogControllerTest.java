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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import fr.luya.blog.document.Article;
import fr.luya.blog.service.ArticleService;
import fr.luya.blog.utils.IntegrationTestUtil;
import fr.luya.blog.utils.MockBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "file:src/main/resources/spring/web.xml" })
public class BlogControllerTest {

    private ArticleController articleControlleur;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    private List<Article> articles;
    private Article articleTmp;

    /**
     * Creation des objets virtuelles via La classe {@link MockBuilder} et le service {@link ArticleService} utilisé
     * dans le controlleur {@link ArticleControlleur} est mocké via Mockito
     */
    @Before
    public void setup() {
        articles = MockBuilder.mockArticles();
        articleTmp = new Article();
        articleTmp.setTitre("Titre5");
        articleTmp.setId("5");

        final ArticleService articleService = mock(ArticleService.class);
        when(articleService.findAllArticles()).thenReturn(articles);
        when(articleService.findById("1")).thenReturn(articles.get(1));
        when(articleService.delete(articles.get(1))).thenAnswer(new Answer<Boolean>() {

            @Override
            public Boolean answer(InvocationOnMock invocation) throws Throwable {
                articles.remove(1);
                return true;
            }
        });
        
        when(articleService.create(articleTmp)).thenAnswer(new Answer<Boolean>() {
            @Override
            public Boolean answer(InvocationOnMock invocation) throws Throwable {
                articles.add(articleTmp);
                return true;
            }
        });
        
        articleControlleur = new ArticleController();
        ReflectionTestUtils.setField(articleControlleur, "service", articleService);

        this.mockMvc = MockMvcBuilders.standaloneSetup(articleControlleur).build();

    }

    /**
     * Test de l'action /article en GET, lors de l'appel a cette action, la liste de tous les articles doivent être
     * retournées
     * 
     * @throws Exception exceptions
     */
    @Test
    public void checkGetAllArticles() throws Exception {
        mockMvc.perform(get("/article")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().bytes(IntegrationTestUtil.convertObjectToJsonBytes(articles)));

    }

    /**
     * Test de l'action /article/{id} en GET, lors de l'appel a cette action, l'article avec l'id {id} doit être
     * retourné
     * 
     * @throws Exception exceptions
     */
    @Test
    public void checkGetById() throws Exception {
        mockMvc.perform(get("/article/1")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().bytes(IntegrationTestUtil.convertObjectToJsonBytes(articles.get(1))));

    }

    /**
     * Test de l'action /article/{id} en DELTE, lors de l'appel a cette action, l'article avec l'id {id} doit être
     * supprimé
     * 
     * @throws Exception exceptions
     */
    @Test
    public void checkDeleteById() throws Exception {
        mockMvc.perform(delete("/article/1"));
        Assert.assertEquals(articles.size(), 2);

    }

    /**
     * Test de l'action /article/ en PUT, lors de l'appel a cette action, l'article avec passee en parametre doit etre
     * creer
     * 
     * @throws Exception exceptions
     */
    @Test
    public void checkCreateArticle() throws Exception {
        mockMvc.perform(
                put("/article").contentType(IntegrationTestUtil.APPLICATION_JSON_UTF8).content(
                        IntegrationTestUtil.convertObjectToJsonBytes(articleTmp))).andExpect(status().isOk())
                .andExpect(content().contentType(IntegrationTestUtil.APPLICATION_JSON_UTF8))
                .andExpect(content().bytes(IntegrationTestUtil.convertObjectToJsonBytes(articleTmp)));
        Assert.assertEquals(articles.size(), 4);

    }

}
