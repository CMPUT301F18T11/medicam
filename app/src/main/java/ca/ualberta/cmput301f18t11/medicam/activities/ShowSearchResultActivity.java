package ca.ualberta.cmput301f18t11.medicam.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ca.ualberta.cmput301f18t11.medicam.R;
import ca.ualberta.cmput301f18t11.medicam.models.PatientRecord;
import ca.ualberta.cmput301f18t11.medicam.models.Problem;
import ca.ualberta.cmput301f18t11.medicam.models.abstracts.Record;

public class ShowSearchResultActivity extends AppCompatActivity {
    private ListView listView;
    private String resultType;
    private Problem problem;
    private String accessType;
    private List<Problem> problemResultList = new ArrayList<>();
    private List<Record> recordsResultList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_search_result);
        listView=findViewById(R.id.search_result_list_view);

        Intent intent = getIntent();
        resultType = intent.getStringExtra("resultType");
        accessType = intent.getStringExtra("accessType");

        if (resultType.equals("problems")){
            problemResultList = (ArrayList<Problem>) intent.getExtras().getSerializable("problemResultList");
            ArrayAdapter<Problem> itemsAdapter = new ArrayAdapter<Problem>(this, android.R.layout.simple_list_item_1, problemResultList);
            listView.setAdapter(itemsAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    List<String> problemList = new ArrayList<>();
                    for (int i = 0; i < problemResultList.size(); i++){
                        problemList.add(problemResultList.get(i).getUuid());
                    }
                    Intent intent = new Intent(ShowSearchResultActivity.this,PatientRecordActivity.class);
                    intent.putExtra("previousProblem", problemList.get(position));
                    String patientUUID = getIntent().getStringExtra("patientUUID");
                    intent.putExtra("patientUUID",patientUUID);
                    startActivity(intent);
                }
            });
            /**
             * above is for displaying problems
             *
             * below is for displaying records
             */

        } else if (resultType.equals("records")){
            recordsResultList = (ArrayList<Record>) intent.getExtras().getSerializable("recordResultList");
            problem = (Problem) intent.getExtras().getSerializable("problem");
            final ArrayList<String> records = problem.getPatientRecords();
            final ArrayList<String> doctorRecords = problem.getCareProviderRecords();
            ArrayAdapter<Record> itemsAdapter = new ArrayAdapter<Record>(this,android.R.layout.simple_list_item_1, recordsResultList);
            listView.setAdapter(itemsAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Record record = recordsResultList.get(position);
                    if(records.contains(record.getUuid())) {
                        Intent intent = new Intent(ShowSearchResultActivity.this, ViewRecordActivity.class);
                        if (accessType.equals("patient")) {
                            intent.putExtra("editable", "YES");
                        }else if (accessType.equals("doctor")){
                            intent.putExtra("editable", "NO");
                        }
                        intent.putExtra("previous", record.getUuid());
                        intent.putExtra("previousProblem", problem.getUuid());
                        startActivity(intent);
                    }else if(doctorRecords.contains(record.getUuid())){
                        Intent intent = new Intent(ShowSearchResultActivity.this, ViewCareProviderRecordActivity.class);
                        if (accessType.equals("patient")) {
                            intent.putExtra("editable", "NO");
                        }else if (accessType.equals("doctor")){
                            intent.putExtra("editable", "YES");
                        }
                        intent.putExtra("previousDoctorRecord",record.getUuid());
                        startActivity(intent);
                    }
                }

            });

        }



    }
}
