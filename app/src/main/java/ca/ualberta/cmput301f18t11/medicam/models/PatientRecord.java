package ca.ualberta.cmput301f18t11.medicam.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.UUID;

import ca.ualberta.cmput301f18t11.medicam.exceptions.ReassignmentException;
import ca.ualberta.cmput301f18t11.medicam.models.abstracts.Record;

public class PatientRecord extends Record {
    private UUID problem;
    private Collection<UUID> attachements = new ArrayList<UUID>();
    private Collection<Enumeration> location;
    private ArrayList<String> tags;

    public UUID getProblem() {
        return problem;
    }

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

    public void setProblem (UUID assigned_patient) throws ReassignmentException {
        if(this.problem == null){
            this.problem = assigned_patient;
        }else {
            throw new ReassignmentException("This record is already attributed to problem with UUID: " + problem.toString());
        }
    }

//    @Override
//    public ArrayList<String> getTags(){
//        this should probably eventually get moved to PersistedModel
//        for(Attachment attachment: attachements){
//            for(String a_tag: attachment.getTags()){
//                tags.add(a_tag);
//            }
//        }
//        Consider setting this up so that tags are updated when the
//         objects property changes.
//    }
}
