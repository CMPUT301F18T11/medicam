package ca.ualberta.cmput301f18t11.medicam.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import ca.ualberta.cmput301f18t11.medicam.R;

public class LoginActivity extends AppCompatActivity {
    private EditText bodyText;
    private String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Assign buttons and text
        bodyText = (EditText) findViewById(R.id.editText);

    }

    //Launches into the create user activity
    public void createNewUser(View view){
        Intent intent = new Intent(this,CreateUserActivity.class);
        startActivity(intent);
    }
    //TODO: Check if userid is valid and then launch into the user profile
    //Launches into the main activity (Patient problem list/ Caregiver patient list)
    public void signIn(View view){
        userid = bodyText.getText().toString();
        Intent intent = new Intent(this,PatientProblemActivity.class);
        startActivity(intent);
    }




}
