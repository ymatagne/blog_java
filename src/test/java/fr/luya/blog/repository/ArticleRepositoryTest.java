package fr.luya.blog.repository;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.luya.blog.document.Article;

/**
 * Test du répository des articles
 * 
 * @author luya
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/resources/spring/web.xml" })
public class ArticleRepositoryTest {

    @Autowired
    ArticleRepository repository;
    @Autowired
    MongoTemplate template;

    /**
     * Initialisation du test, creation d une collection virtuelle
     */
    @Before
    public void initTest() {
        template.dropCollection("article");
        template.createCollection("article");

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
     * Test d'une page d'article
     */
    @Test
    public void shouldPageArticles() {
        final List<Article> articles;

        final Page<Article> page = repository.findAll(new PageRequest(1, 2));

        Assert.assertNotNull(page);
        Assert.assertEquals("le nombre d article ne correspond pas", 6, page.getTotalElements());

        articles = page.getContent();

        Assert.assertNotNull(articles);
        Assert.assertEquals("le nombre d article ne correspond pas", 2, articles.size());

    }

    /**
     * Test de l'ajout d'un article
     */
    @Test
    public void shouldAddArticle() {
        final Article article = new Article();
        article.setId("111");
        article.setTitre("titre7");
        repository.save(article);

        Assert.assertEquals("le nombre d article ne correspond pas", 7, repository.findAll().size());

    }

    /**
     * Test de la suppresion d'un article
     */
    @Test
    public void shouldDeleteArticle() {
        final Article article = new Article();
        article.setId(Integer.toString(1));
        article.setTitre("titre1");
        repository.delete(article);

        Assert.assertEquals("le nombre d article ne correspond pas", 5, repository.findAll().size());

    }

    /**
     * Test de la mise à jour d'un article
     */
    @Test
    public void shouldUpdateArticle() {
        final Article article = new Article();
        article.setId(Integer.toString(1));
        article.setTitre("titre7");
        repository.save(article);

        Assert.assertEquals("le nombre d article ne correspond pas", 6, repository.findAll().size());

    }

}
