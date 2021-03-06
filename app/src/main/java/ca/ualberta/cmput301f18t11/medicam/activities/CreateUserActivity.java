package ca.ualberta.cmput301f18t11.medicam.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import ca.ualberta.cmput301f18t11.medicam.R;
import ca.ualberta.cmput301f18t11.medicam.controllers.abstracts.PersistenceController;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.CareProviderPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.PatientPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.models.CareProvider;
import ca.ualberta.cmput301f18t11.medicam.models.Patient;

public class CreateUserActivity extends AppCompatActivity {
    private EditText enteredUserId;
    private EditText enteredPhoneNumber;
    private EditText enteredEmail;
    private RadioGroup radioGroup;
    private RadioButton accountTypeButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        enteredUserId = findViewById(R.id.userIDTextView);
        enteredPhoneNumber = findViewById(R.id.editingPhoneNumberText);
        enteredEmail = findViewById(R.id.editingEmailText);
        radioGroup = findViewById(R.id.radioGroup);

        //Toolbar Setup
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.createUser_toolbar);
        setSupportActionBar(toolbar);
        TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setText("Create Account");// Sets the title to be shown in the toolbar


    }

    public void checkButton(View view){
        int radio = radioGroup.getCheckedRadioButtonId();
        accountTypeButton = findViewById(radio);
    }

    public void createUserAndSignIn (View view){
        int radio = radioGroup.getCheckedRadioButtonId();
        accountTypeButton = findViewById(radio);
        PersistenceController<Patient> patientPersistenceController = new PatientPersistenceController();
        PersistenceController<CareProvider> careProviderPersistenceController = new CareProviderPersistenceController();

        if (enteredUserId.getText().toString().equals("")){
            Toast.makeText(this,"Please Enter a user id",Toast.LENGTH_SHORT).show();

        } else if (patientPersistenceController.load(enteredUserId.getText().toString(),this)
                != null || careProviderPersistenceController.load(enteredUserId.getText().toString(),
                this) != null){

            Toast.makeText(this,"User id existed, Enter another user id",Toast.LENGTH_SHORT).show();
        } else {
            Log.d("check", accountTypeButton.getText().toString());
            if (accountTypeButton.getText().toString().equals("Patient")) {
                Patient newPatient = new Patient(enteredUserId.getText().toString(),
                        enteredEmail.getText().toString(), enteredPhoneNumber.getText().toString());

                patientPersistenceController.save(newPatient,this);
                Intent intent = new Intent(CreateUserActivity.this, PatientProblemActivity.class);
                intent.putExtra("userid",enteredUserId.getText().toString());
                startActivity(intent);

            } else if(accountTypeButton.getText().toString().equals("Caretaker")){
                CareProvider newDoctor = new CareProvider(enteredUserId.getText().toString(),
                        enteredEmail.getText().toString(), enteredPhoneNumber.getText().toString());

                careProviderPersistenceController.save(newDoctor,this);
                Intent intent = new Intent(CreateUserActivity.this, CareProviderActivity.class);
                intent.putExtra("userid", enteredUserId.getText().toString());
                startActivity(intent);
            }
        }
    }



}
