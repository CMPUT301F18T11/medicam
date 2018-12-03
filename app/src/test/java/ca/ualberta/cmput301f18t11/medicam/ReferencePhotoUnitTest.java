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
        String bodyPart = "part";

        ReferencePhoto testRefe = new ReferencePhoto(uuid, bodyPart, label);
        assertEquals(testRefe.getPhotoUUID(), uuid);
        assertEquals(testRefe.getBodyPart(), bodyPart);
        assertEquals(testRefe.getLabel(), label);
    }

}