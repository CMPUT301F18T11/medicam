package ca.ualberta.cmput301f18t11.medicam.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import ca.ualberta.cmput301f18t11.medicam.R;
import ca.ualberta.cmput301f18t11.medicam.controllers.ElasticSearchController;
import ca.ualberta.cmput301f18t11.medicam.controllers.abstracts.PersistenceController;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.CareProviderPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.PatientPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.models.CareProvider;
import ca.ualberta.cmput301f18t11.medicam.models.Patient;

public class CreateUserActivity extends AppCompatActivity {
    private EditText enteredUserId;
    private EditText enteredPhoneNumber;
    private EditText enteredEmail;
    private EditText enteredAddress;
    private RadioGroup radioGroup;
    private RadioButton accountTypeButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        ElasticSearchController.setIndex_url("cmput301f18t11test");
        enteredUserId = findViewById(R.id.newUserUserIdEdit);
        enteredPhoneNumber = findViewById(R.id.newUserPhoneEdit);
        enteredEmail = findViewById(R.id.newUserEmialEdit);
        enteredAddress = findViewById(R.id.newUserAddressEdit);
        radioGroup = findViewById(R.id.radioGroup);


    }
    //TODO : generate user id as a doctor/patient.
    public void checkButton(View view){
        int radio = radioGroup.getCheckedRadioButtonId();
        accountTypeButton = findViewById(radio);
        Toast.makeText(this,accountTypeButton.getText().toString(),Toast.LENGTH_SHORT).show();
    }

    public void createUserAndSignIn (View view){
        int radio = radioGroup.getCheckedRadioButtonId();
        accountTypeButton = findViewById(radio);
        PersistenceController<Patient> patientPersistenceController = new PatientPersistenceController();
        PersistenceController<CareProvider> careProviderPersistenceController = new CareProviderPersistenceController();
        if (enteredUserId.getText().toString().equals("")){
            Toast.makeText(this,"Please Enter a user id",Toast.LENGTH_SHORT).show();
        } else if (patientPersistenceController.load(enteredUserId.getText().toString(),this)!= null || careProviderPersistenceController.load(enteredUserId.getText().toString(),this) != null){
            Toast.makeText(this,"User id existed, Enter another user id",Toast.LENGTH_SHORT).show();
        } else {
            if (accountTypeButton.getText().toString().equals("Create Patient Account")) {
                Patient newPatient = new Patient(enteredUserId.getText().toString());
                patientPersistenceController.save(newPatient,this);
                Intent intent = new Intent(CreateUserActivity.this, PatientProblemActivity.class);
                intent.putExtra("userid",enteredUserId.getText().toString());
                startActivity(intent);

            } else if(accountTypeButton.getText().toString().equals("Create Doctor Account")){
                CareProvider newDoctor = new CareProvider(enteredUserId.getText().toString());
                careProviderPersistenceController.save(newDoctor,this);
                Intent intent = new Intent(CreateUserActivity.this, CareProviderActivity.class);
                intent.putExtra("userid",enteredUserId.getText().toString());
                startActivity(intent);
            }
        }
    }



}
