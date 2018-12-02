package ca.ualberta.cmput301f18t11.medicam;

import org.junit.Test;

import java.util.Date;
import java.util.UUID;

import ca.ualberta.cmput301f18t11.medicam.exceptions.StringLengthTooLongException;
import ca.ualberta.cmput301f18t11.medicam.models.Problem;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ProblemTest {

    @Test
    public void testAddRecord(){
        Problem test = new Problem(UUID.randomUUID().toString(), "title", new Date(), "description", "245235");
        String record = UUID.randomUUID().toString();
        test.addPatientRecord(record);
        assertTrue(test.hasRecord(record));
    }

    @Test
    public void testRemoveRecord(){
        Problem test = new Problem(UUID.randomUUID().toString(), "title", new Date(), "description", "56546");
        String record = UUID.randomUUID().toString();
        test.addPatientRecord(record);
        test.deletePatientRecord(record);
        assertFalse(test.hasRecord(record));

    }



    @Test
    public void testTitleLimit() {
        Problem test = new Problem(UUID.randomUUID().toString(), "title", new Date(), "description", "56546");
        StringBuilder text = new StringBuilder();
        String textA = "AAAAAAAAAAAA";
        for (int x = 0; x <= 10; x++) {
            text.append(textA);
        }
        boolean thrown = false;

        try {
            test.setTitle(text.toString());
        } catch (StringLengthTooLongException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }
    @Test
    public void testDescriptionLimit() {
        Problem test = new Problem(UUID.randomUUID().toString(), "title", new Date(), "description", "56546");
        StringBuilder text = new StringBuilder();
        String textA = "AAAAAAAAAAAA";
        for (int x = 0; x <= 30; x++) {
            text.append(textA);
        }
        boolean thrown = false;

        try {
            test.setDescription(text.toString());
        } catch (StringLengthTooLongException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    /* Gets and sets don't need to be tested, but are just in case
    @Test
    public void testHasDate(){
        Problem test = new Problem("title", new Date(), "description");
        Date returnDate = test.getDate();
        assertEquals(test.getDate(), returnDate);
    }

    @Test
    public void testHasTitle(){
        Problem test = new Problem("title", new Date(), "description");
        String returnText = test.getTitle();
        assertEquals(test.getTitle(), returnText);
    }

    @Test
    public void testHasDescription(){
        Problem test = new Problem("title", new Date(), "description");
        String returnText = test.getDescription();
        assertEquals(test.getDescription(), returnText);
    }

    @Test
    public void testSetTitle(){
        Problem test = new Problem("title", new Date(), "description");
        String text = "test";
        test.setTitle(text);
        assertEquals(test.getTitle(), text);
    }

    @Test
    public void testSetDescription(){
        Problem test = new Problem("title", new Date(), "description");
        String text = "test";
        test.setDescription(text);
        assertEquals(test.getDescription(), text);
    }

    @Test
    public void testSetDate(){
        Problem test = new Problem("title", new Date(), "description");
        Date date = new Date();
        test.setDate(date);
        assertEquals(test.getDate(), date);
    }

    */
}

