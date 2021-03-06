package ca.ualberta.cmput301f18t11.medicam.activities;

import android.content.Intent;
import android.support.annotation.Nullable;
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
import ca.ualberta.cmput301f18t11.medicam.controllers.abstracts.PersistenceController;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.CareProviderRecordPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.PatientRecordPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.ProblemPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.models.CareProviderRecord;
import ca.ualberta.cmput301f18t11.medicam.models.PatientRecord;
import ca.ualberta.cmput301f18t11.medicam.models.Problem;
import ca.ualberta.cmput301f18t11.medicam.models.abstracts.Record;

public class CareGiverRecordActivity extends AppCompatActivity {
    private static final int ADD_RECORD_REQUESTCODE = 0;
    private TextView title;
    private ListView patientRecordsView;
    private List<Record> displayList= new ArrayList<>();
    private ArrayAdapter<Record> recordArrayAdapter;
    private List<String> patientRecordsArray = new ArrayList<>();
    private List<String> doctorRecordsArray = new ArrayList<>();
    private PersistenceController<Problem> problemControler = new ProblemPersistenceController();
    private PersistenceController<PatientRecord> patientRecordController = new PatientRecordPersistenceController();
    private PersistenceController<CareProviderRecord> doctorRecordController = new CareProviderRecordPersistenceController();
    private Problem problem;
    private String patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caregiver_record);
        patientRecordsView = findViewById(R.id.careGiverRecordListView);

        Intent intent = getIntent();
        String problemUUID = intent.getStringExtra("problemUUID");
        patient = intent.getStringExtra("patient");
        problem = problemControler.load(problemUUID,this);
        patientRecordsArray = problem.getPatientRecords();
        doctorRecordsArray = problem.getCareProviderRecords();

        for (int i = 0; i < patientRecordsArray.size(); i++){
                PatientRecord record = patientRecordController.load(patientRecordsArray.get(i), this);
                displayList.add(record);
        }
        for (int i = 0; i < doctorRecordsArray.size(); i++){
            CareProviderRecord record = doctorRecordController.load(doctorRecordsArray.get(i), this);
            displayList.add(record);
        }

        // if Item Clicked
        patientRecordsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Record record = displayList.get(position);
                if(patientRecordsArray.contains(record.getUuid())) {
                    Intent intent = new Intent(CareGiverRecordActivity.this, ViewRecordActivity.class);
                    intent.putExtra("editable","NO");
                    intent.putExtra("previous", record.getUuid());
                    intent.putExtra("patient", patient);
                    startActivity(intent);
                }else if(doctorRecordsArray.contains(record.getUuid())){
                    Intent intent = new Intent(CareGiverRecordActivity.this, ViewCareProviderRecordActivity.class);
                    intent.putExtra("editable","YES");
                    intent.putExtra("previousProblem", problem.getUuid());
                    intent.putExtra("previousDoctorRecord",record.getUuid());
                    startActivity(intent);
                }
            }
        });


        patientRecordsView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                return true;
            }
        });

        title = findViewById(R.id.caregiverRecord_ProblemTitle);
        title.setText(problem.getTitle());

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (displayList.size() > 1){
            sortDatArray();
        }
        recordArrayAdapter = new ArrayAdapter<Record>(this,android.R.layout.simple_list_item_1, displayList);
        patientRecordsView.setAdapter(recordArrayAdapter);
    }
    protected void onResume() {
        super.onResume();
        problem = problemControler.load(problem.getUuid(), this);
        patientRecordsArray = problem.getPatientRecords();
        doctorRecordsArray = problem.getCareProviderRecords();
        displayList.clear();
        for (int i = 0; i < patientRecordsArray.size(); i++){
            PatientRecord record = patientRecordController.load(patientRecordsArray.get(i), this);
            displayList.add(record);
        }

        for (int i = 0; i < doctorRecordsArray.size(); i++){
            CareProviderRecord record = doctorRecordController.load(doctorRecordsArray.get(i), this);
            displayList.add(record);
        }
        if (displayList.size() > 1){
            sortDatArray();
        }
        recordArrayAdapter = new ArrayAdapter<Record>(this,android.R.layout.simple_list_item_1, displayList);
        patientRecordsView.setAdapter(recordArrayAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_RECORD_REQUESTCODE && resultCode == RESULT_OK){
            CareProviderRecord careProviderRecord = (CareProviderRecord) data.getExtras().getSerializable("doctorRecord");
            problem.addCareProviderRecord(careProviderRecord.getUuid());
            problemControler.save(problem,this);
            displayList.add(careProviderRecord);
        }
    }

    public void goAddDoctorRecord(View view){
        Intent intent = new Intent(CareGiverRecordActivity.this, AddDoctorNoteActivity.class);
        intent.putExtra("purpose", "create");
        intent.putExtra("problemUUID", problem.getUuid());
        startActivityForResult(intent,ADD_RECORD_REQUESTCODE);
    }
    public void searchButtonForCareProvider(View view){
        Intent intent  = new Intent(this,SearchActivity.class);
        intent.putExtra("searchFor","records");
        intent.putExtra("previousProblem",problem);
        intent.putExtra("accessType","doctor");
        intent.putExtra("patientUUID", patient);
        startActivity(intent);
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
