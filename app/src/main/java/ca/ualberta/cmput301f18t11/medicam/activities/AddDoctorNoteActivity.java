package ca.ualberta.cmput301f18t11.medicam.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

import ca.ualberta.cmput301f18t11.medicam.R;
import ca.ualberta.cmput301f18t11.medicam.controllers.ElasticSearchController;
import ca.ualberta.cmput301f18t11.medicam.controllers.abstracts.PersistenceController;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.CareProviderRecordPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.models.CareProviderRecord;

public class AddDoctorNoteActivity extends AppCompatActivity {
    private EditText noteHeader;
    private  EditText noteComment;
    private CareProviderRecord record = new CareProviderRecord();
    private PersistenceController<CareProviderRecord> recordController = new CareProviderRecordPersistenceController();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doctor_note);
        ElasticSearchController.setIndex_url("cmput301f18t11test");

        noteHeader = findViewById(R.id.noteHeaderEditText);
        noteComment  = findViewById(R.id.noteCommentEditText);




    }

    public void passTextAndFinish(View view){
        String noteStr = noteHeader.getText().toString();
        String commentStr = noteComment.getText().toString();
        record.setTitle(noteStr);
        record.setDescription(commentStr);
        record.setTimestamp(new Date());
        recordController.save(record,this);
        finish();
    }
}
