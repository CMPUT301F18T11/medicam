package ca.ualberta.cmput301f18t11.medicam;

import java.util.List;
import java.util.UUID;

public class CareProvider extends User {

    private List<UUID> patients;

    public List<UUID> getPatients() {
        return patients;
    }

    public void setPatients(List<UUID> patients) {
        this.patients = patients;
    }

    public void addPatient(UUID patient) {

    }

    public void addPatient(String patient) {

    }

    public void removePatient(UUID patient) {

    }

    public CareProvider(String userID, String email, String phoneNumber, List<UUID> patients) throws StringTooShortException, InvalidEmailException {
        super(userID, email, phoneNumber);
        this.patients = patients;
    }

    public CareProvider(String userID) throws StringTooShortException {
        super(userID);
    }

    public CareProvider(String userID, String email, String phoneNumber) throws StringTooShortException, InvalidEmailException {
        super(userID, email, phoneNumber);
    }

    public CareProvider() {
        super();
    }
}
