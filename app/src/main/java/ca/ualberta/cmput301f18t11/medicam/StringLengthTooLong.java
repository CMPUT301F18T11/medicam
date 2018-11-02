package ca.ualberta.cmput301f18t11.medicam;

public class StringLengthTooLong extends Exception {
    StringLengthTooLong(){
        super("The message is too long. Please use fewer characters.");
    }
}
