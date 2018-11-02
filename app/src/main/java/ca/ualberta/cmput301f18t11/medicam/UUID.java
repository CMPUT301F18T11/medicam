package ca.ualberta.cmput301f18t11.medicam;

import android.media.Image;

import java.util.List;

public class UUID extends User {
    private List<Problem> problems;
    private Image frontImage;
    private Image backImage;

    public List<Problem> getProblems() {
        return problems;
    }

    public void setProblems(List<Problem> problems) {
        this.problems = problems;
    }

    public void addProblem(Problem problem) {

    }

    public void removeProblem(Problem problem) {

    }

    public void removeProblem(String UUID) {

    }
}
