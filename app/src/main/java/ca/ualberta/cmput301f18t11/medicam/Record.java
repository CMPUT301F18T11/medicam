package ca.ualberta.cmput301f18t11.medicam;

import java.util.Date;

public abstract class Record extends PersistedModel {
    private String title;
    private String description;
    private Date timestamp;

    //get methods
    public String getTitle(){ return title; }
    public String getDescription() { return description; }
    public Date getTimestamp() { return timestamp; }

    //set methods
    public void setTitle(String t){ this.title = t; }
    public void setDescription(String d) { this.description = d; }
    public void setTimestamp(Date ts){ this.timestamp = ts; }
}
