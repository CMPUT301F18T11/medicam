package ca.ualberta.cmput301f18t11.medicam.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ca.ualberta.cmput301f18t11.medicam.R;
import ca.ualberta.cmput301f18t11.medicam.controllers.abstracts.PersistenceController;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.CareProviderRecordPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.ProblemPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.models.CareProviderRecord;
import ca.ualberta.cmput301f18t11.medicam.models.PatientRecord;
import ca.ualberta.cmput301f18t11.medicam.models.Problem;

public class ViewCareProviderRecordActivity extends AppCompatActivity {
    private static final int EDIT_RECORD_REQUEST_CODE=1;
    private TextView titleView;
    private TextView commentView;
    private CareProviderRecord careProviderRecord;
    private Problem problem;
    private String editable;
    private PersistenceController<CareProviderRecord> careProviderRecordController = new CareProviderRecordPersistenceController();
    private PersistenceController<Problem> problemController = new ProblemPersistenceController();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_care_provider_record);
        titleView = findViewById(R.id.view_doctor_note_title);
        commentView = findViewById(R.id.view_doctor_note_comment);

        Intent intent = getIntent();
        editable = intent.getStringExtra("editable");
        if(editable.equals("NO")){
            Button button = findViewById(R.id.go_edit_doctor_note_button);
            button.setVisibility(View.GONE);
        }
        String uuid = intent.getStringExtra("previousDoctorRecord");
        careProviderRecord = careProviderRecordController.load(uuid,this);
        String problemUUID = intent.getStringExtra("previousProblem");
        problem = problemController.load(problemUUID,this);
        titleView.setText(careProviderRecord.getTitle());
        commentView.setText(careProviderRecord.getDescription());


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_RECORD_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            CareProviderRecord newRecord = (CareProviderRecord) data.getExtras().getSerializable("doctorRecord");
            titleView.setText(newRecord.getTitle());
            commentView.setText(newRecord.getDescription());
            problem.deleteCareProviderRecord(newRecord.getUuid());
            problem.addCareProviderRecord(newRecord.getUuid());
            problemController.save(problem,this);
        }
    }

    public void goEditDoctorRecord(View view){
        Intent intent = new Intent(ViewCareProviderRecordActivity.this,AddDoctorNoteActivity.class);
        intent.putExtra("purpose","edit");
        intent.putExtra("previous",careProviderRecord.getUuid());
        startActivityForResult(intent,EDIT_RECORD_REQUEST_CODE);
    }
}
