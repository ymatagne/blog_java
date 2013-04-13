package fr.luya.blog.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.luya.blog.document.Categorie;
import fr.luya.blog.repository.CategorieRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/resources/spring/web.xml" })
public class CategorieServiceTest {
    /**
     * Service appelé par le controlleur. Il permet de faire le lien entre le controller et le répository permettant
     * d'acceder aux données
     */
    @Autowired
    private CategorieService service;

    @Autowired
    CategorieRepository repository;

    /**
     * Initialisation du test, creation d une collection virtuelle
     */
    @Before
    public void initTest() {

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
     * Test de l'ajout d'un categorie
     */
    @Test
    public void shouldAddCategories() {
        final Categorie categorie = new Categorie();
        categorie.setNom("titreTmp");
        Assert.assertTrue(service.create(categorie));
        Assert.assertNotNull(categorie.getId());
    }

    /**
     * Test de la mise a jour d'un categorie
     */
    @Test
    public void shouldUpdateCategorie() {
        final Categorie categorie = service.findById("1");
        Assert.assertEquals("titre1", "titre1");
        categorie.setNom("toto");
        service.update(categorie);
        final Categorie categorieUpdated = service.findById("1");
        Assert.assertEquals("toto", categorieUpdated.getNom());

    }

    /**
     * Test de la suppression d'un categorie
     */
    @Test
    public void shouldDeleteCategorie() {
        final Categorie categorie = service.findById("1");
        Assert.assertTrue(service.delete(categorie));
        Assert.assertEquals("le nombre d categorie ne correspond pas", 5, service.findAllCategories().size());
        Assert.assertFalse(service.delete(categorie));

    }
}
