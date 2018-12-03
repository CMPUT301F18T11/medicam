package ca.ualberta.cmput301f18t11.medicam;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Pair;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import ca.ualberta.cmput301f18t11.medicam.controllers.ElasticSearchController;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.PatientRecordPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.models.PatientRecord;
import ca.ualberta.cmput301f18t11.medicam.models.attachments.BodyLocation;
import ca.ualberta.cmput301f18t11.medicam.models.attachments.Geolocation;

import static org.junit.Assert.assertEquals;

/**
 * Test for CareProviderPersistenceController Class
 * which runs on android device.
 */
@RunWith(AndroidJUnit4.class)
public class BodyLocationSearchControllerTest {
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
        String problemUUID = "thisisauniqueproblemuuid3";
        String patientUUID = "thisisauniqueproblemuuid3";
        String photoUUID = "thisisauniquephotouuid3";
        String label = "dsfasdf";
        String bodyPart = "part of body";
        float lat = 0;
        float lon = 0;
        BodyLocation bodyLocation = new BodyLocation(new Pair<>(lat, lon),photoUUID,label,bodyPart);

        PatientRecord testRecord = new PatientRecord(patientUUID);
        testRecord.setBodyLocation(bodyLocation);
        testRecord.setProblemUUID(problemUUID);



        PatientRecordPersistenceController testController =
                new PatientRecordPersistenceController();

        testController.save(testRecord, appContext);

        List<PatientRecord> resultList = testController
                .searchBodyLocationFromREST(photoUUID, problemUUID);
        PatientRecord result = resultList.get(0);
        assertEquals(result.getUuid(), testRecord.getUuid());

    }
}
