package fr.luya.blog.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.luya.blog.document.Commentaire;
import fr.luya.blog.repository.CommentaireRepository;

/**
 * Service des commentaires, permet de faire le lien entre le controlleur et le répository
 * 
 * @author luya
 */
@Service
public class CommentaireService {

    @Autowired
    private CommentaireRepository repository;

    /**
     * Permet la création d'un commentaire en base de données
     * 
     * @param commentaire à creer
     * @return l'état de la création
     */
    public Boolean create(final Commentaire commentaire) {
//        commentaire.setId(UUID.randomUUID().toString());
        final Commentaire saved = repository.save(commentaire);
        if (saved == null)
            return false;

        return true;
    }

    /**
     * Permet la mise à jour d'un commentaire
     * 
     * @param commentaire à mettre à jour en base
     * @return l'état de la mise à jour
     */
    public Boolean update(final Commentaire commentaire) {
//        Commentaire existingCommentaire = repository.findOne(commentaire.getId());
//        if (existingCommentaire == null)
//            return false;
//
//        existingCommentaire.setCommentaire(commentaire.getCommentaire());
//        existingCommentaire.setDateCreation(commentaire.getDateCreation());
//        existingCommentaire.setEmail(commentaire.getEmail());
//
//        Commentaire saved = repository.save(existingCommentaire);
//        if (saved == null)
//            return false;

        return true;
    }

    /**
     * Permet la suppression d'un commentaire
     * 
     * @param commentaire à supprimer en base
     * @return l'état de la suppression
     */
    public Boolean delete(final Commentaire commentaire) {
//        final Commentaire existingCommentaire = repository.findOne(commentaire.getId());
//        if (existingCommentaire == null)
//            return false;
//
//        repository.delete(existingCommentaire);
//        final Commentaire deletedCommentaire = repository.findOne(commentaire.getId());
//        if (deletedCommentaire != null)
//            return false;

        return true;
    }

    /**
     * Permet de récuperer tous les commentaires présent en base
     * 
     * @return une liste d'commentaire
     */
    public List<Commentaire> findAllCommentaires() {
        return repository.findAll();
    }

    /**
     * Permet de récuperer un commentaire en base via son id
     * 
     * @param id de l'commentaire
     * @return un commentaire
     */
    public Commentaire findById(final String id) {
        return repository.findOne(id);
    }
}
