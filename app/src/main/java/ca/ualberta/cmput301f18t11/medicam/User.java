package ca.ualberta.cmput301f18t11.medicam;

import ca.ualberta.cmput301f18t11.medicam.exceptions.InvalidEmailException;
import ca.ualberta.cmput301f18t11.medicam.exceptions.StringTooShortException;
import io.searchbox.annotations.JestId;

public abstract class User extends PersistedModel {

    private String email;
    private String phoneNumber;

    public String getUserID() {
        return uuid;
    }

    public void setUserID(String userID) throws StringTooShortException {
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

    }

    public User(String userID, String email, String phoneNumber) throws StringTooShortException, InvalidEmailException {

    }

    public User(String userID) throws StringTooShortException {
        this.uuid = userID;
    }
}
