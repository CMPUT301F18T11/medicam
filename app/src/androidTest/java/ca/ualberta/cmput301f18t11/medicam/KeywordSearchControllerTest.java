package ca.ualberta.cmput301f18t11.medicam;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.UUID;

import ca.ualberta.cmput301f18t11.medicam.controllers.ElasticSearchController;
import ca.ualberta.cmput301f18t11.medicam.controllers.abstracts.PersistenceController;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.CareProviderRecordPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.PatientRecordPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.models.CareProviderRecord;
import ca.ualberta.cmput301f18t11.medicam.models.PatientRecord;
import ca.ualberta.cmput301f18t11.medicam.models.abstracts.Record;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Test for CareProviderPersistenceController Class
 * which runs on android device.
 */
@RunWith(AndroidJUnit4.class)
public class KeywordSearchControllerTest {
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
        String title = "ing9843nvu9h8s4un";
        String descriptionWord1 = "asfkndsfadfsdfj";
        String descriptionWord2 = "fdsajkfndsjfnkjadsnfkj";
        String problemUUID = "thisisauniqueproblemuuid";
        String patientUUID = "thisisauniqueproblemuuid";

        String description = descriptionWord1 + " " + descriptionWord2;

        PatientRecord testRecord = new PatientRecord(patientUUID);
        testRecord.setTitle(title);
        testRecord.setDescription(description);
        testRecord.setProblemUUID(problemUUID);



        PatientRecordPersistenceController testController =
                new PatientRecordPersistenceController();

        testController.save(testRecord, appContext);

        List<PatientRecord> resultList = testController.searchFromREST(descriptionWord1, problemUUID);
        PatientRecord result = resultList.get(0);
        assertEquals(result.getUuid(), testRecord.getUuid());

        resultList = testController.searchFromREST(title, problemUUID);
        result = resultList.get(0);
        assertEquals(result.getUuid(), testRecord.getUuid());

        resultList = testController.searchFromREST(descriptionWord2, problemUUID);
        result = resultList.get(0);
        assertEquals(result.getUuid(), testRecord.getUuid());

    }
}
