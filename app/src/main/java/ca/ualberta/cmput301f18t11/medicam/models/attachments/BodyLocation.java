package ca.ualberta.cmput301f18t11.medicam.models.attachments;


import android.graphics.Bitmap;
import android.util.Pair;

import java.io.Serializable;
import java.util.UUID;

import ca.ualberta.cmput301f18t11.medicam.models.abstracts.Attachment;

/**
 * Model for holding information related to a body-location selected
 * by a patient for a record.
 *
 * Contains coordinates for drawing a marker on the photo,
 * and the UUID of the bodylocation photo.
 */
public class BodyLocation implements Serializable {
    private Pair<Integer, Integer> imageCoordinates;
    private String bodyLocationPhotoUUID;

    /**
     * Null contructor for body location.
     */
    public BodyLocation() {
        super();
    }

    public BodyLocation(Pair<Integer, Integer> imageCoordinates, String bodyLocationPhotoUUID) {
        this.imageCoordinates = imageCoordinates;
        this.bodyLocationPhotoUUID = bodyLocationPhotoUUID;
    }

    public Pair<Integer, Integer> getImageCoordinates() {
        return imageCoordinates;
    }

    public void setImageCoordinates(Pair<Integer, Integer> imageCoordinates) {
        this.imageCoordinates = imageCoordinates;
    }

    public String getBodyLocationPhotoUUID() {
        return bodyLocationPhotoUUID;
    }

    public void setBodyLocationPhotoUUID(String bodyLocationPhotoUUID) {
        this.bodyLocationPhotoUUID = bodyLocationPhotoUUID;
    }
}
