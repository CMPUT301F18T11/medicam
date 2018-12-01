package ca.ualberta.cmput301f18t11.medicam.models.attachments;

import android.graphics.Bitmap;

import ca.ualberta.cmput301f18t11.medicam.models.abstracts.PersistedModel;

/**
 * A object that holds a Uri and a Bitmap to represent a photo that is to be attached to a
 * <code>Record</code> object.
 */
public class InstancePhoto extends PersistedModel {
    private Bitmap image;

    /**
     * Empty constructor for creating a blank <code>InstancePhoto</code> object
     * with empty <code>Uri</code> photo and <code>Bitmap</code> cameraPhoto fields.
     */
    public InstancePhoto() {
        super();
    }

    public InstancePhoto(String uuid) {
        super(uuid);
    }

    public InstancePhoto(String UUID, Bitmap image) {
        super(UUID);
        this.image = image;
    }

    /**
     * Sets the uri for the photo that this <code>InstancePhoto</code> object holds.
     *
     * @param photo <code>Uri</code> for the photo this object is meant to hold.
     */
    public void setPhoto(Bitmap photo) {
        this.image = photo;
    }

    /**
     * Gets the Uri that represents the photo held by this attachment.
     *
     * @return <code>Uri</code> type object for the photo held by this attachment.
     */
    public Bitmap getPhoto(){
        return image;
    }

}