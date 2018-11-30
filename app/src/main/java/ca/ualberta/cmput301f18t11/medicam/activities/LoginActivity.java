package ca.ualberta.cmput301f18t11.medicam.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import ca.ualberta.cmput301f18t11.medicam.R;
import ca.ualberta.cmput301f18t11.medicam.controllers.ElasticSearchController;
import ca.ualberta.cmput301f18t11.medicam.controllers.abstracts.PersistenceController;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.CareProviderPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.PatientPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.ProblemPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.models.CareProvider;
import ca.ualberta.cmput301f18t11.medicam.models.Patient;
import ca.ualberta.cmput301f18t11.medicam.models.Problem;

public class LoginActivity extends AppCompatActivity {
    private EditText bodyText;
    private PersistenceController<CareProvider> doctorController = new CareProviderPersistenceController();
    private PersistenceController<Patient> patientController = new PatientPersistenceController();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ElasticSearchController.setIndex_url("cmput301f18t11test");
        //TODO: put this in the right spot so that the conditional execution actually does something
        if (ContextCompat.checkSelfPermission(LoginActivity.this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            //This is the actual request.
            //Gimme that permission boy!
            Toast.makeText(LoginActivity.this, "Gimme that permission boy!", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(LoginActivity.this,new String[]{Manifest.permission.CAMERA}, 1);
        } else {
            //We already have permissions
            Toast.makeText(LoginActivity.this, "Camera permissions have already been granted btw", Toast.LENGTH_SHORT).show();
        }
        //Assign buttons and text
        bodyText = (EditText) findViewById(R.id.editText); //can we make this id more specific?

        // testing
        test();
    }

    //Launches into the create user activity
    public void createNewUser(View view){
        Intent intent = new Intent(this,CreateUserActivity.class);
        startActivity(intent);
    }

    //Launches into the main activity (Patient problem list/ Caregiver patient list)
    public void signIn(View view){
        if (bodyText.getText().toString().equals("")){
            Toast.makeText(this,"Please enter a user id to sign in",Toast.LENGTH_SHORT).show();
        }else {
            /**
             * try to fetch the user from the server and go to the coresponding activities
             */
            if(doctorController.load(bodyText.getText().toString(),this) !=null){
                Intent intent = new Intent(this, CareProviderActivity.class);
                intent.putExtra("userid",bodyText.getText().toString());
                startActivity(intent);
            }else if(patientController.load(bodyText.getText().toString(),this) !=null){
                Intent intent = new Intent(this, PatientProblemActivity.class);
                intent.putExtra("userid",bodyText.getText().toString());
                startActivity(intent);
            }
            /**
             * Toast message to promt user to try again if fail to login with entered userid
             */
            else {
                Toast.makeText(this,"Invalid user id try again",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void test()
    {
        ProblemPersistenceController ps = new ProblemPersistenceController();

        List<Problem> test_search_results = ps.searchFromREST("foot");

        int x = 1;

    }




}
