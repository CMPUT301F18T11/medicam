package ca.ualberta.cmput301f18t11.medicam.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ca.ualberta.cmput301f18t11.medicam.models.abstracts.User;
import ca.ualberta.cmput301f18t11.medicam.exceptions.InvalidEmailException;
import ca.ualberta.cmput301f18t11.medicam.exceptions.StringTooShortException;

/**
 * Represents a careprovider user account that holds a list of patients that are tracked by/assigned
 * to this careprovider.
 * <p>
 * The patients associated with this <code>CareProvider</code> object are represented by a <code>List</code>
 * of <code>String</code> type uuids that uniquely identify each <code>Patient</code>.
 * </p>
 */
public class CareProvider extends User {

    private List<String> patients = new ArrayList<>();

    /**
     * Gets the patients that this careprovider is tracking/are assigned to them.
     *
     * @return patients A <code>List</code> of <code>Patient</code> uuid <code>Strings</code> that uniquely
     *                 identify each <code>Patient</code> object assigned to this <code>CareProvider</code>.
     *
     */
    public List<String> getPatients() {
        return patients;
    }

    /**
     * Sets the patients that this careprovider is tracking/are assigned to them.
     *
     * @param patients A <code>List</code> of <code>Patient</code> uuid <code>Strings</code> that uniquely
     *                 identify each <code>Patient</code> object to be assigned to this <code>CareProvider</code>.
     *
     */
    public void setPatients(List<String> patients) {
        this.patients = patients;
    }

    /**
     * Adds a patient to the list of patients this careprovider is tracking/is assigned to.
     *
     * @param patient A <code>String</code> that uniquely identifies the <code>Patient</code> object
     *                to be assigned to this <code>CareProvider</code>.
     */
    public void addPatient(String patient) {
        this.patients.add(patient);
    }

//    public void addPatient(Patient patient) {
//
//    }

    /**
     * Removes a patient from the list of patients this careprovider is tracking/is assigned to.
     *
     * @param patient A <code>String</code> that uniquely identifies the <code>Patient</code> object
     *                to be removed from this <code>CareProvider</code> object's <code>List</code>
     *                of <code>Patient</code> objects.
     */
    public void removePatient(String patient) {
        this.patients.remove(patient);
    } //This should probably return a bool that informs whether the object was removed or not.

    /**
     * Constructor for setting all possible fields of this <code>CareProvider</code> object.
     *
     * @param userID <code>String</code> to represent the unique identifier for this <code>CareProvider</code> object.
     * @param email <code>String</code> to represent the email for this <code>CareProvider</code> object.
     * @param phoneNumber <code>String</code> to represent the phone number for this <code>CareProvider</code> object.
     * @param patients A <code>List</code> of <code>Patient</code> uuid <code>Strings</code> that uniquely
     *                 identify each <code>Patient</code> object to be assigned to this <code>CareProvider</code>.
     * @throws StringTooShortException Warns that uuid String is shorter than 8 characters.
     * @throws InvalidEmailException Warns that the specified email String is not in an approved format.
     * @see User
     */
    public CareProvider(String userID, String email, String phoneNumber,
                        List<String> patients) throws StringTooShortException, InvalidEmailException {

        super(userID, email, phoneNumber);
        this.patients = patients;
    }

    /**
     * Constructor for setting just the userID (uuid) of this <code>CareProvider</code> and leaving the rest
     * of its components empty.
     * <p>
     *     That is, it specifies the uuid and leaves email, phone number and patients fields empty.
     *
     * @param userID <code>String</code> to represent the unique identifier for this <code>CareProvider</code> object.
     * @throws StringTooShortException Warns that uuid String is shorter than 8 characters.
     * @see User
     */
    public CareProvider(String userID) throws StringTooShortException {
        super(userID);
    }

    /**
     * Constructor for setting all possible fields of this <code>CareProvider</code> object, except
     * the patients field.
     *
     * @param userID <code>String</code> to represent the unique identifier for this <code>CareProvider</code> object.
     * @param email <code>String</code> to represent the email for this <code>CareProvider</code> object.
     * @param phoneNumber <code>String</code> to represent the phone number for this <code>CareProvider</code> object.
     * @throws StringTooShortException Warns that uuid String is shorter than 8 characters.
     * @throws InvalidEmailException Warns that the specified email String is not in an approved format.
     * @see User
     */
    public CareProvider(String userID, String email,
                        String phoneNumber) throws StringTooShortException, InvalidEmailException {
        super(userID, email, phoneNumber);
    }

    /**
     * Empty constructor that initializes a blank <code>CareProvider</code> object.
     *
     * @see User
     */
    public CareProvider() {
        super();
    }
}
