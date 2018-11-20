package ca.ualberta.cmput301f18t11.medicam.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.Serializable;

import ca.ualberta.cmput301f18t11.medicam.R;

public class BodyLocationActivity extends AppCompatActivity {
    private static final int ADD_BODYLOCATION_REQUEST_CODE = 3;
    private Spinner bodyLocationSpinner;
    private String bodyLocationName;
    //private BodyLocation bodyLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_location);

        //set up the bodyLocation spinner
        bodyLocationSpinner = findViewById(R.id.bodyLocationSpinner);
        ArrayAdapter<CharSequence> bodyLocationSpinnerAdapter = ArrayAdapter.createFromResource(this,R.array.body_location_spinner,android.R.layout.simple_selectable_list_item);
        bodyLocationSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        bodyLocationSpinner.setAdapter(bodyLocationSpinnerAdapter);
        bodyLocationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bodyLocationName = parent.getItemAtPosition(position).toString();
                Toast.makeText(BodyLocationActivity.this,"Body: "+bodyLocationName,Toast.LENGTH_SHORT).show();
                //bodyLocation = new BodyLocation();
                //bodyLocation.setBodyParts(bodyLocationName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //end of seting up locations
    }


    public void finishSetingBodyLocation(View view){
        Intent intent = new Intent();
        intent.putExtra("BodyLocation", bodyLocationName);
        setResult(RESULT_OK,intent);
        finish();
    }


}
