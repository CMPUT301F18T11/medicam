package ca.ualberta.cmput301f18t11.medicam;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import ca.ualberta.cmput301f18t11.medicam.controllers.ElasticSearchController;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.PatientRecordPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.models.PatientRecord;
import ca.ualberta.cmput301f18t11.medicam.models.attachments.Geolocation;

import static org.junit.Assert.assertEquals;

/**
 * Test for CareProviderPersistenceController Class
 * which runs on android device.
 */
@RunWith(AndroidJUnit4.class)
public class GeolocationSearchControllerTest {
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
    public void testSearch() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        // use our test index
        ElasticSearchController.setIndex_url("cmput301f18t11test");
        String problemUUID = "thisisauniqueproblemuuid2";
        String patientUUID = "thisisauniqueproblemuuid2";
        Geolocation geolocation = new Geolocation(0,0);



        PatientRecord testRecord = new PatientRecord(patientUUID);
        testRecord.setLocation(geolocation);
        testRecord.setProblemUUID(problemUUID);



        PatientRecordPersistenceController testController =
                new PatientRecordPersistenceController();

        testController.save(testRecord, appContext);

        List<PatientRecord> resultList = testController
                .searchGeoLocationFromREST(geolocation.getLatitude(),geolocation.getLongitude(), 100,problemUUID);
        PatientRecord result = resultList.get(0);
        assertEquals(result.getUuid(), testRecord.getUuid());

    }
}
