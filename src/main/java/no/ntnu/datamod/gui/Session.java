package no.ntnu.datamod.gui;

public class Session {

    private String username;

    /**
     * Sets the username for this Session.
     * @param username username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the username for this Session.
     *
     * @return Returns the username for this Session.
     */
    public String getUsername() {
        return this.username;
    }
}
