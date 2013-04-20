package fr.luya.blog.service;

import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.luya.blog.document.Commentaire;
import fr.luya.blog.repository.CommentaireRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/resources/spring/web.xml" })
public class CommentaireServiceTest {
    /**
     * Service appelé par le controlleur. Il permet de faire le lien entre le controller et le répository permettant
     * d'acceder aux données
     */
    @Autowired
    private CommentaireService service;

    @Autowired
    CommentaireRepository repository;

    /**
     * Initialisation du test, creation d une collection virtuelle
     */
    @Before
    public void initTest() {

        for (int i = 0; i < 6; i++) {
            final Commentaire commentaire = new Commentaire();
            commentaire.setId(Integer.toString(i));
            commentaire.setCommentaire("commentaire" + i);
            commentaire.setDateCreation(new Date());
            commentaire.setEmail("email" + i);
            repository.save(commentaire);
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
     * Test de l'ajout d'un commentaire
     */
    @Test
    public void shouldAddCommentaires() {
        final Commentaire commentaire = new Commentaire();
        commentaire.setId("111");
        commentaire.setCommentaire("commentaire7");
        commentaire.setDateCreation(new Date());
        commentaire.setEmail("email7");
        Assert.assertTrue(service.create(commentaire));
        Assert.assertNotNull(commentaire.getId());
    }

    /**
     * Test de la mise a jour d'un commentaire
     */
    @Test
    public void shouldUpdateCommentaire() {
        final Commentaire commentaire = service.findById("1");
        Assert.assertEquals("titre1", "titre1");
        commentaire.setCommentaire("toto");
        commentaire.setDateCreation(new Date());
        commentaire.setEmail("emailemail");
        service.update(commentaire);
        final Commentaire commentaireUpdated = service.findById("1");
        Assert.assertEquals("toto", commentaireUpdated.getCommentaire());

    }

    /**
     * Test de la suppression d'un commentaire
     */
    @Test
    public void shouldDeleteCommentaire() {
        final Commentaire commentaire = service.findById("1");
        Assert.assertTrue(service.delete(commentaire));
        Assert.assertEquals("le nombre d commentaire ne correspond pas", 5, service.findAllCommentaires().size());
        Assert.assertFalse(service.delete(commentaire));

    }
}
