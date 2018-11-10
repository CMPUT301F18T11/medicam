package ca.ualberta.cmput301f18t11.medicam;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import ca.ualberta.cmput301f18t11.medicam.models.Patient;
import ca.ualberta.cmput301f18t11.medicam.models.Problem;

import static org.junit.Assert.*;

public class PatientUnitTest {
    @Test
    public void testGetAndSetProblems() {
        List<UUID> testProblemList = new ArrayList<>();
        testProblemList.add(new UUID(123,123));
        Patient testPatient = new Patient();
        testPatient.setProblems(testProblemList);

        assertArrayEquals(testPatient.getProblems().toArray(), testProblemList.toArray());

        List<UUID> testProblemListEmpty = new ArrayList<>();
        testPatient.setProblems(testProblemListEmpty);

        assertTrue(testPatient.getProblems().isEmpty());
    }

    @Test
    public void testAddProblem() {
        List<UUID> testProblemList = new ArrayList<>();
        testProblemList.add(UUID.randomUUID());
        Patient testPatient = new Patient();
        testPatient.setProblems(testProblemList);

        // test when adding with UUID of a problem directly
        UUID addedUUID = UUID.randomUUID();
        testPatient.addProblem(addedUUID);
        assertTrue(testPatient.getProblems().contains(addedUUID));

        // test when adding with problem object
        Problem addedProblem = new Problem("title", new Date(), "description");
        addedProblem.createUuid();
        testPatient.addProblem(addedProblem);
        assertTrue(testPatient.getProblems().contains(addedProblem.getUuid()));
    }

    @Test
    public void testGetAndSetFrontPhoto() {
        Patient testPatient = new Patient();
        UUID testUUID = UUID.randomUUID();
        testPatient.setFrontPhoto(testUUID);

        assertEquals(testPatient.getFrontPhoto(), testUUID);
    }

    @Test
    public void testGetAndSetBackPhoto() {
        Patient testPatient = new Patient();
        UUID testUUID = UUID.randomUUID();
        testPatient.setBackPhoto(testUUID);

        assertEquals(testPatient.getBackPhoto(), testUUID);
    }
}
