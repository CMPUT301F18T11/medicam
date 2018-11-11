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
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.CareProviderPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.PatientPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.models.CareProvider;
import ca.ualberta.cmput301f18t11.medicam.models.Patient;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Test for CareProviderPersistenceController Class
 * which runs on android device.
 */
@RunWith(AndroidJUnit4.class)
public class CareProviderPersistenceControllerTest {
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
        String email = "testemail";
        String userid = UUID.randomUUID().toString();
        String phoneNumber = "testphonenumber";

        ArrayList<UUID> patients = new ArrayList<>();
        patients.add(UUID.randomUUID());
        patients.add(UUID.randomUUID());
        patients.add(UUID.randomUUID());

        CareProvider testCareProvider = new CareProvider(userid, email, phoneNumber, patients);

        PersistenceController<CareProvider> testController = new CareProviderPersistenceController();

        testController.save(testCareProvider, appContext);
        // test load
        CareProvider result = testController.load(testCareProvider.getUuid(), appContext);
        assertEquals(result.getUserID(), testCareProvider.getUserID());
        assertEquals(result.getEmail(), testCareProvider.getEmail());
        assertEquals(result.getPhoneNumber(), testCareProvider.getPhoneNumber());
        assertEquals(result.getPatients(), testCareProvider.getPatients());
        // test loadFromRest
        result =  testController.loadFromREST(testCareProvider.getUuid());
        assertEquals(result.getUserID(), testCareProvider.getUserID());
        assertEquals(result.getEmail(), testCareProvider.getEmail());
        assertEquals(result.getPhoneNumber(), testCareProvider.getPhoneNumber());
        assertEquals(result.getPatients(), testCareProvider.getPatients());
        // test loadFromStorage
        result = testController.loadFromStorage(testCareProvider.getUuid(), appContext);
        assertEquals(result.getUserID(), testCareProvider.getUserID());
        assertEquals(result.getEmail(), testCareProvider.getEmail());
        assertEquals(result.getPhoneNumber(), testCareProvider.getPhoneNumber());
        assertEquals(result.getPatients(), testCareProvider.getPatients());

        // delete from both
        testController.delete(testCareProvider,appContext);
        assertNull(testController.load(testCareProvider.getUuid(), appContext));
        assertNull(testController.loadFromREST(testCareProvider.getUuid()));
        assertNull(testController.loadFromStorage(testCareProvider.getUuid(), appContext));
    }
}
