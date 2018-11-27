package ca.ualberta.cmput301f18t11.medicam.models.attachments;

import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;

import java.util.Collection;

import ca.ualberta.cmput301f18t11.medicam.models.abstracts.PhotoAttachment;

/**
 * A object that holds a Uri and a Bitmap to represent a photo that is to be attached to a
 * <code>Record</code> object.
 */
public class InstancePhoto extends PhotoAttachment {
    //private UUID instancePhotoUUID;
    private Collection<Image> instanceImageCollection;
    private String photo;
    private Bitmap cameraPhoto;

    /**
     * Empty constructor for creating a blank <code>InstancePhoto</code> object
     * with empty <code>Uri</code> photo and <code>Bitmap</code> cameraPhoto fields.
     */
    public InstancePhoto(){}

    /**
     * Sets the uri for the photo that this <code>InstancePhoto</code> object holds.
     *
     * @param photo <code>Uri</code> for the photo this object is meant to hold.
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     * Sets the Bitmap that represents the photo held by this attachment.
     *
     * @param cameraPhoto <code>Bitmap</code> type photo to be held by this attachment.
     */
    public void setCameraPhoto(Bitmap cameraPhoto) {
        this.cameraPhoto = cameraPhoto;
    }

    /**
     * Gets the Bitmap that represents the photo held by this attachment.
     *
     * @return <code>Bitmap</code> type photo held by this attachment.
     */
    public Bitmap getCameraPhoto() {
        return cameraPhoto;
    }

    /**
     * Gets the Uri that represents the photo held by this attachment.
     *
     * @return <code>Uri</code> type object for the photo held by this attachment.
     */
    public String getPhoto(){
        return photo;
    }

    /*
    public void addInstancePhotoImage (Image image){
        // function for seeing wiether image size is good will be added later version.
        boolean imageValidSize = true;
        if (imageValidSize) {
            instanceImageCollection.add(image);
        }
    }
    */
}