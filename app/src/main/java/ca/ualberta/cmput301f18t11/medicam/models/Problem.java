package ca.ualberta.cmput301f18t11.medicam.models;


import java.util.ArrayList;
import java.util.Date;

import ca.ualberta.cmput301f18t11.medicam.exceptions.StringLengthTooLongException;
import ca.ualberta.cmput301f18t11.medicam.models.abstracts.PersistedModel;
import ca.ualberta.cmput301f18t11.medicam.models.abstracts.Record;

/**
 * Model for a problem that tracks an affliction that a patient wants to document.
 * <p>
 *     That is, a <code>Problem</code> object that holds: a title(<code>String</code>), date that the
 *     affliction started(<code>Date</code>), description of the affliction(<code>String</code>), and
 *     a two lists containing <code>PatientRecord</code> objects and <code>CareProvider</code> objects
 *     respectively collected by their uniquely identifiable <code>String</code> type uuids.
 * </p>
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
        this.setTitle(title);
        this.date_started = date;
        this.setDescription(desc);
    }

    /**
     * Constructor that captures inputs for the uuid(<code>String</code>), title(<code>String</code>),
     * description(<code>String</code>), patientRecords(<code>ArrayList<String></code>), and
     * careProviderRecords(<code>ArrayList<String></code>) fields of this <code>Problem</code> object.
     *
     * @param uuid <code>String</code> to represent the unique identifier for this <code>Problem</code> object.
     * @param title <code>String</code> that represents the title for this problem.
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

    /**
     * Constructor that sets only the uuid(<code>String</code>), date_started(<code>Date</code>), and
     * description(<code>String</code>) fields of this <code>Problem</code> object.
     *
     * @param uuid <code>String</code> to represent the unique identifier for this <code>Problem</code> object.
     * @param title <code>String</code> that represents the title for this problem
     * @param date_started <code>Date</code> that represents the date that the issue this <code>Problem</code>
     *                    tracks began. User defined, else it defaults to the date the <code>Problem</code>
     *                    object was created.
     * @param description<code>String</code> description of the issue that this <code>Problem</code> object
     *                         is meant to keep track of.
     */

    public Problem(String uuid, String title, Date date_started, String description) {
        super(uuid);
        this.title = title;
        this.date_started = date_started;
        this.description = description;
    }

    /**
     * Gets the title associated with this problem.
     *
     * @return <code>String</code> that represents the title for this problem.
     */

    public String getTitle(){
        return this.title;
    }


    /**
     * Sets the title associated with this problem. The title parameter must be less than MAX_TITLE_CHARS
     * long, or else a <code>StringLengthTooLongException</code> will be thrown.
     *
     * @param title <code>String</code> that will represent the title for this problem.
     * @throws StringLengthTooLongException Warns that the title <code>String</code> is over MAX_TITLE_CHARS
     *                                      long.
     */

    public void setTitle(String title) throws StringLengthTooLongException {
        if (title.length() <= MAX_TITLE_CHARS) {
            this.title = title;
        } else {
            throw new StringLengthTooLongException();
        }
    }


    /**
     * Sets the date that the issue tracked by this problem began.
     *
     * @param date <code>Date</code> that will represent the date that the issue this <code>Problem</code>
     *             tracks began. User defined, else it defaults to the date the <code>Problem</code>
     *             object was created.
     */

    public void setDate(Date date){
        this.date_started = date;
    }


    /**
     * Gets the date that the issue tracked by this problem began.
     *
     * @return <code>Date</code> that represents the date that the issue this <code>Problem</code>
     *      *             tracks began. User defined, else it defaults to the date the <code>Problem</code>
     *      *             object was created.
     */

    public Date getDate(){
        return this.date_started;
    }


    /**
     * Sets the text that describes the issue tracked by this problem. The description <code>String</code>
     * must be under MAX_DESC_CHARS long or the method will throw a <code>StringLengthTooLongException</code>.
     *
     * @param desc <code>String</code> description of the issue that this <code>Problem</code> object
     *             is meant to keep track of.
     * @throws StringLengthTooLongException Warns that the description <code>String</code> is over
     *                                      MAX_DESC_CHARS long.
     */

    public void setDescription(String desc) throws StringLengthTooLongException {

        if (desc.length() <= MAX_DESC_CHARS){
            this.description = desc;
        } else {
            throw new StringLengthTooLongException();
        }

    }


    /**
     * Gets the text that describes the issue tracked by this problem.
     *
     * @return <code>String</code> description of the issue that this <code>Problem</code> object
     *         is meant to keep track of.
     */

    public String getDescription(){
        return this.description;
    }


    //Adding/deleting records


    /**
     * Returns true if the problem has the specified record, false if not.
     * <p>
     *     That is, the method checks if the uuid <code>String</code> 'record' is contained by
     *     this <code>Problem</code> object's patientRecords <code>List</code> or careProviderRecords
     *     <code>List</code>. It returns true if it is found in one or both lists and false otherwise.
     * </p>
     *
     * @param record <code>String</code> uuid that represents the <code>Record</code> object that is
     *               to be found or not found in this <code>Problem</code> object.
     * @return true if the record was found, false if it was not.
     */

    public boolean hasRecord(String record){
        return this.patientRecords.contains(record) || this.careProviderRecords.contains(record);
    }


    /**
     * Adds a patient record by its uuid to the list of patient records that held by this problem.
     *
     * @param record <code>String</code> that represents the <code>PatientRecord</code> object that is to
     *               be added to the <code>List</code> of patient records held by this <code>Problem</code>
     *               object.
     */

    public void addPatientRecord(String record){
        this.patientRecords.add(record);
    }


    /**
     * Adds a care provider record by its uuid to the list of care provider records that held by this problem.
     *
     * @param record <code>String</code> that represents the <code>CareProviderRecord</code> object that is to
     *               be added to the <code>List</code> of care provider records held by this <code>Problem</code>
     *               object.
     */

    public void addCareProviderRecord(String record){
        this.careProviderRecords.add(record);
    }


    /**
     * Removes a record by its index in the list of patient records that held by this problem.
     *
     * @param index <code>int</code> that represents the position of the <code>PatientRecord</code>
     *              object that is to be removed from to the <code>List</code> of patient records
     *              held by this <code>Problem</code> object.
     */

    public void deletePatientRecord(int index){
        this.patientRecords.remove(index);
    }


    /**
     * Removes a record by its uuid <code>String</code> in the list of patient records that held by this problem.
     *
     * @param record <code>String</code> that represents the <code>PatientRecord</code> object that is to
     *               be removed from to the <code>List</code> of patient records held by this <code>Problem</code>
     *               object.
     */

    public void deletePatientRecord(String record){
        this.patientRecords.remove(record);
    }


    /**
     * Removes a record by its index in the list of care provider records that held by this problem.
     *
     * @param index <code>int</code> that represents the position of the <code>CareProviderRecord</code>
     *              object that is to be removed from to the <code>List</code> of care provider records
     *              held by this <code>Problem</code> object.
     */

    public void deleteCareProviderRecord(int index){
        this.careProviderRecords.remove(index);
    }


    /**
     * Removes a record by its uuid <code>String</code> in the list of care provider records that held by this problem.
     *
     * @param record <code>String</code> that represents the position of the <code>CareProviderRecord</code>
     *              object that is to be removed from to the <code>List</code> of care provider records
     *              held by this <code>Problem</code> object.
     */

    public void deleteCareProviderRecord(String record){
        this.careProviderRecords.remove(record);
    }


    /**
     * Gets the list of patient records that are held by this problem.
     *
     * @return <code>ArrayList<String></code> of <code>String</code> type uuids that uniquely identify
     *         each <code>PatientRecord</code> held by this <code>Problem</code> object.
     */

    public ArrayList<String> getPatientRecords(){
        return this.patientRecords;
    }


    /**
     * Gets the list of care provider records that are held by this problem.
     *
     * @return <code>ArrayList<String></code> of <code>String</code> type uuids that uniquely identify
     *         each <code>CareProviderRecord</code> held by this <code>Problem</code> object.
     */
    public ArrayList<String> getCareProviderRecords() {
        return careProviderRecords;
    }

    /**
     * Returns all of this <code>Problem</code> object's components as a <code>String</code>
     *
     * @return the title and description of this <code>Problem</code> concatenated as a <code>String</code>
     *          in the format "Title: title String
     *                         Detail: description string".
     * @return
     */
    public String toString(){
        return "Title: " + this.title +"\n"+"Detail: "+ description;
    }

}

