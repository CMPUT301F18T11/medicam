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
import java.util.List;

import ca.ualberta.cmput301f18t11.medicam.R;
import ca.ualberta.cmput301f18t11.medicam.controllers.ElasticSearchController;
import ca.ualberta.cmput301f18t11.medicam.controllers.abstracts.PersistenceController;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.PatientPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.ProblemPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.models.Patient;
import ca.ualberta.cmput301f18t11.medicam.models.Problem;

public class CareGiverProblemActivity extends AppCompatActivity {
    private ListView problemListView;
    private TextView title;
    private ArrayAdapter<Problem> adapter;
    private List<String> problemList = new ArrayList<>();
    private ArrayList<Problem> problemDisplayList = new ArrayList<>();
    private PersistenceController<Patient> patientController = new PatientPersistenceController();
    private PersistenceController<Problem> problemController = new ProblemPersistenceController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caregiver_problem);

        Intent intent = getIntent();
        final String patientUUID = intent.getStringExtra("patientUUID");
        final Patient patient = patientController.load(patientUUID,this);
        problemList = patient.getProblems();

        for (int i=0; i < problemList.size();i++){
            Problem problem = problemController.load(problemList.get(i),this);
            problemDisplayList.add(problem);
        }

        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,problemDisplayList);
        problemListView =  findViewById(R.id.problems_list_view);
        title = findViewById(R.id.caregiverProblem_Header01);
        title.setText(patientUUID);
        problemListView.setAdapter(adapter);

        problemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CareGiverProblemActivity.this, CareGiverRecordActivity.class);
                intent.putExtra("problemUUID", problemList.get(position));
                intent.putExtra("patient", patientUUID);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,problemDisplayList);
        problemListView.setAdapter(adapter);
    }

    public void searchButtonForCareProvider(View view){
        Intent intent  = new Intent(this,SearchActivity.class);
        intent.putExtra("searchFor","problems");
        String patientUUID = getIntent().getStringExtra("patientUUID");
        intent.putExtra("patientUUID", patientUUID);
        startActivity(intent);
    }
}
