package ca.ualberta.cmput301f18t11.medicam;

import org.junit.Test;

import java.util.Date;
import java.util.UUID;

import ca.ualberta.cmput301f18t11.medicam.exceptions.ReassignmentException;

import ca.ualberta.cmput301f18t11.medicam.models.CareProviderRecord;

import static org.junit.Assert.*;

public class CareProviderRecordTest {

    @Test
    public void testAssignProblemUUID(){
        CareProviderRecord cr_prvdr_rec =  new CareProviderRecord();
        String problemUUID = UUID.randomUUID().toString();
        cr_prvdr_rec.setProblemUUID(problemUUID);

        //To string because the object reference probably won't be preserved
        // when it is sent and brought back with ElasticSearch
        assertEquals(cr_prvdr_rec.getProblemUUID(), problemUUID);
    }

    @Test
    public void testSetComment(){
        CareProviderRecord cr_prvdr_rec =  new CareProviderRecord();
        cr_prvdr_rec.setTitle("Title");
        cr_prvdr_rec.setDescription("Desc");
        assertTrue(cr_prvdr_rec.getTitle().equals("Title"));
        assertTrue(cr_prvdr_rec.getDescription().equals("Desc"));
    }
}