package no.ntnu.datamod.facade;

public interface LibraryClientFacade {

    /**
     * Connect to a chat server.
     *
     * @param host host name or IP address of the chat server
     * @param port TCP port of the chat server
     * @return True on success, false otherwise
     */
    public boolean connect(String host, int port);

    /**
     * Disconnect from the chat server (close the socket)
     */
    public void disconnect();

    /**
     * Return true if the connection is active (opened), false if not.
     *
     * @return Return true if the connection is active (opened), false if not.
     */
    public boolean isConnectionActive();
}