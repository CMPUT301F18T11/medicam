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
import ca.ualberta.cmput301f18t11.medicam.controllers.ElasticSearchController;
import ca.ualberta.cmput301f18t11.medicam.controllers.abstracts.PersistenceController;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.CareProviderPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.PatientPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.ProblemPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.models.CareProvider;
import ca.ualberta.cmput301f18t11.medicam.models.Patient;
import ca.ualberta.cmput301f18t11.medicam.models.Problem;

public class CareGiverProblemActivity extends AppCompatActivity {
    private ListView problemListView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> problemList = new ArrayList<>();
    private PersistenceController<Patient> patientController = new PatientPersistenceController();
    private PersistenceController<Problem> problemControler = new ProblemPersistenceController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care_giver_problem);
        ElasticSearchController.setIndex_url("cmput301f18t11test");

        Intent intent = getIntent();
        String problemUUID = intent.getStringExtra("patientUUID");
        Patient patient = patientController.load(problemUUID,this);
        problemList = patient.getProblems();


        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,problemList);
        problemListView =  findViewById(R.id.problems_list_view);
        problemListView.setAdapter(adapter);
        problemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CareGiverProblemActivity.this,CareGiverRecordActivity.class);
                intent.putExtra("problemUUID",problemList.get(position));
                startActivity(intent);
            }
        });

    }
}
