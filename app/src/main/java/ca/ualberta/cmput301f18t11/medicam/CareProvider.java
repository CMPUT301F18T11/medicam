package ca.ualberta.cmput301f18t11.medicam;

import java.util.List;

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

    public void addPatient(String userID) {

    }

    public void removePatient(UUID patient) {

    }

    public void removePatient(String userID) {

    }

    public CareProvider(String userID, String email, String phoneNumber, List<UUID> patients) {
        super(userID, email, phoneNumber);
        this.patients = patients;
    }

}
