package ca.ualberta.cmput301f18t11.medicam;

import org.junit.Test;

import java.util.UUID;

import ca.ualberta.cmput301f18t11.medicam.models.ReferencePhoto;

import static org.junit.Assert.assertEquals;

public class ReferencePhotoUnitTest {

    @Test
    public void testGetReferencePhotoUUID() {
        ReferencePhoto testRefe = new ReferencePhoto();
        UUID wanttedUUID = UUID.randomUUID();
        testRefe.setReferencePhotoUUID(wanttedUUID);
        UUID answerUUID = testRefe.getReferencePhotoUUID();
        assertEquals(wanttedUUID,answerUUID);
    }

}