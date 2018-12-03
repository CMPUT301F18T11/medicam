package ca.ualberta.cmput301f18t11.medicam.models.attachments;


import android.util.Pair;

import java.io.Serializable;

/**
 * Model for holding information related to a body-location selected
 * by a patient for a record.
 *
 * Contains coordinates for drawing a marker on the photo,
 * and the UUID of the bodylocation photo.
 */
public class BodyLocation implements Serializable {
    private Pair<Integer, Integer> imageCoordinates;
    private ReferencePhoto referencePhoto;

    /**
     * Null contructor for body location.
     */
    public BodyLocation() {
        super();
    }

    public BodyLocation(Pair<Integer, Integer> imageCoordinates, ReferencePhoto referencePhoto) {
        this.imageCoordinates = imageCoordinates;
        this.referencePhoto = referencePhoto;
    }

    public BodyLocation(Pair<Integer, Integer> imageCoordinates, String photoUUID, String label, String bodyPart) {
        this.imageCoordinates = imageCoordinates;
        this.referencePhoto = new ReferencePhoto(photoUUID, bodyPart, label);
    }

    public Pair<Integer, Integer> getImageCoordinates() {
        return imageCoordinates;
    }

    public void setImageCoordinates(Pair<Integer, Integer> imageCoordinates) {
        this.imageCoordinates = imageCoordinates;
    }

    public ReferencePhoto getReferencePhoto() {
        return referencePhoto;
    }

    public void setReferencePhoto(ReferencePhoto referencePhoto) {
        this.referencePhoto = referencePhoto;
    }

    public String getBodyPart() {
        return referencePhoto.getBodyPart();
    }


    public String getLabel() {
        return referencePhoto.getLabel();
    }

    @Override
    public String toString() {
        if (getLabel() == null) {
            return getBodyPart();
        }
        return getBodyPart() + ": " + getLabel();
    }
}
