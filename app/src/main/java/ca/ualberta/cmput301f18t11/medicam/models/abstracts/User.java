package ca.ualberta.cmput301f18t11.medicam.models.abstracts;

import java.util.UUID;

import ca.ualberta.cmput301f18t11.medicam.exceptions.InvalidEmailException;
import ca.ualberta.cmput301f18t11.medicam.exceptions.StringTooShortException;

/**
 * Represents the base functionality that every User object in the app should extend.
 * <p>
 * That is, every user should hold email(email), phone number (phoneNumber), and address (address)
 * Strings that are specified by the user upon account creation.
 */
public abstract class User extends PersistedModel {

    private String email;
    private String phoneNumber;
    private String address;

    /**
     * Returns this User's unique identifying String.
     *
     * @return a string that is used to uniquely identify this User.
     */
    public String getUserID() {
        return uuid;
    }

    /**
     * Set the unique identifier for this User.
     * <p>
     *     This uuid must be 8 characters or longer.
     *     note: StringTooShortException throwing not currently implemented.
     *
     * @param userID a String specific by the User, to uniquely identify said User.
     * @throws StringTooShortException Warns that uuid String is shorter than 8 characters.
     */
    public void setUserID(String userID) throws StringTooShortException {
        this.uuid = userID;
    }

    /**
     * Returns the address associated with this User.
     *
     * @return A String that represents the address associated with this User.
     */
    public String getAddress() {return address;}

    /**
     * Returns the email associated with this User.
     *
     * @return A String that represents the email associated with this User.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the email that is associated with this User.
     * <p>
     *     The email set must be in a valid email format.
     *
     * @param email A String to represents the email associated with this User.
     * @throws InvalidEmailException Warns that the specified email String is not in an approved format.
     */
    public void setEmail(String email) throws InvalidEmailException {
        this.email = email;
    }

    /**
     * Returns the phone number associated with this User.
     *
     * @return A String that represents the phone number associated with this User.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number associated with this User.
     *
     * @param phoneNumber A String to represents the phone number associated with this User.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Sets the address associated with this User.
     *
     * @param address A String to represents the address associated with this User.
     */
    public void setAddress(String address){this.address = address ;}

    /**
     * An empty constructor to initialize a blank User object.
     */
    public User() { }

    /**
     * Constructor to initialize all possible parameters associated with a User.
     *
     * @param userID A String that represents the address associated with this User.
     * @param email A String to represents the email associated with this User.
     * @param phoneNumber A String to represents the phone number associated with this User.
     * @throws StringTooShortException Warns that uuid String is shorter than 8 characters.
     * @throws InvalidEmailException Warns that the specified email String is not in an approved format.
     */
    public User(String userID, String email, String phoneNumber) throws StringTooShortException, InvalidEmailException {
        setUserID(userID);
        setEmail(email);
        setPhoneNumber(phoneNumber);
    }

    /**
     * A simple constructor that only sets the uuid of the User object and leaves the rest of the
     * components blank.
     *
     * @param userID A String that represents the address associated with this User
     * @throws StringTooShortException Warns that uuid String is shorter than 8 characters.
     */
    public User(String userID) throws StringTooShortException {
        setUserID(userID);
    }
}
