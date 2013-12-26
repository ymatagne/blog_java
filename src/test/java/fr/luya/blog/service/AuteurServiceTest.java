package fr.luya.blog.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.luya.blog.document.Auteur;
import fr.luya.blog.exceptions.DuplicateUserEmail;
import fr.luya.blog.repository.AuteurRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/resources/spring/web.xml" })
public class AuteurServiceTest {
    /**
     * Service appelé par le controlleur. Il permet de faire le lien entre le controller et le répository permettant
     * d'acceder aux données
     */
    @Autowired
    private AuteurService service;

    @Autowired
    AuteurRepository repository;

    /**
     * Initialisation du test, creation d une collection virtuelle
     */
    @Before
    public void initTest() {

        for (int i = 0; i < 6; i++) {
            final Auteur auteur = new Auteur();
            auteur.setNom("nom" + i);
            auteur.setAdmin(true);
            auteur.setEmail("email" + i);
            auteur.setPassword("pass" + i);
            auteur.setPrenom("prenom" + i);
            repository.save(auteur);
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
     * Test de l'ajout d'un auteur
     */
    @Test
    public void shouldAddAuteurs() {
        final Auteur auteur = new Auteur();
        auteur.setNom("nom7");
        auteur.setAdmin(true);
        auteur.setEmail("email7");
        auteur.setPassword("pass7");
        auteur.setPrenom("prenom7");
        try {
            service.saveOrUpdate(auteur);
        } catch (DuplicateUserEmail e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Test de la suppression d'un auteur
     */
    @Test
    public void shouldDeleteAuteur() {
        final Auteur auteur = service.findById("email1");
        Assert.assertTrue(service.delete(auteur));
        Assert.assertEquals("le nombre d auteur ne correspond pas", 5, service.findAllAuteurs().size());
        Assert.assertFalse(service.delete(auteur));

    }
}
