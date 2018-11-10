package ca.ualberta.cmput301f18t11.medicam;

import org.junit.Test;

import java.util.Date;
import java.util.UUID;

import ca.ualberta.cmput301f18t11.medicam.exceptions.ReassignmentException;
import ca.ualberta.cmput301f18t11.medicam.models.CareProviderRecord;

import static org.junit.Assert.*;

public class CareProviderRecordTest {

    @Test
    public void testAssignCareProvider(){
        CareProviderRecord cr_prvdr_rec =  new CareProviderRecord();
        UUID cr_prvdr_uuid = UUID.randomUUID();
        cr_prvdr_rec.setCare_provider(cr_prvdr_uuid);
        //To string because the object reference probably won't be preserved
        // when it is sent and brought back with ElasticSearch
        assertEquals(cr_prvdr_rec.getCare_provider().toString(),cr_prvdr_uuid.toString());
    }

    @Test(expected = ReassignmentException.class)
    public void cannot_reassign_care_provider(){
        //We should not be able to reassign which care_provider wrote this record
        CareProviderRecord cr_prvdr_rec =  new CareProviderRecord();
        UUID cr_prvdr_uuid = UUID.randomUUID();
        UUID other_uuid = UUID.randomUUID();
        cr_prvdr_rec.setCare_provider(cr_prvdr_uuid);
        cr_prvdr_rec.setCare_provider(other_uuid);
    }

    @Test
    public void testSetComment(){
        CareProviderRecord cr_prvdr_rec =  new CareProviderRecord();
        cr_prvdr_rec.setTitle("Title");
        cr_prvdr_rec.setDescription("Desc");
        assertTrue(cr_prvdr_rec.getTitle().equals("Title"));
        assertTrue(cr_prvdr_rec.getDescription().equals("Desc"));
    }

    @Test
    public void testSearchability(){
        CareProviderRecord cr_prvdr_rec =  new CareProviderRecord();
        cr_prvdr_rec.setTitle("Title");
        cr_prvdr_rec.setDescription("searchable key-word");
        cr_prvdr_rec.setTimestamp(new Date());
        assertTrue(cr_prvdr_rec.search("key"));
        assertFalse(cr_prvdr_rec.search("banana"));
    }
}