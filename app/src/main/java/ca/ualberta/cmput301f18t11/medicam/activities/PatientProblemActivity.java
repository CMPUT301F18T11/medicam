package ca.ualberta.cmput301f18t11.medicam.activities;

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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

import ca.ualberta.cmput301f18t11.medicam.R;
import ca.ualberta.cmput301f18t11.medicam.models.Problem;

public class PatientProblemActivity extends AppCompatActivity {
    private static final int ADD_PROBLEM_REQUESTCODE = 0;
    private static final int EDIT_PROBLEM_REQUEST_CODE =1;
    private ListView listView;
    private ArrayList<Problem> problemList = new ArrayList<Problem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        problemList.add(new Problem("Title", new Date(), "Description"));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile);

        //Sets up the problemList adapter
        ArrayAdapter<Problem> itemsAdapter = new ArrayAdapter<Problem>(this, android.R.layout.simple_list_item_1, problemList);
        listView = (ListView) findViewById(R.id.problemListView);
        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(PatientProblemActivity.this,PatientRecordActivity.class);
                //intent.putExtra("purpose","edit");
                //indexOfClickedItem = position;
                intent.putExtra("previousProblem",problemList.get(position));
                //startActivityForResult(intent,EDIT_PROBLEM_REQUEST_CODE);;
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Add new problem", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                createProblem(view);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_PROBLEM_REQUESTCODE && resultCode == RESULT_OK){
            Problem newProblem = (Problem) data.getExtras().getSerializable("newProblem");
            problemList.add(newProblem);
            ArrayAdapter<Problem> itemsAdapter = new ArrayAdapter<Problem>(this, android.R.layout.simple_list_item_1, problemList);
            listView.setAdapter(itemsAdapter);
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        //TODO: REFRESH THE LISTVIEW
    }

    //User clicks the search button
    public void searchButton(View view){
        Toast.makeText(this, "Selected search button", Toast.LENGTH_SHORT).show();
    }

    //User clicks Profile button
    public void viewProfile(View view){
        Toast.makeText(this, "Selected user profile button", Toast.LENGTH_SHORT).show();
    }

    public void createProblem(View view){
        Toast.makeText(this, "Add a new problem", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,createProblemActivity.class);
        startActivityForResult(intent,ADD_PROBLEM_REQUESTCODE);
    }
}

