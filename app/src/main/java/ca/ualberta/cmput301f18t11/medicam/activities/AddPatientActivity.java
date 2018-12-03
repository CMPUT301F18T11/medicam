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
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.ShortIDPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.models.CareProvider;
import ca.ualberta.cmput301f18t11.medicam.models.Patient;
import ca.ualberta.cmput301f18t11.medicam.models.ShortID;

public class AddPatientActivity extends AppCompatActivity {
    private EditText enteredUserid;
    private EditText shortIDView;
    private PersistenceController<CareProvider> doctorController = new CareProviderPersistenceController();
    private PersistenceController<Patient> patientController = new PatientPersistenceController();
    private CareProvider careProvider;
    private Patient newPatient = null;
    private ShortIDPersistenceController shortIDChontroller = new ShortIDPersistenceController();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ElasticSearchController.setIndex_url("cmput301f18t11test");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);
        enteredUserid = findViewById(R.id.patientUseridEditText);
        shortIDView = findViewById(R.id.short_id_edit_text);
        Intent intent = getIntent();
        String previousUUID = intent.getStringExtra("careProviderUUID");
        careProvider = doctorController.load(previousUUID,AddPatientActivity.this);



    }
    public void finishAdding(View view){

        if (enteredUserid.getText().toString().equals("")){Toast.makeText(AddPatientActivity.this,"Please Enter User Id",Toast.LENGTH_SHORT).show();
        } else {
            newPatient = patientController.load(enteredUserid.getText().toString(),AddPatientActivity.this);
            if (newPatient != null) {
                careProvider.addPatient(newPatient.getUuid());
                doctorController.save(careProvider, AddPatientActivity.this);
                finish();
            }else{Toast.makeText(AddPatientActivity.this,"No matching user id found",Toast.LENGTH_SHORT).show();}
        }
    }

    public void finishAddingViaShortID (View view){
        if (shortIDView.getText().toString().equals("")){Toast.makeText(AddPatientActivity.this,"Please Enter A Short ID",Toast.LENGTH_SHORT).show();
        }else{
            ShortID shortID = shortIDChontroller.load(shortIDView.getText().toString(),this);
            if (shortID != null) {
                String patientUUID = shortID.getReference_uuid();
                careProvider.addPatient(patientUUID);
                doctorController.save(careProvider,this);
                finish();
            }else{Toast.makeText(AddPatientActivity.this,"No matching short id found",Toast.LENGTH_SHORT).show();}
        }
    }
}
