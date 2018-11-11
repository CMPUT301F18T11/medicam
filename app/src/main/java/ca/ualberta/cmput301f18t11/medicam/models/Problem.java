package ca.ualberta.cmput301f18t11.medicam.models;


import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import ca.ualberta.cmput301f18t11.medicam.exceptions.StringLengthTooLongException;
import ca.ualberta.cmput301f18t11.medicam.models.abstracts.PersistedModel;


//Dummy Problem Class
public class Problem extends PersistedModel {
    private static final int MAX_DESC_CHARS = 300;
    private static final int MAX_TITLE_CHARS = 30;

    private String title;
    private Date date_started = new Date();
    private String description;
    //TODO: Implement Records UUID and Collection
    private ArrayList<UUID> patientRecords = new ArrayList<>();
    private ArrayList<UUID> careProviderRecords = new ArrayList<>();

    public Problem(String title, Date date, String desc){
        super();
        this.title = title;
        this.date_started = date;
        this.description = desc;
    }

    public Problem(UUID uuid, String title, String description,
                   ArrayList<UUID> patientRecords, ArrayList<UUID> careProviderRecords) {
        super(uuid);
        setTitle(title);
        setDescription(description);
        this.patientRecords.addAll(patientRecords);
        this.careProviderRecords.addAll(careProviderRecords);
    }

    public Problem(UUID uuid, String title, Date date_started, String description) {
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

    public boolean hasRecord(UUID record){
        return this.patientRecords.contains(record) || this.careProviderRecords.contains(record);
    }

    public void addPatientRecord(UUID record){
        this.patientRecords.add(record);
    }

    public void addCareProviderRecord(UUID record){
        this.careProviderRecords.add(record);
    }

    public void deletePatientRecord(int index){
        this.patientRecords.remove(index);
    }

    public void deletePatientRecord(UUID record){
        this.patientRecords.remove(record);
    }

    public void deleteCareProviderRecord(int index){
        this.careProviderRecords.remove(index);
    }

    public void deleteCareProviderRecord(UUID record){
        this.careProviderRecords.remove(record);
    }

    public ArrayList<UUID> getPatientRecords(){
        return this.patientRecords;
    }

    public ArrayList<UUID> getCareProviderRecords() {
        return careProviderRecords;
    }
}

