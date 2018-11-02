package ca.ualberta.cmput301f18t11.medicam;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.UUID;

public class PatientRecord extends Record {
    private UUID problem;
    private Collection<UUID> attachements;
    private Collection<Enumeration> location;
    private ArrayList<String> tags;

    public void addAttachment(UUID attachment_uuid){
        attachements.add(attachment_uuid);
    }
    public void removeAttachment(UUID attachment_uuid){
        if(this.hasAttachment(attachment_uuid)){
            attachements.remove(attachment_uuid);
        }
    }
    public Collection<UUID> getAttachementsUUIDS(){ return attachements;}

    public boolean hasAttachment(UUID attachment_uuid){
        return attachements.contains(attachment_uuid);
    }

    public void setPatient (UUID assigned_patient) throws ReassignmentException {
        if(this.problem.toString().isEmpty()){
            this.problem = assigned_patient;
        }else {
            throw new ReassignmentException("This record is already attributed to care provider with UUID: " + problem.toString());
        }
    }

//    @Override
//    public ArrayList<String> getTags(){
//        //this should probably eventually get moved to PersistedModel
//        for(Attachment attachment: attachements){
//            for(String a_tag: attachment.getTags()){
//                tags.add(a_tag);
//            }
//        }
//    }
}
