package fr.luya.blog.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import fr.luya.blog.document.Auteur;

/**
 * Répository, permet de récuperer des informations de la base de donénes
 * 
 * @author luya
 */
public interface AuteurRepository extends MongoRepository<Auteur, String> {

}
