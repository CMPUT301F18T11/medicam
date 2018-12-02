package ca.ualberta.cmput301f18t11.medicam;

import org.junit.Test;

import ca.ualberta.cmput301f18t11.medicam.exceptions.InvalidEmailException;
import ca.ualberta.cmput301f18t11.medicam.exceptions.StringTooShortException;
import ca.ualberta.cmput301f18t11.medicam.models.abstracts.User;

import static org.junit.Assert.*;

public class UserUnitTest {
    private class TestUser extends User {
        private TestUser(String userID, String email, String phoneNumber) {
            super(userID, email, phoneNumber);
        }

        private TestUser(String userID) {
            super(userID);
        }

        private TestUser() {
        }
    }
    @Test
    public void testGetUserID() {
        String testID = "testidthatislongerthan8";
        User testUser = new TestUser(testID);
        assertEquals(testUser.getUserID(), testID);

        String testIDNumbers = "123456789";
        testUser = new TestUser(testIDNumbers);
        assertEquals(testUser.getUserID(), testIDNumbers);
    }

    @Test
    public void testSetUserID() {
        String testID = "testidofmorethan8characters";
        User testUser = new TestUser();
        try {
            testUser.setUserID(testID);
        } catch (StringTooShortException e) {
            assertTrue(false);
        }

        assertEquals(testUser.getUserID(), testID);

        String testIDEmpty = "";
        testUser = new TestUser();
        try {
            testUser.setUserID(testIDEmpty);
        } catch (StringTooShortException e) {
            // this should happen
        }
        assertNotEquals(testUser.getUserID(), testIDEmpty);

        String testIDNumbers = "123456789";
        testUser = new TestUser();
        try {
            testUser.setUserID(testIDNumbers);
        } catch (StringTooShortException e) {
            assertTrue(false);
        }
        assertEquals(testUser.getUserID(), testIDNumbers);

    }

    @Test
    public void testGetEmail() {
        String testID = "testidthatislongerthan8";
        String testEmail = "validemail@email.com";
        String testPhoneNumber = "(888) 888 - 8888";
        User testUser = new TestUser();
        try {
            testUser = null;
            testUser = new TestUser(testID,testEmail,testPhoneNumber);
        } catch (InvalidEmailException e) {
            assertTrue(false);
        }
        assertEquals(testUser.getEmail(), testEmail);
    }

    @Test
    public void testSetEmail() {
        String testEmail = "validemail@email.com";
        User testUser = new TestUser();
        try {
            testUser.setEmail(testEmail);
        } catch (InvalidEmailException e) {
            assertTrue(false);
        }
        assertEquals(testUser.getEmail(), testEmail);
    }

    @Test
    public void testGetPhoneNumber() {
        String testID = "testidthatislongerthan8";
        String testEmail = "validemail@email.com";
        String testPhoneNumber = "(888) 888 - 8888";
        User testUser = new TestUser();
        testUser = new TestUser(testID,testEmail,testPhoneNumber);
        assertEquals(testUser.getEmail(), testEmail);

        String testPhoneNumber1 = "888 888 8888";
        testUser = new TestUser(testID, testEmail, testPhoneNumber1);
        assertEquals(testUser.getPhoneNumber(), testPhoneNumber1);

    }

    @Test
    public void testSetPhoneNumber() {
        String testID = "testidthatislongerthan8";
        String testEmail = "validemail@email.com";
        String testPhoneNumber = "(888) 888 - 8888";
        User testUser = new TestUser();
        testUser.setPhoneNumber(testPhoneNumber);
        assertEquals(testUser.getPhoneNumber(), testPhoneNumber);

        String testPhoneNumber1 = "888 888 8888";
        testUser.setPhoneNumber(testPhoneNumber1);
        assertEquals(testUser.getPhoneNumber(), testPhoneNumber1);

        String testPhoneNumber2 = "8989898989";
        testUser.setPhoneNumber(testPhoneNumber2);
        assertEquals(testUser.getPhoneNumber(), testPhoneNumber2);
    }
}
