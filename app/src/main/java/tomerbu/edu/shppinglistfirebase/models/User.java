package tomerbu.edu.shppinglistfirebase.models;

import java.util.HashMap;

/**
 * Defines the data structure for User objects.
 */
public class User extends BaseModel{
    private String name;
    private String email;
    private String UID;
    private HashMap<String, Object> timeStampLoggedIn;

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    private boolean isLoggedIn;


    /**
     * Required public constructor
     */
    public User() {
    }


    public User(String name, String email,String UID,  HashMap<String, Object> timeStampLoggedIn, boolean isLoggedIn) {
        this.name = name;
        this.email = email;
        this.timeStampLoggedIn = timeStampLoggedIn;
        this.isLoggedIn = isLoggedIn;
        this.UID = UID;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", timeStampLoggedIn=" + timeStampLoggedIn +
                ", isLoggedIn=" + isLoggedIn +
                '}';
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public HashMap<String, Object> getTimeStampLoggedIn() {
        return timeStampLoggedIn;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }


    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getUID() {
        return UID;

    }
}