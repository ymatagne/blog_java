package fr.luya.blog.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.luya.blog.document.Article;
import fr.luya.blog.repository.ArticleRepository;

/**
 * Service des articles, permet de faire le lien entre le controlleur et le répository
 * 
 * @author luya
 */
@Service
public class ArticleService {

    @Autowired
    private ArticleRepository repository;

    /**
     * Permet la création d'un article en base de données
     * 
     * @param article à creer
     * @return l'état de la création
     */
    public Boolean create(final Article article) {
        article.setId(UUID.randomUUID().toString());
        final Article saved = repository.save(article);
        if (saved == null)
            return false;

        return true;
    }

    /**
     * Permet la mise à jour d'un article
     * 
     * @param article à mettre à jour en base
     * @return l'état de la mise à jour
     */
    public Boolean update(final Article article) {
        Article existingArticle = repository.findById(article.getId());
        if (existingArticle == null)
            return false;

        existingArticle.setTitre(article.getTitre());

        Article saved = repository.save(existingArticle);
        if (saved == null)
            return false;

        return true;
    }

    /**
     * Permet la suppression d'un article
     * 
     * @param article à supprimer en base
     * @return l'état de la suppression
     */
    public Boolean delete(final Article article) {
        final Article existingArticle = repository.findById(article.getId());
        if (existingArticle == null)
            return false;

        repository.delete(existingArticle);
        final Article deletedArticle = repository.findById(article.getId());
        if (deletedArticle != null)
            return false;

        return true;
    }

    /**
     * Permet de récuperer tous les articles présent en base
     * 
     * @return une liste d'article
     */
    public List<Article> findAllArticles() {
        return repository.findAll();
    }

    /**
     * Permet de récuperer un article en base via son id
     * 
     * @param id de l'article
     * @return un article
     */
    public Article findById(final String id) {
        return repository.findById(id);
    }
}
