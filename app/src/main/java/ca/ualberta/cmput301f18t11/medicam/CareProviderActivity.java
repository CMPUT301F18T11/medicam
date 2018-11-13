package ca.ualberta.cmput301f18t11.medicam;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.println;

public class CareProviderActivity extends AppCompatActivity {
    private ListView patientListView;
    private ArrayAdapter<Patient> patientArrayAdapter;
    private ArrayList<Patient> patientArrayList = new ArrayList<>();
    private int clickedIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care_provider);
        Patient examplePatient = new Patient("this is a user id");
        patientArrayList.add(examplePatient);


       // Set adapter and show the listView
        patientListView = findViewById(R.id.patientListView);
        patientArrayAdapter = new ArrayAdapter<Patient>(this,android.R.layout.simple_list_item_1,patientArrayList);
        patientListView.setAdapter(patientArrayAdapter);
        patientListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                careProviderProblemActivity(view);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        patientListView.setAdapter(patientArrayAdapter);
    }



    public void goAddPatient(View view){
        Intent intent = new Intent(this,AddPatientActivity.class);
        startActivity(intent);
    }

    public void careProviderProblemActivity(View view) {
        Intent intent = new Intent(this,CareGiverProblemActivity.class);
        startActivity(intent);
    }
}
