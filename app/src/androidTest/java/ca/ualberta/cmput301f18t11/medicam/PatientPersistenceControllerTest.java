package ca.ualberta.cmput301f18t11.medicam;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.UUID;

import ca.ualberta.cmput301f18t11.medicam.controllers.ElasticSearchController;
import ca.ualberta.cmput301f18t11.medicam.controllers.abstracts.PersistenceController;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.PatientPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.models.Patient;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Test for PatientPersistenceController Class
 * which runs on android device.
 */
@RunWith(AndroidJUnit4.class)
public class PatientPersistenceControllerTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("ca.ualberta.cmput301f18t11.medicam", appContext.getPackageName());
    }

    /**
     * Note: Don't use loadFromREST or loadFromStorage directly,
     * Only for testing purposes.
     */
    @Test
    public void testPersistence() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        // use our test index
        ElasticSearchController.setIndex_url("cmput301f18t11test");
        String backPhoto = UUID.randomUUID().toString();
        String frontPhoto = UUID.randomUUID().toString();
        String email = "testemail";
        String userid = UUID.randomUUID().toString();
        String phoneNumber = "testphonenumber";

        ArrayList<String> problems = new ArrayList<>();
        problems.add(UUID.randomUUID().toString());
        problems.add(UUID.randomUUID().toString());
        problems.add(UUID.randomUUID().toString());

        Patient testPatient = new Patient(userid, email, phoneNumber, problems);

        PersistenceController<Patient> testController = new PatientPersistenceController();

        testController.save(testPatient, appContext);
        // test load
        Patient result = testController.load(testPatient.getUuid(), appContext);
        assertEquals(result.getUserID(), testPatient.getUserID());
        assertEquals(result.getProblems(), testPatient.getProblems());
        assertEquals(result.getEmail(), testPatient.getEmail());
        assertEquals(result.getPhoneNumber(), testPatient.getPhoneNumber());
        // test loadFromRest
        result =  testController.loadFromREST(testPatient.getUuid());
        assertEquals(result.getUserID(), testPatient.getUserID());
        assertEquals(result.getProblems(), testPatient.getProblems());
        assertEquals(result.getEmail(), testPatient.getEmail());
        assertEquals(result.getPhoneNumber(), testPatient.getPhoneNumber());
        // test loadFromStorage
        result = testController.loadFromStorage(testPatient.getUuid(), appContext);
        assertEquals(result.getUserID(), testPatient.getUserID());
        assertEquals(result.getProblems(), testPatient.getProblems());
        assertEquals(result.getEmail(), testPatient.getEmail());
        assertEquals(result.getPhoneNumber(), testPatient.getPhoneNumber());

        // delete from both
        testController.delete(testPatient,appContext);
        assertNull(testController.load(testPatient.getUuid(), appContext));
        assertNull(testController.loadFromREST(testPatient.getUuid()));
        assertNull(testController.loadFromStorage(testPatient.getUuid(), appContext));
    }

    @Test
    public void testFromStorage() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        // use our test index
        ElasticSearchController.setIndex_url("cmput301f18t11test");
        String backPhoto = UUID.randomUUID().toString();
        String frontPhoto = UUID.randomUUID().toString();
        String email = "testemail";
        String userid = UUID.randomUUID().toString();
        String phoneNumber = "testphonenumber";

        ArrayList<String> problems = new ArrayList<>();
        problems.add(UUID.randomUUID().toString());
        problems.add(UUID.randomUUID().toString());
        problems.add(UUID.randomUUID().toString());

        Patient testPatient = new Patient(userid, email, phoneNumber, problems);

        PersistenceController<Patient> testController = new PatientPersistenceController();

        testController.save(testPatient, appContext);
        // test loadFromStorage
        Patient result = testController.loadFromStorage(testPatient.getUuid(), appContext);
        assertEquals(result.getUserID(), testPatient.getUserID());
        assertEquals(result.getProblems(), testPatient.getProblems());
        assertEquals(result.getEmail(), testPatient.getEmail());
        assertEquals(result.getPhoneNumber(), testPatient.getPhoneNumber());

        // delete
        testController.delete(testPatient,appContext);
        assertNull(testController.loadFromStorage(testPatient.getUuid(), appContext));
    }
}
