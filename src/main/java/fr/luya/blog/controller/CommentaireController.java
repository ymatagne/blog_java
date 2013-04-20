package fr.luya.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import fr.luya.blog.document.Commentaire;
import fr.luya.blog.service.CommentaireService;

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
    private CommentaireService service;

    /**
     * Permet de récuperer tous les commentaires présents en base de données.
     * 
     * @return une liste contenant tous les commentaires de la base de donénes
     */
    @RequestMapping(value = "/commentaire", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    List<Commentaire> list() {
        return service.findAllCommentaires();
    }

    /**
     * Permet de récueprer un commentaire via son id
     * 
     * @param id de l'commentaire à récuperer
     * @return l'commentaire
     */
    @RequestMapping(value = "/commentaire/{id}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    Commentaire getById(@PathVariable final String id) {
        return service.findById(id);
    }

    /**
     * Permet de créer un nouvel commentaire en base de donnés
     * 
     * @param commentaire à creer
     * @return l'commentaire créer avec son id
     */
    @RequestMapping(value = "/commentaire", method = RequestMethod.PUT)
    @ResponseBody
    public Commentaire create(@RequestBody final Commentaire commentaire) {
        service.create(commentaire);
        return commentaire;

    }

    /**
     * Permet de supprimer un commentaire
     * 
     * @param id de l'commentaire à supprimer
     */
    @RequestMapping(value = "/commentaire/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable final String id) {
        final Commentaire commentaire = service.findById(id);
        service.delete(commentaire);
    }
}
