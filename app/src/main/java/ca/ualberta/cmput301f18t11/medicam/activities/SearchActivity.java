package ca.ualberta.cmput301f18t11.medicam.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ca.ualberta.cmput301f18t11.medicam.R;
import ca.ualberta.cmput301f18t11.medicam.controllers.abstracts.PersistenceController;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.CareProviderPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.CareProviderRecordPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.PatientRecordPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.ProblemPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.models.CareProviderRecord;
import ca.ualberta.cmput301f18t11.medicam.models.Patient;
import ca.ualberta.cmput301f18t11.medicam.models.PatientRecord;
import ca.ualberta.cmput301f18t11.medicam.models.Problem;
import ca.ualberta.cmput301f18t11.medicam.models.abstracts.Record;

public class SearchActivity extends AppCompatActivity {
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
    public List<Record> searchForRecords(String keyword){
        List<Record> resultList= new ArrayList<>();
        List<PatientRecord> patientRecordsResultList = patientRecordController.searchFromREST(keyword,problem.getUuid());
        List<CareProviderRecord> doctorRecordsResultList = doctorRecordController.searchFromREST(keyword,problem.getUuid());
        resultList.addAll(patientRecordsResultList);
        resultList.addAll(doctorRecordsResultList);
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
            Toast.makeText(this,"Records:"+ resultList,Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }
    }
}
