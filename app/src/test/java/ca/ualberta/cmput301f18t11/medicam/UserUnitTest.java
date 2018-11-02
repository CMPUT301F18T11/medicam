package ca.ualberta.cmput301f18t11.medicam;

import org.junit.Test;

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

        String testInvalidEmail1 = "invalidemail.com";
        try {
            testUser = null;
            testUser = new TestUser(testID, testInvalidEmail1, testPhoneNumber);
        } catch (InvalidEmailException e) {
            // this should happen
        }
        assertEquals(testUser, null);

        String testInvalidEmail2 = "invalidemail@com";
        try {
            testUser = null;
            testUser = new TestUser(testID, testInvalidEmail2, testPhoneNumber);
        } catch (InvalidEmailException e) {
            // this should happen
        }
        assertEquals(testUser, null);
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

        String testInvalidEmail1 = "invalidemail.com";
        try {
            testUser.setEmail(testInvalidEmail1);
        } catch (InvalidEmailException e) {
            // this should happen
        }
        assertNotEquals(testUser.getEmail(), testInvalidEmail1);

        String testInvalidEmail2 = "invalidemail@com";
        try {
            testUser.setEmail(testInvalidEmail2);
        } catch (InvalidEmailException e) {
            // this should happen
        }
        assertNotEquals(testUser.getEmail(), testInvalidEmail2);

        String testEmptyEmail = "";
        try {
            testUser.setEmail(testEmptyEmail);
        } catch (InvalidEmailException e) {
            // this should happen
        }
        assertNotEquals(testUser.getEmail(), testEmptyEmail);
    }

    @Test
    public void testGetPhoneNumber() {
        String testID = "testidthatislongerthan8";
        String testEmail = "validemail@email.com";
        String testPhoneNumber = "(888) 888 - 8888";
        User testUser = new TestUser();
        try {
            testUser = new TestUser(testID,testEmail,testPhoneNumber);
        } catch (InvalidEmailException e) {
            //not testing this
        }
        assertEquals(testUser.getEmail(), testEmail);

        String testPhoneNumber1 = "888 888 8888";
        try {
            testUser = new TestUser(testID, testEmail, testPhoneNumber1);
        } catch (InvalidEmailException e) {
            // not testing this
        }
        assertEquals(testUser.getPhoneNumber(), testPhoneNumber1);

        String testPhoneNumber2 = "8989898989";
        try {
            testUser = new TestUser(testID, testPhoneNumber2, testPhoneNumber);
        } catch (InvalidEmailException e) {
            // not testing this
        }
        assertEquals(testUser, null);
    }

    @Test
    public void testSetPhoneNumber() {
        String testID = "testidthatislongerthan8";
        String testEmail = "validemail@email.com";
        String testPhoneNumber = "(888) 888 - 8888";
        User testUser = new TestUser();
        try {
            testUser.setPhoneNumber(testPhoneNumber);
        } catch (InvalidEmailException e) {
            //not testing this
        }
        assertEquals(testUser.getEmail(), testEmail);

        String testPhoneNumber1 = "888 888 8888";
        try {
            testUser.setPhoneNumber(testPhoneNumber1);
        } catch (InvalidEmailException e) {
            // not testing this
        }
        assertEquals(testUser.getPhoneNumber(), testPhoneNumber1);

        String testPhoneNumber2 = "8989898989";
        try {
            testUser.setPhoneNumber(testPhoneNumber2);
        } catch (InvalidEmailException e) {
            // not testing this
        }
        assertEquals(testUser.getPhoneNumber(), testPhoneNumber2);
    }
}
