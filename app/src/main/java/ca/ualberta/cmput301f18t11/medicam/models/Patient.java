package ca.ualberta.cmput301f18t11.medicam.models;


import java.lang.reflect.Array;
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
    private List<String> referencePhotos = new ArrayList<>();

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

    public Patient(String userID, String email, String phoneNumber) throws StringTooShortException, InvalidEmailException {
        super(userID, email, phoneNumber);
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
     * @param referencePhotos a List holding the set of reference photos taken by this Patient.
     * @throws StringTooShortException Warns that uuid String is shorter than 8 characters.
     * @throws InvalidEmailException Warns that the specified email String is not in an approved format.
     * @see User
     */
    public Patient(String userID, String email, String phoneNumber, List<String> problems,
                   List<String> referencePhotos) throws StringTooShortException, InvalidEmailException {
        super(userID, email, phoneNumber);
        this.problems = problems;
        this.referencePhotos = referencePhotos;
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
     * Methods for handling reference photos stored on the patient
     *
     * Get and set for the list itself
     * Add a photoUUID, remove a photoUUID
     */
    public List<String> getReferencePhotos() {
        return referencePhotos;
    }

    public void setReferencePhotos(List<String> referencePhotos) {
        this.referencePhotos = referencePhotos;
    }

    public void addReferencePhoto(String referencePhotoUUID) {
        referencePhotos.add(referencePhotoUUID);
    }

    public void removeReferencePhoto(String referencePhotoUUID) {
        referencePhotos.remove(referencePhotoUUID);
    }


}
