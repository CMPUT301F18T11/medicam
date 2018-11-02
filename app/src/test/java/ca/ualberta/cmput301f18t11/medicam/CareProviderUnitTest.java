package ca.ualberta.cmput301f18t11.medicam;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class CareProviderUnitTest {

    @Test
    public void testGetandSetPatients() {
        CareProvider testCareProvider = new CareProvider();
        // test empty patients list
        List<UUID> testPatients1 = new ArrayList<>();
        testCareProvider.setPatients(testPatients1);

        assertTrue(testCareProvider.getPatients().isEmpty());

        // test random patients list of 2 elements
        List<UUID> testPatients2 = new ArrayList<>();
        testPatients2.add(UUID.randomUUID());
        testPatients2.add(UUID.randomUUID());

        testCareProvider.setPatients(testPatients2);

        assertArrayEquals(testCareProvider.getPatients().toArray(), testPatients2.toArray());

    }

    @Test
    public void testAddPatient() {
        CareProvider testCareProvider = new CareProvider();

        List<UUID> testPatients = new ArrayList<>();
        testCareProvider.setPatients(testPatients);
        // check it is empty first
        assertTrue(testCareProvider.getPatients().isEmpty());

        // check when adding by UUID
        UUID testUUID1 = UUID.randomUUID();
        testCareProvider.addPatient(testUUID1);
        assertEquals(testCareProvider.getPatients().size(), 1);
        assertTrue(testCareProvider.getPatients().contains(testUUID1));

        // check when adding by patient userid
        String testPatient1 = "testpatientuserid";
        // this involves server interaction, since the patient's info must be polled
        // the following UUID should be associated with the testpatient userid
        // within the database for this test to properly work
        UUID testUUID2 = UUID.fromString(testPatient1);
        testCareProvider.addPatient(testPatient1);

        assertTrue(testCareProvider.getPatients().contains(testUUID2));
    }

    @Test
    public void testRemovePatient() {
        CareProvider testCareProvider = new CareProvider();

        List<UUID> testPatients = new ArrayList<>();
        
    }
}
