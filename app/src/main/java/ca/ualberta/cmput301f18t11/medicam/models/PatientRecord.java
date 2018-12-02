package ca.ualberta.cmput301f18t11.medicam.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ca.ualberta.cmput301f18t11.medicam.models.abstracts.Record;
import ca.ualberta.cmput301f18t11.medicam.models.attachments.BodyLocation;
import ca.ualberta.cmput301f18t11.medicam.models.attachments.Geolocation;


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

    //attachments
    private BodyLocation bodyLocation;
    private Geolocation location;
    private List<String> photoList = new ArrayList<>();

    //Constructors
    /**
     * Constructor that sets the unique <code>String</code> that identifies this <code>PatientRecord</code>
     *
     * @param UUID a <code>String</code> that uniquely identifies this <code>PatientRecord</code> object.
     * @see Record
     */
    public PatientRecord(String UUID) {
        super(UUID);
    }
    /**
     * Empty Constructor for initializing a blank <code>PatientRecord</code>.
     */
    public PatientRecord() {
        super();
    }

    public PatientRecord(String uuid, String title, String description, Date timestamp, String creatorUUID) {
        super(uuid, title, description, timestamp, creatorUUID);
    }

    public PatientRecord(String uuid, String title, String description, Date timestamp, String creatorUUID,
                         BodyLocation bodyLocation, Geolocation location, List<String> photoList) {
        super(uuid, title, description, timestamp, creatorUUID);
        this.bodyLocation = bodyLocation;
        this.location = location;
        this.photoList = photoList;
    }

    public PatientRecord(String uuid, String title, String description, Date timestamp,
                         String creatorUUID, BodyLocation bodyLocation, Geolocation location) {
        super(uuid, title, description, timestamp, creatorUUID);
        this.bodyLocation = bodyLocation;
        this.location = location;
    }

    //end constructors

    //getters
    public BodyLocation getBodyLocation() { return bodyLocation; }
    public Geolocation getLocation() { return location; }
    public String getPhotoFromList(int index) { return photoList.get(index); }
    public List<String> getPhotoList() { return photoList; }
    public String getMostRecentPhoto() {return photoList.get(photoList.size() - 1);}
    //end getters


    //setters
    public void setBodyLocation(BodyLocation bodyLocation) { this.bodyLocation = bodyLocation; }
    public void setLocation(Geolocation user_mapLocation) { this.location = user_mapLocation; }
    public void addPhotoToList(String user_photo){ this.photoList.add(user_photo); }
    //end setters

    /**
     * Returns all of this <code>PatientRecord</code> object's components as a <code>String</code>
     *
     * @return the title and timestamp of this <code>PatientRecord</code> concatenated as a <code>String</code>
     *         in the format "Title: title String
     *                        Time: time string".
     */
    @Override
    public String toString(){
        java.text.SimpleDateFormat timeformat = new java.text.SimpleDateFormat("dd-MM-yyyy         HH:mm");
        String timeStr = timeformat.format(getTimestamp());
        return "Title: "+getTitle()+"\n" +"Time: "+timeStr + "\n";
    }
}
