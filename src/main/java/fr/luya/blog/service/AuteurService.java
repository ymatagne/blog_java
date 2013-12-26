package fr.luya.blog.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import fr.luya.blog.document.Auteur;
import fr.luya.blog.exceptions.DuplicateUserEmail;
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
     * @return l'auteur sauvegardé
     * @throws DuplicateUserEmail email deja present en base
     */
    private Auteur create(final Auteur auteur) throws DuplicateUserEmail {
        auteur.setId(UUID.randomUUID().toString());
        Auteur saved = null;
        try {
            saved = repository.save(auteur);
        } catch (final DuplicateKeyException duplicateKeyException) {
            throw new DuplicateUserEmail(duplicateKeyException);
        }
        return saved;
    }

    /**
     * Permet la suppression d'un auteur
     * 
     * @param auteur à supprimer en base
     * @return l'état de la suppression
     */
    public Boolean delete(final Auteur auteur) {      
        repository.delete(auteur);
        final Auteur deletedAuteur = repository.findOne(auteur.getId());
        if (deletedAuteur != null)
            return false;

        return true;
    }

    /**
     * findByEmail Permet de récuperer tous les auteurs présent en base
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

    /**
     * Permet de récuperer un auteur en base via son id
     * 
     * @param id de l'auteur
     * @return un auteur
     */
    public Auteur findByEmail(final String email) {
        return repository.findByEmail(email);
    }

    /**
     * Permet la mise à jour et/ou la création d'un auteur
     * 
     * @param auteur à mettre à jour en base
     * @return l'état de la mise à jour
     * @throws DuplicateUserEmail email deja present en base
     */
    public Auteur saveOrUpdate(final Auteur auteur) throws DuplicateUserEmail {
        if (auteur.getId() == null) {
            return create(auteur);
        } else {
            final Auteur existingAuteur = repository.findOne(auteur.getId());
            existingAuteur.setAdmin(auteur.isAdmin());
            existingAuteur.setNom(auteur.getNom());
            existingAuteur.setPassword(auteur.getPassword());
            existingAuteur.setPrenom(auteur.getPrenom());

            Auteur saved = repository.save(existingAuteur);
            return saved;
        }
    }
}
