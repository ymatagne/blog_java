package fr.luya.blog.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import fr.luya.blog.document.Auteur;

/**
 * Répository, permet de récuperer des informations de la base de donénes
 * 
 * @author luya
 */
public interface AuteurRepository extends MongoRepository<Auteur, String> {

    @Query( "{'email' :?0}")
    public Auteur findByEmail(final String email);

}
