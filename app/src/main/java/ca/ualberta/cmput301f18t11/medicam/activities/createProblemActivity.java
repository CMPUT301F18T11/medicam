package ca.ualberta.cmput301f18t11.medicam.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.UUID;

import ca.ualberta.cmput301f18t11.medicam.R;
import ca.ualberta.cmput301f18t11.medicam.controllers.abstracts.PersistenceController;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.PatientPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.ProblemPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.models.Patient;
import ca.ualberta.cmput301f18t11.medicam.models.Problem;

public class createProblemActivity extends AppCompatActivity {

    private EditText problemTitle;
    private EditText problemDescription;
    private PersistenceController<Problem> problemController = new ProblemPersistenceController();
    private PersistenceController<Patient> patientControler = new PatientPersistenceController();
    private String purpose;
    private Problem previousProblem;
    private Button deleteButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_problem);
        problemTitle = (EditText) findViewById(R.id.problem_title_editText);
        problemDescription = (EditText) findViewById(R.id.probDescription);
        deleteButton = findViewById(R.id.delete_problem_button);

        purpose = getIntent().getStringExtra("purpose");
        if (purpose.equals("edit")){
            deleteButton.setVisibility(View.VISIBLE);
            String problemUUID = getIntent().getStringExtra("problemUUID");
            previousProblem = problemController.load(problemUUID,this);
            problemTitle.setText(previousProblem.getTitle());
            problemDescription.setText(previousProblem.getDescription());
        }


        //Toolbar Setup
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.createProblem_toolbar);
        setSupportActionBar(toolbar);
        TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setText("Create a New Problem");// Sets the title to be shown in the toolbar
    }

    //Creates a new problem object with the filled in user-data
    //Todo: Save the problem to file and elasticsearch
    public void createProblem(View view){
        if (problemTitle.getText().toString().equals("")){Toast.makeText(createProblemActivity.this,"Please Enter a title",Toast.LENGTH_SHORT).show();
        }
        else if (problemDescription.getText().toString().equals("")){Toast.makeText(createProblemActivity.this,"Please Enter a Description",Toast.LENGTH_SHORT).show();

        } else {
            if (purpose.equals("add")) {
                Intent intent = new Intent();
                Problem newProblem = new Problem(UUID.randomUUID().toString(),
                        problemTitle.getText().toString(), new Date(),
                        problemDescription.getText().toString(), getIntent().getStringExtra("patientUUID"));
                problemController.save(newProblem, this);
                intent.putExtra("newProblem", newProblem);
                setResult(RESULT_OK, intent);
                finish();
            }else if (purpose.equals("edit")){
                previousProblem.setTitle(problemTitle.getText().toString());
                previousProblem.setDescription(problemDescription.getText().toString());
                problemController.save(previousProblem,this);
                finish();
            }
        }
    }
    public void deleteProblem(View view){
        Intent intent = new Intent();
        intent.putExtra("deleteUUID",previousProblem.getUuid());
        setResult(RESULT_OK,intent);
        finish();
    }

}
