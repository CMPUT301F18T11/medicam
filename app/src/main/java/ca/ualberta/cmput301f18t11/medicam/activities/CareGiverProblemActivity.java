package ca.ualberta.cmput301f18t11.medicam.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

import ca.ualberta.cmput301f18t11.medicam.R;
import ca.ualberta.cmput301f18t11.medicam.models.Problem;

public class CareGiverProblemActivity extends AppCompatActivity {
    private ListView problemListView;
    private ArrayAdapter<Problem> adapter;
    private ArrayList<Problem> problemList = new ArrayList<Problem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care_giver_problem);
        Date date = new Date();
        Problem example = new Problem("Tis is title ", date,"Description");
        //problemList.add("This is it");
        problemList.add(example);
        adapter = new ArrayAdapter<Problem>(this,android.R.layout.simple_list_item_1,problemList);
        problemListView =  findViewById(R.id.problems_list_view);
        problemListView.setAdapter(adapter);
        problemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                gotoCareGiverRecord(view);
            }
        });

    }

    public void gotoCareGiverRecord(View view){
        Intent intent = new Intent(this,CareGiverRecordActivity.class);
        startActivity(intent);
    }
}
