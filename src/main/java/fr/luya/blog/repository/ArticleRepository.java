package fr.luya.blog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import fr.luya.blog.document.Article;

/**
 * Répository, permet de récuperer des informations de la base de donénes
 * 
 * @author luya
 */
public interface ArticleRepository extends MongoRepository<Article, String> {

    /**
     * Permet de rechercher un article par son titre
     * 
     * @param titre recherché en base de données
     * @return un article
     */
    Article findByTitre(@Param("titre") final String titre);

    /**
     * Permet de rechercher un article par son titre avec un like
     * 
     * @param titre de l'article recherché
     * @param pageable pagination
     * @return une page d'article
     */
    Page<Article> findByTitreLike(@Param("titre") final String titre, final Pageable pageable);

    /**
     * PErmet de récperer un article par son id
     * 
     * @param id de l'article recherché
     * @return l'article correspondant à l'id passé en paramètre
     */
    Article findById(@Param("id") final String id);

}
