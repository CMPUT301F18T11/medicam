package ca.ualberta.cmput301f18t11.medicam.models;

import java.util.UUID;

import ca.ualberta.cmput301f18t11.medicam.exceptions.ReassignmentException;
import ca.ualberta.cmput301f18t11.medicam.models.abstracts.Record;

/**
 * A basic extension of the <code>Record</code> object that simply attributes the record
 * to a specified <code>CareProvider</code> object by that care provider's uuid.
 */
public class CareProviderRecord extends Record {

    /**
     * Constructor that creates a random uuid for this <code>CareProviderRecord</code> object and
     * leave the rest of the object fields empty.
     * @see Record
     */
    public CareProviderRecord(){
        super();
        this.createUuid();
    }

    /**
     * Constructor that sets the unique <code>String</code> type identifier for this object.
     *
     * @param uuid a <code>String</code> that is used to uniquely identify this object.
     * @see Record
     */
    public CareProviderRecord(String uuid) {
        super(uuid);
    }


    /**
     * Returns all of this <code>CareProviderRecord</code> object's important components as a <code>String</code>
     * <p>
     *     That is, it returns the title and the description of the record as a formatted string.
     * </p>
     *
     * @return the title and description of this <code>PatientRecord</code> concatenated as a <code>String</code>
     *         in the format "Title: title String
     *                        Description: comment string".
     */
    public String toString(){
        return "Doctor Says: " + getTitle() + "\n"+"Comment: " + getDescription()+"\n";
    }
}
