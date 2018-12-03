package ca.ualberta.cmput301f18t11.medicam.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ca.ualberta.cmput301f18t11.medicam.R;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.PatientPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.models.Patient;
import ca.ualberta.cmput301f18t11.medicam.models.attachments.ReferencePhoto;

public class BodyLocationListActivity extends AppCompatActivity {
    private static final int EDIT_REFERENCE_PHOTO = 0;
    private static final int CREATE_REFERENCE_PHOTO =1;
    private static final int CREATE_BODY_LOCATION = 2;

    private ListView listView;
    private Button addBodyLocationButton;
    private List<ReferencePhoto> bodyLocationList = new ArrayList<>();
    private PatientPersistenceController patientPersistenceController = new PatientPersistenceController();
    private Patient patient;
    private int editedPhoto;
    private String mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_location_list);

        /**
         * Get intent From previous activity(profileEditting) that contains a String that represents the UUID of the patient
         * Then load problems from the patient
         */
        Intent intent = getIntent();
        String patientUUID = intent.getStringExtra("patient");
        patient = patientPersistenceController.load(patientUUID,this);
        bodyLocationList.addAll(patient.getBodyLocations());

        mode = intent.getStringExtra("mode");
        if (mode.equals("select")) {
            addBodyLocationButton.setVisibility(View.GONE);
        }
        /**
         * Setup the ArrayAdapter to show the list of problems
         *
         */
        ArrayAdapter<ReferencePhoto> itemsAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, bodyLocationList);
        listView = findViewById(R.id.bodyLocationViewList);
        listView.setAdapter(itemsAdapter);
        /**
         * Set on click listener so that clicking on one of the bodylocations will bring the user to
         * the bodylocation creation page for that bodylocation
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (mode.equals("edit")) {
                    Intent intent = new Intent(BodyLocationListActivity.this,
                            CreateBodyLocationActivity.class);
                    intent.putExtra("mode", "edit");
                    intent.putExtra("bodyLocation", bodyLocationList.get(position));
                    editedPhoto = position;
                    startActivityForResult(intent, EDIT_REFERENCE_PHOTO);
                    
                } else if (mode.equals("select")) {
                    Intent intent = new Intent(BodyLocationListActivity.this,
                            BodyLocationActivity.class);
                    intent.putExtra("photo", bodyLocationList.get(position));
                    startActivityForResult(intent, CREATE_BODY_LOCATION);
                }
            }
        });

        addBodyLocationButton = findViewById(R.id.addBodyLocationButton);
        addBodyLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BodyLocationListActivity.this, CreateBodyLocationActivity.class);
                intent.putExtra("mode", "create");
                startActivityForResult(intent, CREATE_REFERENCE_PHOTO);
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
        if(requestCode == CREATE_REFERENCE_PHOTO && resultCode == RESULT_OK){
            ReferencePhoto newReferencePhoto = (ReferencePhoto) data.getExtras().getSerializable("new");
            patient.addBodyLocation(newReferencePhoto);
            bodyLocationList.add(newReferencePhoto);
            patientPersistenceController.save(patient, this);
        }

        else if (requestCode == EDIT_REFERENCE_PHOTO && resultCode == RESULT_OK){
            ReferencePhoto editedReferencePhoto = (ReferencePhoto) data.getExtras().getSerializable("new");
            patient.removeBodyLocation(editedPhoto);
            bodyLocationList.remove(editedPhoto);
            bodyLocationList.add(editedReferencePhoto);
            patient.addBodyLocation(editedReferencePhoto);
            patientPersistenceController.save(patient, this);
        }

        else if (requestCode == CREATE_BODY_LOCATION && resultCode == RESULT_OK){
            //TODO
        }

    }

    @Override
    protected void onResume(){
        super.onResume();

        ArrayAdapter<ReferencePhoto> itemsAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, bodyLocationList);
        listView.setAdapter(itemsAdapter);

    }
}

