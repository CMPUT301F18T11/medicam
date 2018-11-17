package ca.ualberta.cmput301f18t11.medicam.models;

import java.util.UUID;

import ca.ualberta.cmput301f18t11.medicam.models.abstracts.Attachment;

public class BodyLocation extends Attachment {
    private String bodyParts;


    public void setBodyParts(String bodyParts){
        this.bodyParts = bodyParts;
    }

    public String getBodyParts(){
        return this.bodyParts;
    }

}
