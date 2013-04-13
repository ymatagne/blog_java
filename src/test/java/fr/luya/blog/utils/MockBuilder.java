package fr.luya.blog.utils;

import java.util.ArrayList;
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
        auteur.setId("1");
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
        final Article article1 = new Article();
        article1.setId("2");
        article1.setTitre("Titre2");
        article1.setArticle("article");
        article1.setAuteur(auteur);
        article1.setCategorie(categorie);
        article1.setResume("resume");
        article1.setValide(true);
        article1.setCommentaires(new ArrayList<Commentaire>());
        final Article article2 = new Article();
        article2.setId("3");
        article2.setTitre("Titre3");
        article2.setArticle("article");
        article2.setAuteur(auteur);
        article2.setCategorie(categorie);
        article2.setResume("resume");
        article2.setValide(true);
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
}
