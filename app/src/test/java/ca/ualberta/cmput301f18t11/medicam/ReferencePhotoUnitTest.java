package ca.ualberta.cmput301f18t11.medicam;

import android.graphics.Bitmap;

import org.junit.Test;

import java.util.UUID;

import ca.ualberta.cmput301f18t11.medicam.models.attachments.ReferencePhoto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ReferencePhotoUnitTest {

    @Test
    public void testGetReferencePhotoUUID() {
        String uuid = "3452345435";
        String label = "label";

        ReferencePhoto testRefe = new ReferencePhoto(uuid, null, label);
        assertEquals(testRefe.getUuid(), uuid);
        assertNull(testRefe.getPhoto());
        assertEquals(testRefe.getLabel(), label);
    }

}