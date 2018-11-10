package ca.ualberta.cmput301f18t11.medicam;

import android.location.Location;

import org.junit.Test;

import java.util.ArrayList;

import ca.ualberta.cmput301f18t11.medicam.models.LocationAttachment;

import static org.junit.Assert.assertEquals;

public class LocationAttachmentUnitTest {

    @Test
    public void testNearby() {
        LocationAttachment testLoca = new LocationAttachment();
        boolean answer = false;
        boolean espect;
        Location oc = null;
        espect = testLoca.nearby(oc,0.00000);
        assertEquals(answer,espect);
    }

    @Test
    public void testMap_coordinates() {
        LocationAttachment testLoca = new LocationAttachment();
        ArrayList<Double> answer = new ArrayList<Double>(2);
        answer.add(0,0.00000000);
        answer.add(1,1.11111111);
        testLoca.setMap_coordinates(0.00000000,1.11111111);
        ArrayList espect = testLoca.getMap_coordinates();
        assertEquals(answer,espect);

    }
}