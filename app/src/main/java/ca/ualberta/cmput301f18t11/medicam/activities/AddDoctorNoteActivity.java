package ca.ualberta.cmput301f18t11.medicam.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.Date;

import ca.ualberta.cmput301f18t11.medicam.R;
import ca.ualberta.cmput301f18t11.medicam.controllers.ElasticSearchController;
import ca.ualberta.cmput301f18t11.medicam.controllers.abstracts.PersistenceController;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.CareProviderRecordPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.models.CareProviderRecord;

public class AddDoctorNoteActivity extends AppCompatActivity {
    private EditText noteHeader;
    private EditText noteComment;
    private String purpose;
    private CareProviderRecord record = new CareProviderRecord();
    private PersistenceController<CareProviderRecord> recordController = new CareProviderRecordPersistenceController();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doctor_note);

        noteHeader = findViewById(R.id.noteHeaderEditText);
        noteComment  = findViewById(R.id.noteCommentEditText);

        purpose = getIntent().getStringExtra("purpose");
        if (purpose.equals("edit")){
            Intent intent = getIntent();
            String uuid = intent.getStringExtra("previous");
            record = recordController.load(uuid,this);
            noteHeader.setText(record.getTitle());
            noteComment.setText(record.getDescription());
        }


    }

    public void passTextAndFinish(View view){
        String noteStr = noteHeader.getText().toString();
        String commentStr = noteComment.getText().toString();
        record.setTitle(noteStr);
        record.setDescription(commentStr);
        if (!purpose.equals("edit")){
            record.setTimestamp(new Date());
        }

        record.setProblemUUID(getIntent().getStringExtra("problemUUID"));

        recordController.save(record,this);
        Intent intent = new Intent();
        intent.putExtra("doctorRecord", record);
        setResult(RESULT_OK,intent);
        finish();
    }
}
