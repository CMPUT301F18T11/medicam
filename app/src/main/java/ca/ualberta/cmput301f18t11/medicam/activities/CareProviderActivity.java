package ca.ualberta.cmput301f18t11.medicam.activities;

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
import android.widget.Toast;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

import ca.ualberta.cmput301f18t11.medicam.R;
import ca.ualberta.cmput301f18t11.medicam.activities.AddPatientActivity;
import ca.ualberta.cmput301f18t11.medicam.controllers.ElasticSearchController;
import ca.ualberta.cmput301f18t11.medicam.controllers.abstracts.PersistenceController;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.CareProviderPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.PatientPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.models.CareProvider;
import ca.ualberta.cmput301f18t11.medicam.models.Patient;

import static java.sql.DriverManager.println;

public class CareProviderActivity extends AppCompatActivity {
    private ListView patientListView;
    private CareProvider careProvider;
    private ArrayAdapter<String> patientArrayAdapter;
    private List<String> patientArrayList = new ArrayList<>();
    private int clickedIndex;
    private PersistenceController<CareProvider> persistenceController = new CareProviderPersistenceController();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ElasticSearchController.setIndex_url("cmput301f18t11test");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care_provider);
        Intent intent = getIntent();
        String uuid = intent.getStringExtra("userid");

        careProvider = persistenceController.load(uuid,CareProviderActivity.this);
        //ArrayList = careProvider.getPatients();

       // Set adapter and show the listView
        patientArrayList = careProvider.getPatients();
        Toast.makeText(this,careProvider.getPatients().toString(),Toast.LENGTH_SHORT).show();
        patientListView = findViewById(R.id.patientListView);
        patientArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,patientArrayList);
        patientListView.setAdapter(patientArrayAdapter);
        patientListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CareProviderActivity.this,CareGiverProblemActivity.class);
                intent.putExtra("patientUUID",patientArrayList.get(position));
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        careProvider = persistenceController.load(careProvider.getUuid(),CareProviderActivity.this);
        Toast.makeText(this,careProvider.getPatients().toString(),Toast.LENGTH_SHORT).show();
        patientArrayList = careProvider.getPatients();
        patientArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,patientArrayList);
        patientListView.setAdapter(patientArrayAdapter);
    }



    public void goAddPatient(View view){
        Intent intent = new Intent(this,AddPatientActivity.class);
        intent.putExtra("careProviderUUID",careProvider.getUuid());
        startActivity(intent);
    }
}
