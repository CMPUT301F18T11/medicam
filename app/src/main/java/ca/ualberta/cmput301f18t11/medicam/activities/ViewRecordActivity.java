package ca.ualberta.cmput301f18t11.medicam.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collection;
import java.util.Date;

import ca.ualberta.cmput301f18t11.medicam.R;
import ca.ualberta.cmput301f18t11.medicam.controllers.ElasticSearchController;
import ca.ualberta.cmput301f18t11.medicam.controllers.GeolocationController;
import ca.ualberta.cmput301f18t11.medicam.controllers.abstracts.PersistenceController;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.PatientRecordPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.ProblemPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.models.PatientRecord;
import ca.ualberta.cmput301f18t11.medicam.models.Problem;

public class ViewRecordActivity extends AppCompatActivity {
    private TextView recordTitle;
    private TextView recordComment;
    private ImageView recordPhoto;
    private TextView recordTime;
    private TextView recordDate;
    private PatientRecord patientRecord;
    private String problemUUID;
    private Problem problem;
    private String editable;

    private TextView geoLocationTextView;
    private ImageButton mapButton;

    private static final int EDIT_RECORD_REQUEST_CODE = 1;
    private PersistenceController<PatientRecord> recordController = new PatientRecordPersistenceController();
    private PersistenceController<Problem> problemController = new ProblemPersistenceController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_record);
        ElasticSearchController.setIndex_url("cmput301f18t11test");

        recordTitle=findViewById(R.id.record_view_title_textView);
        recordComment=findViewById(R.id.record_view_comment_textView);
        recordPhoto=findViewById(R.id.record_view_photo_imageView);
        recordTime=findViewById(R.id.record_view_time_view);
        recordDate=findViewById(R.id.record_view_date_view);

        geoLocationTextView = findViewById(R.id.viewRecordGeolocationTextView);
        mapButton = findViewById(R.id.viewRecordMapButton);

        Intent intent =  getIntent();
        editable = intent.getStringExtra("editable");
        if (editable.equals("NO")){
            Button button = findViewById(R.id.go_edit_button);
            button.setVisibility(View.GONE);
        }
        String recordUUID = intent.getStringExtra("previous");
        problemUUID = intent.getStringExtra("previousProblem");
        problem = problemController.load(problemUUID,this);
        fetchPrevious(recordUUID);

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (patientRecord.getLocation() == null) {
                    return;
                }
                startActivity(GeolocationController.viewLocation(ViewRecordActivity.this, patientRecord.getLocation()));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_RECORD_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            PatientRecord newRecord = (PatientRecord) data.getExtras().getSerializable("newRecord");
            fetchPrevious(newRecord.getUuid());
            problem.deletePatientRecord(patientRecord.getUuid());
            problem.addPatientRecord(newRecord.getUuid());
            problemController.save(problem,this);
        }
    }

    private void displayGeolocation() {
        if (patientRecord.getLocation() == null) {
            geoLocationTextView.setText(R.string.noLocation);
        } else {
            geoLocationTextView.setText(String.format("Lat: %s\nLng: %s",
                    patientRecord.getLocation().getLatitude(),
                    patientRecord.getLocation().getLongitude()));
        }
    }

    public void fetchPrevious(String UUID){
        patientRecord = recordController.load(UUID,this);
        recordTitle.setText(patientRecord.getTitle());
        recordComment.setText(patientRecord.getDescription());
        java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("dd/MM/YYYY");
        String dateStr = dateFormat.format(patientRecord.getTimestamp());
        recordDate.setText(dateStr);
        java.text.SimpleDateFormat timeformat = new java.text.SimpleDateFormat("HH:mm");
        String timeStr = timeformat.format(patientRecord.getTimestamp());
        recordTime.setText(timeStr);
        displayGeolocation();

        //TODO: fetch Also PHOTOS BODY LOCATIONS and photos
    }

    public void goEditRecord(View view){
        Intent intent = new Intent(ViewRecordActivity.this,createRecordActivity.class);
        intent.putExtra("purpose","edit");
        intent.putExtra("previous", patientRecord.getUuid());
        intent.putExtra("problemUUID", problemUUID);
        startActivityForResult(intent, EDIT_RECORD_REQUEST_CODE);
    }

    public void photoSlideshow(View view){
        Intent intent = new Intent(ViewRecordActivity.this, RecordPhotosSlideshow.class);
        startActivity(intent);
    }
}
