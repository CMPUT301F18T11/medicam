package ca.ualberta.cmput301f18t11.medicam.activities;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ca.ualberta.cmput301f18t11.medicam.R;
import ca.ualberta.cmput301f18t11.medicam.controllers.ElasticSearchController;
import ca.ualberta.cmput301f18t11.medicam.controllers.abstracts.PersistenceController;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.CareProviderRecordPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.PatientRecordPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.ProblemPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.models.CareProviderRecord;
import ca.ualberta.cmput301f18t11.medicam.models.PatientRecord;
import ca.ualberta.cmput301f18t11.medicam.models.Problem;

public class CareGiverRecordActivity extends AppCompatActivity {
    private static final int ADD_RECORD_REQUESTCODE = 0;
    private ListView patientRecordsView;
    private ListView doctorRecordsView;
    private List<PatientRecord> recordDisplayList = new ArrayList<PatientRecord>();
    private List<CareProviderRecord> doctorRecordDisplayList = new ArrayList<CareProviderRecord>();
    private ArrayAdapter<PatientRecord> recordArrayAdapter;
    private ArrayAdapter<CareProviderRecord> careProviderRecordArrayAdapter;
    private List<String> patientRecordsArray = new ArrayList<>();
    private List<String> doctorRecordsArray = new ArrayList<>();
    private PersistenceController<Problem> problemControler = new ProblemPersistenceController();
    private PersistenceController<PatientRecord> patientRecordController = new PatientRecordPersistenceController();
    private PersistenceController<CareProviderRecord> doctorRecordController = new CareProviderRecordPersistenceController();
    private Problem problem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care_giver_record);
        ElasticSearchController.setIndex_url("cmput301f18t11test");
        doctorRecordsView = findViewById(R.id.doctorRecordView);
        patientRecordsView = findViewById(R.id.careGiverRecordListView);


        Intent intent = getIntent();
        String problemUUID = intent.getStringExtra("problemUUID");
        problem = problemControler.load(problemUUID,this);
        patientRecordsArray = problem.getPatientRecords();
        doctorRecordsArray = problem.getCareProviderRecords();

        if(doctorRecordsArray.size() == 0){
            doctorRecordsView.setVisibility(View.GONE);
        }
        //TODO: add doctor NOTEs


        for (int i = 0; i < patientRecordsArray.size(); i++){
                PatientRecord record = patientRecordController.load(patientRecordsArray.get(i), this);
                recordDisplayList.add(record);
        }
        for (int i = 0; i < doctorRecordsArray.size(); i++){
            CareProviderRecord record = doctorRecordController.load(doctorRecordsArray.get(i), this);
            doctorRecordDisplayList.add(record);
        }

        recordArrayAdapter = new ArrayAdapter<PatientRecord>(this,android.R.layout.simple_list_item_1, recordDisplayList);
        patientRecordsView.setAdapter(recordArrayAdapter);
        careProviderRecordArrayAdapter = new ArrayAdapter<CareProviderRecord>(this,android.R.layout.simple_list_item_1,doctorRecordDisplayList);
        doctorRecordsView.setAdapter(careProviderRecordArrayAdapter);


        // if Item Clicked
        patientRecordsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CareGiverRecordActivity.this,CareGiverAttachActivity.class);
                intent.putExtra("recordUUID", patientRecordsArray.get(position));
                startActivity(intent);
            }
        });

        // if Long pressed:
        // TODO Toast or show the actual comment.
        patientRecordsView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(CareGiverRecordActivity.this,"This will show the patient comments on this Long pressed record",Toast.LENGTH_LONG).show();
                return true;
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_RECORD_REQUESTCODE && resultCode == RESULT_OK){
            CareProviderRecord careProviderRecord = (CareProviderRecord) data.getExtras().getSerializable("doctorRecord");
            problem.addCareProviderRecord(careProviderRecord.getUuid());
            problemControler.save(problem,this);
            doctorRecordDisplayList.add(careProviderRecord);
            careProviderRecordArrayAdapter = new ArrayAdapter<CareProviderRecord>(this,android.R.layout.simple_list_item_1,doctorRecordDisplayList);
            doctorRecordsView.setAdapter(careProviderRecordArrayAdapter);
        }
    }

    public void goAddDoctorRecord(View view){
        Intent intent = new Intent(CareGiverRecordActivity.this,AddDoctorNoteActivity.class);
        startActivityForResult(intent,ADD_RECORD_REQUESTCODE);
    }

}
