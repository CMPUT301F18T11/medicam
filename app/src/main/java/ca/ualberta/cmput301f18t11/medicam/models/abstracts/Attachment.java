package ca.ualberta.cmput301f18t11.medicam.models.abstracts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public abstract class Attachment {
    private String attachment_uuid;
    private Collection<String> details = new ArrayList<>();
    
    public String getAttachment_uuid(){
        return attachment_uuid;
    }
    
    public Collection<String> getDetail(){
        return details;
    }
}
