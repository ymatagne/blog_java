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

import fr.luya.blog.document.Auteur;

/**
 * Test du répository des auteurs
 *
 * @author luya
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/resources/spring/web.xml"})
public class AuteurRepositoryTest {

    @Autowired
    AuteurRepository repository;
    @Autowired
    MongoTemplate template;

    /**
     * Initialisation du test, creation d une collection virtuelle
     */
    @Before
    public void initTest() {
        template.dropCollection("auteur");
        template.createCollection("auteur");

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
     * Test d'une page d'auteur
     */
    @Test
    public void shouldPageAuteurs() {
        final List<Auteur> auteurs;

        final Page<Auteur> page = repository.findAll(new PageRequest(1, 2));

        Assert.assertNotNull(page);
        Assert.assertEquals("le nombre d auteur ne correspond pas", 6, page.getTotalElements());

        auteurs = page.getContent();

        Assert.assertNotNull(auteurs);
        Assert.assertEquals("le nombre d auteur ne correspond pas", 2, auteurs.size());

    }

    /**
     * Test de l'ajout d'un auteur
     */
    @Test
    public void shouldAddAuteur() {
        final Auteur auteur = new Auteur();
        auteur.setNom("nom7");
        auteur.setAdmin(true);
        auteur.setEmail("email7");
        auteur.setPassword("pass7");
        auteur.setPrenom("prenom7");
        repository.save(auteur);

        Assert.assertEquals("le nombre d auteur ne correspond pas", 7, repository.findAll().size());

    }

    /**
     * Test de la suppresion d'un auteur
     */
    @Test
    public void shouldDeleteAuteur() {
        final Auteur auteur = repository.findByEmail("email1");
        repository.delete(auteur);

        Assert.assertEquals("le nombre d auteur ne correspond pas", 5, repository.findAll().size());

    }

    /**
     * Test de la mise à jour d'un auteur
     */
    @Test
    public void shouldUpdateAuteur() {
        final Auteur auteur = repository.findByEmail("email1");
        auteur.setNom("nom8");
        auteur.setAdmin(true);
        auteur.setPassword("pass8");
        auteur.setPrenom("prenom8");
        repository.save(auteur);

        Assert.assertEquals("le nombre d auteur ne correspond pas", 6, repository.findAll().size());

    }

}
