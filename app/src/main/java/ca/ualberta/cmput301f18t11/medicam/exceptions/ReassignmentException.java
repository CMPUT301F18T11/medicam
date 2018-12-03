package ca.ualberta.cmput301f18t11.medicam.exceptions;


/**
 *  ReassignmentException to be thrown when changing ids, ids such as uuid fields can be changed, but
 *  only in exceptional situations and classes.
 */
public class ReassignmentException extends RuntimeException {
    //This exception might later be changed to a checked one or removed entirely
    public ReassignmentException() { super(); }
    public ReassignmentException(String message){ super(message); }
    public ReassignmentException(String message, Throwable cause){ super(message, cause); }
}
