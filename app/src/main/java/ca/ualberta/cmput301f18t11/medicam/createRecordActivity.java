package ca.ualberta.cmput301f18t11.medicam;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.nfc.Tag;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.net.URI;
import java.sql.Time;
import java.util.Calendar;

public class createRecordActivity extends AppCompatActivity {
    private static final int OPEN_CAMERA_REQUEST_CODE = 0;
    private static final int OPEN_GALLAY_REQUEST_CODE = 1;

    //Resource used for date picker: https://www.youtube.com/watch?v=hwe1abDO2Ag
    //Resource used for time picker: https://www.youtube.com/watch?v=QMwaNN_aM3U

    private TextView displayDate;
    private TextView displayTime;
    private DatePickerDialog.OnDateSetListener mDate;
    private TimePickerDialog.OnTimeSetListener mTime;
    private ImageView photoImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_record);
        photoImageView = findViewById(R.id.recorePhotoImageView);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == OPEN_CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            Log.d("SELECT PHOTO DIALOG","onActivityResult: done takeing new photo");
            Bitmap bitmap;
            bitmap = (Bitmap) data.getExtras().get("data");
            photoImageView.setImageBitmap(bitmap);
        }

        else if(requestCode == OPEN_GALLAY_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            Log.d("SELECT PHOTO DIALOG","onActivityResult: done SELECTING new photo");
            Uri selectedImageUri = data.getData();
            photoImageView.setImageURI(selectedImageUri);
        }
    }

    public void goToCamera(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,OPEN_CAMERA_REQUEST_CODE);
    }
    public void goToGallary(View view){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,OPEN_GALLAY_REQUEST_CODE);
    }

}
