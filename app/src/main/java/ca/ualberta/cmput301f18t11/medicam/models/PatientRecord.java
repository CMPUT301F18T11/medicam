package ca.ualberta.cmput301f18t11.medicam.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.UUID;

import ca.ualberta.cmput301f18t11.medicam.models.abstracts.Record;


/**
 * A extension of the <code>Record</code> class that allows a patient to record and track an affliction
 * over time.
 * <p>
 *     That is, a model that holds a number of attachments of different types such as a geo-location,
 *     photo and body location in addition to all the fields held by the abstract record class.
 *     This model is also attributed to a specific <code>Patient</code> object by that patient's uniquely
 *     identifiable <code>String</code> type uuid.
 * </p>
 */

public class PatientRecord extends Record {
    private String patient;
    private Collection<String> attachments = new ArrayList<>();
    private Collection<Enumeration> location;


    /**
     * Constructor that sets the unique <code>String</code> that identifies this <code>PatientRecord</code>
     *
     * @param uuid a <code>String</code> that uniquely identifies this <code>PatientRecord</code> object.
     * @see Record
     */

    public PatientRecord(String uuid) {
        super(uuid);
    }


    /**
     * Constructor that sets the unique <code>String</code> that identifies this <code>PatientRecord</code>
     * and assigns which <code>Patient</code> this <code>Record</code> belongs to.
     *
     * @param uuid a <code>String</code> that uniquely identifies this <code>PatientRecord</code> object.
     * @param patient a uuid for the <code>Patient</code> that this <code>Record</code> belongs to.
     * @see Record
     */

    public PatientRecord(String uuid, String patient) {
        super(uuid);
        this.patient = patient;
    }


    /**
     * Empty Constructor for initializing a blank <code>PatientRecord</code>.
     */

    public PatientRecord() {
        super();
    }


    /**
     * Adds a <code>String</code> that uniquely identifies an <code>Attachment</code> that should be
     * associated with this <code>PatientRecord</code> via the attachments <code>Collection</code>.
     *
     * @param attachment_uuid The <code>String</code> that uniquely identifies the <code>Attachment</code> to be
     *                        associate with this <code>PatientRecord</code>.
     */
    public void addAttachment(String attachment_uuid){
        attachments.add(attachment_uuid);
    }

    /**
     * Removes a <code>String</code> that uniquely identifies an <code>Attachment</code> that may be
     * associated with this <code>PatientRecord</code> via the attachments <code>Collection</code>.
     *
     * @param attachment_uuid The <code>String</code> that uniquely identifies the <code>Attachment</code> to be
     *                        removed from this <code>PatientRecord</code>.
     */

    public void removeAttachment(String attachment_uuid){
        if(this.hasAttachment(attachment_uuid)){
            attachments.remove(attachment_uuid);
        }
    }


    /**
     * Returns all <code>Strings</code> that uniquely identify all <code>Attachment</code> objects
     * associated with this <code>PatientRecord</code>.
     *
     * @return A <code>Collection</code> of <code>Strings</code> that uniquely identify all <code>Attachment</code> objects
     *         associated with this <code>PatientRecord</code>.
     */
    public Collection<String> getAttachmentsUUIDS(){ return attachments;}

    /**
     * Returns boolean variable that informs if the <code>Attachment</code> object with specified uuid <code>String</code> is
     * associated with this <code>PatientRecord</code>.
     *
     * @param attachment_uuid the uuid <code>String</code> of <code>Attachment</code> object to be found
     * @return true if the <code>Attachment</code> object with specified uuid <code>String</code> is
     *         associated with this <code>PatientRecord</code>, false if it is not.
     */

    public boolean hasAttachment(String attachment_uuid){
        return attachments.contains(attachment_uuid);
    }


    /**
     * Gets the <code>Patient</code> object that is associate with this <code>PatientRecord</code>.
     *
     * @return Reference to the <code>Patient</code> object that this <code>PatientRecord</code> is associate with.
     */

    public String getPatient() {
        return patient;
    }


    /**
     * Sets the <code>Patient</code> object that is associate with this <code>PatientRecord</code>.
     * @param patient Reference to the <code>Patient</code> object that this <code>PatientRecord</code> is to be associate with.
     */

    public void setPatient(String patient) {
        this.patient = patient;
    }


    /**
     * Returns all of this <code>PatientRecord</code> object's components as a <code>String</code>
     *
     * @return the title and timestamp of this <code>PatientRecord</code> concatenated as a <code>String</code>
     *         in the format "Title: title String
     *                        Time: time string".
     */
    public String toString(){
        java.text.SimpleDateFormat timeformat = new java.text.SimpleDateFormat("dd-MM-yyyy         HH:mm");
        String timeStr = timeformat.format(getTimestamp());
        return "Title: "+getTitle()+"\n" +"Time: "+timeStr + "\n";
    }

    //    Don't think we had a "Tags" field for records.
//    @Override
//    public ArrayList<String> getTags(){
//        this should probably eventually get moved to PersistedModel
//        for(Attachment attachment: attachments){
//            for(String a_tag: attachment.getTags()){
//                tags.add(a_tag);
//            }
//        }
//        Consider setting this up so that tags are updated when the
//         objects property changes.
//    }
}
