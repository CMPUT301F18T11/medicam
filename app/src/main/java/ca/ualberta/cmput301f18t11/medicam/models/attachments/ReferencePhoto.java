package ca.ualberta.cmput301f18t11.medicam.models.attachments;

import android.graphics.Bitmap;

import ca.ualberta.cmput301f18t11.medicam.models.abstracts.PersistedModel;


/**
 *  Model for storing information related to a reference photo.
 *
 *  Includes the photo itself, and a label to identify the photo.
 */

public class ReferencePhoto extends PersistedModel {
    private Bitmap photo;
    private String label;
    /**
     * Empty constructor for initializing a empty instance of this object with blank reference_side
     * and referencePhotoUUID fields.
     */
    public ReferencePhoto(){

    }

    /**
     * Constructor that accepts an image type to be held by this attachment.
     *
     * @param image
     * @param UUID
     * @param label
     */
    public ReferencePhoto(String UUID, Bitmap image, String label){
        super(UUID);
        this.photo = image;
        this.label = label;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
