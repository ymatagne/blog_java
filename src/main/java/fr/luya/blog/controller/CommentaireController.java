package fr.luya.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import fr.luya.blog.document.Article;
import fr.luya.blog.service.ArticleService;

/**
 * Controlleur des actions sur les commentaires. Permet d'executer les differentes actions d'ajout, de suppression et de
 * récuperations des commentaires
 * 
 * @author luya
 */
@Controller
public class CommentaireController {

    /**
     * Service appelé par le controlleur. Il permet de faire le lien entre le controller et le répository permettant
     * d'acceder aux données
     */
    @Autowired
    private ArticleService articleService;

    /**
     * Permet de mettre a jour les commentaires d'un article
     * 
     * @param commentaire à creer
     * @return l'commentaire créer avec son id
     */
    @RequestMapping(value = "/commentaire", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public void updateCommentaire(@RequestBody final Article article) {
        articleService.addCommentaire(article);
    }
}
