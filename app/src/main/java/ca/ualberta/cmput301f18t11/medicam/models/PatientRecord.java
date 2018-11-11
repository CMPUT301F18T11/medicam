package ca.ualberta.cmput301f18t11.medicam.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.UUID;

import ca.ualberta.cmput301f18t11.medicam.models.abstracts.Record;

public class PatientRecord extends Record {
    private UUID patient;
    private Collection<UUID> attachments = new ArrayList<>();
    private Collection<Enumeration> location;
    private ArrayList<String> tags;

    public PatientRecord(UUID uuid) {
        super(uuid);
    }

    public PatientRecord(UUID uuid, UUID patient) {
        super(uuid);
        this.patient = patient;
    }

    public PatientRecord() {
        super();
    }

    public void addAttachment(UUID attachment_uuid){
        attachments.add(attachment_uuid);
    }
    public void removeAttachment(UUID attachment_uuid){
        if(this.hasAttachment(attachment_uuid)){
            attachments.remove(attachment_uuid);
        }
    }
    public Collection<UUID> getAttachmentsUUIDS(){ return attachments;}

    public boolean hasAttachment(UUID attachment_uuid){
        return attachments.contains(attachment_uuid);
    }

    public UUID getPatient() {
        return patient;
    }

    public void setPatient(UUID patient) {
        this.patient = patient;
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
