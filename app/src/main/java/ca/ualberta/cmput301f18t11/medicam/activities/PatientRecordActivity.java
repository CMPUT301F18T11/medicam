package ca.ualberta.cmput301f18t11.medicam.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ca.ualberta.cmput301f18t11.medicam.R;
import ca.ualberta.cmput301f18t11.medicam.controllers.ElasticSearchController;
import ca.ualberta.cmput301f18t11.medicam.controllers.abstracts.PersistenceController;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.CareProviderRecordPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.PatientRecordPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.ProblemPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.models.CareProviderRecord;
import ca.ualberta.cmput301f18t11.medicam.models.PatientRecord;
import ca.ualberta.cmput301f18t11.medicam.models.Problem;

public class PatientRecordActivity extends AppCompatActivity {
    private static final int ADD_RECORD_REQUEST_CODE = 0;
    private static final int EDIT_RECORD_REQUEST_CODE = 1;
    private TextView viewTitle, viewDescription;
    private ListView listView;
    private ListView doctorNoteView;
    private ArrayList<PatientRecord> patientRecordDisplayArray= new ArrayList<PatientRecord>();
    private ArrayList<CareProviderRecord> doctorRecordDisplayArray = new ArrayList<CareProviderRecord>();
    private ArrayList<String> records;
    private ArrayList<String> doctorRecords;
    private int indexOfClickedItem;
    private Problem previousProblem;
    private PersistenceController<Problem> problemController = new ProblemPersistenceController();
    private PersistenceController<PatientRecord> recordController = new PatientRecordPersistenceController();
    private PersistenceController<CareProviderRecord> doctorRecordController = new CareProviderRecordPersistenceController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem);
        ElasticSearchController.setIndex_url("cmput301f18t11test");
        doctorNoteView = findViewById(R.id.doctorNoteView);
        listView = (ListView) findViewById(R.id.recordListView);
        viewTitle = (TextView) findViewById(R.id.viewTitle);
        viewDescription = (TextView) findViewById(R.id.viewDesc);

        //Sets the title and description of Problem
        String problemUUID = getIntent().getStringExtra("previousProblem");
        previousProblem = problemController.load(problemUUID,this);
        viewTitle.setText(previousProblem.getTitle());
        viewDescription.setText(previousProblem.getDescription());
        records = previousProblem.getPatientRecords();
        doctorRecords = previousProblem.getCareProviderRecords();

        for (int i = 0; i < records.size(); i++){
            PatientRecord record = recordController.load(records.get(i), this);
            patientRecordDisplayArray.add(record);
        }

        for (int i = 0; i < doctorRecords.size(); i++){
            CareProviderRecord record = doctorRecordController.load(doctorRecords.get(i), this);
            doctorRecordDisplayArray.add(record);
        }


        if(doctorRecords.size() == 0){
            doctorNoteView.setVisibility(View.GONE);
        }

        //Sets up the recordList adapter

        ArrayAdapter<PatientRecord> itemsAdapter = new ArrayAdapter<PatientRecord>(this, android.R.layout.simple_list_item_1, patientRecordDisplayArray);
        listView.setAdapter(itemsAdapter);

        ArrayAdapter<CareProviderRecord> doctorAdapter = new ArrayAdapter<CareProviderRecord>(this, android.R.layout.simple_list_item_1, doctorRecordDisplayArray);
        doctorNoteView.setAdapter(doctorAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //Toast.makeText(PatientRecordActivity.this, "Selected Record" , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(PatientRecordActivity.this,createRecordActivity.class);
                intent.putExtra("purpose","edit");
                indexOfClickedItem = position;
                intent.putExtra("previous",records.get(indexOfClickedItem));
                startActivityForResult(intent,EDIT_RECORD_REQUEST_CODE);
            }
        });


        //Sets up the floating action button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createRecord(view);
            }
        });
    }
//This will return the object(PatientRecord) From create record activity.

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_RECORD_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            PatientRecord newRecord = (PatientRecord) data.getExtras().getSerializable("newRecord");
            previousProblem.addPatientRecord(newRecord.getUuid());
            problemController.save(previousProblem,this);
            patientRecordDisplayArray.add(newRecord);
            ArrayAdapter<PatientRecord> itemsAdapter = new ArrayAdapter<PatientRecord>(this, android.R.layout.simple_list_item_1, patientRecordDisplayArray);
            listView.setAdapter(itemsAdapter);
        }
        else if (requestCode == EDIT_RECORD_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            PatientRecord newRecord = (PatientRecord) data.getExtras().getSerializable("newRecord");
            records.set(indexOfClickedItem,newRecord.getUuid());
            patientRecordDisplayArray.set(indexOfClickedItem,newRecord);
            problemController.save(previousProblem,this);
            ArrayAdapter<PatientRecord> itemsAdapter = new ArrayAdapter<PatientRecord>(this, android.R.layout.simple_list_item_1, patientRecordDisplayArray);
            listView.setAdapter(itemsAdapter);
        }
    }

    public void exit(View view){
        this.finish();
    }

    public void search(View view){
        Toast.makeText(PatientRecordActivity.this, "Selected search button", Toast.LENGTH_SHORT).show();
    }

    public void createRecord(View view){
        Intent intent = new Intent(this,createRecordActivity.class);
        intent.putExtra("purpose","add");
        startActivityForResult(intent,ADD_RECORD_REQUEST_CODE);
    }
}
