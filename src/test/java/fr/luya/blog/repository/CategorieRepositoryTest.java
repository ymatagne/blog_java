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

import fr.luya.blog.document.Categorie;

/**
 * Test du répository des categories
 * 
 * @author luya
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/resources/spring/web.xml" })
public class CategorieRepositoryTest {

    @Autowired
    CategorieRepository repository;
    @Autowired
    MongoTemplate template;

    /**
     * Initialisation du test, creation d une collection virtuelle
     */
    @Before
    public void initTest() {
        template.dropCollection("categorie");
        template.createCollection("categorie");

        for (int i = 0; i < 6; i++) {
            final Categorie categorie = new Categorie();
            categorie.setId(Integer.toString(i));
            categorie.setNom("titre" + i);
            repository.save(categorie);
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
     * Test d'une page d'categorie
     */
    @Test
    public void shouldPageCategories() {
        final List<Categorie> categories;

        final Page<Categorie> page = repository.findAll(new PageRequest(1, 2));

        Assert.assertNotNull(page);
        Assert.assertEquals("le nombre d categorie ne correspond pas", 6, page.getTotalElements());

        categories = page.getContent();

        Assert.assertNotNull(categories);
        Assert.assertEquals("le nombre d categorie ne correspond pas", 2, categories.size());

    }

    /**
     * Test de l'ajout d'un categorie
     */
    @Test
    public void shouldAddCategorie() {
        final Categorie categorie = new Categorie();
        categorie.setId("111");
        categorie.setNom("titre7");
        repository.save(categorie);

        Assert.assertEquals("le nombre d categorie ne correspond pas", 7, repository.findAll().size());

    }

    /**
     * Test de la suppresion d'un categorie
     */
    @Test
    public void shouldDeleteCategorie() {
        final Categorie categorie = new Categorie();
        categorie.setId(Integer.toString(1));
        categorie.setNom("titre1");
        repository.delete(categorie);

        Assert.assertEquals("le nombre d categorie ne correspond pas", 5, repository.findAll().size());

    }

    /**
     * Test de la mise à jour d'un categorie
     */
    @Test
    public void shouldUpdateCategorie() {
        final Categorie categorie = new Categorie();
        categorie.setId(Integer.toString(1));
        categorie.setNom("titre7");
        repository.save(categorie);

        Assert.assertEquals("le nombre d categorie ne correspond pas", 6, repository.findAll().size());

    }

}
