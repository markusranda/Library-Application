import static org.junit.Assert.*;

import entities.Book;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class BookTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class BookTest
{
    private Book book1;

    /**
     * Default constructor for test class BookTest
     */
    public BookTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        book1 = new Book("JBB", "Turnetips", 420.0, "Roald", "1");
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }


    @Test
    public void testGetPublisher()
    {
        assertEquals("JBB", book1.getPublisher());
    }
    
    @Test
    public void testGetAuthor()
    {
        assertEquals("Roald", book1.getAuthor());
    }
    
    @Test
    public void testGetTitle()
    {
        assertEquals("Turnetips", book1.getTitle());
    }
    
    @Test
    public void testGetEdition()
    {
        assertEquals("1", book1.getEdition());
    }

    @Test
    public void testGetPrice()
    {
        assertEquals(420.0, book1.getPrice(), 0.1);
    }

    @Test
    public void testGetBookDetails()
    {
        assertEquals("JBB, Turnetips, Roald, 1, 420.0", book1.getDetails());
    }

    @Test
    public void testSetAuthor()
    {
        assertEquals("Roald", book1.getAuthor());
    }

    @Test
    public void testSetEdition()
    {
        assertEquals("1", book1.getEdition());
    }

}















