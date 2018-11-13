package ca.ualberta.cmput301f18t11.medicam;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.sql.Time;
import java.util.Calendar;

public class createRecordActivity extends AppCompatActivity {

    //Resource used for date picker: https://www.youtube.com/watch?v=hwe1abDO2Ag
    //Resource used for time picker: https://www.youtube.com/watch?v=QMwaNN_aM3U

    private TextView displayDate;
    private TextView displayTime;
    private DatePickerDialog.OnDateSetListener mDate;
    private TimePickerDialog.OnTimeSetListener mTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_record);

        displayDate = (TextView) findViewById(R.id.dateView);
        displayTime = (TextView) findViewById(R.id.timeView);

        displayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dateDialog = new DatePickerDialog(createRecordActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDate, year, month, day);
                dateDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dateDialog.show();

            }
        });


        displayTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        mDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month++;
                Log.d("Setdate", "onDateSet: date: " + year + "/" + month + "/" + dayOfMonth);
                String date = year + "/" + month + "/" + dayOfMonth;
                displayDate.setText(date);
            }
        };

    }

}
