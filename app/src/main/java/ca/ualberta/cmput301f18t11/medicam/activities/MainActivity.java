package ca.ualberta.cmput301f18t11.medicam.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.util.UUID;

import ca.ualberta.cmput301f18t11.medicam.R;
import ca.ualberta.cmput301f18t11.medicam.controllers.ElasticSearchController;
import ca.ualberta.cmput301f18t11.medicam.controllers.PatientPersistanceController;
import ca.ualberta.cmput301f18t11.medicam.models.Patient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }


    @Override
    protected void onStart() {
        super.onStart();

        PatientPersistanceController p = new PatientPersistanceController(getApplicationContext());
//
//        Patient test = new Patient("BarrackObama");
//
//        test.addProblem(UUID.randomUUID());
//
//        test.addProblem(UUID.randomUUID());
//
//        test.addProblem(UUID.randomUUID());
//
//        test.addProblem(UUID.randomUUID());
//
//        test.addProblem(UUID.randomUUID());
//
//        test.addProblem(UUID.randomUUID());
//
//        test.addProblem(UUID.randomUUID());
//
//
//        p.saveToREST(test);

        Patient test = p.loadFromREST("BarrackObama");


        int x = 1;



    }
}
