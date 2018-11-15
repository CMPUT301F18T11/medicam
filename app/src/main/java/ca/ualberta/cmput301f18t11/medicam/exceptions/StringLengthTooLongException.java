package ca.ualberta.cmput301f18t11.medicam.exceptions;

public class StringLengthTooLongException extends RuntimeException {
    public StringLengthTooLongException(){
        super("The message is too long. Please use fewer characters.");
    }
}
