package ui;

import logic.LiteratureRegistry;
import entities.Book;
import entities.Newspaper;
import entities.Magazine;
import entities.Literature;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Iterator;

/**
 * Makes up the user interface (text based) of the application.
 * Is responsible for all interaction with user, like displaying the
 * menu/litterature information and receiving input from the user.
 * 
 * @author Gruppe 11 Markus Randa, Lars Ous, Frode Pedersen.
 * @version 1.0
 */
public class ApplicationUI 
{
    private LiteratureRegistry registry;
    private InputValidator validator;

    /* 
     *  A String array that holds all the menu choices
     *  that the user can choose from.
     */
    private String[] menuItems = 
        {
            "1. List all products",
            "2. Add new product",
            "3. Find a product by name",
            "4. Delete a book by index",
        };

    /**
     * Creates an instance of the ApplicationUI User interface. 
     */
    public ApplicationUI() 
    {
        registry = new LiteratureRegistry();
        validator = new InputValidator();
        fillLitteratureRegistry();
    }

    /**
     * Starts the application by showing the menu and retrieving input from the
     * user.
     */
    public void start() 
    {
        System.out.print('\u000C');
        boolean quit = false;

        while (!quit) 
        {
            try 
            {
                int menuSelection = this.showMenu();
                switch (menuSelection) 
                {
                    case 1: // List all products
                    listAllProducts();
                    break;

                    case 2: // Add new product
                    addNewProduct();
                    break;

                    case 3: // Find a product by name
                    findProductByTitle();
                    break;

                    case 4: // Delete a book from the system
                    removeProduct();
                    break;

                    case 5: // Quit the application
                    System.out.println("\nThank you for using the Aviskiosk application. Bye!\n");
                    quit = true;
                    break;

                    default:
                }
            } 

            catch (InputMismatchException ime)
            {
                System.out.println("\nERROR: Please provide a number between 1 and " 
                    + (this.menuItems.length + 1) + "..\n");
            }
        }        
    }

    /**
     * Displays the menu to the user, and waits for the users input. The user is
     * expected to input an integer between 1 and the max number of menu items. 
     * If the user inputs anything else, an InputMismatchException is thrown. 
     * The method returns the valid input from the user.
     *
     * @return the menu number (between 1 and max menu item number) provided by the user.
     * @throws InputMismatchException if user enters an invalid number/menu choice
     */
    private int showMenu() throws InputMismatchException 
    {
        System.out.println("\n**** Aviskiosk ****\n");
        // Display the menu

        for ( String menuItem : menuItems )
        {
            System.out.println(menuItem);
        }
        int maxMenuItemNumber = menuItems.length + 1;
        // Add the "Exit"-choice to the menu
        System.out.println(maxMenuItemNumber + ". Exit\n");
        System.out.println("Please choose menu item (1-" + maxMenuItemNumber + "): ");
        // Read input from user
        Scanner reader = new Scanner(System.in);
        int menuSelection = reader.nextInt();
        if ((menuSelection < 1) || (menuSelection > maxMenuItemNumber)) 
        {
            throw new InputMismatchException();
        }
        return menuSelection;
    }

    /**
     * 
     */
    private int showSubMenu(String[] subMenuItems) throws InputMismatchException 
    {
        //System.out.println("\n**** Aviskiosk ****\n");
        // Display the menu
        for ( String subMenuItem : subMenuItems )
        {
            System.out.println(subMenuItem);
        }
        int maxMenuItemNumber = subMenuItems.length;
        // Add the "Exit"-choice to the menu
        //System.out.println(maxMenuItemNumber + ". Exit\n");
        System.out.println("Please choose menu item (1-" + maxMenuItemNumber + "): ");
        // Read input from user
        Scanner reader = new Scanner(System.in);
        int subMenuSelection = reader.nextInt();
        if ((subMenuSelection < 1) || (subMenuSelection > maxMenuItemNumber)) 
        {
            throw new InputMismatchException();
        }
        return subMenuSelection;
    }

    // ------ The methods below this line are "helper"-methods, used from the menu ----
    // ------ All these methods are made private, since they are only used by the menu ---

    /**
     * Prints out a list of all the available products
     * that the registry currently is holding.
     */
    private void listAllProducts()
    {
        String[] subMenuItems = 
            {
                "1. List all books",
                "2. List all magazines",
                "3. List all newspapers",
                "4. List everything"
            };
        int choice = this.showSubMenu(subMenuItems);
        try
        {
            switch (choice) 
            {
                case 1: // books
                {
                    listAllLiterature(Book.class);
                    break;
                }
                case 2: // magazines
                {
                    listAllLiterature(Magazine.class);
                    break;
                }
                case 3: // newspapers
                {
                    listAllLiterature(Newspaper.class);
                    break;
                }

                case 4: //everything
                {
                    listAllLiterature();
                    break;
                }

                default:
            }
        }
        catch (InputMismatchException ime)
        {
            System.out.println("\nERROR: Please provide a number between 1 and " 
                + (subMenuItems.length + 1) + "..\n");
        }
    }

    /**
     * List the details of all the available literature
     */
    private void listAllLiterature()
    {
        Iterator<Literature> it = this.registry.getIterator();
        while(it.hasNext())
        {
            Literature lit = it.next();
            System.out.println(lit.getDetails());
        }
    }
    
    /**
     * List the details of all the literature of the desired type
     * @desiredType The desired type of literature
     */
    private void listAllLiterature(Class desiredType)
    {
        Iterator<Literature> it = this.registry.getIterator();
        while(it.hasNext())
        {
            Literature lit = it.next();
            if (lit.getClass().equals(desiredType))
            {
                System.out.println(lit.getDetails());
            }
        }
    }

    /**
     * Adds multiple products to the registry, this is
     * mainly used for testing purposes to actually have.
     */

    private void fillLitteratureRegistry()
    {
        fillRegistryWithMagazines();
        fillRegistryWithNewspapers();
    }

    private void fillRegistryWithMagazines()
    {
        registry.addLiterature
        (new Magazine("T&T", "Teknikk og triks", 55.0, "19.19.2018", 45));
        registry.addLiterature
        (new Magazine("R&R", "Roing eller Kano", 55.0, "19.19.2018", 45));
    }

    private void fillRegistryWithNewspapers()
    {
        registry.addLiterature
        (new Newspaper("Troste AS", "Trosteredaksjonen", 206.0, "19.03.2018", 78, false));
        registry.addLiterature
        (new Newspaper("Fræna AS", "Romsdals budstikke", 66.0, "19.09.2018", 11, true));
    }

    private void addNewProduct()
    {
        String[] subMenuItems = 
            {
                "1. Add new book",
                "2. Add new magazine",
                "3. Add new newspaper",
            };

        int choice = showSubMenu(subMenuItems);
        try
        {
            switch (choice)
            {
                case 1: // Books
                {
                    addNewBook();
                    break;
                }

                case 2: // Magazine
                {
                    addNewMagazine();
                    break;
                }

                case 3: // Newspaper
                {
                    addNewNewspaper();
                    break;
                }
            }
        }
        catch (InputMismatchException ime)
        {
            System.out.println("\nERROR: Please provide a number between 1 and " 
                + (subMenuItems.length + 1) + "..\n");
        }
    }

    /**
     * Add a new Book to the register.
     * The user will get asked to input the title, author,
     * publisher, edition and price for a product. If the user
     * inputs values that are unrealistic or wrong, the method
     * catches the errors and tells the user to try again.
     */
    private void addNewBook()
    {        
        // Opprett et objekt av Scanner
        Scanner reader = new Scanner(System.in);
        // Start med å skrive inn et navn
        int stage = 1;

        // Opprett variabler for å huske input
        String publisher = null;
        String title = null;
        String author = null;
        String edition = null;
        String testPrice = null;
        Double price = 0.0;

        while (stage == 1)
        {
            // Be om å få utgiver på boka fra brukern
            System.out.print("Please enter the name of the publisher of the book: ");
            publisher = reader.nextLine();
            if(validator.checkValid(publisher))
            {
                stage = 2;
            }
            else 
            {
                System.out.println("Please enter a valid name");
                reader.reset();
            }
        }

        while (stage == 2)
        {
            System.out.print("Please enter the title of the book: ");
            title = reader.nextLine();
            if(validator.checkValid(title))
            {
                stage = 3;
            }
            else 
            {
                System.out.println("Please enter a valid name");
                reader.reset();
            }            
        }

        while (stage == 3)
        {
            System.out.print("Please enter the name of the author of the book: ");
            author = reader.nextLine();
            if(validator.checkValid(author))
            {
                stage = 4;
            }     
            else 
            {
                System.out.println("Please enter a valid name");
                reader.reset();
            }            
        }

        while (stage == 4)
        {
            System.out.print("Please enter the edition of the book: ");
            edition = reader.nextLine();
            if(validator.checkValid(edition))
            {
                stage = 5;
            }
            else 
            {
                System.out.println("Please enter a valid edition");
                reader.reset();
            }            
        }

        while (stage == 5)
        {
            System.out.print("Please enter the price of the book: ");
            testPrice = reader.nextLine();
            //the String price can be converted to a double
            if(validator.checkValidDouble(testPrice))
            {
                price = Double.valueOf(testPrice);
                stage = 6;
            }
            // if the String price can't be converted to a double
            else
            {
                System.out.println("Please enter a valid price");
                reader.reset();
            }
        }

        if (stage == 6)
        {
            registry.addLiterature(new Book(publisher, title, price, author, edition));
            System.out.println("Congratulations! You have successfully added the book");
        }
    }

    /**
     * Add a new Book to the register.
     * The user will get asked to input the title, author,
     * publisher, edition and price for a product. If the user
     * inputs values that are unrealistic or wrong, the method
     * catches the errors and tells the user to try again.
     */
    private void addNewNewspaper()
    {        
        // Opprett et objekt av Scanner
        Scanner reader = new Scanner(System.in);
        // Start med å skrive inn et navn
        int stage = 1;
        // Opprett variabler for å huske input
        String publisher = null;
        String title = null;
        String date = null;
        String testPages = null;
        int pages = 0;
        String testPrice = null;
        Double price = 0.0;
        boolean includesComics = false;
        String testIncludesComics = null;

        while (stage == 1)
        {
            System.out.print("Please enter the name of the publisher of the newspaper: ");
            publisher = reader.nextLine();
            if(validator.checkValid(publisher))
            {
                stage = 2;
            }
            else 
            {
                System.out.println("Please enter a valid name");
                reader.reset();
            }
        }

        while (stage == 2)
        {
            System.out.print("Please enter the title of the newspaper: ");
            title = reader.nextLine();
            if(validator.checkValid(title))
            {
                stage = 3;
            }
            else 
            {
                System.out.println("Please enter a valid name");
                reader.reset();
            }            
        }

        while (stage == 3)
        {
            System.out.print("Please enter the date of the newspaper" 
                + "in the following format 'dd.MM.yyyy': ");
            date = reader.nextLine();
            if(validator.checkValidDate(date))
            {
                stage = 4;
            }     
            else 
            {
                System.out.println("Please enter a valid date");
                reader.reset();
            }            
        }

        while (stage == 4)
        {
            System.out.print("Please enter the number of pages in the newspaper: ");
            testPages = reader.nextLine();
            if(validator.checkValid(testPages))
            {
                pages = Integer.valueOf(testPages);
                stage = 5;
            }
            else 
            {
                System.out.println("Please enter a valid number of pages");
                reader.reset();
            }            
        }

        while (stage == 5)
        {
            System.out.print("Please enter the price of the newspaper: ");
            testPrice = reader.nextLine();
            //the String price can be converted to a double
            if(validator.checkValidDouble(testPrice))
            {
                price = Double.valueOf(testPrice);
                stage = 6;
            }
            // if the String price can't be converted to a double
            else
            {
                System.out.println("Please enter a valid price");
                reader.reset();
            }
        }
        
        while (stage == 6)
        {
            System.out.print("Does the newspaper contain comics?"
            + "\n" + "in the following format 'true' or 'false'");
            testIncludesComics = reader.nextLine();
            //the String price can be converted to a double
            if(validator.checkValidBoolean(testIncludesComics))
            {
                includesComics = Boolean.valueOf(testIncludesComics);
                stage = 7;
            }
            // if the String price can't be converted to a double
            else
            {
                System.out.println("Please enter 'true' or 'false'");
                reader.reset();
            }
        }

        if (stage == 7)
        {
            registry.addLiterature(new Newspaper(publisher, title, price, date, pages, includesComics));
            System.out.println("Congratulations! You have successfully added the newspaper");
        }
    }

    /**
     * Add a new Book to the register.
     * The user will get asked to input the title, author,
     * publisher, edition and price for a product. If the user
     * inputs values that are unrealistic or wrong, the method
     * catches the errors and tells the user to try again.
     */
    private void addNewMagazine()
    {        
        // Opprett et objekt av Scanner
        Scanner reader = new Scanner(System.in);
        // Start med å skrive inn et navn
        int stage = 1;
        // Opprett variabler for å huske input
        String publisher = null;
        String title = null;
        String date = null;
        String testPages = null;
        int pages = 0;
        String testPrice = null;
        Double price = 0.0;

        while (stage == 1)
        {
            System.out.print("Please enter the name of the publisher of the magazine: ");
            publisher = reader.nextLine();
            if(validator.checkValid(publisher))
            {
                stage = 2;
            }
            else 
            {
                System.out.println("Please enter a valid name");
                reader.reset();
            }
        }

        while (stage == 2)
        {
            System.out.print("Please enter the title of the magazine: ");
            title = reader.nextLine();
            if(validator.checkValid(title))
            {
                stage = 3;
            }
            else 
            {
                System.out.println("Please enter a valid name");
                reader.reset();
            }            
        }

        while (stage == 3)
        {
            System.out.print("Please enter the date of the magazine" 
                + "\n" + "in the following format '12.24.2000': ");
            date = reader.nextLine();
            if(validator.checkValidDate(date))
            {
                stage = 4;
            }     
            else 
            {
                System.out.println("Please enter a valid date");
                reader.reset();
            }            
        }

        while (stage == 4)
        {
            System.out.print("Please enter the number of pages in the magazine: ");
            testPages = reader.nextLine();
            if(validator.checkValid(testPages))
            {
                pages = Integer.valueOf(testPages);
                stage = 5;
            }
            else 
            {
                System.out.println("Please enter a valid number of pages");
                reader.reset();
            }            
        }

        while (stage == 5)
        {
            System.out.print("Please enter the price of the magazine: ");
            testPrice = reader.nextLine();
            //the String price can be converted to a double
            if(validator.checkValidDouble(testPrice))
            {
                price = Double.valueOf(testPrice);
                stage = 6;
            }
            // if the String price can't be converted to a double
            else
            {
                System.out.println("Please enter a valid price");
                reader.reset();
            }
        }

        if (stage == 6)
        {
            registry.addLiterature(new Magazine(publisher, title, price, date, pages));
            System.out.println("Congratulations! You have successfully added the magazine");
        }
    }

    /**
     * Removes the specified book using the index.
     * The index would usually be found with either using a search-function,
     * or the user knows before-hand what index the book is saved at.
     */

    private void removeProduct()
    {
        // Opprett et objekt av Scanner
        Scanner reader = new Scanner(System.in);

        // Be om å få indeksen på boka fra brukern
        System.out.print("Please enter the index of the literature: ");
        // Husk indeks
        int index = reader.nextInt();
        reader.nextLine();

        // Spør om bruker er sikker på sletting
        System.out.println("Are you sure you want to delete: " + registry.getAllLiteratureInfo(index) +"?");

        System.out.println("Enter y/n to confirm or cancel deletetion");
        String answer = reader.nextLine();

        if(answer.contains("y"))
        {
            registry.removeLiterature(index);
            System.out.println("The object has been deleted");
        }

        if(answer.contains("n"))
        {
            System.out.println("The operation has been canceled");
        }
        else
        {
            System.out.println("Please enter either y or n");
        }
    }

    /**
     * Lists all products that has a "hit" with
     * the inputted searchword by user. The searchword
     * in this case would be the products title.
     */

    private void findProductByTitle()
    {
        // Opprett et objekt av Scanner
        Scanner reader = new Scanner(System.in);

        // Be om å få utgiver på boka fra brukern
        System.out.print("Please enter the title of the book: ");
        // Husk utgiver
        String title = reader.nextLine();

        System.out.println(registry.listByTitle(title));
    }
}
