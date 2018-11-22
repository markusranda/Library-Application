package no.ntnu.datamod.logic;
import no.ntnu.datamod.data.Book;
import no.ntnu.datamod.data.Branch;
import no.ntnu.datamod.data.Loan;
import no.ntnu.datamod.data.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseClient {

    private String host = "192.168.50.50";
    private int port = 3306;
    private String database = "library_db";


    /**
     * Returns all the books from the table Book as an ArrayList<Book>.
     *
     * @return Returns all the books from the table Book as an ArrayList<Book>.
     */
    public ArrayList<Book> getBooksList()  throws SQLException {
            DatabaseConnection connector = new DatabaseConnection(host, port, database);
            Connection connection = connector.getConnection();

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
            connection.close();
            return rowList;
    }

    /**
     * Returns all the users from the table Users as an ArrayList<User>.
     *
     * @return Returns all the users from the table Book as an ArrayList<User>.
     */
    public ArrayList<User> getUsersList() throws SQLException {
        DatabaseConnection connector = new DatabaseConnection(host, port, database);
        Connection connection = connector.getConnection();

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
        connection.close();
        return rowList;
    }

    /**
     * Returns all the branches from the table Branches as an ArrayList<Branch>.
     *
     * @return Returns all the branches from the table Book as an ArrayList<Branch>.
     */
    public ArrayList<Branch> getBranchList() throws SQLException {
        DatabaseConnection connector = new DatabaseConnection(host, port, database);
        Connection connection = connector.getConnection();

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
        connection.close();
        return rowList;
    }

    /**
     * Returns all the loans from the table Loans as an ArrayList<Loan>.
     *
     * @return Returns all the loans from the table Book as an ArrayList<Loan>.
     */
    public ArrayList<Loan> getLoansList() throws SQLException{
        DatabaseConnection connector = new DatabaseConnection(host, port, database);
        Connection connection = connector.getConnection();

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
        connection.close();
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
     * Returns the quantity of a specific Book, in a specific Branch.
     *
     * @param bookID bookID
     * @param branchID branchID
     * @return Returns the quantity of a specific Book, in a specific Branch. Returns 0 if an Exception occurs.
     */
    public int getQuantity(long bookID, long branchID) {
        try {
            DatabaseConnection connector = new DatabaseConnection(host, port, database);
            Connection connection = connector.getConnection();
            int size = 0;

            String fullCommand =
                    "SELECT quantity " +
                    "FROM Book_Quantity " +
                    "WHERE idBook = " + bookID + " AND idBranch = " + branchID + ";";

            Statement stm = connection.createStatement();

            // Query
            ResultSet result;
            boolean returningRows = stm.execute(fullCommand);
            if (returningRows)
                result = stm.getResultSet();
            else
                throw new SQLException("There are no results from the given query \n");

            if(result.next()) {
                size = result.getInt(1);
            }
            connection.close();
            return size;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        }

    /**
     * Adds a new user to the database with the given parameter values
     * defining the user to be added.
     *
     *
     *
     * @param password password
     * @param usertype the type of user
     * @param username username
     * @return Returns number of rows affected.
     */
    public int addUserToDatabase(String username, String password, String usertype) throws SQLException {
        DatabaseConnection connector = new DatabaseConnection(host, port, database);
        Connection connection = connector.getConnection();

        try {
            String fullCommand = "INSERT INTO Users (username, password, usertype) VALUES" +
                    "( '" +
                    username + "', '" +
                    password + "', '" +
                    usertype + "' );";


            // Create statement
            Statement stm = null;
            stm = connection.createStatement();

            // Query
            int execution = stm.executeUpdate(fullCommand);
            connection.close();
            return execution;
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            connection.close();
            return 0;
        }
    }

    /**
     *
     * @param title Book title
     * @param publisher Book publisher
     * @param ISBN Book ISBN
     * @param image Book image???
     * @return Number of edited rows
     * @throws SQLException
     */
    public int addBookToDatabase(String title, String publisher, String ISBN, String image) throws SQLException{
        DatabaseConnection connector = new DatabaseConnection(host, port, database);
        Connection connection = connector.getConnection();

        try {
            String fullCommand = "INSERT INTO Books (title, publisher, ISBN, image) VALUES ('" +
                    title + "', '" +
                    publisher + "', '" +
                    ISBN + "', '" +
                    image + "');";

            // Create statement
            Statement stm = null;
            stm = connection.createStatement();

            // Query
            int execution = stm.executeUpdate(fullCommand);
            connection.close();
            return execution;
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            connection.close();
            return 0;
        }
    }

    /**
     *
     * @param title Book title
     * @param publisher Book publisher
     * @return Number of edited rows
     * @throws SQLException
     */
    public int addBookToDatabase(String title, String publisher) throws SQLException{
        DatabaseConnection connector = new DatabaseConnection(host, port, database);
        Connection connection = connector.getConnection();

        try {
            String fullCommand = "INSERT INTO Books (title, publisher, ISBN, image) VALUES ('" +
                    title + "', '" +
                    publisher + "', " +
                    null + ", " +
                    null + ");";

            // Create statement
            Statement stm = null;
            stm = connection.createStatement();

            // Query
            int execution = stm.executeUpdate(fullCommand);
            connection.close();
            return execution;
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            connection.close();
            return 0;
        }
    }

    /**
     *
     * @param idBook The book ID of the book that is borrowed.
     * @param idUser The borrowers user ID.
     * @return Number of edited rows in database.
     * @throws SQLException
     */
    public int addLoanToDatabase(long idBook, long idUser) throws SQLException{
            DatabaseConnection connector = new DatabaseConnection(host, port, database);
            Connection connection = connector.getConnection();

            try {
                String loanDate = "CURDATE()";
                String loanDue = "DATE_ADD(CURDATE(), INTERVAL 2 MONTH)";

                String fullCommand = "INSERT INTO Loans (loanDate, loanDue, idBook, idUser) VALUES (" +
                        loanDate + ", " +
                        loanDue + ", " +
                        idBook + ", " +
                        idUser + ");";



                // Create statement
                Statement stm = null;
                stm = connection.createStatement();

                // Query
                int execution = stm.executeUpdate(fullCommand);
                connection.close();
                return execution;
            }catch (Exception ex){
                System.out.println(ex.getMessage());
                connection.close();
                return 0;
            }
    }


    public int removeBookFromDatabase(long idBook) throws SQLException{
        DatabaseConnection connector = new DatabaseConnection(host, port, database);
        Connection connection = connector.getConnection();

        try {
            String fullCommand = "DELETE FROM Books WHERE idBook = " + idBook + ";";

            // Create statement
            Statement stm = null;
            stm = connection.createStatement();

            // Query
            int execution = stm.executeUpdate(fullCommand);
            connection.close();
            return execution;
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            connection.close();
            return 0;
        }
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

}