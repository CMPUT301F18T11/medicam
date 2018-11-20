package ca.ualberta.cmput301f18t11.medicam.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ca.ualberta.cmput301f18t11.medicam.R;
import ca.ualberta.cmput301f18t11.medicam.controllers.ElasticSearchController;
import ca.ualberta.cmput301f18t11.medicam.controllers.abstracts.PersistenceController;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.CareProviderPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.PatientPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.models.CareProvider;
import ca.ualberta.cmput301f18t11.medicam.models.Patient;

public class AddPatientActivity extends AppCompatActivity {
    private EditText enteredUserid;
    private PersistenceController<CareProvider> doctorController = new CareProviderPersistenceController();
    private PersistenceController<Patient> patientController = new PatientPersistenceController();
    private CareProvider careProvider;
    private Patient newPatient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ElasticSearchController.setIndex_url("cmput301f18t11test");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);
        enteredUserid = findViewById(R.id.patientUseridEditText);
        Intent intent = getIntent();
        String previousUUID = intent.getStringExtra("careProviderUUID");
        careProvider = doctorController.load(previousUUID,AddPatientActivity.this);



    }
    public void finishAdding(View view){
                // TODO: check if the id exists in database

        if (enteredUserid.getText().toString().equals("")){Toast.makeText(AddPatientActivity.this,"Please Enter User Id",Toast.LENGTH_SHORT).show();
        } else {
            newPatient = patientController.load(enteredUserid.getText().toString(),AddPatientActivity.this);
            careProvider.addPatient(newPatient.getUuid());
            doctorController.save(careProvider,AddPatientActivity.this);
            // TODO: change the type to Json object instead of a String
            finish();
        }
    }
}
