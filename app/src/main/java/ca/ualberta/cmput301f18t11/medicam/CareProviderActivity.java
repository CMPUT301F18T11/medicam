package ca.ualberta.cmput301f18t11.medicam;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.println;

public class CareProviderActivity extends AppCompatActivity {
    private ListView patientListView;
    private ArrayAdapter<String> patientArrayAdapter;
    private ArrayList<String> patientArrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care_provider);
        patientListView = findViewById(R.id.patientListView);
        patientArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,patientArrayList);

        goAddPatient();

    }

    @Override
    protected void onStart() {
        super.onStart();
        patientListView.setAdapter(patientArrayAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                String newpatientId =  data.getStringExtra("enteredUserId");
                patientArrayList.add(newpatientId);
            }
        }

    }

    //  protected void


    public void goAddPatient(){
        Button addPatientButton = findViewById(R.id.addPatientButton);
        addPatientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CareProviderActivity.this,AddPatientActivity.class);
                startActivityForResult(intent,1);
            }
        });
    }

}
