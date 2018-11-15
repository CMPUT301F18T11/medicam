package ca.ualberta.cmput301f18t11.medicam;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

public class problemActivity extends AppCompatActivity {
    private static final int ADD_RECORD_REQUEST_CODE = 0;
    //TODO: The current problem should be the one that the user selected in the prev activity
    Problem problem = new Problem("Title", new Date(), "Description");
    private TextView viewTitle, viewDescription;
    private ListView listView;
    private ArrayList<Record> records = new ArrayList<Record>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        records.add(new PatientRecord());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem);

        viewTitle = (TextView) findViewById(R.id.viewTitle);
        viewDescription = (TextView) findViewById(R.id.viewDesc);

        //Sets the title and description of Problem
        viewTitle.setText(problem.getTitle());
        viewDescription.setText(problem.getDescription());

        //Sets up the recordList adapter
        ArrayAdapter<Record> itemsAdapter = new ArrayAdapter<Record>(this, android.R.layout.simple_list_item_1, records);
        listView = (ListView) findViewById(R.id.recordListView);
        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(problemActivity.this, "Selected Record" , Toast.LENGTH_SHORT).show();
                //viewRecord(view);
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
            Toast.makeText(this, "HELLOTHISISHERE: "+newRecord.getTitle(), Toast.LENGTH_SHORT).show();
            records.add(newRecord);
            ArrayAdapter<Record> itemsAdapter = new ArrayAdapter<Record>(this, android.R.layout.simple_list_item_1, records);
            listView.setAdapter(itemsAdapter);
        }
    }

    public void exit(View view){
        this.finish();
    }

    public void search(View view){
        Toast.makeText(problemActivity.this, "Selected search button", Toast.LENGTH_SHORT).show();
    }

    public void createRecord(View view){
        Toast.makeText(problemActivity.this, "Create new record", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, createRecordActivity.class);
        startActivityForResult(intent,ADD_RECORD_REQUEST_CODE);
    }
}
