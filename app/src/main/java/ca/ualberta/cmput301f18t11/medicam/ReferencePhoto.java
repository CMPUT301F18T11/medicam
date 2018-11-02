package ca.ualberta.cmput301f18t11.medicam;

import android.media.Image;

import java.util.UUID;

public class ReferencePhoto extends PhotoAttachment{
    private ReferenceSide reference_side;
    private UUID referencePhotoUUID;

    public ReferencePhoto(){};
    public ReferencePhoto(Image image){};



    public void setReferencePhotoUUID(UUID aUUID) {
        this.referencePhotoUUID = aUUID;
    }

    public UUID getReferencePhotoUUID(){
        return referencePhotoUUID;
    }
}
