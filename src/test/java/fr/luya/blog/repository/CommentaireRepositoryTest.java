package fr.luya.blog.repository;

import java.util.Date;
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

import fr.luya.blog.document.Commentaire;

/**
 * Test du répository des commentaires
 * 
 * @author luya
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/resources/spring/web.xml" })
public class CommentaireRepositoryTest {

    @Autowired
    CommentaireRepository repository;
    @Autowired
    MongoTemplate template;

    /**
     * Initialisation du test, creation d une collection virtuelle
     */
    @Before
    public void initTest() {
        template.dropCollection("commentaire");
        template.createCollection("commentaire");

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
     * Test d'une page d'commentaire
     */
    @Test
    public void shouldPageCommentaires() {
        final List<Commentaire> commentaires;

        final Page<Commentaire> page = repository.findAll(new PageRequest(1, 2));

        Assert.assertNotNull(page);
        Assert.assertEquals("le nombre d commentaire ne correspond pas", 6, page.getTotalElements());

        commentaires = page.getContent();

        Assert.assertNotNull(commentaires);
        Assert.assertEquals("le nombre d commentaire ne correspond pas", 2, commentaires.size());

    }

    /**
     * Test de l'ajout d'un commentaire
     */
    @Test
    public void shouldAddCommentaire() {
        final Commentaire commentaire = new Commentaire();
        commentaire.setId("111");
        commentaire.setCommentaire("commentaire7");
        commentaire.setDateCreation(new Date());
        commentaire.setEmail("email7");
        repository.save(commentaire);

        Assert.assertEquals("le nombre d commentaire ne correspond pas", 7, repository.findAll().size());

    }

    /**
     * Test de la suppresion d'un commentaire
     */
    @Test
    public void shouldDeleteCommentaire() {
        final Commentaire commentaire = new Commentaire();
        commentaire.setId(Integer.toString(1));
        commentaire.setCommentaire("commentaire1");
        commentaire.setDateCreation(new Date());
        commentaire.setEmail("email1");
        repository.delete(commentaire);

        Assert.assertEquals("le nombre d commentaire ne correspond pas", 5, repository.findAll().size());

    }

    /**
     * Test de la mise à jour d'un commentaire
     */
    @Test
    public void shouldUpdateCommentaire() {
        final Commentaire commentaire = new Commentaire();
        commentaire.setId(Integer.toString(1));
        commentaire.setCommentaire("commentaire666");
        commentaire.setDateCreation(new Date());
        commentaire.setEmail("emailemail");
        repository.save(commentaire);

        Assert.assertEquals("le nombre d commentaire ne correspond pas", 6, repository.findAll().size());

    }

}
