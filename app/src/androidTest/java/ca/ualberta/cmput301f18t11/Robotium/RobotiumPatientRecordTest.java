package ca.ualberta.cmput301f18t11.Robotium;
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
import ca.ualberta.cmput301f18t11.medicam.activities.CustomCameraActivity;

import ca.ualberta.cmput301f18t11.medicam.activities.createRecordActivity;

import static junit.framework.TestCase.assertTrue;

@RunWith(AndroidJUnit4.class)
public class RobotiumPatientRecordTest {
    private String test_uid;
    private String test_phone_num;
    private String test_email;
    private String test_address;

    @Rule
    public ActivityTestRule<createRecordActivity> createRecordActivityTestRule =
            new ActivityTestRule<>(createRecordActivity.class);
    private Solo solo;

    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),
                createRecordActivityTestRule.getActivity());
    }

    @After
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

    @Test
    public void testOpenCamera() throws Exception {
        solo.unlockScreen();

        login();

        solo.clickOnView(solo.getView(R.id.cameraButton));
        solo.assertCurrentActivity("Expected CustomCameraActivity", CustomCameraActivity.class);
    }

    private void login(){
        test_uid = UUID.randomUUID().toString();
        test_phone_num = "123-456-7890";
        test_email = "test@tester.ca";
        test_address = "42 Wallaby Way, Sidney";
        String title = "Can't feel foot";
        String desc = "Since my amputation I can't feel my foot.";


        solo.unlockScreen();

        //create a new user
        solo.clickOnView(solo.getView(R.id.sign_up_button));
        solo.enterText(0,test_uid);
        solo.enterText(1,test_phone_num);
        solo.enterText(2,test_email);
        solo.enterText(3,test_address);
        solo.clickOnView(solo.getView(R.id.create_user_button));

        //Create a problem
        solo.clickOnView(solo.getView(R.id.floatingActionButton));
        solo.enterText(0, title);
        solo.enterText(1,desc);
        solo.clickOnView(solo.getView(R.id.createProblemButton));

        boolean problem_found = solo.searchText(title);

        assertTrue("Problem created or not created", problem_found);
    }
}