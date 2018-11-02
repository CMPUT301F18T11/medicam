package ca.ualberta.cmput301f18t11.medicam;

public abstract class User extends PersistedModel {
    private String userID;
    private String email;
    private String phoneNumber;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) throws StringTooShortException {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
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

    public User(String userID, String email, String phoneNumber) throws StringTooShortException {

    }

    public User(String userID) throws StringTooShortException {
        this.userID = userID;
    }
}
