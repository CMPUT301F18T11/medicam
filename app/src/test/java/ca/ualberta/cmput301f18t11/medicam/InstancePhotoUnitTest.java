package ca.ualberta.cmput301f18t11.medicam;

import org.junit.Test;

import java.util.UUID;

import ca.ualberta.cmput301f18t11.medicam.models.InstancePhoto;

import static org.junit.Assert.*;

public class InstancePhotoUnitTest {
    @Test
    public void getInstancePhotoUUID(){
        String wanttedUUID = UUID.randomUUID().toString();
        InstancePhoto testInsta = new InstancePhoto();
        assertEquals(wanttedUUID,answerUUID);
    }

}