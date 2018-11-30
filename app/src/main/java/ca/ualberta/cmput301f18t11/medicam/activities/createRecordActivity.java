package ca.ualberta.cmput301f18t11.medicam.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ca.ualberta.cmput301f18t11.medicam.R;
import ca.ualberta.cmput301f18t11.medicam.controllers.ElasticSearchController;
import ca.ualberta.cmput301f18t11.medicam.controllers.GeolocationController;
import ca.ualberta.cmput301f18t11.medicam.controllers.abstracts.PersistenceController;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.PatientRecordPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.models.attachments.BodyLocation;
import ca.ualberta.cmput301f18t11.medicam.models.attachments.Geolocation;
import ca.ualberta.cmput301f18t11.medicam.models.attachments.InstancePhoto;
import ca.ualberta.cmput301f18t11.medicam.models.PatientRecord;

public class createRecordActivity extends AppCompatActivity {
    private static final int OPEN_CAMERA_REQUEST_CODE = 0;
    private static final int OPEN_GALLERY_REQUEST_CODE = 1; //Refactored from GALLAY -> GALLERY
    private static final int ADD_BODYLOCATION_REQUEST_CODE = 3;
    private static final int UPDATE_GEOLOCATION_REQUEST_CODE = 4;

    private BodyLocation bodyLocation = null;
    private ArrayList<InstancePhoto> photos = new ArrayList<>();
    private PatientRecord record = new PatientRecord();
    private Date datetime = new Date();
    private String purpose; // this will give us the info of whether the user want to Edit the record Or add a Record
    private Geolocation location;
    //Resource used for date picker: https://www.youtube.com/watch?v=hwe1abDO2Ag
    //Resource used for time picker: https://www.youtube.com/watch?v=QMwaNN_aM3U

    private TextView displayDate;
    private TextView displayTime;
    private ImageView photoImageView;
    private TextView bodyLocationTextView;
    private EditText recordTitle;
    private EditText recordComment;
    private TextView geoLocationTextView;
    private ImageButton mapButton;
    private Calendar calendar = Calendar.getInstance();
    private PersistenceController<PatientRecord> recordController = new PatientRecordPersistenceController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_record);
        ElasticSearchController.setIndex_url("cmput301f18t11test");

        photoImageView = findViewById(R.id.recorePhotoImageView4View);
        displayDate = (TextView) findViewById(R.id.dateView4View);
        displayTime = (TextView) findViewById(R.id.timeView4View);
        bodyLocationTextView = findViewById(R.id.bodyLocationTextView4View);
        recordTitle = findViewById(R.id.recordTitle4View);
        recordComment = findViewById(R.id.recordcomment4View);
        mapButton = findViewById(R.id.viewRecordMapButton);
        geoLocationTextView = findViewById(R.id.addressTextView);

        displayDateAndTime();
        purpose = getIntent().getStringExtra("purpose");
        if(purpose.equals("edit")){
            TextView textView = findViewById(R.id.recordCreate_Header01);
            textView.setText("Editing Record");
            Button button = findViewById(R.id.recordbutton);
            button.setText("Save Changes");
            fetchPrevious();
        }
        // set DATE picker
        displayDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar cal = Calendar.getInstance();
                    int year = cal.get(Calendar.YEAR);
                    int month = cal.get(Calendar.MONTH);
                    int day = cal.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datepicker = new DatePickerDialog(createRecordActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int day) {
                            calendar.set(Calendar.YEAR, year);
                            calendar.set(Calendar.MONTH, month);
                            calendar.set(Calendar.DAY_OF_MONTH, day);
                            datetime = calendar.getTime();
                            //Display date and set Date datetime to the selected date
                            java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("dd-MM-yyyy");
                            String dateStr = dateFormat.format(datetime);
                            displayDate.setText(dateStr);
                        }
                    }, year, month, day);
                    datepicker.show();
                }
        });
        //ent of set DATE picker

        // set TIME picker
        displayTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Calendar cal = Calendar.getInstance();
                    int hour = cal.get(Calendar.HOUR_OF_DAY);
                    int minute = cal.get(Calendar.MINUTE);
                    TimePickerDialog timepicker = new TimePickerDialog(createRecordActivity.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            calendar.set(Calendar.MINUTE, minute);
                            datetime = calendar.getTime();
                            //Display time and set Date datetime to the selected time
                            java.text.SimpleDateFormat timeformat = new java.text.SimpleDateFormat("HH:mm");
                            String timeStr = timeformat.format(datetime);
                            displayTime.setText(timeStr);
                        }
                    }, hour, minute, true);
                    timepicker.show();
                }
        });
        //ent of set TIME picker

        // set geolocation picker
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (location == null) {
                    startActivityForResult(GeolocationController.selectLocation(createRecordActivity.this),
                            UPDATE_GEOLOCATION_REQUEST_CODE);

                } else {
                    startActivityForResult(GeolocationController.selectLocation(createRecordActivity.this, location),
                            UPDATE_GEOLOCATION_REQUEST_CODE);
                }
            }
        });

    }
//Fetch Image From USER and Store as Bitmap / Uri type.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //TODO Geo Location  and Save photos + bodylocations
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == OPEN_CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            Log.d("SELECT PHOTO DIALOG","onActivityResult: done takeing new photo");
            // TODO CHANGE FOR NEW ARRAYLIST HOLDER
//            Bitmap bitmap;
//            bitmap = (Bitmap) data.getExtras().get("data");
//            photo = new InstancePhoto();
//            photo.setPhoto(bitmap);
//            record.addPhotoToList(photo.getUuid());
//            //recordController.save(record,this);
//            photoImageView.setImageBitmap(photo.getPhoto());
        }

        else if(requestCode == OPEN_GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            Log.d("SELECT PHOTO DIALOG","onActivityResult: done SELECTING new photo");
            Uri selectedImageUri = data.getData();
//            photo = new InstancePhoto();
            //TODO FIX THIS FOR ARRAY OF INSTANCEPHOTO
//            photo.setPhoto(selectedImageUri);
//            record.addPhotoToList(photo.getUuid());
            //recordController.save(record,this);
//            photoImageView.setImageURI(Uri.parse(photo.getPhoto()));
        }

        else if(requestCode == ADD_BODYLOCATION_REQUEST_CODE&& resultCode == Activity.RESULT_OK){
            Log.d("SET BODY LOCATION","onActivityResult: done setting BodyLocation");
            String bodylocationString = data.getStringExtra("BodyLocation");
            Toast.makeText(this,"BodyLocation set to be: "+bodylocationString,Toast.LENGTH_SHORT).show();
            bodyLocation = new BodyLocation();
            // TODO NEW BODY LOCATION LOGIC
            // Not BodyLocation is Stored as a Object contain a name of the body location
            //record.addAttachment(bodyLocation.getAttachment_uuid());
            //recordController.save(record,this);
//            bodyLocationTextView.setText(bodyLocation.getBodyPart());
        }

        else if (requestCode == UPDATE_GEOLOCATION_REQUEST_CODE) {
            location = MapsActivity.location;
            displayGeolocation();
        }
    }

    private void displayGeolocation() {
        if (location == null) {
            geoLocationTextView.setText(R.string.noLocation);
        } else {
            geoLocationTextView.setText(String.format("Lat: %s\nLng: %s", location.getLatitude(), location.getLongitude()));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    /**
     * End of OnActivityResult
     */
    public void displayDateAndTime(){
        java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("dd-MM-yyyy");
        String dateStr = dateFormat.format(datetime);
        displayDate.setText(dateStr);
        java.text.SimpleDateFormat timeformat = new java.text.SimpleDateFormat("HH:mm");
        String timeStr = timeformat.format(datetime);
        displayTime.setText(timeStr);
    }

    public void goToCamera(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,OPEN_CAMERA_REQUEST_CODE);
    }
    
    public void goToGallery(View view){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, OPEN_GALLERY_REQUEST_CODE);
    }

    public void goAddBodyLocation(View view){
        Intent intent = new Intent(this,BodyLocationActivity.class);
        startActivityForResult(intent,ADD_BODYLOCATION_REQUEST_CODE);
    }

    public void saveRecord(View view){
        if (recordTitle.getText().toString().equals("")){Toast.makeText(createRecordActivity.this,"Please Enter a title",Toast.LENGTH_SHORT).show();
        }else {
            Intent intent = new Intent();
            record.setTitle(recordTitle.getText().toString());
            record.setDescription(recordComment.getText().toString());
            record.setTimestamp(datetime);
//            record.addPhotoToList(photo.getUuid());

            record.setBodyLocation(bodyLocation);

            record.setLocation(location);
            record.setProblemUUID(getIntent().getStringExtra("problemUUID"));
            recordController.save(record,this);
            intent.putExtra("newRecord", record);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    public void fetchPrevious(){
        Intent intent =  getIntent();
        String recordUUID = intent.getStringExtra("previous");
        record = recordController.load(recordUUID,this);
        recordTitle.setText(record.getTitle());
        recordComment.setText(record.getDescription());

        datetime = record.getTimestamp();
        java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("dd/MM/YYYY");
        String dateStr = dateFormat.format(datetime);
        displayDate.setText(dateStr);

        java.text.SimpleDateFormat timeFormat = new java.text.SimpleDateFormat("HH:mm");
        String timeStr = timeFormat.format(datetime);
        displayTime.setText(timeStr);

        location = record.getLocation();
        displayGeolocation();

        //TODO: fetch Also PHOTOS BODY LOCATIONS AND PHOTO LIST
    }

}
