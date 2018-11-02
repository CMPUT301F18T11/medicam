package ca.ualberta.cmput301f18t11.medicam;

import android.icu.text.AlphabeticIndex;
import android.util.Log;

import org.junit.Test;

import java.util.Date;

import static android.content.ContentValues.TAG;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ProblemTest {

    @Test
    public void testAddRecord(){
        DummyProblemClass test = new DummyProblemClass("title", new Date(), "description");
        DummyRecordClass record = new DummyRecordClass();
        test.addRecord(record);
        assertTrue(test.hasRecord(record));
    }

    @Test
    public void testRemoveRecord(){
        DummyProblemClass test = new DummyProblemClass("title", new Date(), "description");
        DummyRecordClass record = new DummyRecordClass();
        test.addRecord(record);
        test.deleteRecord(record);
        assertFalse(test.hasRecord(record));

    }



    @Test
    public void testTitleLimit() {
        DummyProblemClass test = new DummyProblemClass("title", new Date(), "description");
        String text = "AAA";
        boolean thrown = false;

        try {
            test.setTitle(text);
        } catch (StringLengthTooLong e) {
            thrown = true;
        }
        assertTrue(thrown);
    }
    @Test
    public void testDescriptionLimit() {
        DummyProblemClass test = new DummyProblemClass("title", new Date(), "description");
        String text = "AAA";
        boolean thrown = false;

        try {
            test.setDescription(text);
        } catch (StringLengthTooLong e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    /* Gets and sets don't need to be tested
    @Test
    public void testHasDate(){
        DummyProblemClass test = new DummyProblemClass("title", new Date(), "description");
        Date returnDate = test.getDate();
        assertEquals(test.getDate(), returnDate);
    }

    @Test
    public void testHasTitle(){
        DummyProblemClass test = new DummyProblemClass("title", new Date(), "description");
        String returnText = test.getTitle();
        assertEquals(test.getTitle(), returnText);
    }

    @Test
    public void testHasDescription(){
        DummyProblemClass test = new DummyProblemClass("title", new Date(), "description");
        String returnText = test.getDescription();
        assertEquals(test.getDescription(), returnText);
    }


    @Test
    public void testSetDate(){
        DummyProblemClass test = new DummyProblemClass("title", new Date(), "description");
    }

    @Test
    public void testSetTitle(){
        DummyProblemClass test = new DummyProblemClass("title", new Date(), "description");
    }

    public void testSetDescription(){
        DummyProblemClass test = new DummyProblemClass("title", new Date(), "description");
    }

    public void testDescriptionLimit(){
        DummyProblemClass test = new DummyProblemClass("title", new Date(), "description");
    }
    */
}

