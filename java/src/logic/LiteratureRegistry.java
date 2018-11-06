package logic;

import entities.Literature;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class manages all the books from our Newspaper stand. The things it can manage
 * are things such as registrering new books, listing books by author/title or all books and
 * removing books.
 * 
 * @author Gruppe 11 Markus Randa, Lars Ous, Frode Pedersen.
 * @version 1.0.0
 */

public class LiteratureRegistry {

    private ArrayList<Literature> literature;

    /**
     * Creates books object.
     */
    public LiteratureRegistry() {
        literature = new ArrayList<>();
    }

    /**
     * Adds a new literature object and stores it in
     * the in the Literature registry's registry.
     */
    public void addLiterature(Literature literateObject) {
        literature.add(literateObject);
    }

    /**
     * Removes a Book from the registry
     */
    public void removeLiterature(int Index) {
        if (indexValid(Index)) {
            literature.remove(Index);
        }
    }

    /**
     * Determine whether the given index is valid for the collection.
     * Print an error message if it is not.
     *
     * @param index The index to be checked.
     * @return true if the index is valid, false otherwise.
     */
    private boolean indexValid(int index) {
        // The return value.
        // Set according to whether the index is valid or not.
        boolean valid;

        if (index < 0) {
            System.out.println("Index cannot be negative: " + index);
            valid = false;
        } else if (index >= literature.size()) {
            System.out.println("Index is too large: " + index);
            valid = false;
        } else {
            valid = true;
        }
        return valid;
    }

    /**
     * Lists book details of searched title.
     *
     * @title The title of the book.
     */
    public String listByTitle(String title) {
        String returnString = "";
        for (Literature literature : this.literature) {
            if (literature.getTitle().toLowerCase().contains(title.toLowerCase())) {
                returnString += (this.literature.indexOf(literature) + " - " + literature.getDetails() + "\n");
            }
        }
        return returnString;
    }

    public String getAllLiteratureInfo(int index) {
        Literature lit = literature.get(index);

        String returnString = lit.getDetails();

        return returnString;
    }

    /**
     * Returns the iterator of the registry-collection
     *
     * @return the iterator of the registry-collection
     */
    public Iterator<Literature> getIterator() {
        return this.literature.iterator();

    }

}

