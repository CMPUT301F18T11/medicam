package ca.ualberta.cmput301f18t11.medicam.models;

import java.util.ArrayList;
import java.util.List;

import ca.ualberta.cmput301f18t11.medicam.models.abstracts.User;
import ca.ualberta.cmput301f18t11.medicam.exceptions.InvalidEmailException;
import ca.ualberta.cmput301f18t11.medicam.exceptions.StringTooShortException;

/**
 * Represents a patient that uses this app to record their various medical problems.
 * <p>
 * The problems are held as a <code>Collection</code> of <code>String</code> uuids that uniquely
 * represent every individual <code>Problem</code> object that is associated with this patient.
 * Each patient can also record a front-facing and back-facing full-body photo to use to point out
 * where on their body each problem is occurring. These photo each have a uri <code>String</code>
 * that is stored so that the photo can be retrieved when needed.
 */
public class Patient extends User {

    private List<String> problems = new ArrayList<>();
    private String frontPhoto;
    private String backPhoto;

    /**
     * Empty constructor for initializing a blank <code>Patient</code> object, with an implicit call
     * to super().
     */
    public Patient() {
    }

    /**
     * Constructor that sets all possible fields for a <code>Patient</code> object, excluding front and back
     * photos.
     *
     * @param userID <code>String</code> to represent the unique identifier for this <code>Patient</code> object.
     * @param email <code>String</code> to represent the email for this <code>Patient</code> object.
     * @param phoneNumber <code>String</code> to represent the phone number for this <code>Patient</code> object.
     * @param problems <code>List</code> that holds all the <code>String</code> type uuids that represent
     *                 all the <code>Problem</code> objects associated with this <code>Patient</code>.
     * @throws StringTooShortException Warns that uuid String is shorter than 8 characters.
     * @throws InvalidEmailException Warns that the specified email String is not in an approved format.
     * @see User
     */
    public Patient(String userID, String email, String phoneNumber,
                   List<String> problems) throws StringTooShortException, InvalidEmailException {
        super(userID, email, phoneNumber);
        this.problems = problems;
    }

    /**
     * Constructor for setting just the userID (uuid) of this <code>Patient</code> and leaving the rest
     * of its components empty.
     * <p>
     *     That is, it specifies the uuid and leaves email, phone number and problems fields empty.
     *
     * @param userID <code>String</code> to represent the unique identifier for this <code>Patient</code> object.
     * @throws StringTooShortException Warns that uuid String is shorter than 8 characters.
     * @see User
     */
    public Patient(String userID) throws StringTooShortException {
        super(userID);
    }

    /**
     * Constructor that sets all possible fields for a <code>Patient</code> object, including front and back
     * photos.
     *
     * @param userID <code>String</code> to represent the unique identifier for this <code>Patient</code> object.
     * @param email <code>String</code> to represent the email for this <code>Patient</code> object.
     * @param phoneNumber <code>String</code> to represent the phone number for this <code>Patient</code> object.
     * @param problems <code>List</code> that holds all the <code>String</code> type uuids that represent
     *                 all the <code>Problem</code> objects associated with this <code>Patient</code>.
     * @param frontPhoto a <code>String</code> that represents the <code>Uri</code> for the front facing
     *                   full-body photo associated with this <code>Patient</code>.
     * @param backPhoto a <code>String</code> that represents the <code>Uri</code> for the back facing
     *                  full-body photo associated with this <code>Patient</code>.
     * @throws StringTooShortException Warns that uuid String is shorter than 8 characters.
     * @throws InvalidEmailException Warns that the specified email String is not in an approved format.
     * @see User
     */
    public Patient(String userID, String email, String phoneNumber, List<String> problems,
                   String frontPhoto, String backPhoto) throws StringTooShortException, InvalidEmailException {
        super(userID, email, phoneNumber);
        this.problems = problems;
        this.frontPhoto = frontPhoto;
        this.backPhoto = backPhoto;
    }

    /**
     * Gets a list of uuids for all the problems associated with this patient.
     *
     * @return <code>List</code> of <code>String</code> type uuids that represent all <code>Problem</code>
     *          objects that associated with this <code>Patient</code>.
     */
    public List<String> getProblems() {
        return problems;
    }

    /**
     * Sets a list of uuids for all the problems associated with this patient.
     *
     * @param problems <code>List</code> of <code>String</code> type uuids that represent all <code>Problem</code>
     *          objects that are to be associated with this <code>Patient</code>.
     */
    public void setProblems(List<String> problems) {
        this.problems = problems;
    }

    /**
     * Adds a problem by uuid to the list of problems that are associated with this patient.
     *
     * @param problem A <code>String</code> that uniquely identifies the <code>Problem</code> object
     *                being added to the <code>List</code> of problems for this <code>Patient</code>.
     */
    public void addProblem(String problem)
    {
        problems.add(problem);
    }

    /**
     * Adds a <code>Problem</code> object to the list of problems that are associated with this patient.
     * note: The uuid of the <code>Problem</code> object is added instead of the problem itself.
     *
     * @param problem A <code>Problem</code> object who's uuid <code>String</code> is to be added to the <code>List</code> of problems
     *                for this <code>Patient</code>.
     */
    public void addProblem(Problem problem) {
        problems.add(problem.getUuid());
    }

    /**
     * Gets the uri <code>String</code> that represents the, user defined, front-facing full-body
     * photo associated with this patient.
     *
     * @return <code>String</code> that represents the uri for the user defined, front-facing full-body
     *         photo associated with this patient.
     */
    public String getFrontPhoto() {
        return frontPhoto;
    }

    /**
     * Sets the uri <code>String</code> that represents the, user defined, front-facing full-body
     * photo associated with this patient.
     *
     * @param frontPhoto <code>String</code> that is to represents the uri for the user defined, front-facing full-body
     *         photo associated with this patient.
     */
    public void setFrontPhoto(String frontPhoto) {
        this.frontPhoto = frontPhoto;
    }

    /**
     * Gets the uri <code>String</code> that represents the, user defined, back-facing full-body
     * photo associated with this patient.
     *
     * @return <code>String</code> that represents the uri for the user defined, back-facing full-body
     *         photo associated with this patient.
     */
    public String getBackPhoto() {
        return backPhoto;
    }

    /**
     * Sets the uri <code>String</code> that represents the, user defined, front-facing full-body
     * photo associated with this patient.
     *
     * @param backPhoto <code>String</code> that is to represents the uri for the user defined, back-facing full-body
     *         photo associated with this patient.
     */
    public void setBackPhoto(String backPhoto) {
        this.backPhoto = backPhoto;
    }

}
