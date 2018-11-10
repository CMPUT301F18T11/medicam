package ca.ualberta.cmput301f18t11.medicam.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.UUID;

import ca.ualberta.cmput301f18t11.medicam.R;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.CareProviderPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.models.CareProvider;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }


    @Override
    protected void onStart() {
        super.onStart();


        CareProvider testProvider = new CareProvider("TestProvider123123");

        testProvider.addPatient(UUID.randomUUID());
        testProvider.addPatient(UUID.randomUUID());
        testProvider.addPatient(UUID.randomUUID());

        CareProviderPersistenceController cp = new CareProviderPersistenceController();

        cp.save(testProvider, getApplicationContext());


        CareProvider fromRest    = cp.loadFromREST("TestProvider123123");
        CareProvider fromStorage = cp.loadFromStorage("TestProvider123123",getApplicationContext());

        int x = 1 + 1;

        cp.delete(testProvider, getApplicationContext());

        CareProvider fromRest2    = cp.loadFromREST("TestProvider123123");
        CareProvider fromStorage2 = cp.loadFromStorage("TestProvider123123",getApplicationContext());

        int y = 1 + 2;


    }
}
