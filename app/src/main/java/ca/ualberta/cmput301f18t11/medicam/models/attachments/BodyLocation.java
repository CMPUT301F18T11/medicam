package ca.ualberta.cmput301f18t11.medicam.models.attachments;


import android.graphics.Bitmap;
import android.util.Pair;

import java.util.UUID;

import ca.ualberta.cmput301f18t11.medicam.models.abstracts.Attachment;

/**
 * <code>Attachment</code> that represents the afflicted body part associated with the <code>Record</code> that
 * holds this <code>Attachment</code>
 */
public class BodyLocation extends Attachment {
    private Pair<Integer,Integer> imageCoordinates;
    private String bodyPart;
    private Bitmap frontPhoto;
    private Bitmap backPhoto;

    /**
     * Sets the String representing the body part indicated by this body location object.
     *
     * @param user_bodyPart the String representing the body part indicated by this body location object.
     */
    public void setBodyPart(String user_bodyPart){
        this.bodyPart = user_bodyPart;
    }

    /**
     * Sets the front-facing, full-body photo held by this body location attachment.
     *
     * @param user_frontPhoto <code>Bitmap</code> type image of the users full body facing forward.
     */
    public void setFrontPhoto(Bitmap user_frontPhoto) {
        this.frontPhoto = user_frontPhoto;
    }

    /**
     * Sets the front-facing, full-body photo held by this body location attachment.
     *
     * @param user_backPhoto <code>Bitmap</code> type image of the users full body facing backward.
     */
    public void setBackPhoto(Bitmap user_backPhoto) {
        this.backPhoto = backPhoto;
    }

    /**
     * Gets the String representing the body part indicated by this body location object.
     *
     * @return  the String representing the body part indicated by this body location object.
     */
    public String getBodyPart() {
        return bodyPart;
    }

    /**
     * Gets the front-facing, full-body photo held by this body location attachment.
     *
     * @return <code>Bitmap</code> type image of the users full body facing forward held by this
     *          <code>BodyLocation</code> object.
     */
    public Bitmap getFrontPhoto() {
        return frontPhoto;
    }

    /**
     * Gets the back-facing, full-body photo held by this body location attachment.
     *
     * @return <code>Bitmap</code> type image of the users full body facing backward held by this
     *          <code>BodyLocation</code> object.
     */
    public Bitmap getBackPhoto() {
        return backPhoto;
    }
}
