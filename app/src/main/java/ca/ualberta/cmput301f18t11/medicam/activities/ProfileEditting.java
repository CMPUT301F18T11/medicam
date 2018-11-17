package ca.ualberta.cmput301f18t11.medicam.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import ca.ualberta.cmput301f18t11.medicam.R;
import ca.ualberta.cmput301f18t11.medicam.controllers.abstracts.PersistenceController;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.PatientPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.models.Patient;

public class ProfileEditting extends AppCompatActivity {
    private TextView userId;
    private EditText enteredPhoneNumber;
    private EditText enteredEmail;
    private EditText enteredAddress;
    private Patient patient;
    private PersistenceController<Patient> patientController = new PatientPersistenceController();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_editting);

        userId = findViewById(R.id.userIDTextView);
        enteredPhoneNumber = findViewById(R.id.editingPhoneNumberText);
        enteredEmail = findViewById(R.id.editingEmailText);
        enteredAddress = findViewById(R.id.editinAddressText);

        Intent intent = getIntent();
        String userUUID = intent.getStringExtra("USERUUID");
        patient = patientController.load(userUUID,this);
    }


    public void finishEditing(View view){
        patient.setPhoneNumber(enteredPhoneNumber.getText().toString());
        patient.setEmail(enteredEmail.getText().toString());
        patient.setAddress(enteredAddress.getText().toString());
        patientController.save(patient,this);
        finish();
    }
}
