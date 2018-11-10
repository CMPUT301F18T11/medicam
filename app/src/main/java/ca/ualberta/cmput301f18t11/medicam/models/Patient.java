package ca.ualberta.cmput301f18t11.medicam.models;


import java.util.ArrayList;
import java.util.List;

import ca.ualberta.cmput301f18t11.medicam.models.abstracts.User;
import ca.ualberta.cmput301f18t11.medicam.exceptions.InvalidEmailException;
import ca.ualberta.cmput301f18t11.medicam.exceptions.StringTooShortException;

public class Patient extends User {

    private List<String> problems = new ArrayList<>();
    private String frontPhoto;
    private String backPhoto;

    public Patient() {
    }

    public Patient(String userID, String email, String phoneNumber, List<String> problems) throws StringTooShortException, InvalidEmailException {
        super(userID, email, phoneNumber);
        this.problems = problems;
    }

    public Patient(String userID) throws StringTooShortException {
        super(userID);
    }

    public Patient(String userID, String email, String phoneNumber, List<String> problems, String frontPhoto, String backPhoto) throws StringTooShortException, InvalidEmailException {
        super(userID, email, phoneNumber);
        this.problems = problems;
        this.frontPhoto = frontPhoto;
        this.backPhoto = backPhoto;
    }

    public List<String> getProblems() {
        return problems;
    }

    public void setProblems(List<String> problems) {
        this.problems = problems;
    }

    public void addProblem(String problem)
    {
        problems.add(problem);
    }

    public void addProblem(Problem problem) {
    }

    public String getFrontPhoto() {
        return frontPhoto;
    }

    public void setFrontPhoto(String frontPhoto) {
        this.frontPhoto = frontPhoto;
    }

    public String getBackPhoto() {
        return backPhoto;
    }

    public void setBackPhoto(String backPhoto) {
        this.backPhoto = backPhoto;
    }




}
