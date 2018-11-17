package ca.ualberta.cmput301f18t11.medicam.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ca.ualberta.cmput301f18t11.medicam.models.abstracts.User;
import ca.ualberta.cmput301f18t11.medicam.exceptions.InvalidEmailException;
import ca.ualberta.cmput301f18t11.medicam.exceptions.StringTooShortException;

public class CareProvider extends User {

    private ArrayList<String> patients = new ArrayList<>();

    public ArrayList<String> getPatients() {
        return patients;
    }

    public void setPatients(ArrayList<String> patients) {
        this.patients = patients;
    }

    public void addPatient(String patient) {
        this.patients.add(patient);
    }

    public void addPatient(Patient patient) {

    }

    public void removePatient(String patient) {
        this.patients.remove(patient);
    }

    public CareProvider(String userID, String email, String phoneNumber,
                        ArrayList<String> patients) throws StringTooShortException, InvalidEmailException {

        super(userID, email, phoneNumber);
        this.patients = patients;
    }

    public CareProvider(String userID) throws StringTooShortException {
        super(userID);
    }

    public CareProvider(String userID, String email,
                        String phoneNumber) throws StringTooShortException, InvalidEmailException {
        super(userID, email, phoneNumber);
    }

    public CareProvider() {
        super();
    }
}
