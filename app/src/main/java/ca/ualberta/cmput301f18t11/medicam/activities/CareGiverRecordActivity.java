package ca.ualberta.cmput301f18t11.medicam.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import ca.ualberta.cmput301f18t11.medicam.R;
import ca.ualberta.cmput301f18t11.medicam.controllers.ElasticSearchController;
import ca.ualberta.cmput301f18t11.medicam.controllers.abstracts.PersistenceController;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.CareProviderRecordPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.ProblemPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.models.CareProviderRecord;
import ca.ualberta.cmput301f18t11.medicam.models.Problem;

public class CareGiverRecordActivity extends AppCompatActivity {
    private ListView careGiverRecordsView;
    private ArrayAdapter<String> recordArrayAdapter;
    private ArrayList<String> recordArrayList = new ArrayList<>();
    private PersistenceController<Problem> problemControler = new ProblemPersistenceController();
    private PersistenceController<CareProviderRecord> recordController = new CareProviderRecordPersistenceController();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care_giver_record);
        ElasticSearchController.setIndex_url("cmput301f18t11test");
        //Record example = new PatientRecord();
        //recordArrayList.add(example);

        Intent intent = getIntent();
        String problemUUID = intent.getStringExtra("problemUUID");
        Problem problem = problemControler.load(problemUUID,this);
        recordArrayList = problem.getPatientRecords();
        //TODO: add doctor NOTEs
        //recordArrayList.add(recordController.load())

        recordArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, recordArrayList);
        careGiverRecordsView = findViewById(R.id.careGiverRecordListView);
        careGiverRecordsView.setAdapter(recordArrayAdapter);

        // if Item Clicked
        careGiverRecordsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CareGiverRecordActivity.this,CareGiverAttachActivity.class);
                intent.putExtra("recordUUID", recordArrayList.get(position));
                startActivity(intent);
            }
        });

        // if Long pressed:
        // TODO Toast or show the actual comment.
        careGiverRecordsView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(CareGiverRecordActivity.this,"This will show the patient comments on this Long pressed record",Toast.LENGTH_LONG).show();
                return true;
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
