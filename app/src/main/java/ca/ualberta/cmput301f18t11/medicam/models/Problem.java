package ca.ualberta.cmput301f18t11.medicam.models;


import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import ca.ualberta.cmput301f18t11.medicam.exceptions.StringLengthTooLongException;
import ca.ualberta.cmput301f18t11.medicam.models.abstracts.PersistedModel;
import ca.ualberta.cmput301f18t11.medicam.models.abstracts.Record;


//Dummy Problem Class

/**
 *
 */
public class Problem extends PersistedModel {
    private static final int MAX_DESC_CHARS = 300;
    private static final int MAX_TITLE_CHARS = 30;

    private String title;
    private Date date_started = new Date();
    private String description;
    //TODO: Implement Records UUID and Collection
    private ArrayList<String> patientRecords = new ArrayList<>();
    private ArrayList<String> careProviderRecords = new ArrayList<>();

    /**
     * Constructor that sets the title(<code>String</code>), date_started(<code>Date</code>), and
     * description(<code>String</code>) for this problem.
     *
     * @param title <code>String</code> that represents the title for this problem
     * @param date <code>Date</code> that represents the date that the issue this <code>Problem</code>
     *             tracks began. User defined, else it defaults to the date the <code>Problem</code>
     *             object was created.
     * @param desc <code>String</code> description of the issue that this <code>Problem</code> object
     *             is meant to keep track of.
     */
    public Problem(String title, Date date, String desc){
        super();
        this.title = title;
        this.date_started = date;
        this.description = desc;
    }

    /**
     * Constructor that captures inputs for the uuid(<code>String</code>), title(<code>String</code>),
     * description(<code>String</code>), patientRecords(<code>ArrayList<String></code>), and
     * careProviderRecords(<code>ArrayList<String></code>) fields of this <code>Problem</code> object.
     *
     * @param uuid <code>String</code> to represent the unique identifier for this <code>Problem</code> object.
     * @param title <code>String</code> that represents the title for this problem
     * @param description <code>String</code> description of the issue that this <code>Problem</code> object
     *                    is meant to keep track of.
     * @param patientRecords <code>ArrayList</code> of <code>String</code> objects that uniquely
     *                       identify each <code>PatientRecord</code> object that is associated with
     *                       this <code>Problem</code> object.
     * @param careProviderRecords <code>ArrayList</code> of <code>String</code> objects that uniquely
     *                            identify each <code>CareProviderRecord</code> object that is associated with
     *                            this <code>Problem</code> object.
     */
    public Problem(String uuid, String title, String description,
                   ArrayList<String> patientRecords, ArrayList<String> careProviderRecords) {
        super(uuid);
        setTitle(title);
        setDescription(description);
        this.patientRecords.addAll(patientRecords);
        this.careProviderRecords.addAll(careProviderRecords);
    }

    public Problem(String uuid, String title, Date date_started, String description) {
        super(uuid);
        this.title = title;
        this.date_started = date_started;
        this.description = description;
    }

    //Getters and Setters
    public String getTitle(){
        return this.title;
    }

    public void setTitle(String title) throws StringLengthTooLongException {
        if (title.length() <= MAX_TITLE_CHARS) {
            this.title = title;
        } else {
            throw new StringLengthTooLongException();
        }
    }

    public void setDate(Date date){
        this.date_started = date;
    }

    public Date getDate(){
        return this.date_started;
    }

    public void setDescription(String desc) throws StringLengthTooLongException {

        if (desc.length() <= MAX_DESC_CHARS){
            this.description = desc;
        } else {
            throw new StringLengthTooLongException();
        }

    }

    public String getDescription(){
        return this.description;
    }


    //Adding/deleting records

    public boolean hasRecord(String record){
        return this.patientRecords.contains(record) || this.careProviderRecords.contains(record);
    }

    public void addPatientRecord(String record){
        this.patientRecords.add(record);
    }

    public void addCareProviderRecord(String record){
        this.careProviderRecords.add(record);
    }

    public void deletePatientRecord(int index){
        this.patientRecords.remove(index);
    }

    public void deletePatientRecord(String record){
        this.patientRecords.remove(record);
    }

    public void deleteCareProviderRecord(int index){
        this.careProviderRecords.remove(index);
    }

    public void deleteCareProviderRecord(String record){
        this.careProviderRecords.remove(record);
    }

    public ArrayList<String> getPatientRecords(){
        return this.patientRecords;
    }

    public ArrayList<String> getCareProviderRecords() {
        return careProviderRecords;
    }


    public String toString(){
        return "Title: " + this.title +"\n"+"Detail: "+ description;
    }
}

