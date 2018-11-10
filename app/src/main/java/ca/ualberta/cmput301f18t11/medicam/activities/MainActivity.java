package ca.ualberta.cmput301f18t11.medicam.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ca.ualberta.cmput301f18t11.medicam.R;
import ca.ualberta.cmput301f18t11.medicam.controllers.PatientPersistenceController;
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

        PatientPersistenceController p = new PatientPersistenceController(getApplicationContext());
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
