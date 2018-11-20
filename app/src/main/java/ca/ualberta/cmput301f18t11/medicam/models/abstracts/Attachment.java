package ca.ualberta.cmput301f18t11.medicam.models.abstracts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

/**
 * An abstract class that represents the base functionality that all attachments for all
 * records should have. These attachments can be photos, geo-locations, or body locations and
 * they are associated with one specific record only.
 */
public abstract class Attachment {
    private String attachment_uuid;
    private Collection<String> details = new ArrayList<>();

    /**
     * Returns the uuid of the attachment as a <code>String</code>.
     *
     * @return the uuid of the calling attachment
     */
    public String getAttachment_uuid(){
        return attachment_uuid;
    }

    /**
     * Returns the details of the attachment as a searchable collection of strings.
     * These details are different for each attachment.
     * <p>
     * For example a an image attachment might return
     * a filename where a location attachment might
     * return latitude and longitude pairs. Most all attachment
     * will have the title of the attachment as well as the
     * attachment type among its details.
     *
     *
     * @return A collection a strings that describe the contents of the attachment.
     */
    public Collection<String> getDetail(){
        return details;
    }
}
