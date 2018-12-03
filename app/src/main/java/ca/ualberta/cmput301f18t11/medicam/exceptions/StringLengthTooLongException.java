package ca.ualberta.cmput301f18t11.medicam.exceptions;


/**
 *  StringLengthTooLongException to be thrown on string fields where a max length is present and exceeded.
 */
public class StringLengthTooLongException extends RuntimeException {
    public StringLengthTooLongException(){
        super("The message is too long. Please use fewer characters.");
    }
}
