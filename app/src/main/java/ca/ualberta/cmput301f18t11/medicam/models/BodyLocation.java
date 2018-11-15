package ca.ualberta.cmput301f18t11.medicam.models;

import java.util.UUID;

public class BodyLocation {
    private String bodyParts;
    private UUID bodyLocationUUID;

    public void setBodyParts(String bodyParts){
        this.bodyParts = bodyParts;
    }

    public String getBodyParts(){
        return this.bodyParts;
    }

    public void setBodyLocationUUID(UUID bodyLocationUUID) {
        this.bodyLocationUUID = bodyLocationUUID;
    }
    public String getBodyLocationUUID() {
        return this.bodyLocationUUID.toString();
    }
}
