package ca.ualberta.cmput301f18t11.medicam;

import org.junit.Test;

import java.util.Date;
import java.util.UUID;


import ca.ualberta.cmput301f18t11.medicam.models.PatientRecord;
import ca.ualberta.cmput301f18t11.medicam.models.attachments.BodyLocation;
import ca.ualberta.cmput301f18t11.medicam.models.attachments.Geolocation;

import static org.junit.Assert.*;

public class PatientRecordTest {

    @Test
    public void testAssignProblem(){
        PatientRecord patient_rec =  new PatientRecord();
        String problem_uuid = UUID.randomUUID().toString();

        patient_rec.setProblemUUID(problem_uuid);
        //To string because the object reference probably won't be preserved
        // if it is sent and brought back with ElasticSearch
        assertEquals(patient_rec.getProblemUUID(),problem_uuid);

        //To string because the object reference probably won't be preserved
        // if it is sent and brought back with ElasticSearch
    }

    @Test
    public void testGetterSetters() {
        String uuid = "12391230923";
        String title = "title";
        String description = "description";
        Date time = new Date();
        String creatoruuid = "112312312312";
        BodyLocation bodylocation = new BodyLocation();
        Geolocation mapLocation = new Geolocation(0.1,0.1);
        PatientRecord patientRecord = new PatientRecord(uuid, title, description, time, creatoruuid, bodylocation, mapLocation);

        assertEquals(patientRecord.getUuid(), uuid);
        assertEquals(patientRecord.getTitle(), title);
        assertEquals(patientRecord.getDescription(), description);
        assertEquals(patientRecord.getTimestamp(), time);
        assertEquals(patientRecord.getProblemUUID(), creatoruuid);
        assertEquals(patientRecord.getBodyLocation().getImageCoordinates(), bodylocation.getImageCoordinates());
        assertEquals(patientRecord.getBodyLocation().getReferencePhoto(), bodylocation.getReferencePhoto());


        assertTrue(patientRecord.getLocation().getLatitude() > 0.09);
        assertTrue(patientRecord.getLocation().getLongitude() > 0.09);

        assertTrue(patientRecord.getLocation().getLatitude() < 0.11);
        assertTrue(patientRecord.getLocation().getLongitude() < 0.11);
    }
}