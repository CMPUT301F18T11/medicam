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
    private URI photo_uri;

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
    public void setInstancePhotoUUID(String instancePhotoUUID) {
        this.instancePhotoUUID = instancePhotoUUID;
    }

    public String getInstancePhotoUUID(){
        return instancePhotoUUID;
    }
    public void setCameraPhoto(Bitmap user_photo){this.photo = user_photo;}
    public Bitmap getCameraPhoto(){return this.photo; /*placeholder*/}
    public void setPhoto(Uri user_photo_uri){}
    public Uri getPhoto(){return null;}

}
