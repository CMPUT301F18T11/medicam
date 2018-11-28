package ca.ualberta.cmput301f18t11.Robotium;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.UUID;

import ca.ualberta.cmput301f18t11.medicam.R;
import ca.ualberta.cmput301f18t11.medicam.activities.CreateUserActivity;
import ca.ualberta.cmput301f18t11.medicam.activities.LoginActivity;

import ca.ualberta.cmput301f18t11.medicam.activities.PatientProblemActivity;
import ca.ualberta.cmput301f18t11.medicam.activities.createProblemActivity;
import ca.ualberta.cmput301f18t11.medicam.models.Patient;

import static junit.framework.TestCase.assertTrue;

@RunWith(AndroidJUnit4.class)
public class RobotiumPatientTest {
    private String test_uid;
    private String test_phone_num;
    private String test_email;
    private String test_address;

    @Rule
    public ActivityTestRule<LoginActivity> loginActivityActivityTestRule =
            new ActivityTestRule<>(LoginActivity.class);
    private Solo solo;

    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),
                loginActivityActivityTestRule.getActivity());
    }

    @After
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

    @Test
    public void testAddProblem() throws Exception {
        test_uid = UUID.randomUUID().toString();
        test_phone_num = "123-456-7890";
        test_email = "test@tester.ca";
        test_address = "42 Wallaby Way, Sidney";
        String title = "Can't feel foot";
        String desc = "Since my amputation I can't feel my foot.";


        solo.unlockScreen();

        //create a new user
        solo.clickOnView(solo.getView(R.id.sign_up_button));
        solo.assertCurrentActivity("Expect CreateUser", CreateUserActivity.class);
        solo.enterText(0,test_uid);
        solo.enterText(1,test_phone_num);
        solo.enterText(2,test_email);
        solo.enterText(3,test_address);
        solo.clickOnView(solo.getView(R.id.createButton));

        //If the login was successful the next screen should be the PatientProblemActivity
        solo.assertCurrentActivity("Expect PatientProblemActivity", PatientProblemActivity.class);

        solo.assertCurrentActivity("Expect PatientProblemActivity", PatientProblemActivity.class);

        //Create a problem
        solo.clickOnView(solo.getView(R.id.floatingActionButton));
        solo.assertCurrentActivity("Expect CreateProblemActivity", createProblemActivity.class);
        solo.enterText(0, title);
        solo.enterText(1,desc);
        solo.clickOnView(solo.getView(R.id.createProblemButton));

        boolean problem_found = solo.searchText(title);

        assertTrue("Problem created or not created", problem_found);
    }
}