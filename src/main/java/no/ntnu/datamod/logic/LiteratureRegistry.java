package no.ntnu.datamod.logic;

import no.ntnu.datamod.data.Literature;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * The main purpose of this class is to hold the registry of all literature
 * objects, it's also responsible for adding and removing literature to the registry.
 *
 * @author (Markus Randa, Lars Ous, Frode Pedersen)
 * @version (2018.05.04)
 */

public class LiteratureRegistry {

    private ArrayList<Literature> literature;

    /**
     * Constructor of LiteratureRegistry.
     * The only things being created here is the list that holds
     * all literature objects.
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
     *  Removes a literature object from the registry using the object
     *  itself to determine what to delete.
     * @param literatureObject enter the Literature object that is desired to remove.
     * @return returns true only if the object exists in the registry.
     */
    public Boolean removeLiterature(Literature literatureObject) {
        Boolean success = false;
        if(literatureObject != null) {
            literature.remove(literatureObject);
            success = true;
        }
        return success;
    }

    /**
     * Determine whether the given index is valid for the collection.
     * Checks if the index exists in the registry and returns
     * true or false depending on the result.
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
     * Returns the iterator of the registry-collection
     * @return the iterator of the registry-collection
     */
    public Iterator<Literature> getIterator() {
        return this.literature.iterator();
    }

    /**
     * Returns the whole registry as an ArrayList.
     * @return Returns the whole registry as an ArrayList.
     */
    public ArrayList<Literature> getLiterature() {
        return literature;
    }
}

