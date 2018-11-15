package ca.ualberta.cmput301f18t11.medicam.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import ca.ualberta.cmput301f18t11.medicam.R;
import ca.ualberta.cmput301f18t11.medicam.models.abstracts.Record;

public class CareGiverRecordActivity extends AppCompatActivity {
    private ListView careGiverRecordsView;
    private ArrayAdapter<Record> careProviderRecordArrayAdapter;
    private ArrayList<Record> careProviderRecordArrayList = new ArrayList<Record>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care_giver_record);

        //Record example = new PatientRecord();
        //careProviderRecordArrayList.add(example);

        careGiverRecordsView = findViewById(R.id.careGiverRecordListView);
        ArrayList<String> anExampleStringList = new ArrayList<String>();
        anExampleStringList.add("This is just an example");
        ArrayAdapter<String> exampleArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,anExampleStringList);
        //careProviderRecordArrayAdapter = new ArrayAdapter<CareProviderRecord>(this,android.R.layout.simple_list_item_1,careProviderRecordArrayList);
        careGiverRecordsView.setAdapter(exampleArrayAdapter);

        // if Item Clicked
        careGiverRecordsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                seeRecordDetail(view);

            }
        });

        // if Long pressed:
        // TODO Toast or show the actual comment.
        careGiverRecordsView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(CareGiverRecordActivity.this,"This will show the patient comments on this Long pressed record",Toast.LENGTH_LONG).show();
                return true;
            }
        });

    }

    public void seeRecordDetail(View view){
        Intent intent = new Intent(this,CareGiverAttachActivity.class);
        startActivity(intent);
    }
}
