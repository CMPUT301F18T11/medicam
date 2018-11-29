package ca.ualberta.cmput301f18t11.medicam.models.abstracts;

import android.media.Image;

import ca.ualberta.cmput301f18t11.medicam.models.abstracts.Attachment;
import ca.ualberta.cmput301f18t11.medicam.models.attachments.BodyLocation;

/**
 * This class represents the functionality that every record attachment that involves a photo
 * should have.
 * <p>
 * That is, this attachment should hold an Image(photo) and a BodyLocation(body_location).
 */

public abstract class PhotoAttachment extends Attachment {
    private Image photo;
    private BodyLocation body_location;
}
