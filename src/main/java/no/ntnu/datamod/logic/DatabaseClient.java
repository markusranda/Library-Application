package no.ntnu.datamod.logic;
import no.ntnu.datamod.facade.LibraryClientFacade;

import java.io.*;
import java.net.Socket;
import java.sql.*;

public class DatabaseClient implements LibraryClientFacade {

    private InputStream in;
    private OutputStream out;
    private PrintWriter toServer;
    private BufferedReader fromServer;
    private Socket connection;

    private String lastError = null; // Last error message will be stored here

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
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            System.out.println("Connecting to database: " + connectionString);
            conn = DriverManager.getConnection(
                    connectionString);
            //stmt = conn.createStatement();
            //rs = stmt.executeQuery("SELECT name FROM Books");
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
     * Close the socket. This method must be synchronized, because several
     * threads may try to call it. For example: When "Disconnect" button is
     * pressed in the GUI thread, the connection will get closed. Meanwhile, the
     * background thread trying to read server's response will get error in the
     * input stream and may try to call this method when the socket is already
     * in the process of being closed. with "synchronized" keyword we make sure
     * that no two threads call this method in parallel.
     */
    @Override
    public synchronized void disconnect() {
        if (connection != null) {
            System.out.println("Disconnecting...");
            try {
                // Close the socket and streams
                toServer.close();
                fromServer.close();
                connection.close();
            } catch (IOException e) {
                System.out.println("Error while closing connection: "
                        + e.getMessage());
                lastError = e.getMessage();
                connection = null;
            }
        } else {
            System.out.println("No connection to close");
        }
        System.out.println("Disconnected");
        connection = null;
    }

    /**
     * Return true if the connection is active (opened), false if not.
     *
     * @return
     */
    @Override
    public boolean isConnectionActive() {
        return connection != null;
    }

    /**
     * Get the last error message that the server returned.
     *
     * @return
     */
    @Override
    public String getLastError() {
        if (lastError != null) {
            return lastError;
        } else {
            return "";
        }
    }
}