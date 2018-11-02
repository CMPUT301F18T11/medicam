package ca.ualberta.cmput301f18t11.medicam;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;


//Dummy Problem Class
public class Problem extends PersistedModel{
    private String title;
    private Date date_started = new Date();
    private String description;
    private int MAX_DESC_CHARS = 1; //Normal value is 300, set to 1 for testing
    private int MAX_TITLE_CHARS = 1; //Normal value is 30, set to 1 for testing
    //TODO: Implement Records UUID and Collection
    private ArrayList<UUID> records = new ArrayList<UUID>() {
    };

    public Problem(String title, Date date, String desc){
        this.title = title;
        this.date_started = date;
        this.description = desc;
        //this.records.add(record);

    }
    //Getters and Setters
    public String getTitle(){
        return this.title;
    }

    public void setTitle(String title) throws StringLengthTooLong {
        if (title.length() <= MAX_TITLE_CHARS) {
            this.title = title;
        } else {
            throw new StringLengthTooLong();
        }
    }

    public void setDate(Date date){
        this.date_started = date;
    }

    public Date getDate(){
        return this.date_started;
    }

    public void setDescription(String desc) throws StringLengthTooLong{

        if (desc.length() <= MAX_DESC_CHARS){
            this.description = desc;
        } else {
            throw new StringLengthTooLong();
        }

    }

    public String getDescription(){
        return this.description;
    }


    //Adding/deleting records

    public boolean hasRecord(UUID record){
        return this.records.contains(record);
    }

    public void addRecord(UUID record){
        this.records.add(record);

    }

    public void deleteRecord(int index){
        this.records.remove(index);
    }

    public void deleteRecord(UUID record){
        this.records.remove(record);
    }

    public ArrayList<UUID> getRecords(){
        return this.records;
    }

}

