package ca.ualberta.cmput301f18t11.medicam;

import java.util.List;
import java.util.UUID;

public class Patient extends User {
    private List<UUID> problems;
    private UUID frontPhoto;
    private UUID backPhoto;

    public Patient() {

    }

    public Patient(String userID, String email, String phoneNumber, List<UUID> problems) throws StringTooShortException, InvalidEmailException {
        super(userID, email, phoneNumber);
        this.problems = problems;
    }

    public Patient(String userID) throws StringTooShortException {
        super(userID);
    }

    public List<UUID> getProblems() {
        return problems;
    }

    public void setProblems(List<UUID> problems) {
        this.problems = problems;
    }

    public void addProblem(UUID problem) {

    }

    public void addProblem(Problem problem) {

    }

    public UUID getFrontPhoto() {
        return frontPhoto;
    }

    public void setFrontPhoto(UUID frontPhoto) {
        this.frontPhoto = frontPhoto;
    }

    public UUID getBackPhoto() {
        return backPhoto;
    }

    public void setBackPhoto(UUID backPhoto) {
        this.backPhoto = backPhoto;
    }
}
