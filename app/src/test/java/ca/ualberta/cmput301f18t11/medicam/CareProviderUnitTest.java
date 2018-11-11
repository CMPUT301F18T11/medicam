package ca.ualberta.cmput301f18t11.medicam;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ca.ualberta.cmput301f18t11.medicam.models.CareProvider;

import static org.junit.Assert.*;

public class CareProviderUnitTest {

    @Test
    public void testGetAndSetPatients() {
        CareProvider testCareProvider = new CareProvider();
        // test empty patients list
        List<String> testPatients1 = new ArrayList<>();
        testCareProvider.setPatients(testPatients1);

        assertTrue(testCareProvider.getPatients().isEmpty());

        // test random patients list of 2 elements
        List<String> testPatients2 = new ArrayList<>();
        testPatients2.add(UUID.randomUUID().toString());
        testPatients2.add(UUID.randomUUID().toString());

        testCareProvider.setPatients(testPatients2);

        assertArrayEquals(testCareProvider.getPatients().toArray(), testPatients2.toArray());

    }

    @Test
    public void testAddPatient() {
        CareProvider testCareProvider = new CareProvider();

        List<String> testPatients = new ArrayList<>();
        testCareProvider.setPatients(testPatients);
        // check it is empty first
        assertTrue(testCareProvider.getPatients().isEmpty());

        // check when adding by UUID
        String testUUID1 = UUID.randomUUID().toString();
        testCareProvider.addPatient(testUUID1);
        assertEquals(testCareProvider.getPatients().size(), 1);
        assertTrue(testCareProvider.getPatients().contains(testUUID1));

        // check when adding by patient userid
        String testPatient1 = UUID.randomUUID().toString();
        // this involves server interaction, since the patient's info must be polled
        // the following UUID should be associated with the testpatient userid
        // within the database for this test to properly work
        testCareProvider.addPatient(testPatient1);

        assertTrue(testCareProvider.getPatients().contains(testPatient1));
    }

    @Test
    public void testRemovePatient() {
        CareProvider testCareProvider = new CareProvider();

        List<String> testPatients = new ArrayList<>();
        String testUUID1 = UUID.randomUUID().toString();
        String testUUID2 = UUID.randomUUID().toString();
        String testUUID3 = UUID.randomUUID().toString();

        testPatients.add(testUUID1);
        testPatients.add(testUUID2);
        testPatients.add(testUUID3);

        assertTrue(testPatients.contains(testUUID1));
        assertTrue(testPatients.contains(testUUID2));
        assertTrue(testPatients.contains(testUUID3));

        testCareProvider.setPatients(testPatients);
        testCareProvider.removePatient(testUUID1);
        assertFalse(testCareProvider.getPatients().contains(testUUID1));

        testCareProvider.removePatient(testUUID2);
        assertFalse(testCareProvider.getPatients().contains(testUUID2));

        testCareProvider.removePatient(testUUID3);
        assertFalse(testCareProvider.getPatients().contains(testUUID3));
    }
}
