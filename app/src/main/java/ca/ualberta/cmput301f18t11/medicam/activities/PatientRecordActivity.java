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

import ca.ualberta.cmput301f18t11.medicam.R;
import ca.ualberta.cmput301f18t11.medicam.controllers.ElasticSearchController;
import ca.ualberta.cmput301f18t11.medicam.controllers.abstracts.PersistenceController;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.PatientRecordPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.ProblemPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.models.PatientRecord;
import ca.ualberta.cmput301f18t11.medicam.models.Problem;

public class PatientRecordActivity extends AppCompatActivity {
    private static final int ADD_RECORD_REQUEST_CODE = 0;
    private static final int EDIT_RECORD_REQUEST_CODE = 1;
    private TextView viewTitle, viewDescription;
    private ListView listView;
    private ArrayList<String> records;
    private int indexOfClickedItem;
    private Problem previousProblem;
    private PersistenceController<Problem> problemController = new ProblemPersistenceController();
    private PersistenceController<PatientRecord> recordController = new PatientRecordPersistenceController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem);
        ElasticSearchController.setIndex_url("cmput301f18t11test");


        viewTitle = (TextView) findViewById(R.id.viewTitle);
        viewDescription = (TextView) findViewById(R.id.viewDesc);

        //Sets the title and description of Problem
        String problemUUID = getIntent().getStringExtra("previousProblem");
        previousProblem = problemController.load(problemUUID,this);
        viewTitle.setText(previousProblem.getTitle());
        viewDescription.setText(previousProblem.getDescription());
        records = previousProblem.getPatientRecords();

        //Sets up the recordList adapter
        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, records);
        listView = (ListView) findViewById(R.id.recordListView);
        listView.setAdapter(itemsAdapter);



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
            ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, records);
            listView.setAdapter(itemsAdapter);
        }
        else if (requestCode == EDIT_RECORD_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            PatientRecord newRecord = (PatientRecord) data.getExtras().getSerializable("newRecord");
            records.set(indexOfClickedItem,newRecord.getUuid());

            ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, records);
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
