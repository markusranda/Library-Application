package entities;

/**
 * Write a description of class Newspaper here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Newspaper extends Literature
{
    private String date;
    private int pages;
    private boolean includesComics;

    /**
     * Constructor for objects of class Newspaper
     */
    public Newspaper(String publisher, String title, double price, String date, int pages, boolean includesComics)
    {
        super(publisher, title, price);
        this.date = date;
        this.pages = pages;
        this.includesComics = includesComics;
    }
    
    /**
     * insert javadoc();
     */
    public String getDetails()
    {
        return getPublisher() + ", " + getTitle() + ", " + String.valueOf(getPrice()) 
        + ", " + date + ", " + pages + ", " +includesComics;        
    }
}
