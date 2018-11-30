package ca.ualberta.cmput301f18t11.medicam.models.attachments;


import android.graphics.Bitmap;
import android.util.Pair;

import java.util.UUID;

import ca.ualberta.cmput301f18t11.medicam.models.abstracts.Attachment;

/**
 * Model for holding information related to a body-location selected
 * by a patient for a record.
 *
 * Contains coordinates for drawing a marker on the photo,
 * and the UUID of the bodylocation photo.
 */
public class BodyLocation {
    private Pair<Integer, Integer> imageCoordinates;
    private String bodyLocationPhotoUUID;

    /**
     * Null contructor for body location.
     */
    public BodyLocation() {

    }

    public BodyLocation(Pair<Integer, Integer> imageCoordinates, String bodyLocationPhotoUUID) {
        this.imageCoordinates = imageCoordinates;
        this.bodyLocationPhotoUUID = bodyLocationPhotoUUID;
    }
}
