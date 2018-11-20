package ca.ualberta.cmput301f18t11.medicam.models.abstracts;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * Represents the base functionality that all record objects in the app should extend.
 * <p>
 *     That is, each record should have a title, description, timestamp and collection that represents
 *     all the aforementioned variables in a collection for searching.
 */
public abstract class Record extends PersistedModel {
    private String title;
    private String description;
    private Date timestamp;
    private ArrayList<String> tags;

    /**
     * Constructor for setting a specific unique identifier
     *
     * @param uuid a string that is used to uniquely identify this object
     * @see PersistedModel
     */
    public Record(String uuid) {
        super(uuid);
    }

    /**
     * Constructor for when the uuid is not specifically defined. Takes no parameters.
     *
     * @see PersistedModel
     */
    public Record() {
        super();
    }

    //get methods
    /**
     * Returns the user defined title for this record.
     *
     * @return A String that represents the title specified for this record.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the user defined description of this record.
     *
     * @return A String that represents the description specified for this record.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the time at which this record was created.
     *
     * @return A Date that represents the timestamp specified for this record.
     */
    public Date getTimestamp() {
        return timestamp;
    }

    //set methods

    /**
     * To set the title for this record
     *
     * @param t A String that will be title associated with this record.
     */
    public void setTitle(String t) {
        this.title = t;
    }

    /**
     * To set the description for this record
     *
     * @param d A String that will be description associated with this record.
     */
    public void setDescription(String d) {
        this.description = d;
    }

    /**
     * To set the timestamp for this record
     *
     * @param ts A Date that will be time stamp associated with this record.
     */
    public void setTimestamp(Date ts) {
        this.timestamp = ts;
    }

    /**
     * A placeholder function that allows the record to be identified by its components
     * so that it can be searched by a keyword in its components.
     *
     * @return a ArrayList of Strings that contains all the records components, including title, desciption and timestamp
     */
    public ArrayList<String> getTags() {
        //this should probably eventually get moved to PersistedModel

        //Consider setting this up so that tags are updated when the
        // objects property changes.
        tags = new ArrayList<String>();

        tags.add(getTitle());
        tags.add(getDescription());
        tags.add(getTimestamp().toString());

        return tags;
    }

    /**
     * A placeholder function that searches through the record's components to see if they include
     * a specified String 'search_term'.
     *
     * @param search_term the String that is to be found or not found within the record's components.
     * @return a boolean value that is true if the search term was found in the records components, false if it was not.
     */
    public boolean search(String search_term) {
        for (String tag : getTags()) {
            if (tag.contains(search_term)) {
                return true;
            }
        }
        return false;
    }
}

