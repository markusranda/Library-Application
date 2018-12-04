package no.ntnu.datamod.gui;

public class Model {
    private final static Model instance = new Model();

    public static Model getInstance() {
        return instance;
    }

    private Session user = new Session();

    public Session currentUser() {
        return user;
    }
}
