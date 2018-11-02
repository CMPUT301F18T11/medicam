package ca.ualberta.cmput301f18t11.medicam;

public class ReassignmentException extends RuntimeException {
    //This exception might later be changed to a checked one or removed entirely
    public ReassignmentException() { super(); }
    public ReassignmentException(String message){ super(message); }
    public ReassignmentException(String message, Throwable cause){ super(message, cause); }
}
