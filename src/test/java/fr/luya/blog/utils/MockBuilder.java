package fr.luya.blog.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.luya.blog.document.Article;
import fr.luya.blog.document.Auteur;
import fr.luya.blog.document.Categorie;
import fr.luya.blog.document.Commentaire;

/**
 * Permet la création d'objet pour test
 * 
 * @author luya
 */
public final class MockBuilder {

    private MockBuilder() {
    }

    /**
     * Permet de creer une liste d article mocke
     * 
     * @return une liste d article
     */
    public static List<Article> mockArticles() {
        final List<Article> articles = new ArrayList<Article>();
        final Auteur auteur = new Auteur();
        auteur.setAdmin(true);
        auteur.setEmail("email");
        auteur.setNom("nom");
        auteur.setPassword("password");
        auteur.setPrenom("prenom");
        final Categorie categorie = new Categorie();
        categorie.setId("1");
        categorie.setNom("nom categorie");
        final Article article = new Article();
        article.setId("1");
        article.setTitre("Titre1");
        article.setArticle("article");
        article.setAuteur(auteur);
        article.setCategorie(categorie);
        article.setCommentaires(new ArrayList<Commentaire>());
        article.setResume("resume");
        article.setValide(true);
        article.setDateCreation(new Date());
        final Article article1 = new Article();
        article1.setId("2");
        article1.setTitre("Titre2");
        article1.setArticle("article");
        article1.setAuteur(auteur);
        article1.setCategorie(categorie);
        article1.setResume("resume");
        article1.setValide(true);
        article1.setCommentaires(new ArrayList<Commentaire>());
        article1.setDateCreation(new Date());
        final Article article2 = new Article();
        article2.setId("3");
        article2.setTitre("Titre3");
        article2.setArticle("article");
        article2.setAuteur(auteur);
        article2.setCategorie(categorie);
        article2.setResume("resume");
        article2.setValide(true);
        article2.setDateCreation(new Date());
        article2.setCommentaires(new ArrayList<Commentaire>());
        articles.add(article);
        articles.add(article1);
        articles.add(article2);
        return articles;
    }

    /**
     * @return une liste de categorie
     */
    public static List<Categorie> mockCategories() {
        final List<Categorie> categories = new ArrayList<Categorie>();
        final Categorie categorie = new Categorie();
        categorie.setId("1");
        categorie.setNom("categorie A");
        final Categorie categorie1 = new Categorie();
        categorie1.setId("2");
        categorie1.setNom("categorie B");
        final Categorie categorie2 = new Categorie();
        categorie2.setId("3");
        categorie2.setNom("categorie C");
        categories.add(categorie);
        categories.add(categorie1);
        categories.add(categorie2);
        return categories;
    }

    public static List<Auteur> mockAuteurs() {
        final List<Auteur> auteurs = new ArrayList<Auteur>();
        final Auteur auteur = new Auteur();
        auteur.setAdmin(true);
        auteur.setEmail("email");
        auteur.setNom("nom");
        auteur.setPassword("pass");
        auteur.setPrenom("prenom");
        final Auteur auteur1 = new Auteur();
        auteur1.setAdmin(true);
        auteur1.setEmail("email");
        auteur1.setNom("nom1");
        auteur1.setPassword("pass1");
        auteur1.setPrenom("prenom1");
        final Auteur auteur2 = new Auteur();
        auteur2.setAdmin(true);
        auteur2.setEmail("email");
        auteur2.setNom("nom2");
        auteur2.setPassword("pass2");
        auteur2.setPrenom("prenom2");

        auteurs.add(auteur);
        auteurs.add(auteur1);
        auteurs.add(auteur2);
        return auteurs;
    }

    public static List<Commentaire> mockCommentaires() {
        final List<Commentaire> commentaires = new ArrayList<Commentaire>();
        final Commentaire commentaire = new Commentaire();
        commentaire.setEmail("email");
        commentaire.setId("0");
        commentaire.setCommentaire("commentaire");
        commentaire.setEmail("email");
        commentaire.setDateCreation(new Date());
        final Commentaire commentaire1 = new Commentaire();
        commentaire1.setEmail("email");
        commentaire1.setId("1");
        commentaire1.setCommentaire("commentaire1");
        commentaire1.setEmail("email1");
        commentaire1.setDateCreation(new Date());
        final Commentaire commentaire2 = new Commentaire();
        commentaire2.setEmail("email");
        commentaire2.setId("2");
        commentaire2.setCommentaire("commentaire2");
        commentaire2.setEmail("email2");
        commentaire2.setDateCreation(new Date());

        commentaires.add(commentaire);
        commentaires.add(commentaire1);
        commentaires.add(commentaire2);
        return commentaires;
    }
}
