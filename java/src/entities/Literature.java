package entities;

/**
 * Write a description of class Litterature here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class Literature
{
    private String title;
    private String publisher;
    private Double price;

    /**
     * Constructor for objects of class Litterature
     */
    public Literature(String publisher, String title, Double price)
    {
        this.title = title;
        this.publisher = publisher;
        this.price = price;
    }
    
    public abstract String getDetails();
    
    public String getTitle()
    {
        return title;
    }
    
    public String getPublisher()
    {
        return publisher;
    }
    
    public Double getPrice()
    {
        return price;
    }
}
