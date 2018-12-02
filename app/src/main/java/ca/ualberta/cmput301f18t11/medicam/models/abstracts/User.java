package ca.ualberta.cmput301f18t11.medicam.models.abstracts;

import java.util.UUID;

import ca.ualberta.cmput301f18t11.medicam.exceptions.InvalidEmailException;
import ca.ualberta.cmput301f18t11.medicam.exceptions.StringTooShortException;

public abstract class User extends PersistedModel {
    private final static int MIN_USERNAME_LENGTH = 8;

    private String email;
    private String phoneNumber;

    public String getUserID() {
        return uuid;
    }

    public void setUserID(String userID) throws StringTooShortException {
        if (userID.length() < MIN_USERNAME_LENGTH) {
            throw new StringTooShortException();
        }
        this.uuid = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws InvalidEmailException {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public User() {
        super();
    }

    public User(String userID, String email, String phoneNumber) throws StringTooShortException, InvalidEmailException {
        setUserID(userID);
        setEmail(email);
        setPhoneNumber(phoneNumber);
    }

    public User(String userID) throws StringTooShortException {
        setUserID(userID);
    }
}
