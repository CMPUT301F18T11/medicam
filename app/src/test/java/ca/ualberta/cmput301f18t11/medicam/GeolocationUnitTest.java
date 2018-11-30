package ca.ualberta.cmput301f18t11.medicam;


import org.junit.Test;


import ca.ualberta.cmput301f18t11.medicam.models.attachments.Geolocation;

import static org.junit.Assert.assertTrue;

public class GeolocationUnitTest {


    @Test
    public void testMap_coordinates() {
        Geolocation testLoca = new Geolocation(60,60);

        assertTrue(testLoca.getLatitude() > 59.9999);
        assertTrue(testLoca.getLongitude() > 59.9999);

        assertTrue(testLoca.getLatitude() < 60.0001);
        assertTrue(testLoca.getLongitude() < 60.0001);

    }
}