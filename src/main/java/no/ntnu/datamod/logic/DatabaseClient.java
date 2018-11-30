package no.ntnu.datamod.logic;
import no.ntnu.datamod.data.*;
import no.ntnu.datamod.gui.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DatabaseClient {

    private String host = "192.168.50.50";
    private int port = 3306;
    private String database = "library_db";


    /**
     * Retrieves the first- and last name of a specific username.
     *
     * @return Returns the first- and last name of a person if and only if there is a currently logged on user,
     * returns null otherwise.
     */
    public String getNameOfCurrentUser(){
        String name = null;

        // Retrieve the username of currently logged on user
        String username = Model.getInstance().currentUser().getUsername();

        try {
            DatabaseConnection connector = new DatabaseConnection(host, port, database);
            Connection connection = connector.getConnection();

            Statement stm = connection.createStatement();


            String queryCustomer = "SELECT * FROM Customer_Users " +
                    "WHERE username = '" + username + "' ;";

            String queryEmployee = "SELECT * FROM Employee_Users " +
                    "WHERE username = '" + username + "' ;";


            if ( resultSetHasRows(queryCustomer, stm) ) {

                String queryName = "SELECT CONCAT(C.fname, ' ', C.lname) as name\n" +
                        "FROM Customer_Users CU JOIN Customer C on CU.idCustomer = C.idCustomer\n" +
                        "WHERE CU.username = '" + username + "';";

                ResultSet resultSet = stm.executeQuery(queryName);

                if(resultSet.last()){
                    resultSet = stm.getResultSet();
                    name = resultSet.getString(1);
                }
            }

            else if ( resultSetHasRows(queryEmployee, stm) ) {

                String queryName = "SELECT CONCAT(E.fname, ' ', E.lname) as name\n" +
                        "FROM Employee_Users EU JOIN Employee E on EU.idEmployee = E.idEmployee\n" +
                        "WHERE EU.username = '" + username + "';";


                ResultSet resultSet = stm.executeQuery(queryName);

                if(resultSet.last()){
                    resultSet = stm.getResultSet();
                    name = resultSet.getString(1);
                }
            }
            else {
                throw new SQLException("No results from query.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return name;
    }

    /**
     * Returns true if and only if the ResultSet returns any rows, otherwise returns false.
     *
     * @param query Query
     * @param stm Statement
     * @return Returns true if and only if the ResultSet returns any rows, otherwise returns false.
     */
    private boolean resultSetHasRows(String query, Statement stm){
        
        try {
            boolean result = false;
            stm.execute(query);
            ResultSet resultSet = stm.getResultSet();
            resultSet.last();

            if ( !(resultSet.getRow() == 0) ) {
                result = true;
            }

            return result;

        } catch (SQLException e1) {
            e1.printStackTrace();
            return false;
        }
    }

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
            int idBook = (int) row.get("idBook");
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

    public ArrayList<Book> getDetailedBooksList()  throws SQLException {
        DatabaseConnection connector = new DatabaseConnection(host, port, database);
        Connection connection = connector.getConnection();

        String fullCommand = "SELECT DISTINCT B.idBook, Br.idBranch, B.title, B.publisher, genre, Authors, ISBN, Br.name AS 'branch', Br.quantity, image\n" +
                "FROM Books B, Branches branch,\n" +
                "     (SELECT  b.idBook, b.title,\n" +
                "              GROUP_CONCAT(CONCAT(a.fName, ' ', a.lName) ORDER BY CONCAT(a.fName, ' ', a.lName)) AS Authors\n" +
                "      FROM Books b\n" +
                "             LEFT JOIN Book_Authors ba\n" +
                "               ON b.idBook = ba.idBook\n" +
                "             LEFT JOIN Authors a\n" +
                "               ON ba.idAuthors = a.idAuthors\n" +
                "      GROUP BY b.idBook\n" +
                "     ) AS title_authors,\n" +
                "     (SELECT B2.idBook, G2.name AS genre FROM Books B2\n" +
                "     LEFT JOIN Book_Genres Genre2 on B2.idBook = Genre2.idBook\n" +
                "     LEFT JOIN Genre G2 on Genre2.idGenre = G2.idGenre) AS G,\n" +
                "     (SELECT B3.idBook, B4.idBranch, BQ.quantity, B4.name\n" +
                "      FROM Books B3\n" +
                "     LEFT JOIN Book_Quantity BQ on B3.idBook = BQ.idBook\n" +
                "     LEFT JOIN Branches B4 on BQ.idBranch = B4.idBranch) AS Br\n" +
                "WHERE title_authors.idBook = B.idBook AND G.idBook = B.idBook AND Br.idBook = B.idBook\n AND Br.quantity IS NOT NULL\n" +
                "ORDER BY B.idBook";
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
            int idBook = (int) row.get("idBook");
            String isbn = (String) row.get("ISBN");
            String author = (String) row.get("Authors");
            String genre = (String) row.get("genre");
            int quantity = (int) row.get("quantity");
            String branch = (String) row.get("branch");
            int idBranch = (int) row.get("idBranch");
            Book book = new Book(idBook, title, author, idBranch, quantity, genre, publisher, branch, isbn);
            rowList.add(book);

            //String image = (String) row.get("image");






        }

        // Closes the statement
        stm.close();
        connection.close();
        return rowList;
    }

    /**
     * Returns all the books from the table Book as an ArrayList<Author>.
     *
     * @return Returns all the books from the table Book as an ArrayList<Author>.
     */
    public ArrayList<Author> getAuthorList()  throws SQLException {
        DatabaseConnection connector = new DatabaseConnection(host, port, database);
        Connection connection = connector.getConnection();

        String fullCommand = "SELECT * FROM Authors";
        ArrayList<Author> rowList = new ArrayList<>();
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
            String lName = (String) row.get("lName");
            String fName = (String) row.get("fName");
            int idAuthors = (int) row.get("idAuthors");

            Author author = new Author(idAuthors, lName, fName);
            rowList.add(author);
        }

        // Closes the statement
        stm.close();
        connection.close();
        return rowList;
    }

    /**
     * Returns all the books from the table Book as an ArrayList<Customer>.
     *
     * @return Returns all the books from the table Book as an ArrayList<Customer>.
     */
    public ArrayList<Customer> getCustomerList()  throws SQLException {
        DatabaseConnection connector = new DatabaseConnection(host, port, database);
        Connection connection = connector.getConnection();

        String fullCommand = "SELECT * FROM Customer";
        ArrayList<Customer> rowList = new ArrayList<>();
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
            String lname = (String) row.get("lname");
            String fname = (String) row.get("fname");
            int idCustomer = (int) row.get("idCustomer");
            String address = (String) row.get("address");
            String phone = (String) row.get("phone");

            Customer customer = new Customer(idCustomer, lname, fname, address, phone);
            rowList.add(customer);
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
            String username = (String) row.get("username");
            String password = (String) row.get("password");
            String usertype = (String)row.get("usertype");
            User user = new User(username, password, usertype);
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
            int idBranch = (int) row.get("idBranch");
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
            int idLoan = (int) row.get("idLoan");
            java.sql.Date loanDate = (java.sql.Date) row.get("loanDate");
            java.sql.Date loanDue = (java.sql.Date) row.get("loanDate");
            int idBook = (int) row.get("idBook");
            String username = (String) row.get("username");

            Loan loan = new Loan(idLoan, loanDate, loanDue, idBook, username);
            rowList.add(loan);
        }

        // Closes the statement
        stm.close();
        connection.close();
        return rowList;
    }

    public ArrayList<Employee> getEmployeeList()  throws SQLException {
        DatabaseConnection connector = new DatabaseConnection(host, port, database);
        Connection connection = connector.getConnection();

        String fullCommand =
                "       SELECT E.idEmployee, fname, lname, E.address, phone, accountNumber,\n" +
                "       SSN, position, B.name, EU.username\n" +
                "       FROM Employee E\n" +
                "       JOIN Branches B on E.idBranch = B.idBranch\n" +
                "       JOIN Employee_Users EU on E.idEmployee = EU.idEmployee;\n";
        ArrayList<Employee> rowList = new ArrayList<>();
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
            int idEmployee = (int) row.get("idEmployee");
            String fname = (String) row.get("fname");
            String lname = (String) row.get("lname");
            String address = (String) row.get("address");
            String phone = (String) row.get("phone");
            String accountNumber = (String) row.get("accountNumber");
            String SSN = (String) row.get("SSN");
            String position = (String) row.get("position");
            String branch = (String) row.get("name");
            String username = (String) row.get("username");


            Employee employee = new Employee(idEmployee, fname, lname, address, phone,
                    accountNumber, SSN, position, branch, username);
            rowList.add(employee);
        }

        // Closes the statement
        stm.close();
        connection.close();
        return rowList;
    }

    public ArrayList<Genre> getGenreList()  throws SQLException {
        DatabaseConnection connector = new DatabaseConnection(host, port, database);
        Connection connection = connector.getConnection();

        String fullCommand = "SELECT * FROM Genre";
        ArrayList<Genre> rowList = new ArrayList<>();
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
            String name = (String) row.get("name");
            int idGenre = (int) row.get("idGenre");


            Genre genre = new Genre(idGenre, name);
            rowList.add(genre);
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
    public int getQuantity(int bookID, int branchID) {
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

        // Add rows to the database //

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
        } catch (Exception ex){
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
        int bookID = 0;

        try {
            String queryInsertBook = "INSERT INTO Books (title, publisher, ISBN, image) VALUES ('" +
                    title + "', '" +
                    publisher + "', " +
                    null + ", " +
                    null + ");";

            // Create statement
            Statement stm = null;
            stm = connection.createStatement();

            // Insert new Employee
            stm.executeUpdate(queryInsertBook, Statement.RETURN_GENERATED_KEYS);

            // Retrieve Book ID
            ResultSet rs = stm.getGeneratedKeys();
            if(rs.next()) {
                bookID = rs.getInt(1); }

            connection.close();
            return bookID;

        }catch (Exception ex){
            System.out.println(ex.getMessage());
            connection.close();
            return bookID;
        }
    }

    /**
     *
     * @param idBook The book ID of the book that is borrowed.
     * @param idBranch The branch where the book resides.
     * @param username The borrowers username.
     * @return Number of edited rows in database.
     * @throws SQLException
     */
    public int addLoanToDatabase(int idBook, int idBranch, String username) throws SQLException{
            DatabaseConnection connector = new DatabaseConnection(host, port, database);
            Connection connection = connector.getConnection();

            try {
                String loanDate = "CURDATE()";
                String loanDue = "DATE_ADD(CURDATE(), INTERVAL 2 MONTH)";

                String fullCommand = "INSERT INTO Loans (loanDate, loanDue, idBook, idBranch, username) VALUES (" +
                        loanDate + ", " +
                        loanDue + ", " +
                        idBook + ", " +
                        idBranch + ", '" +
                        username + "');";


                // Create statement
                Statement stm = null;
                stm = connection.createStatement();

                // Query
                int execution = stm.executeUpdate(fullCommand);
                connection.close();
                return execution;
            }catch (Exception ex){
                connection.close();
                return 0;
            }
    }

    /**
     *
     * @param fName Firstname of author.
     * @param lName Lastname of author
     * @return Number of edited rows in database.
     * @throws SQLException
     */
    public int addAuthorToDatabase(String fName, String lName) throws SQLException{
        DatabaseConnection connector = new DatabaseConnection(host, port, database);
        Connection connection = connector.getConnection();

        try {

            String fullCommand = "INSERT INTO Authors (lName, fName) VALUES ('" +
                    lName + "', '" +
                    fName + "');";


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
     * @param name Name of the branch.
     * @param address The branches address.
     * @return Number of edited rows in database.
     * @throws SQLException
     */
    public int addBranchToDatabase(String name, String address) throws SQLException{
        DatabaseConnection connector = new DatabaseConnection(host, port, database);
        Connection connection = connector.getConnection();

        try {

            String fullCommand = "INSERT INTO Branches (name, address) VALUES ('" +
                    name + "', '" +
                    address + "');";


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
     * @param fname Firstname of the customer.
     * @param lname Lastname of the customer.
     * @param address Customers address.
     * @param phone Customers phone number
     * @return Number of edited rows in database.
     * @throws SQLException
     */
    public int addCustomerToDatabase(String fname, String lname, String address, String phone) throws SQLException{
        DatabaseConnection connector = new DatabaseConnection(host, port, database);
        Connection connection = connector.getConnection();

        try {

            String fullCommand = "INSERT INTO Customer (fname, lname, address, phone) VALUES ('" +
                    fname + "', '" +
                    lname + "', '" +
                    address + "', '" +
                    phone + "');";


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
     * @param fname Firstname of the employee.
     * @param lname Lastname of the employee.
     * @param address Employees address.
     * @param phone Employees phone number.
     * @param accountNumber Employees accountnumber.
     * @param SSN Employees social security number.
     * @param position Employees working position.
     * @param idBranch Branch ID of where the employee working at.
     * @return Returns the id of the created Employee, if the insert statement failed it will return 0.
     * @throws SQLException
     */
    public int addEmployeeToDatabase(String fname, String lname, String address, String phone, String accountNumber,
                                     String SSN, String position, int idBranch) throws SQLException{

        DatabaseConnection connector = new DatabaseConnection(host, port, database);
        Connection connection = connector.getConnection();
        int employeeID = 0;

        try {

            String queryInsertEmployee = "INSERT INTO Employee (fname, lname, address, phone, accountNumber, SSN, position, idBranch) VALUES ('" +
                    fname + "', '" +
                    lname + "', '" +
                    address + "', '" +
                    phone + "', '" +
                    accountNumber + "', " +
                    SSN + ", '" +
                    position + "', " +
                    idBranch + ");";

            // Create statement
            Statement stm = null;
            stm = connection.createStatement();

            // Insert new Employee
            stm.executeUpdate(queryInsertEmployee, Statement.RETURN_GENERATED_KEYS);

            // Retrieve Employee ID
            ResultSet rs = stm.getGeneratedKeys();
            if(rs.next()) {
                employeeID = rs.getInt(1); }

            connection.close();
            return employeeID;

        }catch (Exception ex){
            System.out.println(ex.getMessage());
            connection.close();
            return employeeID;
        }
    }

    /**
     *
     * @param name The genre name.
     * @return Number of edited rows in database.
     * @throws SQLException
     */
    public int addGenreToDatabase(String name) throws SQLException{
        DatabaseConnection connector = new DatabaseConnection(host, port, database);
        Connection connection = connector.getConnection();

        try {

            String fullCommand = "INSERT INTO Genre (name) VALUES ('" +
                    name + "');";


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
     * @param idBook Book ID.
     * @param idAuthor Author ID.
     * @return Number of edited rows in database.
     * @throws SQLException
     */
    public int addBookAuthorJunctionToDatabase(int idBook, int idAuthor) throws SQLException{
        DatabaseConnection connector = new DatabaseConnection(host, port, database);
        Connection connection = connector.getConnection();

        try {

            String fullCommand = "INSERT INTO Book_Authors (idBook, idAuthors) VALUES (" +
                    idBook + ", " +
                    idAuthor + ");";


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
     * @param idBook Book ID.
     * @param idGenre Genre ID.
     * @return Number of edited rows in database.
     * @throws SQLException
     */
    public int addBookGenreJunctionToDatabase(int idBook, int idGenre) throws SQLException{
        DatabaseConnection connector = new DatabaseConnection(host, port, database);
        Connection connection = connector.getConnection();

        try {

            String fullCommand = "INSERT INTO Book_Genres (idBook, idGenre) VALUES (" +
                    idBook + ", " +
                    idGenre + ");";


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
     * @param idBook Book ID.
     * @param idBranch Branch ID.
     * @param quantity Quantity of books in given branch.
     * @return Number of edited rows in database.
     * @throws SQLException
     */
    public int addBookQuantityJunctionToDatabase(int idBook, int idBranch, int quantity) throws SQLException{
        DatabaseConnection connector = new DatabaseConnection(host, port, database);
        Connection connection = connector.getConnection();

        try {

            String fullCommand = "INSERT INTO Book_Quantity (idBook, idBranch, quantity) VALUES (" +
                    idBook + ", " +
                    idBranch + ", " +
                    quantity + ");";


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
     * @param idUser User ID.
     * @param idCustomer Customer ID.
     * @return Number of edited rows in database.
     * @throws SQLException
     */
    public int addCustomerUserJunctionToDatabase(int idUser, int idCustomer) throws SQLException{
        DatabaseConnection connector = new DatabaseConnection(host, port, database);
        Connection connection = connector.getConnection();

        try {



            String fullCommand = "INSERT INTO Customer_Users (Users_idUser, Customer_idCustomer) VALUES (" +
                    idUser + ", " +
                    idCustomer + ");";


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
     * @param username Username.
     * @param idEmployee Employee ID.
     * @return Number of edited rows in database.
     * @throws SQLException
     */
    public int addEmployeeUserJunctionToDatabase(String username, int idEmployee) throws SQLException{
        DatabaseConnection connector = new DatabaseConnection(host, port, database);
        Connection connection = connector.getConnection();

        try {

            String fullCommand =
                    "INSERT INTO Employee_Users(username, idEmployee)\n" +
                            "VALUES('" + username + "', " + idEmployee + ")";

            // Create statement
            Statement stm = null;
            stm = connection.createStatement();

            // Query
            int execution = stm.executeUpdate(fullCommand);
            connection.close();
            return execution;

        } catch (Exception ex){

            System.out.println(ex.getMessage());
            connection.close();
            return 0;
        }
    }



    public int removeBookFromDatabase(int idBook) throws SQLException{
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

    /**
     * Updates the quantity of books in the database, and returns all the books weren't available.
     *
     * @return Returns back the shoppingCart with all the remaining books that couldn't be borrowed.
     * If it returns an empty HashMap all went well.
     */
    public HashMap<Literature, Branch> updateQuantity(HashMap<Literature, Branch> shoppingCart) {
        HashMap<Literature, Branch> books = (HashMap) shoppingCart.clone();

        try {
            DatabaseConnection connector = new DatabaseConnection(host, port, database);
            Connection connection = connector.getConnection();
            Statement stm = connection.createStatement();

            Iterator it = books.entrySet().iterator() ;
             while (it.hasNext()) {
                 Map.Entry pair = (Map.Entry)it.next();
                 Book book = (Book) pair.getKey();
                 Branch branch = (Branch) pair.getValue();
                 int idBook = book.getIdBook();
                 int idBranch = branch.getIdBranch();

                 if (getQuantity(idBook, idBranch) >= 1) {
                     String fullCommand =
                             "UPDATE Book_Quantity " +
                                     "SET quantity = quantity - 1 " +
                                     "WHERE idBook = " + idBook + " AND idBranch = " + idBranch + ";";
                     stm.execute(fullCommand);
                     it.remove();
                 }
             }

             stm.close();
             connector.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return books;
        }
        return books;
    }

    /**
     * Tries to login to the application checking the database's users table for
     * a match on username and password.
     *
     * @param username username
     * @param password password
     * @return
     */
    public boolean tryLogin(String username, String password) throws SQLException{
        DatabaseConnection connector = new DatabaseConnection(host, port, database);
        Connection connection = connector.getConnection();
        Statement stm = connection.createStatement();

        boolean result = false;

        String fullCommand =
                        "SELECT * FROM Users WHERE username = '" + username + "' AND password = '" + password + "';";

        // Query
        stm.execute(fullCommand);
        ResultSet queryResult = stm.getResultSet();
        if (queryResult.next()) {
            result = true;
        }

        stm.close();
        connector.closeConnection();

        return result;
    }

    /**
     * Finds out which books were borrowed and creates new loans for each book that
     * was lent from the library.
     *
     * @param booksToBeRented The Books that's going to be rented.
     */
    public void createLoans(HashMap<Literature, Branch> booksToBeRented) {

        for (HashMap.Entry<Literature, Branch> book : booksToBeRented.entrySet()) {

            int idBook = book.getKey().getIdBook();
            int idBranch = book.getKey().getIdBook();
            String username = Model.getInstance().currentUser().getUsername();

            try {

                addLoanToDatabase(idBook, idBranch, username);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Returns an ArrayList with data compatible for the MyPage in library application. This includes information
     * about loans done by the current user, with loan date, due date, remaining days until due, book title,
     * book author, library, Fine.
     *
     * The fine will be calculated by the query based on how long it's been since the book went due.
     *
     * @return Returns an arraylist with Strings containing data mentioned above.
     */
    public ArrayList<Loan> getLoansForUser() throws SQLException {
        DatabaseConnection connector = new DatabaseConnection(host, port, database);
        Connection connection = connector.getConnection();

        ArrayList<Loan> rowlist = new ArrayList<>();
        String currentUser = Model.getInstance().currentUser().getUsername();
        Statement stm = null;

        try {
            String fullCommand =
                    "SELECT  l.idLoan, l.idBook, l.idBranch, l.username,  branch.name, title_authors.title, Authors," +
                            "l.loanDate, l.loanDue, DATEDIFF(l.loanDue, CURDATE()) AS 'Remaining days'," +
                            "CASE\n" +
                            "        WHEN (DATEDIFF(CURDATE(), l.loanDue) * 5) < 0 THEN 0\n" +
                            "        ELSE DATEDIFF(CURDATE(), l.loanDue) * 5\n" +
                            "END as 'Fine'" +

                            "FROM Loans l, Branches branch," +

                            "(SELECT  b.idBook, b.title, " +
                            "GROUP_CONCAT(CONCAT(a.fName, ' ', a.lName) ORDER BY CONCAT(a.fName, ' ', a.lName)) AS Authors " +
                            "FROM Books b " +
                            "LEFT JOIN Book_Authors ba " +
                            "ON b.idBook = ba.idBook " +
                            "LEFT JOIN Authors a " +
                            "ON ba.idAuthors = a.idAuthors " +
                            "GROUP BY b.idBook " +
                            ") AS title_authors " +

            "WHERE title_authors.idBook = l.idBook AND branch.idBranch = l.idBranch AND l.username = '" + currentUser + "' " +
            "ORDER BY l.idLoan";

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
                int idLoan = (int) row.get("idLoan");
                java.sql.Date loanDate = (java.sql.Date) row.get("loanDate");
                java.sql.Date loanDue = (java.sql.Date) row.get("loanDue");
                int idBook = (int) row.get("idBook");
                String username = (String) row.get("username");
                int idBranch = (int) row.get("idBranch");
                String library = (String) row.get("name");
                String bookTitle = (String) row.get("title");
                String authors = (String) row.get("Authors");
                long remainingDays = (long) row.get("Remaining days");
                long fine = (long) row.get("Fine");

                Loan loan = new Loan(idLoan, loanDate, loanDue, idBook, username, idBranch, library, bookTitle,
                        authors, remainingDays, fine);

                rowlist.add(loan);
            }

            stm.close();
            connection.close();
            return rowlist;


        } catch (SQLException | NullPointerException e) {

            e.printStackTrace();

        }

        return rowlist;
    }

    /**
     * Uses the parameter Loan to update the database's quantity of a certain book and removes it from the Loans table.
     *
     * @param loan The loan which is to be removed
     * @throws SQLException
     */
    public void returnBook(Loan loan) throws SQLException {
        DatabaseConnection connector = new DatabaseConnection(host, port, database);
        Connection connection = connector.getConnection();
        Statement stm = connection.createStatement();

        int idBook = loan.getIdBook();
        int idBranch = loan.getIdBranch();
        int idLoan = loan.getIdLoan();

        String updateQuantityQuery =
                "UPDATE Book_Quantity " +
                        "SET quantity = quantity + 1 " +
                        "WHERE idBook = " + idBook + " AND idBranch = " + idBranch + ";";
        stm.execute(updateQuantityQuery);

        String removeFromLoans =
                "DELETE FROM Loans WHERE idLoan = '" + idLoan + "';";

        stm.execute(removeFromLoans);

    }
}