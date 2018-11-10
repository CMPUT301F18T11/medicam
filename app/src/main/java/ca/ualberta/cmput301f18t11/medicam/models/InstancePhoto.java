package ca.ualberta.cmput301f18t11.medicam.models;

import android.media.Image;

import java.util.Collection;
import java.util.UUID;

import ca.ualberta.cmput301f18t11.medicam.models.abstracts.PhotoAttachment;

public class InstancePhoto extends PhotoAttachment {
    private UUID instancePhotoUUID;
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
    public void setInstancePhotoUUID(UUID instancePhotoUUID) {
        this.instancePhotoUUID = instancePhotoUUID;
    }

    public UUID getInstancePhotoUUID(){
        return instancePhotoUUID;
    }

}
