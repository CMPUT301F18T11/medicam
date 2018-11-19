package ca.ualberta.cmput301f18t11.medicam.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import ca.ualberta.cmput301f18t11.medicam.R;
import ca.ualberta.cmput301f18t11.medicam.controllers.ElasticSearchController;
import ca.ualberta.cmput301f18t11.medicam.controllers.abstracts.PersistenceController;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.CareProviderPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.PatientPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.models.CareProvider;
import ca.ualberta.cmput301f18t11.medicam.models.Patient;

public class LoginActivity extends AppCompatActivity {
    private EditText bodyText;
    private PersistenceController<CareProvider> doctorController = new CareProviderPersistenceController();
    private PersistenceController<Patient> patientController = new PatientPersistenceController();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ElasticSearchController.setIndex_url("cmput301f18t11test");
        //Assign buttons and text
        bodyText = (EditText) findViewById(R.id.editText); //can we make this id more specific?
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




}
