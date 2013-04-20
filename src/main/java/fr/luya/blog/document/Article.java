package fr.luya.blog.document;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Document Article
 * 
 * @author luya
 */
@Document
public class Article {
    @Id
    private String id;

    private String titre;

    private String article;

    private String resume;

    private Boolean valide;

    private Date dateCreation;

    @DBRef
    private List<Commentaire> commentaires;

    @DBRef
    private Auteur auteur;

    @DBRef
    private Categorie categorie;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the titre
     */
    public String getTitre() {
        return titre;
    }

    /**
     * @param titre the titre to set
     */
    public void setTitre(String titre) {
        this.titre = titre;
    }

    /**
     * @return the article
     */
    public String getArticle() {
        return article;
    }

    /**
     * @param article the article to set
     */
    public void setArticle(String article) {
        this.article = article;
    }

    /**
     * @return the resume
     */
    public String getResume() {
        return resume;
    }

    /**
     * @param resume the resume to set
     */
    public void setResume(String resume) {
        this.resume = resume;
    }

    /**
     * @return the valide
     */
    public Boolean getValide() {
        return valide;
    }

    /**
     * @param valide the valide to set
     */
    public void setValide(Boolean valide) {
        this.valide = valide;
    }

    /**
     * @return the commentaires
     */
    public List<Commentaire> getCommentaires() {
        return commentaires;
    }

    /**
     * @param commentaires the commentaires to set
     */
    public void setCommentaires(List<Commentaire> commentaires) {
        this.commentaires = commentaires;
    }

    /**
     * @return the auteur
     */
    public Auteur getAuteur() {
        return auteur;
    }

    /**
     * @param auteur the auteur to set
     */
    public void setAuteur(Auteur auteur) {
        this.auteur = auteur;
    }

    /**
     * @return the categorie
     */
    public Categorie getCategorie() {
        return categorie;
    }

    /**
     * @param categorie the categorie to set
     */
    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((article == null) ? 0 : article.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((resume == null) ? 0 : resume.hashCode());
        result = prime * result + ((titre == null) ? 0 : titre.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Article)) {
            return false;
        }
        Article other = (Article) obj;
        if (article == null) {
            if (other.article != null) {
                return false;
            }
        } else if (!article.equals(other.article)) {
            return false;
        }

        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (resume == null) {
            if (other.resume != null) {
                return false;
            }
        } else if (!resume.equals(other.resume)) {
            return false;
        }
        if (titre == null) {
            if (other.titre != null) {
                return false;
            }
        } else if (!titre.equals(other.titre)) {
            return false;
        }
        return true;
    }

    /**
     * @return the dateCreation
     */
    public Date getDateCreation() {
        return dateCreation;
    }

    /**
     * @param dateCreation the dateCreation to set
     */
    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }
}
