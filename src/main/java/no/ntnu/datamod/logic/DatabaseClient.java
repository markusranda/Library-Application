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
    public ArrayList<Book> getBooksList()  throws SQLException {
        String fullCommand = "SELECT * FROM Books";
        ArrayList<Book> rowList = new ArrayList<>();
        Statement stm;

        // Create statement
        stm = connection.createStatement();

        // Query
        ResultSet result;
        boolean returningRows = stm.execute(fullCommand);
        if (returningRows)
            result = stm.getResultSet();
        else
            throw new SQLException("There are no results from the given query \n");

        ArrayList<HashMap<String,Object>> rows = createObjectList(result);

        // Uses the previously created HashMap to match key for value
        // and creates the required object. And puts em all into a list.
        for (HashMap<String, Object> row : rows) {
            String publisher = (String) row.get("publisher");
            String title = (String) row.get("title");
            long idBook = (int) row.get("idBook");
            String authors = (String) row.get("authors");
            String isbn = (String) row.get("isbn");
            String image = (String) row.get("image");

            Book book = new Book(publisher, title, idBook, authors, isbn, image);
            rowList.add(book);
        }

        // Closes the statement
        stm.close();
        return rowList;
    }

    /**
     * Returns all the users from the table Users as an ArrayList<User>.
     *
     * @return Returns all the users from the table Book as an ArrayList<User>.
     */
    public ArrayList<User> getUsersList() throws SQLException {
        String fullCommand = "SELECT * FROM Users";
        ArrayList<User> rowList = new ArrayList<>();
        Statement stm;

        // Create statement
        stm = connection.createStatement();

        // Query
        ResultSet result;
        boolean returningRows = stm.execute(fullCommand);
        if (returningRows)
            result = stm.getResultSet();
        else
            throw new SQLException("There are no results from the given query \n");

        ArrayList<HashMap<String,Object>> rows = createObjectList(result);

        // Uses the previously created HashMap to match key for value
        // and creates the required object. And puts em all into a list.
        for (HashMap<String, Object> row : rows) {
            long idUser = (int) row.get("idUser");
            String username = (String) row.get("username");
            String password = (String) row.get("password");
            String usertype = (String)row.get("usertype");
            User user = new User(idUser, username, password, usertype);
            rowList.add(user);
        }

        // Closes the statement
        stm.close();
        return rowList;
    }

    /**
     * Returns all the branches from the table Branches as an ArrayList<Branch>.
     *
     * @return Returns all the branches from the table Book as an ArrayList<Branch>.
     */
    public ArrayList<Branch> getBranchList() throws SQLException {
        String fullCommand = "SELECT * FROM Branches";
        ArrayList<Branch> rowList = new ArrayList<>();
        Statement stm;

            // Create statement
            stm = connection.createStatement();

            // Query
            ResultSet result;
            boolean returningRows = stm.execute(fullCommand);
            if (returningRows)
                result = stm.getResultSet();
            else
                throw new SQLException("There are no results from the given query \n");

            ArrayList<HashMap<String,Object>> rows = createObjectList(result);

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
    }

    /**
     * Returns all the loans from the table Loans as an ArrayList<Loan>.
     *
     * @return Returns all the loans from the table Book as an ArrayList<Loan>.
     */
    public ArrayList<Loan> getLoansList() throws SQLException{
        String fullCommand = "SELECT * FROM Loans";
        ArrayList<Loan> rowList = new ArrayList<>();
        Statement stm;

        // Create statement
        stm = connection.createStatement();

        // Query
        ResultSet result;
        boolean returningRows = stm.execute(fullCommand);
        if (returningRows)
            result = stm.getResultSet();
        else
            throw new SQLException("There are no results from the given query \n");

        ArrayList<HashMap<String,Object>> rows = createObjectList(result);

        // Uses the previously created HashMap to match key for value
        // and creates the required object. And puts em all into a list.
        for (HashMap<String, Object> row : rows) {
            long idLoans = (int) row.get("idLoans");
            java.sql.Date loanDate = (java.sql.Date) row.get("loanDate");
            java.sql.Date loanDue = (java.sql.Date) row.get("loanDate");
            long idBook = (int) row.get("idBook");
            long idUser = (int) row.get("idUser");

            Loan loan = new Loan(idLoans, loanDate, loanDue, idBook, idUser);
            rowList.add(loan);
        }

        // Closes the statement
        stm.close();
        return rowList;
    }

    /**
     * Creates an ArrayList with a HashMap that contains mappings
     * to each of the field's values.
     * @param result the ResultSet
     * @return Returns an ArrayList with HashMap that contains mappings
     *      to each of the field's values.
     * @throws SQLException todo
     */
    private ArrayList<HashMap<String,Object>> createObjectList(ResultSet result) throws SQLException {

        ArrayList<HashMap<String, Object>> rows = new ArrayList<>();

        // Get metadata
        ResultSetMetaData meta ;
        meta = result.getMetaData();

        // Get column names
        int colCount = meta.getColumnCount();
        ArrayList<String> cols = new ArrayList<>();
        for (int index=1; index <= colCount; index++)
            cols.add(meta.getColumnName(index));

        while (result.next()) {
            HashMap<String,Object> row = new HashMap<>();
            for (String colName:cols) {
                Object val = result.getObject(colName);
                row.put(colName,val);
            }
            rows.add(row);
        }

        return rows;
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