package ca.ualberta.cmput301f18t11.medicam.models;

import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;

import java.net.URI;
import java.util.Collection;
import java.util.UUID;

import ca.ualberta.cmput301f18t11.medicam.models.abstracts.PhotoAttachment;

public class InstancePhoto extends PhotoAttachment {
    private String instancePhotoUUID;
    private Collection<Image> instanceImageCollection;
    private Bitmap photo;
    private Uri photo_uri;

    public InstancePhoto(){}


    /*
    public void addInstancePhotoImage (Image image){
        // function for seeing wiether image size is good will be added later version.
        boolean imageValidSize = true;
        if (imageValidSize) {
            instanceImageCollection.add(image);
        }
    }
    */
    //set
    public void setInstancePhotoUUID(String instancePhotoUUID) {
        this.instancePhotoUUID = instancePhotoUUID;
    }
    public void setCameraPhoto(Bitmap user_photo){this.photo = user_photo;}
    public void setPhoto(Uri user_photo_uri){this.photo_uri = user_photo_uri;}

    //get
    public String getInstancePhotoUUID(){
        return this.instancePhotoUUID;
    }
    public Bitmap getCameraPhoto(){return this.photo;}
    public Uri getPhoto(){return this.photo_uri;}

}
