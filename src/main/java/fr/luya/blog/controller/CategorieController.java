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

import fr.luya.blog.document.Categorie;
import fr.luya.blog.service.CategorieService;

/**
 * Controlleur des actions sur les categories. Permet d'executer les differentes actions d'ajout, de suppression et de
 * récuperations des categories
 * 
 * @author luya
 */
@Controller
public class CategorieController {

    /**
     * Service appelé par le controlleur. Il permet de faire le lien entre le controller et le répository permettant
     * d'acceder aux données
     */
    @Autowired
    private CategorieService service;

    /**
     * Permet de récuperer tous les categories présents en base de données.
     * 
     * @return une liste contenant tous les categories de la base de donénes
     */
    @RequestMapping(value = "/categorie", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    List<Categorie> list() {
        return service.findAllCategories();
    }

    /**
     * Permet de récueprer un categorie via son id
     * 
     * @param id de l'categorie à récuperer
     * @return l'categorie
     */
    @RequestMapping(value = "/categorie/{id}", method = RequestMethod.GET, produces = "application/json")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public @ResponseBody
    Categorie getById(@PathVariable final String id) {
        return service.findById(id);
    }

    /**
     * Permet de créer un nouvel categorie en base de donnés
     * 
     * @param categorie à creer
     * @return l'categorie créer avec son id
     */
    @RequestMapping(value = "/categorie", method = RequestMethod.PUT)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @ResponseBody
    public Categorie create(@RequestBody final Categorie categorie) {
        if (categorie.getId() != null) {
            service.update(categorie);
        } else {
            service.create(categorie);
        }
        return categorie;

    }

    /**
     * Permet de supprimer un categorie
     * 
     * @param id de l'categorie à supprimer
     */
    @RequestMapping(value = "/categorie/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable final String id) {
        final Categorie categorie = service.findById(id);
        service.delete(categorie);
    }
}
