package ca.ualberta.cmput301f18t11.medicam.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import ca.ualberta.cmput301f18t11.medicam.R;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.InstancePhotoPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.models.attachments.BodyLocation;
import ca.ualberta.cmput301f18t11.medicam.models.attachments.InstancePhoto;
import ca.ualberta.cmput301f18t11.medicam.models.attachments.ReferencePhoto;

public class CreateBodyLocationActivity extends AppCompatActivity {
    private static final int OPEN_CAMERA_REQUEST_CODE = 4;
    private static final int OPEN_GALLERY_REQUEST_CODE = 5;

    private Spinner bodyLocationSpinner;
    private String bodyLocationPart;
    private TextView bodyLocationLabelTextView;
    private ImageView bodyLocationImageView;
    private ImageButton galleryButton;
    private ImageButton takePhotoButton;
    private InstancePhoto currentPhoto;
    private String mode;

    private String latest_image;

    private InstancePhotoPersistenceController instancePhotoPersistenceController =
            new InstancePhotoPersistenceController();
    //private BodyLocation bodyLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_location);

        bodyLocationLabelTextView = findViewById(R.id.bodyLocationDescriptionTextView);
        bodyLocationImageView = findViewById(R.id.createBodyLocationImageView);
        galleryButton = findViewById(R.id.addPhotoFromGalleryBodyLocation);
        takePhotoButton = findViewById(R.id.takePhotoBodyLocationButton);

        mode = getIntent().getStringExtra("mode");

        if (mode.equals("edit")) {
            ReferencePhoto previousBodyLocation = (ReferencePhoto) getIntent()
                    .getExtras().getSerializable("bodyLocation");
            bodyLocationLabelTextView.setText(previousBodyLocation.getLabel());
            bodyLocationPart = previousBodyLocation.getBodyPart();
            currentPhoto = instancePhotoPersistenceController.load(
                    previousBodyLocation.getPhotoUUID(), this);

            bodyLocationImageView.setImageBitmap(currentPhoto.getPhoto());
        }

        //set up the bodyLocation spinner
        bodyLocationSpinner = findViewById(R.id.bodyLocationSpinner);
        ArrayAdapter<CharSequence> bodyLocationSpinnerAdapter =
                ArrayAdapter.createFromResource(this,R.array.body_location_spinner,
                        android.R.layout.simple_selectable_list_item);
        bodyLocationSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        bodyLocationSpinner.setAdapter(bodyLocationSpinnerAdapter);
        bodyLocationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bodyLocationPart = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToGallery(v);
            }
        });

        takePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCamera(v);
            }
        });
    }

    public void goToCamera(View view){

        File image = null;
        try {
            image = createImageFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            if (image != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "ca.ualberta.cmput301f18t11.medicam",image);
                latest_image = image.getAbsolutePath();
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, OPEN_CAMERA_REQUEST_CODE);
            }
        }
    }

    public void goToGallery(View view){
        Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent, OPEN_GALLERY_REQUEST_CODE);
    }

    private File createImageFile() throws IOException {
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        String timesStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File image = File.createTempFile("medicam_" + timesStamp,".jpg", storageDir);

        return image;
    }


    public void finishSetingBodyLocation(View view){
        Intent intent = new Intent();
        ReferencePhoto photo = new ReferencePhoto();
        photo.setBodyPart(bodyLocationPart);
        photo.setLabel(bodyLocationLabelTextView.getText().toString());
        photo.setPhotoUUID(currentPhoto.getUuid());
        instancePhotoPersistenceController.save(currentPhoto, this);
        intent.putExtra("new", photo);
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == OPEN_CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK){

            File mImageFile = new File(latest_image);
            Bitmap bMap = BitmapFactory.decodeFile(latest_image);
            currentPhoto = new InstancePhoto(bMap);

            Uri imageUri = Uri.fromFile(mImageFile);
            bodyLocationImageView.setImageURI(imageUri);
        }

        else if(requestCode == OPEN_GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            Uri selectedImageUri = data.getData();
            InputStream inputStream;
            if (selectedImageUri != null) {
                try {
                    inputStream = getContentResolver().openInputStream(selectedImageUri);
                    Bitmap bMap = BitmapFactory.decodeStream(inputStream);
                    currentPhoto = new InstancePhoto(bMap);
                    bodyLocationImageView.setImageURI(selectedImageUri);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public void checkButton(View view) {
        return;
    }

}
