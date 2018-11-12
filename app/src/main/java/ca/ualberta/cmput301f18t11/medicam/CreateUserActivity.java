package ca.ualberta.cmput301f18t11.medicam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateUserActivity extends AppCompatActivity {
    private EditText enteredUserId;
    private EditText enteredPhoneNumber;
    private EditText enteredEmail;
    private EditText enteredAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);


        enteredUserId = findViewById(R.id.newUserUserIdEdit);
        enteredPhoneNumber = findViewById(R.id.newUserPhoneEdit);
        enteredEmail = findViewById(R.id.newUserEmialEdit);
        enteredAddress = findViewById(R.id.newUserAddressEdit);


    }
    //TODO : generate user id as a doctor/patient.
    public void setCareGiver(View view){
        Toast.makeText(this,"will set up as a careGiver",Toast.LENGTH_LONG).show();
    }
    public void setPatient(View view){
        Toast.makeText(this,"will set up as a Patient",Toast.LENGTH_LONG).show();
    }

    public void createUserAndSignIn (View view){
        Intent intent = new Intent(CreateUserActivity.this,CareProviderActivity.class);
        startActivity(intent);
    }



}
