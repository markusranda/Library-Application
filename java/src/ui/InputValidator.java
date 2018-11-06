package ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * This class is a helper-class for the ApplicationUI, and
 * contains methods to help validate user-input and to hold
 * certain criterias.
 *
 * @author Gruppe 11 Markus Randa, Lars Ous, Frode Pedersen.
 * @version (a version number or a date)
 */
public class InputValidator
{
    private int MAX_STRING_LENGTH = 50;

    /**
     * Constructor for objects of class InputValidator
     */
    public InputValidator()
    {

    }

    /**
     * Checks if the parameter String can be converted to
     * a boolean value.
     * @return only returns true if the parameter String can be converted to Boolean
     */
    public boolean checkValidBoolean(String input)
    {
        boolean success = false;
        if(input.equals("true") || input.equals("false"))
        {
            success = true;
        }
        else
        {
            success = false;
        }
        return success;
    } 

    /**
     * Checks if the parameter String is in the correct
     * format as a date. The criteria is that the String
     * has to be "reasonable" and a correct format with
     * "day.month.year".
     * 
     * @return will return true only if all the criterias are met.
     */
    public boolean checkValidDate(String input)
    {
        boolean success = false;
        if((input.isEmpty() || input.length() > MAX_STRING_LENGTH))
        {
            return false;
        }
        if(isDateCorrectFormat(input))
        {
            success = true;
        }
        else
        {
            success = false;
        }
        return success;
    }

    public static boolean isDateCorrectFormat(String inDate) 
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }

    /**
     * Checks if the parameter String is within the set criteria,
     * and returns a boolean value of the result. The criteria
     * in this instance is that String isn't empty, and if the
     * string exceed the MAX_STRING_LENGTH constant.
     * 
     * @return will return true only if the all the criterias
     * for the string is met.
     */
    public boolean checkValid(String input)
    {
        boolean success = false;
        if(!(input.isEmpty() || input.length() > MAX_STRING_LENGTH ))
        {
            success = true;
        }
        else
        {
            success = false;
        }
        return success;
    }

    /**
     * Checks if the parameter String can be used as a Double
     * 
     * @parameter input enter the string to check.
     * @return will only return true if the parameter is possible
     * to convert to a Double.
     */
    public boolean checkValidDouble(String input)
    {
        boolean success = false;
        try
        {
            // Try to convert input to Double
            Double.valueOf(input);
            success = true;
        }
        catch(Exception e)
        {
            success = false;
        }
        return success;
    }
}
