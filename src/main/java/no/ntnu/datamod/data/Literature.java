package no.ntnu.datamod.data;

import java.sql.Blob;

/**
 * Write a description of class Literature here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class Literature
{
    private String title;
    private String publisher;

    /**
     * Constructor for objects of class Literature
     */
    public Literature(String publisher, String title)
    {
        this.title = title;
        this.publisher = publisher;
    }

    public String getTitle() {
        return title;
    }
    
    public String getPublisher() {
        return publisher;
    }

    public abstract byte[] getImage();

    public abstract int getIdBook();
}
