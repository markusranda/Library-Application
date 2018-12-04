package no.ntnu.datamod.logic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private String host;
    private int port;
    private String database;
    private Connection connection;

    public DatabaseConnection(String host, int port, String database)
            throws SQLException{
        this.host = host;
        this.port = port;
        this.database = database;

        try {
            String connectionString = "jdbc:mysql://"+ host + ":" + port + "/" +
                    database + "?user=dbuser&password=password&useUnicode=true&characterEncoding=UTF-8";

            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

            connection = DriverManager.getConnection(
                    connectionString);

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    public void closeConnection() throws SQLException {
        connection.close();
    }

    public boolean isConnectionAlive() {
        try {
            return connection.isClosed();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Returns the Connection instance from the connection.
     *
     * @return Returns the Connection instance from the connection.
     */
    public Connection getConnection() {
        return connection;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getDatabase() {
        return database;
    }
}
