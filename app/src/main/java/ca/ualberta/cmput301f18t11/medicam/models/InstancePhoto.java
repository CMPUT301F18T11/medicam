package ca.ualberta.cmput301f18t11.medicam.models;

import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;

import java.util.Collection;
import java.util.UUID;

import ca.ualberta.cmput301f18t11.medicam.models.abstracts.PhotoAttachment;

public class InstancePhoto extends PhotoAttachment {
    //private UUID instancePhotoUUID;
    private Collection<Image> instanceImageCollection;
    private Uri photo;
    private Bitmap cameraPhoto;

    public InstancePhoto(){}
    public void setPhoto(Uri photo) {
        this.photo = photo;
    }

    public void setCameraPhoto(Bitmap cameraPhoto) {
        this.cameraPhoto = cameraPhoto;
    }

    public Bitmap getCameraPhoto() {
        return cameraPhoto;
    }
    public Uri getPhoto(){
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
