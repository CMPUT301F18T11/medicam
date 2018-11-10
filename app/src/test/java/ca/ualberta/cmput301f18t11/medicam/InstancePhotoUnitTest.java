package ca.ualberta.cmput301f18t11.medicam;

import org.junit.Test;

import java.util.UUID;

import ca.ualberta.cmput301f18t11.medicam.models.InstancePhoto;

import static org.junit.Assert.*;

public class InstancePhotoUnitTest {
    @Test
    public void getInstancePhotoUUID(){
        UUID wanttedUUID = UUID.randomUUID();
        InstancePhoto testInsta = new InstancePhoto();
        testInsta.setInstancePhotoUUID(wanttedUUID);
        UUID answerUUID = testInsta.getInstancePhotoUUID();
        assertEquals(wanttedUUID,answerUUID);
    }

}