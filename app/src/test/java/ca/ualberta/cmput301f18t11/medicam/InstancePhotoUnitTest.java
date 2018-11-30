package ca.ualberta.cmput301f18t11.medicam;

import org.junit.Test;

import java.util.UUID;

import ca.ualberta.cmput301f18t11.medicam.models.attachments.InstancePhoto;

import static org.junit.Assert.*;

public class InstancePhotoUnitTest {
    @Test
    public void getInstancePhotoUUID(){
        String wanttedUUID = UUID.randomUUID().toString();
        InstancePhoto testInsta = new InstancePhoto();
        testInsta.setInstancePhotoUUID(wanttedUUID);
        String answerUUID = testInsta.getInstancePhotoUUID();
        assertEquals(wanttedUUID,answerUUID);
    }

}