package fr.luya.blog.utils;

import java.util.ArrayList;
import java.util.List;

import fr.luya.blog.document.Article;

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
        final Article article = new Article();
        article.setId("1");
        article.setTitre("Titre1");
        final Article article1 = new Article();
        article1.setId("2");
        article1.setTitre("Titre2");
        final Article article2 = new Article();
        article2.setId("3");
        article2.setTitre("Titre3");
        articles.add(article);
        articles.add(article1);
        articles.add(article2);
        return articles;
    }
}
