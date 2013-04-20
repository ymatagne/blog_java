package fr.luya.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.luya.blog.document.Auteur;
import fr.luya.blog.repository.AuteurRepository;

/**
 * Service des auteurs, permet de faire le lien entre le controlleur et le répository
 * 
 * @author luya
 */
@Service
public class AuteurService {

    @Autowired
    private AuteurRepository repository;

    /**
     * Permet la création d'un auteur en base de données
     * 
     * @param auteur à creer
     * @return l'état de la création
     */
    public Boolean create(final Auteur auteur) {
        final Auteur saved = repository.save(auteur);
        if (saved == null)
            return false;

        return true;
    }

    /**
     * Permet la mise à jour d'un auteur
     * 
     * @param auteur à mettre à jour en base
     * @return l'état de la mise à jour
     */
    public Boolean update(final Auteur auteur) {
        Auteur existingAuteur = repository.findOne(auteur.getEmail());
        if (existingAuteur == null)
            return false;

        existingAuteur.setEmail(auteur.getEmail());
        existingAuteur.setAdmin(auteur.isAdmin());
        existingAuteur.setNom(auteur.getNom());
        existingAuteur.setPassword(auteur.getPassword());
        existingAuteur.setPrenom(auteur.getPrenom());

        Auteur saved = repository.save(existingAuteur);
        if (saved == null)
            return false;

        return true;
    }

    /**
     * Permet la suppression d'un auteur
     * 
     * @param auteur à supprimer en base
     * @return l'état de la suppression
     */
    public Boolean delete(final Auteur auteur) {
        final Auteur existingAuteur = repository.findOne(auteur.getEmail());
        if (existingAuteur == null)
            return false;

        repository.delete(existingAuteur);
        final Auteur deletedAuteur = repository.findOne(auteur.getEmail());
        if (deletedAuteur != null)
            return false;

        return true;
    }

    /**
     * Permet de récuperer tous les auteurs présent en base
     * 
     * @return une liste d'auteur
     */
    public List<Auteur> findAllAuteurs() {
        return repository.findAll();
    }

    /**
     * Permet de récuperer un auteur en base via son id
     * 
     * @param id de l'auteur
     * @return un auteur
     */
    public Auteur findById(final String id) {
        return repository.findOne(id);
    }
}
