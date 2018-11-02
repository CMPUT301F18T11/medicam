package ca.ualberta.cmput301f18t11.medicam;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserUnitTest {
    private class TestUser extends User {
        public TestUser(String userID, String email, String phoneNumber) {
            super(userID, email, phoneNumber);
        }

        public TestUser(String userID) {
            super(userID);
        }

        public TestUser() {
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

        }
        assertNotEquals(testUser.getUserID(), testIDEmpty);

        String testIDNumbers = "123456789";
        testUser = new TestUser();
        try {
            testUser.setUserID(testIDNumbers);
        } catch (StringTooShortException e) {

        }
        assertEquals(testUser.getUserID(), testIDNumbers);

    }

    @Test
    public void testGetEmail() {

    }

    @Test
    public void testGetPhoneNumber() {

    }

    @Test
    public void testSetPhoneNumber() {

    }
}
