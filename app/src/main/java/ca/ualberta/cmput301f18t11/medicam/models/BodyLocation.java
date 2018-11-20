package ca.ualberta.cmput301f18t11.medicam.models;

import java.util.UUID;

import ca.ualberta.cmput301f18t11.medicam.models.abstracts.Attachment;

/**
 * <code>Attachment</code> that represents the afflicted body part associated with the <code>Record</code> that
 * holds this <code>Attachment</code>
 */
public class BodyLocation extends Attachment {
    private String bodyParts;


    /**
     * Sets the <code>String</code> that describes the body part associated with this <code>Attachment</code>.
     *
     * @param bodyParts <code>String</code> that to be associated with this <code>Attachment</code>.
     */
    public void setBodyParts(String bodyParts){
        this.bodyParts = bodyParts;
    }

    /**
     * Gets the <code>String</code> that describes the body part associated with this <code>Attachment</code>.
     *
     * @return <code>String</code> that is associated with this <code>Attachment</code>.
     */
    public String getBodyParts(){
        return this.bodyParts;
    }

}
