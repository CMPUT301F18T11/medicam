package ca.ualberta.cmput301f18t11.medicam.models;

import android.media.Image;

import java.util.Collection;
import java.util.UUID;

import ca.ualberta.cmput301f18t11.medicam.models.abstracts.PhotoAttachment;

public class InstancePhoto extends PhotoAttachment {
    private String instancePhotoUUID;
    private Collection<Image> instanceImageCollection;

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

}
