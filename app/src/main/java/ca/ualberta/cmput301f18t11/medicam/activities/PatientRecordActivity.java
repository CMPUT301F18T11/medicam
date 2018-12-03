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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import ca.ualberta.cmput301f18t11.medicam.models.abstracts.Record;

public class PatientRecordActivity extends AppCompatActivity {
    private static final int ADD_RECORD_REQUEST_CODE = 0;
    private TextView viewTitle, viewDescription;
    private ListView listView;
    private List<Record> displayList= new ArrayList<>();
    private ArrayList<String> records;
    private ArrayList<String> doctorRecords;
    private int indexOfClickedItem;
    private Problem previousProblem;
    private PersistenceController<Problem> problemController = new ProblemPersistenceController();
    private PersistenceController<PatientRecord> recordController = new PatientRecordPersistenceController();
    private PersistenceController<CareProviderRecord> doctorRecordController = new CareProviderRecordPersistenceController();
    private String patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem);
        ElasticSearchController.setIndex_url("cmput301f18t11test");
        listView = (ListView) findViewById(R.id.recordListView);
        viewTitle = (TextView) findViewById(R.id.viewTitle);
        viewDescription = (TextView) findViewById(R.id.viewDesc);

        //Sets the title and description of Problem
        String problemUUID = getIntent().getStringExtra("previousProblem");
        patient = getIntent().getStringExtra("patient");
        previousProblem = problemController.load(problemUUID,this);
        viewTitle.setText(previousProblem.getTitle());
        viewDescription.setText(previousProblem.getDescription());
        records = previousProblem.getPatientRecords();
        doctorRecords = previousProblem.getCareProviderRecords();

        for (int i = 0; i < records.size(); i++){
            PatientRecord record = recordController.load(records.get(i), this);
            displayList.add(record);
        }

        for (int i = 0; i < doctorRecords.size(); i++){
            CareProviderRecord record = doctorRecordController.load(doctorRecords.get(i), this);
            displayList.add(record);
        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Record record = displayList.get(position);
                if(records.contains(record.getUuid())) {
                    Intent intent = new Intent(PatientRecordActivity.this, ViewRecordActivity.class);
                    indexOfClickedItem = position;
                    intent.putExtra("editable","YES");
                    intent.putExtra("previous", record.getUuid());
                    intent.putExtra("previousProblem", previousProblem.getUuid());
                    intent.putExtra("patient", patient);
                    startActivity(intent);
                }else if(doctorRecords.contains(record.getUuid())){
                    Intent intent = new Intent(PatientRecordActivity.this, ViewCareProviderRecordActivity.class);
                    indexOfClickedItem = position;
                    intent.putExtra("editable","NO");
                    intent.putExtra("previousDoctorRecord",record.getUuid());
                    startActivity(intent);
                }
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
    protected void onStart() {
        super.onStart();
        if (displayList.size() > 1){
            sortDatArray();
        }
        ArrayAdapter<Record> itemsAdapter = new ArrayAdapter<Record>(this, android.R.layout.simple_list_item_1, displayList);
        listView.setAdapter(itemsAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        records = previousProblem.getPatientRecords();
        doctorRecords = previousProblem.getCareProviderRecords();
        displayList.clear();
        for (int i = 0; i < records.size(); i++){
            PatientRecord record = recordController.load(records.get(i), this);
            displayList.add(record);
        }

        for (int i = 0; i < doctorRecords.size(); i++){
            CareProviderRecord record = doctorRecordController.load(doctorRecords.get(i), this);
            displayList.add(record);
        }
        if (displayList.size() > 1){
            sortDatArray();
        }
        ArrayAdapter<Record> itemsAdapter = new ArrayAdapter<Record>(this, android.R.layout.simple_list_item_1, displayList);
        listView.setAdapter(itemsAdapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_RECORD_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            PatientRecord newRecord = (PatientRecord) data.getExtras().getSerializable("newRecord");
            previousProblem.addPatientRecord(newRecord.getUuid());
            problemController.save(previousProblem,this);
            displayList.add(newRecord);
        }
    }

    public void exit(View view){
        this.finish();
    }

    public void search(View view){
        Intent intent  = new Intent(this,SearchActivity.class);
        intent.putExtra("searchFor","records");
        intent.putExtra("previousProblem",previousProblem);
        intent.putExtra("accessType","patient");
        startActivity(intent);
    }

    public void createRecord(View view){
        Intent intent = new Intent(this,createRecordActivity.class);
        intent.putExtra("purpose","add");
        intent.putExtra("problemUUID", previousProblem.getUuid());
        startActivityForResult(intent,ADD_RECORD_REQUEST_CODE);
    }

    private void sortDatArray (){  // simple method of sorting an Array List that contains objects.
        Collections.sort(displayList, new Comparator<Record>() {
            @Override
            public int compare(Record o1, Record o2) {

                return Integer.valueOf((o2.getTimestamp()).compareTo(o1.getTimestamp()));
            }
        });
    }
}
