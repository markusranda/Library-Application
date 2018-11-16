package no.ntnu.datamod.logic;
import com.sun.xml.internal.bind.v2.TODO;
import no.ntnu.datamod.data.Book;
import no.ntnu.datamod.data.Branch;
import no.ntnu.datamod.data.Loan;
import no.ntnu.datamod.data.User;
import no.ntnu.datamod.facade.LibraryClientFacade;

import javax.security.auth.login.Configuration;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseClient implements LibraryClientFacade {

    private Connection connection;
    private String lastError = null;

    /**
     * Returns all the books from the table Book as an ArrayList<Book>.
     *
     * @return Returns all the books from the table Book as an ArrayList<Book>.
     */
    public ArrayList<Book> getBooksList() {


        /*
         Todo
         Query the database for all the books in the Book table.
         Then create objects of the type Book, and finally place all of them in
         the list and return it.
          */
        return new ArrayList<>();
    }

    /**
     * Returns all the books from the table Book as an ArrayList<Book>.
     *
     * @return Returns all the books from the table Book as an ArrayList<Book>.
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
     * Returns all the books from the table Book as an ArrayList<Book>.
     *
     * @return Returns all the books from the table Book as an ArrayList<Book>.
     */
    public ArrayList<Branch> getBranchList() {
        String fullCommand = "SELECT * FROM Branches";
        ArrayList<HashMap<String,Object>> rows = new ArrayList<>();
        ArrayList<Branch> rowList = new ArrayList<>();
        Statement stm = null;
        try {
            // Create statement
            stm = connection.createStatement();

            // Query
            ResultSet result = null;
            boolean returningRows = stm.execute(fullCommand);
            if (returningRows)
                result = stm.getResultSet();
            else
                // Returns a empty ArrayList if can't execute
                return new ArrayList<>();

            // Get metadata
            ResultSetMetaData meta = null;
            meta = result.getMetaData();

            // Get column names
            int colCount = meta.getColumnCount();
            ArrayList<String> cols = new ArrayList<>();
            for (int index=1; index <= colCount; index++)
                cols.add(meta.getColumnName(index));

            // Creates an ArrayList with a HashMap that contains mappings
            // to each of the fields values.
            while (result.next()) {
                HashMap<String,Object> row = new HashMap<>();
                for (String colName:cols) {
                    Object val = result.getObject(colName);
                    row.put(colName,val);
                }
                rows.add(row);
            }

            // Uses the previously created HashMap to match key for value
            // and creates the required object. And puts em all into a list.
            for (HashMap<String, Object> row : rows) {
                long idBranch = (int) row.get("idBranch");
                String name = (String) row.get("name");
                String address = (String) row.get("address");
                Branch branch = new Branch(idBranch, name, address);
                rowList.add(branch);
            }

            // Closes the statement
            stm.close();
            return rowList;

        } catch (Exception ex) {
            System.out.print(ex.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Returns all the books from the table Book as an ArrayList<Book>.
     *
     * @return Returns all the books from the table Book as an ArrayList<Book>.
     */
    public ArrayList<Loan> getLoansList() {
        /*
         Todo
         Query the database for all the loans in the Loan table.
         Then create objects of the type loan, and finally place all of them in
         the list and return it.
          */
        return new ArrayList<>();
    }

    /**
     * Adds a new user to the database with the given parameter values
     * defining the user to be added.
     *
     *
     *
     * @param idUser The ID of the user
     * @param password password
     * @param usertype the type of user
     * @param username username
     *
     * @return Returns number of rows affected.
     */
    public int addUserToDatabase(long idUser, String username, String password, String usertype) {
        // TODO: 16.11.2018 addUserToDatabase needs idEmploee and idCustomer to work

        try {
            String fullCommand = "INSERT INTO Users (idUser, username, password, usertype) VALUES" +
                    "( " +
                    idUser + ", '" +
                    username + "', '" +
                    password + "', '" +
                    usertype + "' )";


            PreparedStatement preparedStatement = connection.prepareStatement(fullCommand);

            // Create statement
            Statement stm = null;
            stm = connection.createStatement();

            // Query
            ResultSet result = null;
            return stm.executeUpdate(fullCommand);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }


        return 0;
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
     * An example on how to do a query and retrieve an entire "table" from the database which can be parsed further.
     *
     * @param fullCommand The SQL statement
     * @return Returns a "table" as a result of the SQL statement
     */
    public ArrayList<HashMap<String,Object>> rawQuery(String fullCommand) {
        try {

            // Create statement
            Statement stm = null;
            stm = connection.createStatement();

            // Query
            ResultSet result = null;
            boolean returningRows = stm.execute(fullCommand);
            if (returningRows)
                result = stm.getResultSet();
            else
                // Returns a empty ArrayList if can't execute
                return new ArrayList<>();

            // Get metadata
            ResultSetMetaData meta = null;
            meta = result.getMetaData();

            // Get column names
            int colCount = meta.getColumnCount();
            ArrayList<String> cols = new ArrayList<>();
            for (int index=1; index <= colCount; index++)
                cols.add(meta.getColumnName(index));

            // Fetch out rows
            ArrayList<HashMap<String,Object>> rows =
                    new ArrayList<>();


            while (result.next()) {
                HashMap<String,Object> row = new HashMap<>();
                for (String colName:cols) {
                    Object val = result.getObject(colName);
                    row.put(colName,val);
                }
                rows.add(row);
            }

            //close statement
            stm.close();

            //pass back rows
            return rows;
        }
        catch (Exception ex) {
            System.out.print(ex.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Connect to a chat server.
     *
     * @param host host name or IP address of the chat server
     * @param port TCP port of the chat server
     * @return True on success, false otherwise
     */
    @Override
    public boolean connect(String host, int port, String database) {
        String connectionString = "jdbc:mysql://"+ host + ":" + port + "/" +
                database + "?user=dbuser&password=password&useUnicode=true&characterEncoding=UTF-8";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            System.out.println("Connecting to database: " + database);
            connection = DriverManager.getConnection(
                    connectionString);
            ArrayList<Branch> branches =  getBranchList();
            // TODO: 16.11.2018 addUserToDatabase is called here for testing purposes. remove when done!
            addUserToDatabase(1234,"Arneboii", "ziudshfireq","Sjef");
            return true;
        } catch (Exception ex) {
            System.out.println("SQLException: " + ex.getMessage());
            return false;
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