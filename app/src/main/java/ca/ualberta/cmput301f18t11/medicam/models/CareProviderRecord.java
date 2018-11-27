package ca.ualberta.cmput301f18t11.medicam.models;

import java.util.UUID;

import ca.ualberta.cmput301f18t11.medicam.exceptions.ReassignmentException;
import ca.ualberta.cmput301f18t11.medicam.models.abstracts.Record;

/**
 * A basic extension of the <code>Record</code> object that simply attributes the record
 * to a specified <code>CareProvider</code> object by that care provider's uuid.
 */
public class CareProviderRecord extends Record {
    private String care_provider;

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
     * Gets the care provider that this <code>CareProviderRecord</code> is associated with.
     *
     * @return <code>String</code> type uuid that represents the unique identifier of the <code>CareProvider</code>
     *         object that this <code>CareProviderRecord</code> object is associated with.
     */
    public String getCare_provider() {
        return care_provider;
    }

    /**
     * Sets the care provider that this <code>CareProviderRecord</code> is associated with.
     * <p>
     *     Each <code>CareProviderRecord</code> can only be associated with one <code>CareProvider</code>
     *     object ever. Therefore, if someone attempts to reassign this record to another care provider then
     *     this method will fail and throw a ReassignmentException.
     *
     * @param assigned_care_provider <code>String</code> type uuid that represents the unique identifier of the <code>CareProvider</code>
     *         object that this <code>CareProviderRecord</code> object is associated with.
     * @throws ReassignmentException Warns that this record has already been attributed to a care provider
     *                               and should not be reassigned.
     */
    public void setCare_provider (String assigned_care_provider) throws ReassignmentException {
        if(this.care_provider == null){
            this.care_provider = assigned_care_provider;
        }else {
            throw new ReassignmentException("This record is already attributed to care provider with UUID: " + care_provider.toString());
        }
    }

<<<<<<< HEAD
    /**
     * Returns all of this <code>CareProviderRecord</code> object's important components as a <code>String</code>
     * <p>
     *     That is, it return the title and the description of the record as a formatted string.
     * </p>
     *
     * @return the title and description of this <code>PatientRecord</code> concatenated as a <code>String</code>
     *         in the format "Title: title String
     *                        Description: comment string".
     */
    public String toString(){
        return "Doctor Says: " + getTitle() + "\n"+"Comment: " + getDescription()+"\n";
    }

=======
    public String toString(){
        return "Doctor Says: " + getTitle() + "\n"+"Comment: " + getDescription()+"\n";
    }
>>>>>>> 77e512af75885e3a7e6c2767b26566b6059795cc
}
