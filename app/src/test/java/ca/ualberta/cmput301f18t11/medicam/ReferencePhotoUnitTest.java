package ca.ualberta.cmput301f18t11.medicam;

import org.junit.Test;

import java.util.UUID;

import ca.ualberta.cmput301f18t11.medicam.models.attachments.ReferencePhoto;

import static org.junit.Assert.assertEquals;

public class ReferencePhotoUnitTest {

    @Test
    public void testGetReferencePhotoUUID() {
        ReferencePhoto testRefe = new ReferencePhoto();
        String wanttedUUID = UUID.randomUUID().toString();
        testRefe.setReferencePhotoUUID(wanttedUUID);
        String answerUUID = testRefe.getReferencePhotoUUID();
        assertEquals(wanttedUUID,answerUUID);
    }

}