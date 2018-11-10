package ca.ualberta.cmput301f18t11.medicam.models;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ca.ualberta.cmput301f18t11.medicam.Problem;
import ca.ualberta.cmput301f18t11.medicam.User;
import ca.ualberta.cmput301f18t11.medicam.controllers.ElasticSearchController;
import ca.ualberta.cmput301f18t11.medicam.controllers.InternalStorageController;
import ca.ualberta.cmput301f18t11.medicam.exceptions.InvalidEmailException;
import ca.ualberta.cmput301f18t11.medicam.exceptions.StringTooShortException;
import io.searchbox.annotations.JestId;

public class Patient extends User {

    private List<UUID> problems = new ArrayList<>();
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

    public Patient(String userID, String email, String phoneNumber, List<UUID> problems, UUID frontPhoto, UUID backPhoto) throws StringTooShortException, InvalidEmailException {
        super(userID, email, phoneNumber);
        this.problems = problems;
        this.frontPhoto = frontPhoto;
        this.backPhoto = backPhoto;
    }

    public List<UUID> getProblems() {
        return problems;
    }

    public void setProblems(List<UUID> problems) {
        this.problems = problems;
    }

    public void addProblem(UUID problem)
    {
        problems.add(problem);
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
