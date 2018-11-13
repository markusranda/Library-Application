package no.ntnu.datamod.data;

/**
 *   This class stores details about created books,
 *   such as publisher, title, author and the edition of the book.
 *   
 *   @author Gruppe 11 Markus Randa, Lars Ous, Frode Pedersen.
 *   @version 1.0.0
 */
public class Book extends Literature
{
    private String author;
    private String edition;
    private String imageURL;

    /* todo
    finish creating this class with fields and methods
     */

    /**
     * Constructor for objects of class Book.
     * @param publisher The books publisher.
     * @param title The books title.
     * @param author The books author.
     * @param edition The books edition.
     */
    public Book(String publisher, String title, String author, String edition)
    {
        super(publisher, title);
        this.author = author;
        this.edition = edition;
    }

    // ---------- AKSESSOR METHODS ---------- //
    
    /**
     * Return all of the books details: publisher, title, author, edition
     * @return The books details
     */
    public String getDetails()
    {
        return getPublisher() + ", " + getTitle() + ", " 
        + author + ", " + edition;
    }

    /**
     * Return the author.
     *@return The books author
     */
    public String getAuthor()
    {
        return author;
    }

    /**
     * Return the edition.
     *@return The books edition
     */
    public String getEdition()
    {
        return edition;
    }

    /**
     * The books image URL as a String, if the object doesn't
     * have a string it will return null.
     *
     * @return The books image URL as a String, if the object doesn't
     * have a string it will return null.
     */
    public String getImageURL() { return imageURL; }
}
