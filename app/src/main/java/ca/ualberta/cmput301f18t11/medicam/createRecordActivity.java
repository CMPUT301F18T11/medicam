package ca.ualberta.cmput301f18t11.medicam;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.sql.Time;
import java.util.Calendar;
import java.util.UUID;

public class createRecordActivity extends AppCompatActivity {
    private static final int OPEN_CAMERA_REQUEST_CODE = 0;
    private static final int OPEN_GALLAY_REQUEST_CODE = 1;
    private static final int ADD_BODYLOCATION_REQUEST_CODE = 3;

    private BodyLocation bodyLocation = null;
    private InstancePhoto photo = null;
    private PatientRecord record = new PatientRecord();;
    //Resource used for date picker: https://www.youtube.com/watch?v=hwe1abDO2Ag
    //Resource used for time picker: https://www.youtube.com/watch?v=QMwaNN_aM3U

    private TextView displayDate;
    private TextView displayTime;
    private DatePickerDialog.OnDateSetListener mDate;
    private TimePickerDialog.OnTimeSetListener mTime;
    private ImageView photoImageView;
    private TextView bodyLocationTextView;
    private EditText recordTitle;
    private EditText recordComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_record);
        photoImageView = findViewById(R.id.recorePhotoImageView);
        displayDate = (TextView) findViewById(R.id.dateView);
        displayTime = (TextView) findViewById(R.id.timeView);
        bodyLocationTextView = findViewById(R.id.bodyLocationTextView);
        recordTitle = findViewById(R.id.recordTitle);
        recordComment = findViewById(R.id.recordcomment);

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
//Fetch Image From USER and Store as Bitmap / Uri type.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == OPEN_CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            Log.d("SELECT PHOTO DIALOG","onActivityResult: done takeing new photo");
            Bitmap bitmap;
            bitmap = (Bitmap) data.getExtras().get("data");
            photo = new InstancePhoto();
            photo.setCameraPhoto(bitmap);
            photoImageView.setImageBitmap(photo.getCameraPhoto());
        }

        else if(requestCode == OPEN_GALLAY_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            Log.d("SELECT PHOTO DIALOG","onActivityResult: done SELECTING new photo");
            Uri selectedImageUri = data.getData();
            photo = new InstancePhoto();
            photo.setPhoto(selectedImageUri);
            //RANDOM UUID FOR NOW
            photo.setInstancePhotoUUID(UUID.randomUUID());
            photoImageView.setImageURI(photo.getPhoto());
        }

        else if(requestCode == ADD_BODYLOCATION_REQUEST_CODE&& resultCode == Activity.RESULT_OK){
            Log.d("SET BODY LOCATION","onActivityResult: done setting BodyLocation");
            String bodylocationString = data.getStringExtra("BodyLocation");
            Toast.makeText(this,"BodyLocation set to be: "+bodylocationString,Toast.LENGTH_SHORT).show();
            bodyLocation = new BodyLocation();
            // Not BodyLocation is Stored as a Object contain a name of the body location
            bodyLocation.setBodyParts(bodylocationString);
            bodyLocation.setBodyLocationUUID(UUID.randomUUID());
            bodyLocationTextView.setText(bodyLocation.getBodyParts());
        }
    }


    public void goToCamera(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,OPEN_CAMERA_REQUEST_CODE);
    }
    public void goToGallery(View view){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,OPEN_GALLAY_REQUEST_CODE);
    }
    public void goAddBodyLocation(View view){
        Intent intent = new Intent(this,BodyLocationActivity.class);
        startActivityForResult(intent,ADD_BODYLOCATION_REQUEST_CODE);
    }
    public void saveRecord(View view){
        Intent intent =  new Intent();
        record.setTitle(recordTitle.getText().toString());
        record.setDescription(recordComment.getText().toString());
        if (bodyLocation != null) {
            record.addAttachment(photo.getInstancePhotoUUID());
        }
        if (bodyLocation != null) {
            record.addAttachment(bodyLocation.getBodyLocationUUID());
        }
        intent.putExtra("newRecord",record);
        setResult(RESULT_OK,intent);
        finish();
    }

}
