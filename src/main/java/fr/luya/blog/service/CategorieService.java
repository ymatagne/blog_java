package fr.luya.blog.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.luya.blog.document.Categorie;
import fr.luya.blog.repository.CategorieRepository;

/**
 * Service des categories, permet de faire le lien entre le controlleur et le répository
 * 
 * @author luya
 */
@Service
public class CategorieService {

    @Autowired
    private CategorieRepository repository;

    /**
     * Permet la création d'un categorie en base de données
     * 
     * @param categorie à creer
     * @return l'état de la création
     */
    public Boolean create(final Categorie categorie) {
        categorie.setId(UUID.randomUUID().toString());
        final Categorie saved = repository.save(categorie);
        if (saved == null)
            return false;

        return true;
    }

    /**
     * Permet la mise à jour d'un categorie
     * 
     * @param categorie à mettre à jour en base
     * @return l'état de la mise à jour
     */
    public Boolean update(final Categorie categorie) {
        Categorie existingCategorie = repository.findOne(categorie.getId());
        if (existingCategorie == null)
            return false;

        existingCategorie.setNom(categorie.getNom());

        Categorie saved = repository.save(existingCategorie);
        if (saved == null)
            return false;

        return true;
    }

    /**
     * Permet la suppression d'un categorie
     * 
     * @param categorie à supprimer en base
     * @return l'état de la suppression
     */
    public Boolean delete(final Categorie categorie) {
        final Categorie existingCategorie = repository.findOne(categorie.getId());
        if (existingCategorie == null)
            return false;

        repository.delete(existingCategorie);
        final Categorie deletedCategorie = repository.findOne(categorie.getId());
        if (deletedCategorie != null)
            return false;

        return true;
    }

    /**
     * Permet de récuperer tous les categories présent en base
     * 
     * @return une liste de categories
     */
    public List<Categorie> findAllCategories() {
        return repository.findAll();
    }

    /**
     * Permet de récuperer un article en base via son id
     * 
     * @param id de la categorie
     * @return une categorie
     */
    public Categorie findById(final String id) {
        return repository.findOne(id);
    }
}
