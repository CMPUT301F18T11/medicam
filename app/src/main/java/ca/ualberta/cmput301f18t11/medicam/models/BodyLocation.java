package ca.ualberta.cmput301f18t11.medicam.models;

import ca.ualberta.cmput301f18t11.medicam.models.abstracts.Attachment;

public class BodyLocation extends Attachment {
    private String bodyParts;


    public void setBodyParts(String user_body_parts){
        this.bodyParts = user_body_parts;
    }

    public String getBodyParts(){
        return this.bodyParts;
    }

}