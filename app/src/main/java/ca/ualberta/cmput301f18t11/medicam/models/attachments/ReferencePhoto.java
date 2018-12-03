package ca.ualberta.cmput301f18t11.medicam.models.attachments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

import ca.ualberta.cmput301f18t11.medicam.models.abstracts.PersistedModel;


/**
 *  Model for storing information related to a reference photo.
 *
 *  Includes the uuid of the photo, and a bodypart + label to identify the photo.
 */

public class ReferencePhoto implements Serializable {
    private String photoUUID;
    private String bodyPart;
    private String label;
    /**
     * Empty constructor for initializing a empty instance of this object with blank reference_side
     * and referencePhotoUUID fields.
     */
    public ReferencePhoto(){
        super();
    }

    /**
     * Constructor that accepts an image type to be held by this attachment.
     *
     * @param photoUUID
     * @param bodyPart
     * @param label
     */
    public ReferencePhoto(String photoUUID, String bodyPart, String label) {
        this.photoUUID = photoUUID;
        this.bodyPart = bodyPart;
        this.label = label;
    }

    /**
     *
     * @return String uuid of referenced photo
     */
    public String getPhotoUUID() {
        return photoUUID;
    }

    /**
     * Sets referenced photo
     * @param photoUUID
     */
    public void setPhotoUUID(String photoUUID) {
        this.photoUUID = photoUUID;
    }

    /**
     * gets the string descriptor of referenced body part
     * @return
     */
    public String getBodyPart() {
        return bodyPart;
    }

    /**
     * sets referenced bodypart
     * @param bodyPart
     */
    public void setBodyPart(String bodyPart) {
        this.bodyPart = bodyPart;
    }

    /**
     * gets custom user-made label
     * @return
     */
    public String getLabel() {
        return label;
    }

    /**
     * sets user-made label
     * @param label
     */
    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        if (getLabel() == null) {
            return getBodyPart();
        }
        return getBodyPart() + ": " + getLabel();
    }
}
