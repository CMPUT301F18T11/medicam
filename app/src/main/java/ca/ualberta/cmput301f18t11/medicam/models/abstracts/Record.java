package ca.ualberta.cmput301f18t11.medicam.models.abstracts;


import java.util.Date;


/** Abstract class for record objects to be attached to a problem. Contains basis for a record's key fields.
 */
public abstract class Record extends PersistedModel {
    private String title;
    private String description;
    private Date timestamp;
    private String problemUUID;


    /** Constructor for records using a user supplied field.
     *
     * @param uuid
     */
    public Record(String uuid) {
        super(uuid);
    }

    public Record() {
        super();
    }

    /** Full constructor for record's key fields, to be delegated to by concrete classes.
     *
     * @param uuid uuid of object to be created
     * @param title title of record
     * @param description description of record
     * @param timestamp time of creation of record or time of relevance
     * @param problemUUID problem uuid of that the record is associated to
     */
    public Record(String uuid, String title, String description, Date timestamp, String problemUUID) {
        super(uuid);
        this.title = title;
        this.description = description;
        this.timestamp = timestamp;
        this.problemUUID = problemUUID;
    }

    //get methods
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * Gets the user that this <code>Record</code> is associated with.
     *
     * @return <code>String</code> type UUID that represents the unique identifier of the <code>User</code>
     *         object that this <code>Record</code> object is associated with.
     */
    public String getProblemUUID() {
        return problemUUID;
    }

    //set methods
    public void setTitle(String t) {
        this.title = t;
    }

    public void setDescription(String d) {
        this.description = d;
    }

    public void setTimestamp(Date ts) {
        this.timestamp = ts;
    }

    /**
     * Sets the user that this <code>Record</code> is associated with.\
     *
     * @param problemUUID <code>String</code> type UUID that represents the unique identifier of the <code>User</code>
     *         object that this <code>Record</code> object is associated with.
     */
    public void setProblemUUID(String problemUUID) {
        this.problemUUID = problemUUID;
    }
}

