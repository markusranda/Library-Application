

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ui.InputValidator;

/**
 * The test class InputValidatorTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class InputValidatorTest
{
    /**
     * Default constructor for test class InputValidatorTest
     */
    public InputValidatorTest()
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
    public void testCheckValidNormalName()
    {
        InputValidator inputVal1 = new InputValidator();
        assertEquals(true, inputVal1.checkValid("Markus Randa"));
    }
    
    @Test
    public void testCheckValidEmptyString()
    {
        InputValidator inputVal1 = new InputValidator();
        assertEquals(false, inputVal1.checkValid(""));
    }
    
    @Test
    public void testCheckValidStringOver50()
    {
        InputValidator inputVal1 = new InputValidator();
        assertEquals(false, inputVal1.checkValid("123451234512345123451234563124352134654riuthgsdiufhgsidufhgoisuhdrtfoiy7w458ytodifuhgosndrujtbglsiudfnmgisudfrtynhsue5rynthigsudyrklituhbsyfdgkuhsket"));
    }

    @Test
    public void testCheckValidDoubleString()
    {
        InputValidator inputVal1 = new InputValidator();
        assertEquals(false, inputVal1.checkValidDouble("cracker"));
    }

    @Test
    public void testCheckValidDoubleInt()
    {
        InputValidator inputVal1 = new InputValidator();
        assertEquals(true, inputVal1.checkValidDouble("420"));
    }
}






