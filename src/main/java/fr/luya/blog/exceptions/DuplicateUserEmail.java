package fr.luya.blog.exceptions;

import org.springframework.dao.DuplicateKeyException;

/**
 * exception lancé si l'utilisateur entré en base de données contient la même adresse mail qu'un autre utilisateur
 * 
 * @author luya
 */
public class DuplicateUserEmail extends Exception {

    /**
     * Constructeur de l'exception
     * 
     * @param duplicateKeyException erreur mongodb
     */
    public DuplicateUserEmail(final DuplicateKeyException duplicateKeyException) {
        super(duplicateKeyException);
    }

    /**
     * 
     */
    private static final long serialVersionUID = -5987340616047457158L;

}
