package ca.ualberta.cmput301f18t11.medicam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class CareProviderProblemActivity extends AppCompatActivity {
    private ListView problewmsView = findViewById(R.id.careProviderProblemView);
    private ArrayList<String> exampleList = new ArrayList<>();
    private ArrayAdapter<String> exampleAdapter;
    private String titleString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care_provider_problem);
        //exampleList.add("Problem 1");
        //exampleAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,exampleList);
        //titleString = getIntent().getStringExtra("patientName");
        //setTitle("patient");

    }


}
