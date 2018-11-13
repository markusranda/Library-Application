package no.ntnu.datamod.logic;
import no.ntnu.datamod.data.Book;
import no.ntnu.datamod.data.Branch;
import no.ntnu.datamod.data.Loan;
import no.ntnu.datamod.data.User;
import no.ntnu.datamod.facade.LibraryClientFacade;

import java.io.*;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;

public class DatabaseClient implements LibraryClientFacade {

    private InputStream in;
    private OutputStream out;
    private PrintWriter toServer;
    private BufferedReader fromServer;
    private Connection connection;
    private String lastError = null; // Last error message will be stored here

    /**
     * Returns all the books from the table Books as an ArrayList<Book>.
     *
     * @return Returns all the books from the table Books as an ArrayList<Book>.
     */
    public ArrayList<Book> getBooksList() {
        /*
         Todo
         Query the database for all the books in the Books table.
         Then create objects of the type Book, and finally place all of them in
         the list and return it.
          */
        return new ArrayList<>();
    }

    /**
     * Returns all the books from the table Books as an ArrayList<Book>.
     *
     * @return Returns all the books from the table Books as an ArrayList<Book>.
     */
    public ArrayList<User> getUsersList() {
        /*
         Todo
         Query the database for all the User in the User table.
         Then create objects of the type User, and finally place all of them in
         the list and return it.
          */
        return new ArrayList<>();
    }

    /**
     * Returns all the books from the table Books as an ArrayList<Book>.
     *
     * @return Returns all the books from the table Books as an ArrayList<Book>.
     */
    public ArrayList<Branch> getBranchList() {
        /*
         Todo
         Query the database for all the Branch in the Branch table.
         Then create objects of the type Branch, and finally place all of them in
         the list and return it.
          */
        return new ArrayList<>();
    }

    /**
     * Returns all the books from the table Books as an ArrayList<Book>.
     *
     * @return Returns all the books from the table Books as an ArrayList<Book>.
     */
    public ArrayList<Loan> getLoansList() {
        /*
         Todo
         Query the database for all the loans in the Loans table.
         Then create objects of the type loan, and finally place all of them in
         the list and return it.
          */
        return new ArrayList<>();
    }

    /**
     * Adds a new user to the database with the given parameter values
     * defining the user to be added.
     *
     * @param idUser The ID of the user
     * @param fname first name
     * @param lname last name
     * @param address home address
     * @param phone phone number
     * @param email email address
     * @param password password
     * @param usertype the type of user
     */
    public void addUserToDatabase(int idUser, String fname, String lname, String address,
                                  String phone, String email, String password, String usertype) {
        /* todo
           Add the new user to the database using the parameters.
        */
    }

    /**
     * Searches for all books that match the given keyword.
     *
     * @param keyword The given keyword to search with
     */
    public Book findBookMatch(String keyword) {
        /* todo
        Create a method that finds a book with a keyword
        and returns it as a Book.
         */
        return null;
    }

    /**
     * Connect to a chat server.
     *
     * @param host host name or IP address of the chat server
     * @param port TCP port of the chat server
     * @return True on success, false otherwise
     */
    @Override
    public boolean connect(String host, int port) {
        String database = "library_db";
        String connectionString = "jdbc:mysql://"+ host + ":" + port + "/" +
                database + "?user=dbuser&password=password&useUnicode=true&characterEncoding=UTF-8";
        Statement stmt = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            System.out.println("Connecting to database: " + connectionString);
            connection = DriverManager.getConnection(
                    connectionString);
            return true;
        }
        catch (Exception ex) {
            System.out.println("SQLException: " + ex.getMessage());
            return false;
        }
        finally {
            // it is a good idea to release
            // resources in a finally{} block
            // in reverse-order of their creation
            // if they are no-longer needed

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) {
                } // ignore

                rs = null;
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {
                } // ignore

                stmt = null;
            }
        }
    }

    /**
     * Disconnect from the chat server (close the socket)
     */
    @Override
    public void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Return true if the connection is active (opened), false if not.
     *
     * @return Return true if the connection is active (opened), false if not.
     */
    @Override
    public boolean isConnectionActive() {
        return connection != null;
    }

    /**
     * Returns the last error message from the database
     * if there has been an error sent from the database,
     * otherwise returns null.
     *
     * @return Returns the last error message from the database,
     * otherwise returns null.
     */
    @Override
    public String getLastError() {
        if (lastError != null) {
            return lastError;
        } else {
            return null;
        }
    }
}