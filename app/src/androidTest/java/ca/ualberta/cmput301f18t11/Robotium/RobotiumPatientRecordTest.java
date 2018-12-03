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
import java.util.concurrent.ThreadLocalRandom;

import ca.ualberta.cmput301f18t11.medicam.R;
import ca.ualberta.cmput301f18t11.medicam.activities.BodyLocationActivity;
import ca.ualberta.cmput301f18t11.medicam.activities.BodyLocationListActivity;
import ca.ualberta.cmput301f18t11.medicam.activities.CustomCameraActivity;

import ca.ualberta.cmput301f18t11.medicam.activities.LoginActivity;
import ca.ualberta.cmput301f18t11.medicam.activities.PatientProblemActivity;
import ca.ualberta.cmput301f18t11.medicam.activities.PatientRecordActivity;
import ca.ualberta.cmput301f18t11.medicam.activities.createRecordActivity;
import ca.ualberta.cmput301f18t11.medicam.models.PatientRecord;

import static junit.framework.TestCase.assertTrue;

@RunWith(AndroidJUnit4.class)
public class RobotiumPatientRecordTest {
    private String test_uid;
    private String randomTestIndex = String.valueOf(ThreadLocalRandom.current().nextInt(0,101));
    private String test_record_title;
    private String test_record_comment;
    private String test_body_part;

    @Rule
    public ActivityTestRule<LoginActivity> createRecordActivityTestRule =
            new ActivityTestRule<>(LoginActivity.class);
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
    public void testCreateRecord() throws Exception {
        test_uid = "testing_patient_user";
        test_record_title = "test title" + randomTestIndex;
        test_record_comment = "test comment" + randomTestIndex;

        //sign in a new user
        solo.enterText(0, test_uid);
        solo.clickOnView(solo.getView(R.id.sign_in_button));
        //If the login was successful the next screen should be the PatientProblemActivity
        solo.assertCurrentActivity("Expect PatientProblemActivity", PatientProblemActivity.class);

        //Enter first problem in list
        solo.clickInList(1);
        solo.assertCurrentActivity("Expect PatientRecordActivity", PatientRecordActivity.class);

        //Create a record
        solo.clickOnView(solo.getView(R.id.fab)); //
        solo.assertCurrentActivity("Expect createRecordActivity", createRecordActivity.class);

        //Add bodylocation
        solo.clickOnView(solo.getView(R.id.bodyLocationButton4View));
        solo.assertCurrentActivity("Expect BodyLocationListActivity", BodyLocationListActivity.class);
        solo.clickInList(1);
        solo.assertCurrentActivity("Expect BodyLocationActivity", BodyLocationActivity.class);
        solo.clickOnView(solo.getView(R.id.displayReferencePhotoImageView));
        solo.clickOnView(solo.getView(R.id.displayReferencePhotoConfirmButton));

        //Add title and everything else
        boolean foundBodyPart = solo.searchText("my hand");
        solo.enterText(0, test_record_title);
        solo.enterText(1, test_record_comment);
        assertTrue("body location succesfully chose", foundBodyPart);
    }
}