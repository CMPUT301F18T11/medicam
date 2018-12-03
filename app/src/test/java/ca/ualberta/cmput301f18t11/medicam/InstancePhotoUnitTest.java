package ca.ualberta.cmput301f18t11.medicam;

import org.junit.Test;

import java.util.UUID;

import ca.ualberta.cmput301f18t11.medicam.models.attachments.InstancePhoto;

import static org.junit.Assert.*;

public class InstancePhotoUnitTest {
    @Test
    public void getInstancePhotoUUID(){
        InstancePhoto testInsta = new InstancePhoto();
        assertNotNull(testInsta.getUuid());
    }

}