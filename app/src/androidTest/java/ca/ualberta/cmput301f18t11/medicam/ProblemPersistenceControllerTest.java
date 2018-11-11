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
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.ProblemPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.models.Problem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Test for CareProviderPersistenceController Class
 * which runs on android device.
 */
@RunWith(AndroidJUnit4.class)
public class ProblemPersistenceControllerTest {
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
        String uuid = UUID.randomUUID().toString();
        String title = "title";
        String description = "description";



        ArrayList<String> patientRecords = new ArrayList<>();
        patientRecords.add(UUID.randomUUID().toString());
        patientRecords.add(UUID.randomUUID().toString());
        patientRecords.add(UUID.randomUUID().toString());

        ArrayList<String> careProviderRecords = new ArrayList<>();
        careProviderRecords.add(UUID.randomUUID().toString());
        careProviderRecords.add(UUID.randomUUID().toString());
        careProviderRecords.add(UUID.randomUUID().toString());

        Problem testProblem = new Problem(uuid, title, description, patientRecords, careProviderRecords);

        PersistenceController<Problem> testController = new ProblemPersistenceController();

        testController.save(testProblem, appContext);
        // test load
        Problem result = testController.load(testProblem.getUuid(), appContext);
        assertEquals(result.getUuid(), testProblem.getUuid());
        assertEquals(result.getDate().toString(), testProblem.getDate().toString());
        assertEquals(result.getDescription(), testProblem.getDescription());
        assertEquals(result.getPatientRecords(), testProblem.getPatientRecords());
        assertEquals(result.getCareProviderRecords(), testProblem.getCareProviderRecords());
        assertEquals(result.getTitle(), testProblem.getTitle());
        // test loadFromRest
        result =  testController.loadFromREST(testProblem.getUuid());
        assertEquals(result.getUuid(), testProblem.getUuid());
        assertEquals(result.getDate().toString(), testProblem.getDate().toString());
        assertEquals(result.getDescription(), testProblem.getDescription());
        assertEquals(result.getPatientRecords(), testProblem.getPatientRecords());
        assertEquals(result.getCareProviderRecords(), testProblem.getCareProviderRecords());
        assertEquals(result.getTitle(), testProblem.getTitle());

        // test loadFromStorage
        result = testController.loadFromStorage(testProblem.getUuid(), appContext);
        assertEquals(result.getUuid(), testProblem.getUuid());
        assertEquals(result.getDate().toString(), testProblem.getDate().toString());
        assertEquals(result.getDescription(), testProblem.getDescription());
        assertEquals(result.getPatientRecords(), testProblem.getPatientRecords());
        assertEquals(result.getCareProviderRecords(), testProblem.getCareProviderRecords());
        assertEquals(result.getTitle(), testProblem.getTitle());

        // delete from both
        testController.delete(testProblem,appContext);
        assertNull(testController.load(testProblem.getUuid(), appContext));
        assertNull(testController.loadFromREST(testProblem.getUuid()));
        assertNull(testController.loadFromStorage(testProblem.getUuid(), appContext));
    }
}
