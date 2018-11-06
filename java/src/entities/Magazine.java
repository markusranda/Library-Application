package entities;

/**
 * Write a description of class Magazine here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Magazine extends Literature
{
    private String date;
    private int pages;

    /**
     * Constructor for objects of class Magazine
     */
    public Magazine(String publisher, String title, double price, String date, int pages)
    {
        super(publisher, title, price);
        this.date = date;
        this.pages = pages;
    }
    
    /**
     * insert javadoc();
     */
    public String getDetails()
    {
        return getPublisher() + ", " + getTitle() + ", " + String.valueOf(getPrice()) 
        + ", " + date + ", " + pages;   
    }
}
