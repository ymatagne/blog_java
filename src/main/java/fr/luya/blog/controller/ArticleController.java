package fr.luya.blog.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import fr.luya.blog.document.Article;
import fr.luya.blog.document.Commentaire;
import fr.luya.blog.service.ArticleService;

/**
 * Controlleur des actions sur les articles. Permet d'executer les differentes actions d'ajout, de suppression et de
 * récuperations des articles
 * 
 * @author luya
 */
@Controller
public class ArticleController {

    /**
     * Service appelé par le controlleur. Il permet de faire le lien entre le controller et le répository permettant
     * d'acceder aux données
     */
    @Autowired
    private ArticleService service;

    /**
     * Permet de récuperer tous les articles présents en base de données.
     * 
     * @return une liste contenant tous les articles de la base de donénes
     */
    @RequestMapping(value = "/article", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    List<Article> list() {
        return service.findAllArticles();
    }

    /**
     * Permet de récueprer un article via son id
     * 
     * @param id de l'article à récuperer
     * @return l'article
     */
    @RequestMapping(value = "/article/{id}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    Article getById(@PathVariable final String id) {
        return service.findById(id);
    }

    /**
     * Permet de créer un nouvel article en base de donnés
     * 
     * @param article à creer
     * @return l'article créer avec son id
     */
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @RequestMapping(value = "/article", method = RequestMethod.PUT)
    @ResponseBody
    public Article create(@RequestBody final Article article) {
        if (article.getId() == null) {
            article.setDateCreation(new Date());
            article.setCommentaires(new ArrayList<Commentaire>());
            service.create(article);
        } else {
            service.update(article);
        }
        return article;
    }

    /**
     * Permet de supprimer un article
     * 
     * @param id de l'article à supprimer
     */
    @RequestMapping(value = "/article/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable final String id) {
        final Article article = service.findById(id);
        service.delete(article);
    }
}
