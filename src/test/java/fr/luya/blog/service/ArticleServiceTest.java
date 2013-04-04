package fr.luya.blog.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.luya.blog.document.Article;
import fr.luya.blog.repository.ArticleRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/resources/spring/web.xml" })
public class ArticleServiceTest {
    /**
     * Service appelé par le controlleur. Il permet de faire le lien entre le controller et le répository permettant
     * d'acceder aux données
     */
    @Autowired
    private ArticleService service;

    @Autowired
    ArticleRepository repository;

    /**
     * Initialisation du test, creation d une collection virtuelle
     */
    @Before
    public void initTest() {

        for (int i = 0; i < 6; i++) {
            final Article article = new Article();
            article.setId(Integer.toString(i));
            article.setTitre("titre" + i);
            repository.save(article);
        }
    }

    /**
     * Suppression des données crees pour le test. Cette méthode est lancé après chaque test
     */
    @After
    public void afterTest() {
        repository.deleteAll();
    }

    /**
     * Test de l'ajout d'un article
     */
    @Test
    public void shouldAddArticles() {
        final Article article = new Article();
        article.setTitre("titreTmp");
        Assert.assertTrue(service.create(article));
        Assert.assertNotNull(article.getId());
    }

    /**
     * Test de la mise a jour d'un article
     */
    @Test
    public void shouldUpdateArticle() {
        final Article article = service.findById("1");
        Assert.assertEquals("titre1", "titre1");
        article.setTitre("toto");
        service.update(article);
        final Article articleUpdated = service.findById("1");
        Assert.assertEquals("toto", articleUpdated.getTitre());

    }

    /**
     * Test de la suppression d'un article
     */
    @Test
    public void shouldDeleteArticle() {
        final Article article = service.findById("1");
        Assert.assertTrue(service.delete(article));
        Assert.assertEquals("le nombre d article ne correspond pas", 5, service.findAllArticles().size());
        final Article articleNotExisting = new Article();
        Assert.assertFalse(service.delete(articleNotExisting));

    }
}
