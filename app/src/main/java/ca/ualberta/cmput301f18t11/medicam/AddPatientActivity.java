package ca.ualberta.cmput301f18t11.medicam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddPatientActivity extends AppCompatActivity {
    private EditText enteredUserid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);
        enteredUserid = findViewById(R.id.patientUseridEditText);



    }
    public void finishAdding(View view){
                // TODO: check if the id exists in database
        if (enteredUserid.getText().toString().equals("")){Toast.makeText(AddPatientActivity.this,"Please Enter User Id",Toast.LENGTH_SHORT).show();
        } else {
            // TODO: change the type to Json object instead of a String
            Toast.makeText(this,"User: "+ enteredUserid.getText().toString() +"\n will be added! \n",Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
