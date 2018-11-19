package ca.ualberta.cmput301f18t11.medicam.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.UUID;

import ca.ualberta.cmput301f18t11.medicam.models.abstracts.Record;

public class PatientRecord extends Record {
    private String patient;
    private Collection<String> attachments = new ArrayList<>();
    private Collection<Enumeration> location;

    public PatientRecord(String uuid) {
        super(uuid);
    }

    public PatientRecord(String uuid, String patient) {
        super(uuid);
        this.patient = patient;
    }

    public PatientRecord() {
        super();
    }

    public void addAttachment(String attachment_uuid){
        attachments.add(attachment_uuid);
    }
    public void removeAttachment(String attachment_uuid){
        if(this.hasAttachment(attachment_uuid)){
            attachments.remove(attachment_uuid);
        }
    }
    public Collection<String> getAttachmentsUUIDS(){ return attachments;}

    public boolean hasAttachment(String attachment_uuid){
        return attachments.contains(attachment_uuid);
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public String toString(){
        java.text.SimpleDateFormat timeformat = new java.text.SimpleDateFormat("dd-MM-yyyy         HH:mm");
        String timeStr = timeformat.format(getTimestamp());
        return "Title: "+getTitle()+"\n" +"Time: "+timeStr + "\n";
    }
    //    Don't think we had a "Tags" field for records.
//    @Override
//    public ArrayList<String> getTags(){
//        this should probably eventually get moved to PersistedModel
//        for(Attachment attachment: attachments){
//            for(String a_tag: attachment.getTags()){
//                tags.add(a_tag);
//            }
//        }
//        Consider setting this up so that tags are updated when the
//         objects property changes.
//    }
}
