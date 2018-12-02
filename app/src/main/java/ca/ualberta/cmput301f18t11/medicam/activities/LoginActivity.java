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

import ca.ualberta.cmput301f18t11.medicam.R;
import ca.ualberta.cmput301f18t11.medicam.controllers.ElasticSearchController;
import ca.ualberta.cmput301f18t11.medicam.controllers.abstracts.PersistenceController;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.CareProviderPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.PatientPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.ShortIDPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.models.CareProvider;
import ca.ualberta.cmput301f18t11.medicam.models.Patient;
import ca.ualberta.cmput301f18t11.medicam.models.ShortID;

public class LoginActivity extends AppCompatActivity {
    private EditText userIdOrShortID;
    private PersistenceController<CareProvider> doctorController = new CareProviderPersistenceController();
    private PersistenceController<Patient> patientController = new PatientPersistenceController();
    private ShortIDPersistenceController shortIDController = new ShortIDPersistenceController();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ElasticSearchController.setIndex_url("cmput301f18t11test");

        //TODO: put this in the right spot so that the conditional execution actually does something
        if (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            //This is the actual request.
            //Gimme that permission boy!
            Toast.makeText(LoginActivity.this, "Gimme that permission boy!", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(LoginActivity.this, new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.LOCATION_HARDWARE}, 1);
        } else {
            //We already have permissions
            Toast.makeText(LoginActivity.this, "Camera permissions have already been granted btw", Toast.LENGTH_SHORT).show();
        }
        //Assign buttons and text
        userIdOrShortID = (EditText) findViewById(R.id.user_id_or_short_id_edit_text); //can we make this id more specific?

    }

    //Launches into the create user activity
    public void createNewUser(View view){
        Intent intent = new Intent(this, CreateUserActivity.class);
        startActivity(intent);
    }

    //Launches into the main activity (Patient problem list/ Caregiver patient list)
    public void signIn(View view){
        if (userIdOrShortID.getText().toString().equals("")){
            Toast.makeText(this,"Please enter a user id to sign in",Toast.LENGTH_SHORT).show();
        }else {
            /**
             * try to fetch the user from the server and go to the coresponding activities
             */
            if(doctorController.load(userIdOrShortID.getText().toString(),this) !=null){
                Intent intent = new Intent(this, CareProviderActivity.class);
                intent.putExtra("userid", userIdOrShortID.getText().toString());
                startActivity(intent);
            }else if(patientController.load(userIdOrShortID.getText().toString(),this) !=null){
                Intent intent = new Intent(this, PatientProblemActivity.class);
                intent.putExtra("userid", userIdOrShortID.getText().toString());
                startActivity(intent);
            }else if(shortIDController.load(userIdOrShortID.getText().toString(),this)!=null){
                Intent intent = new Intent(this,PatientProblemActivity.class);
                ShortID shortID = shortIDController.load(userIdOrShortID.getText().toString(),this);
                String userUUID = shortID.getReference_uuid();
                intent.putExtra("userid",userUUID);
                startActivity(intent);
            }
            /**
             * Toast message to promt user to try again if fail to login with entered userid
             */
            else {
                Toast.makeText(this,"Invalid user id (or short id) try again",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1 : {

                if(grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //Permission was granted
                    //do stuff
                    Toast.makeText(LoginActivity.this, "Everything permissions granted, yay!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Camera permissions denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


}
