package ca.ualberta.cmput301f18t11.medicam.models.abstracts;


import java.util.ArrayList;
import java.util.Date;

import ca.ualberta.cmput301f18t11.medicam.models.attachments.BodyLocation;

public abstract class Record extends PersistedModel {
    private String title;
    private String description;
    private Date timestamp;
    private String creatorUUID;


    public Record(String uuid) {
        super(uuid);
    }

    public Record() {
        super();
    }

    public Record(String uuid, String title, String description, Date timestamp, String creatorUUID) {
        super(uuid);
        this.title = title;
        this.description = description;
        this.timestamp = timestamp;
        this.creatorUUID = creatorUUID;
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
    public String getCreatorUUID() {
        return creatorUUID;
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
     * @param creatorUUID <code>String</code> type UUID that represents the unique identifier of the <code>User</code>
     *         object that this <code>Record</code> object is associated with.
     */
    public void setCreatorUUID(String creatorUUID) {
        this.creatorUUID = creatorUUID;
    }
}

