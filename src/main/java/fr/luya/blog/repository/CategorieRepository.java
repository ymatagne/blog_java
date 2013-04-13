package fr.luya.blog.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import fr.luya.blog.document.Categorie;

/**
 * Répository, permet de récuperer des informations de la base de donénes
 * 
 * @author luya
 */
public interface CategorieRepository extends MongoRepository<Categorie, String> {
}
