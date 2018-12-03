package ca.ualberta.cmput301f18t11.medicam.activities;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ca.ualberta.cmput301f18t11.medicam.R;
import ca.ualberta.cmput301f18t11.medicam.controllers.GeolocationController;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.CareProviderRecordPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.PatientPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.PatientRecordPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.ProblemPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.models.CareProviderRecord;
import ca.ualberta.cmput301f18t11.medicam.models.Patient;
import ca.ualberta.cmput301f18t11.medicam.models.PatientRecord;
import ca.ualberta.cmput301f18t11.medicam.models.Problem;
import ca.ualberta.cmput301f18t11.medicam.models.abstracts.Record;
import ca.ualberta.cmput301f18t11.medicam.models.attachments.Geolocation;

public class SearchActivity extends AppCompatActivity {
    private static final int UPDATE_GEOLOCATION_REQUEST_CODE = 1;
    private static final int GET_PHOTO_UUID_REQUEST_CODE = 2;
    private String searchtype;
    private String patientUUID;
    private EditText keyword;
    private Problem problem;
    private String accessType;
    private ProblemPersistenceController problemController = new ProblemPersistenceController();
    private PatientRecordPersistenceController patientRecordController = new PatientRecordPersistenceController();
    private CareProviderRecordPersistenceController doctorRecordController = new CareProviderRecordPersistenceController();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        keyword = findViewById(R.id.keyword);

        //Toolbar Setup
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.searchPage_toolbar);
        setSupportActionBar(toolbar);
        TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setText("Search");// Sets the title to be shown in the toolbar

        Intent intent = getIntent();
        searchtype = intent.getStringExtra("searchFor");
        patientUUID = intent.getStringExtra("patientUUID");
        accessType = intent.getStringExtra("accessType");
        problem = (Problem) intent.getExtras().getSerializable("previousProblem");

    }

    public void searchByKeyword(View view){
            gotoResultActivity();
    }


    public List<Problem> searchForProblems(String keyword){
        List<Problem> resultList = problemController.searchFromREST(keyword,patientUUID);
        return resultList;
    }

    public List<Problem> searchForProblems(Geolocation geolocation) {
        List<Problem> resultList = new ArrayList<>();
        ProblemPersistenceController problemPersistenceController = new ProblemPersistenceController();
        Patient patient = new PatientPersistenceController().load(patientUUID, this);

        for (String problemUUID : patient.getProblems()) {
            List<PatientRecord> patientResultList = patientRecordController.searchGeoLocationFromREST(geolocation.getLatitude(),
                    geolocation.getLongitude(),100, problemUUID);

            if (patientResultList != null) {
                if (!patientResultList.isEmpty()) {
                    resultList.add(problemPersistenceController.load(problemUUID, this));
                }
            }
        }


        return resultList;
    }
    public List<Record> searchForRecords(String keyword){
        List<Record> resultList= new ArrayList<>();
        List<PatientRecord> patientRecordsResultList = patientRecordController.searchFromREST(keyword,problem.getUuid());
        List<CareProviderRecord> doctorRecordsResultList = doctorRecordController.searchFromREST(keyword,problem.getUuid());
        resultList.addAll(patientRecordsResultList);
        resultList.addAll(doctorRecordsResultList);
        return resultList;
    }

    public List<Record> searchForRecords(Geolocation geolocation) {
        List<Record> resultList= new ArrayList<>();
        List<PatientRecord> patientResultList = patientRecordController.searchGeoLocationFromREST(geolocation.getLatitude(),
                geolocation.getLongitude(),100, problem.getUuid());
        resultList.addAll(patientResultList);
        return resultList;
    }

    public void gotoResultActivity(){
        if (searchtype.equals("problems")) {
            List<Problem> resultList = searchForProblems(keyword.getText().toString());
            Intent intent = new Intent(SearchActivity.this,ShowSearchResultActivity.class);
            intent.putExtra("problemResultList", (Serializable) resultList);
            intent.putExtra("resultType","problems");
            intent.putExtra("patientUUID",patientUUID);
            startActivity(intent);
        }
        else if (searchtype.equals("records")){
            List<Record> resultList = searchForRecords(keyword.getText().toString());
            Intent intent = new Intent(SearchActivity.this,ShowSearchResultActivity.class);
            intent.putExtra("recordResultList", (Serializable) resultList);
            intent.putExtra("resultType","records");
            intent.putExtra("problem",problem);
            intent.putExtra("accessType",accessType);
            startActivity(intent);
        }
    }

    public void goToResultActivityGeolocation(View view) {
        startActivityForResult(GeolocationController.selectLocation(this),
                UPDATE_GEOLOCATION_REQUEST_CODE);
    }

    public void goToResultActivityBodyLocation(View view) {
        Intent intent = new Intent(this, BodyLocationListActivity.class);
        intent.putExtra("mode", "selectImage");
        intent.putExtra("patient", patientUUID);
        startActivityForResult(intent, GET_PHOTO_UUID_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UPDATE_GEOLOCATION_REQUEST_CODE) {
            Geolocation location = MapsActivity.location;
            if (location == null) {
                return;
            }

            if (searchtype.equals("problems")) {
                List<Problem> resultList = searchForProblems(location);
                Intent intent = new Intent(SearchActivity.this,ShowSearchResultActivity.class);
                intent.putExtra("problemResultList", (Serializable) resultList);
                intent.putExtra("resultType","problems");
                intent.putExtra("patientUUID",patientUUID);
                startActivity(intent);
            }
            else if (searchtype.equals("records")){
                List<Record> resultList = searchForRecords(location);
                Intent intent = new Intent(SearchActivity.this,ShowSearchResultActivity.class);
                intent.putExtra("recordResultList", (Serializable) resultList);
                intent.putExtra("resultType","records");
                intent.putExtra("problem",problem);
                intent.putExtra("accessType",accessType);
                startActivity(intent);
            }
        }

        else if (requestCode == GET_PHOTO_UUID_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, data.getStringExtra("result"), Toast.LENGTH_SHORT).show();
                // todo
            }
        }

    }
}
