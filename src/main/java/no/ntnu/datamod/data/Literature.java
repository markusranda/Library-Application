package no.ntnu.datamod.data;

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

    /**
     * Constructor for objects of class Littrature
     */
    public Literature(String publisher, String title)
    {
        this.title = title;
        this.publisher = publisher;
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
}
