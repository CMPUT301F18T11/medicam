package ca.ualberta.cmput301f18t11.medicam.models;

import android.media.Image;

import java.util.UUID;

import ca.ualberta.cmput301f18t11.medicam.models.abstracts.PhotoAttachment;
import ca.ualberta.cmput301f18t11.medicam.utils.ReferenceSide;


/**
 * A front facing or back facing full body photo meant to be attached to <code>Problem</code> object
 * that can be set by a patient.
 */

public class ReferencePhoto extends PhotoAttachment {
    private ReferenceSide reference_side;
    private String referencePhotoUUID;

    /**
     * Empty constructor for initializing a empty instance of this object with blank reference_side
     * and referencePhotoUUID fields.
     */
    public ReferencePhoto(){};

    /**
     * Constructor that accepts an image type to be held by this attachment.
     *
     * @param image
     * @deprecated November 12th, 2018
     */
    public ReferencePhoto(Image image){};

    /**
     * Sets the unique <code>String</code> type identifier for the photo that is to be held by this
     * object.
     *
     * @param aUUID uniquely identifiable <code>String</code> for the photo that is to be held by this
     *              attachment.
     */

    public void setReferencePhotoUUID(String aUUID) {
        this.referencePhotoUUID = aUUID;
    }


    /**
     * Gets the unique <code>String</code> type identifier for the photo that is to be held by this
     * object.
     *
     * @return  a uniquely identifiable <code>String</code> for the photo that is held by this
     *              attachment.
     */

    public String getReferencePhotoUUID(){
        return referencePhotoUUID;
    }
}
