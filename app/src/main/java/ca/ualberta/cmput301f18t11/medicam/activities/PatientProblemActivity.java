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

import java.util.ArrayList;
import java.util.List;

import ca.ualberta.cmput301f18t11.medicam.R;
import ca.ualberta.cmput301f18t11.medicam.controllers.abstracts.PersistenceController;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.PatientPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.ProblemPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.models.Patient;
import ca.ualberta.cmput301f18t11.medicam.models.Problem;

public class PatientProblemActivity extends AppCompatActivity {
    private static final int ADD_PROBLEM_REQUESTCODE = 0;
    private static final int EDIT_PROBLEM_REQUEST_CODE =1;
    private ListView listView;
    private List<Problem> problemDisplayList = new ArrayList<>();
    private List<String> problemList = new ArrayList<>();
    private PersistenceController<Problem> problemControler = new ProblemPersistenceController();
    private PersistenceController<Patient> patientControler = new PatientPersistenceController();
    private Patient patient;
    private String patientid;
    private ArrayAdapter<Problem> itemsAdapter;
    private int onCkickItemIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_mainpage);

        /**
         * Get intent From previous activity(LoginActivity) that contains a String that represents the UUID of the patient
         * Then load problems from the patient
         */
        Intent intent = getIntent();
        patientid = intent.getStringExtra("userid");
        patient = patientControler.load(patientid,PatientProblemActivity.this);
        problemList.addAll(patient.getProblems()); //This is a decent convention for going from List up to ArrayList
        for (int i = 0; i < problemList.size(); i++){
            Problem problem = problemControler.load(problemList.get(i), this);
            problemDisplayList.add(problem);
        }
        /**
         * Setup the ArrayAdapter to show the list of problems
         *
         */
        itemsAdapter = new ArrayAdapter<Problem>(this, android.R.layout.simple_list_item_1, problemDisplayList);
        listView = findViewById(R.id.problemListView);
        listView.setAdapter(itemsAdapter);
        /**
         * Set on click listener so that clicking on one of the problem will bring the user to the recordViewing page(PatientRecordActivity) for that problem
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(PatientProblemActivity.this,PatientRecordActivity.class);
                intent.putExtra("previousProblem", problemList.get(position));
                intent.putExtra("patient", patientid);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String problemID = problemList.get(position);
                onCkickItemIndex = position;
                Intent intent = new Intent(PatientProblemActivity.this, createProblemActivity.class);
                intent.putExtra("patientUUID", patient.getUuid());
                intent.putExtra("problemUUID",problemID);
                intent.putExtra("purpose","edit");
                startActivityForResult(intent,EDIT_PROBLEM_REQUEST_CODE);
                return true;

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

    /**
     * When came back from the createProblemActivity, get the created problem and add to the problemList
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_PROBLEM_REQUESTCODE && resultCode == RESULT_OK){
            Problem newProblem = (Problem) data.getExtras().getSerializable("newProblem");
            patient.addProblem(newProblem.getUuid());
            problemDisplayList.add(newProblem);
            problemList.add(newProblem.getUuid());

            patientControler.save(patient,this);
        }else if(requestCode == EDIT_PROBLEM_REQUEST_CODE && resultCode == RESULT_OK){
            String deleteUUID = data.getStringExtra("deleteUUID");
            patient.getProblems().remove(deleteUUID);
            patientControler.save(patient, PatientProblemActivity.this);
            problemControler.delete(problemDisplayList.get(onCkickItemIndex), PatientProblemActivity.this);
            problemDisplayList.remove(onCkickItemIndex);
            ArrayAdapter<Problem> newAdapter = new ArrayAdapter<Problem>(PatientProblemActivity.this, android.R.layout.simple_list_item_1, problemDisplayList);
            listView.setAdapter(newAdapter);
            //itemsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        problemList.clear();
        problemDisplayList.clear();
        problemList.addAll(patient.getProblems());
        for (int i = 0; i < problemList.size(); i++){
            Problem problem = problemControler.load(problemList.get(i), PatientProblemActivity.this);
            problemDisplayList.add(problem);
        }
        ArrayAdapter<Problem> newAdapter = new ArrayAdapter<Problem>(PatientProblemActivity.this, android.R.layout.simple_list_item_1, problemDisplayList);
        listView.setAdapter(newAdapter);
        //TODO: REFRESH THE LISTVIEW
    }

    //User clicks the search button
    public void searchButton(View view){
        Intent intent  = new Intent(this,SearchActivity.class);
        intent.putExtra("searchFor","problems");
        intent.putExtra("patientUUID", patient.getUuid());
        startActivity(intent);
    }

    //User clicks Profile button
    public void viewProfile(View view){
        Intent intent = new Intent(this, ProfileEditting.class);
        intent.putExtra("USERUUID", patient.getUuid());
        startActivity(intent);

    }

    public void createProblem(View view){
        Intent intent = new Intent(this, createProblemActivity.class);
        intent.putExtra("patientUUID", patient.getUuid());
        intent.putExtra("purpose","add");
        startActivityForResult(intent,ADD_PROBLEM_REQUESTCODE);
    }
}

