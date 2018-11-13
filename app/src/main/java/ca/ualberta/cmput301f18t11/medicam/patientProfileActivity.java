package ca.ualberta.cmput301f18t11.medicam;

import android.content.Intent;
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

public class patientProfileActivity extends AppCompatActivity {
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
                Toast.makeText(patientProfileActivity.this, "Selected Problem" , Toast.LENGTH_SHORT).show();
                problemActivity(view);
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
    protected void onResume(){
        super.onResume();
        //TODO: REFRESH THE LISTVIEW
    }

    public void problemActivity(View view){
        Intent intent = new Intent(this, problemActivity.class);
        startActivity(intent);
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
        Intent intent = new Intent(this, createProblemActivity.class);
        startActivity(intent);
    }
}

