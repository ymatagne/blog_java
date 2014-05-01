package fr.luya.blog.controller;

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

import fr.luya.blog.document.Auteur;
import fr.luya.blog.exceptions.DuplicateUserEmail;
import fr.luya.blog.service.AuteurService;

/**
 * Controlleur des actions sur les auteurs. Permet d'executer les differentes actions d'ajout, de suppression et de
 * récuperations des auteurs
 * 
 * @author luya
 */
@Controller
public class AuteurController {

    /**
     * Service appelé par le controlleur. Il permet de faire le lien entre le controller et le répository permettant
     * d'acceder aux données
     */
    @Autowired
    private AuteurService service;

    /**
     * Permet de récuperer tous les auteurs présents en base de données.
     * 
     * @return une liste contenant tous les auteurs de la base de donénes
     */
    @RequestMapping(value = "/auteur", method = RequestMethod.GET, produces = "application/json")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public @ResponseBody
    List<Auteur> list() {
        return service.findAllAuteurs();
    }

    /**
     * Permet de récueprer un auteur via son id
     * 
     * @param email de l'auteur à récuperer
     * @return l'auteur
     * @throws Exception
     */
    @RequestMapping(value = "/auteurByEmail/{email:.+}", method = RequestMethod.GET, produces = "application/json")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public @ResponseBody
    String getByEmail(@PathVariable final String email) {
        return Boolean.toString(service.findByEmail(email) != null);
    }

    /**
     * Permet de récueprer un auteur via son id
     * 
     * @param id de l'auteur à récuperer
     * @return l'auteur
     */
    @RequestMapping(value = "/auteur/{id:.+}", headers = "Accept=*/*", method = RequestMethod.GET, produces = "application/json")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public @ResponseBody
    Auteur getById(@PathVariable final String id) {
        return service.findById(id);
    }

    /**
     * Permet de créer un nouvel auteur en base de donnés
     * 
     * @param auteur à creer
     * @return l'auteur créer avec son id
     * @throws DuplicateUserEmail email deja present en base
     */
    @RequestMapping(value = "/auteur", method = RequestMethod.PUT, headers = "Accept=*/*", produces = "application/json;charset=UTF-8")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @ResponseBody
    public Auteur create(@RequestBody final Auteur auteur) {
        try {
            service.saveOrUpdate(auteur);
        } catch (final DuplicateUserEmail e) {
            auteur.setEmail(null);
            auteur.setId(null);
        }
        return auteur;

    }

    /**
     * Permet de supprimer un auteur
     * 
     * @param id de l'auteur à supprimer
     */
    @RequestMapping(value = "/auteur/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable final String id) {
        final Auteur auteur = service.findById(id);
        service.delete(auteur);
    }
}
