package ca.ualberta.cmput301f18t11.medicam;

import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.UUID;

public class PatientRecord extends Record {
    private Collection<UUID> attachements;
    private Collection<Enumeration<Double>> location;

    public void addAttachment(UUID attachment_uuid){}
    public void removeAttachment(UUID attachment_uuid){}
    public Collection<UUID> getAttachementsUUIDS(){ return attachements;}
}
