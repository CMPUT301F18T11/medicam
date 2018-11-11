package ca.ualberta.cmput301f18t11.medicam.models.abstracts;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public abstract class Record extends PersistedModel {
    private String title;
    private String description;
    private Date timestamp;
    private ArrayList<String> tags;

    public Record(UUID uuid) {
        super(uuid);
    }

    public Record() {
        super();
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

    public boolean search(String search_term) {
        for (String tag : getTags()) {
            if (tag.contains(search_term)) {
                return true;
            }
        }
        return false;
    }
}

