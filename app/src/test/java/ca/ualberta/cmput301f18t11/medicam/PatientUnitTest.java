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
        ArrayList<String> testProblemList = new ArrayList<>();
        testProblemList.add(new UUID(123,123).toString());
        Patient testPatient = new Patient();
        testPatient.setProblems(testProblemList);

        assertArrayEquals(testPatient.getProblems().toArray(), testProblemList.toArray());

        ArrayList<String> testProblemListEmpty = new ArrayList<>();
        testPatient.setProblems(testProblemListEmpty);

        assertTrue(testPatient.getProblems().isEmpty());
    }

    @Test
    public void testAddProblem() {
        ArrayList<String> testProblemList = new ArrayList<>();
        testProblemList.add(UUID.randomUUID().toString());
        Patient testPatient = new Patient();
        testPatient.setProblems(testProblemList);

        // test when adding with UUID of a problem directly
        String addedUUID = UUID.randomUUID().toString();
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
        String testUUID = UUID.randomUUID().toString();
        testPatient.setFrontPhoto(testUUID);

        assertEquals(testPatient.getFrontPhoto(), testUUID);
    }

    @Test
    public void testGetAndSetBackPhoto() {
        Patient testPatient = new Patient();
        String testUUID = UUID.randomUUID().toString();
        testPatient.setBackPhoto(testUUID);

        assertEquals(testPatient.getBackPhoto(), testUUID);
    }
}
