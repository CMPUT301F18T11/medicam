package ca.ualberta.cmput301f18t11.medicam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.Date;

public class createProblemActivity extends AppCompatActivity {

    private EditText problemTitle;
    private EditText problemDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_problem);

        problemTitle = (EditText) findViewById(R.id.viewTitle);
        problemDescription = (EditText) findViewById(R.id.probDescription);
    }

    //Creates a new problem object with the filled in user-data
    //Todo: Save the problem to file and elasticsearch
    //Todo: Implement character restrictions on screen
    public void createProblem(View view){
        Problem newProblem = new Problem(problemTitle.toString(), new Date(), problemDescription.toString());
        this.finish();
    }


}
