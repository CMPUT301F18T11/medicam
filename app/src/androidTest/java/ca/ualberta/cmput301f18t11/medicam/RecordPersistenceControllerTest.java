package ca.ualberta.cmput301f18t11.medicam;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.UUID;

import ca.ualberta.cmput301f18t11.medicam.controllers.ElasticSearchController;
import ca.ualberta.cmput301f18t11.medicam.controllers.abstracts.PersistenceController;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.CareProviderRecordPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.PatientRecordPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.models.CareProviderRecord;
import ca.ualberta.cmput301f18t11.medicam.models.Patient;
import ca.ualberta.cmput301f18t11.medicam.models.PatientRecord;
import ca.ualberta.cmput301f18t11.medicam.models.abstracts.Record;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Test for CareProviderPersistenceController Class
 * which runs on android device.
 */
@RunWith(AndroidJUnit4.class)
public class RecordPersistenceControllerTest {
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
    public void testPersistencePatientRecord() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        // use our test index
        ElasticSearchController.setIndex_url("cmput301f18t11test");
        UUID uuid = UUID.randomUUID();

        PatientRecord testRecord = new PatientRecord(uuid);

        PersistenceController<PatientRecord> testController = new PatientRecordPersistenceController();

        testController.save(testRecord, appContext);
        // test load
        PatientRecord result = testController.load(testRecord.getUuid(), appContext);
        assertEquals(result.getUuid(), testRecord.getUuid());

        // test loadFromRest
        result =  testController.loadFromREST(testRecord.getUuid());
        assertEquals(result.getUuid(), testRecord.getUuid());


        // test loadFromStorage
        result = testController.loadFromStorage(testRecord.getUuid(), appContext);
        assertEquals(result.getUuid(), testRecord.getUuid());


        // delete from both
        testController.delete(testRecord,appContext);
        assertNull(testController.load(testRecord.getUuid(), appContext));
        assertNull(testController.loadFromREST(testRecord.getUuid()));
        assertNull(testController.loadFromStorage(testRecord.getUuid(), appContext));
    }

    /**
     * Note: Don't use loadFromREST or loadFromStorage directly,
     * Only for testing purposes.
     */
    @Test
    public void testPersistenceCareProviderRecord() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        // use our test index
        ElasticSearchController.setIndex_url("cmput301f18t11test");
        UUID uuid = UUID.randomUUID();

        CareProviderRecord testRecord = new CareProviderRecord(uuid);

        PersistenceController<CareProviderRecord> testController = new CareProviderRecordPersistenceController();

        testController.save(testRecord, appContext);
        // test load
        Record result = testController.load(testRecord.getUuid(), appContext);
        assertEquals(result.getUuid(), testRecord.getUuid());

        // test loadFromRest
        result =  testController.loadFromREST(testRecord.getUuid());
        assertEquals(result.getUuid(), testRecord.getUuid());


        // test loadFromStorage
        result = testController.loadFromStorage(testRecord.getUuid(), appContext);
        assertEquals(result.getUuid(), testRecord.getUuid());


        // delete from both
        testController.delete(testRecord, appContext);
        assertNull(testController.load(testRecord.getUuid(), appContext));
        assertNull(testController.loadFromREST(testRecord.getUuid()));
        assertNull(testController.loadFromStorage(testRecord.getUuid(), appContext));
    }
}
