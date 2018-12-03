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
    private Float imageCoordinateX;
    private Float imageCoordinateY;
    private ReferencePhoto referencePhoto;

    /**
     * Null contructor for body location.
     */
    public BodyLocation() {
        super();
    }

    public BodyLocation(Pair<Float, Float> imageCoordinates, ReferencePhoto referencePhoto) {
        this.imageCoordinateX = imageCoordinates.first;
        this.imageCoordinateY = imageCoordinates.second;
        this.referencePhoto = referencePhoto;
    }

    public BodyLocation(Pair<Float, Float> imageCoordinates, String photoUUID, String label, String bodyPart) {
        this.imageCoordinateX = imageCoordinates.first;
        this.imageCoordinateY = imageCoordinates.second;
        this.referencePhoto = new ReferencePhoto(photoUUID, bodyPart, label);
    }

    public Pair<Float, Float> getImageCoordinates() {
        return new Pair<>(imageCoordinateX, imageCoordinateY);
    }

    public void setImageCoordinates(Pair<Float, Float> imageCoordinates) {
        this.imageCoordinateX = imageCoordinates.first;
        this.imageCoordinateY = imageCoordinates.second;
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
        return referencePhoto.toString();
    }
}
