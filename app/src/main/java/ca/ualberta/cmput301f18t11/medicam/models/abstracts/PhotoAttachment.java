package ca.ualberta.cmput301f18t11.medicam.models.abstracts;

import android.media.Image;

import ca.ualberta.cmput301f18t11.medicam.models.BodyLocation;

public abstract class PhotoAttachment extends Attachment {
    private Image photo;
    private BodyLocation body_location;
}
