package ca.ualberta.cmput301f18t11.medicam.models.attachments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

import ca.ualberta.cmput301f18t11.medicam.models.abstracts.PersistedModel;

/**
 * A object that holds a Uri and a Bitmap to represent a photo that is to be attached to a
 * <code>Record</code> object.
 */
public class InstancePhoto extends PersistedModel {
    private String base64EncodedImage;

    /**
     * Empty constructor for creating a blank <code>InstancePhoto</code> object
     * with empty <code>Uri</code> photo and <code>Bitmap</code> cameraPhoto fields.
     */
    public InstancePhoto() {
        super();
    }

    public InstancePhoto( Bitmap photo) {
        super();
        setPhoto(photo);
    }

    /**
     * Sets the uri for the photo that this <code>InstancePhoto</code> object holds.
     *
     * @param photo <code>Uri</code> for the photo this object is meant to hold.
     */
    public void setPhoto(Bitmap photo) {

        photo = Bitmap.createScaledBitmap(photo,70,125,true);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        this.base64EncodedImage = Base64.encodeToString(b, Base64.DEFAULT);
    }


    /**
     * Gets the Bitmap that represents the photo held by this attachment by decoding
     * the base64 encoded string
     *
     * @return <code>Bitmap</code> type object for the photo held by this attachment.
     */
    public Bitmap getPhoto(){
        byte[] decodedString = Base64.decode(base64EncodedImage, Base64.DEFAULT);
        Bitmap bMap = BitmapFactory.decodeByteArray(decodedString,0, decodedString.length);
        return bMap;
    }

}