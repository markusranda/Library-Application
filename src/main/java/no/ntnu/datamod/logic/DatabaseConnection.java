package no.ntnu.datamod.logic;

import com.mysql.cj.xdevapi.SqlDataResult;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private String host;
    private int port;
    private String database;
    private Connection connection;

    public DatabaseConnection(String host, int port, String database)
            throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException{
        this.host = host;
        this.port = port;
        this.database = database;

        String connectionString = "jdbc:mysql://"+ host + ":" + port + "/" +
                database + "?user=dbuser&password=password&useUnicode=true&characterEncoding=UTF-8";

        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        connection = DriverManager.getConnection(
                connectionString);
    }

    public void closeConnection() throws SQLException {
        connection.close();
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
