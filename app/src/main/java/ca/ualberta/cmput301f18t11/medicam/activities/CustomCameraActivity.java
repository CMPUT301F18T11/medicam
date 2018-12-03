package ca.ualberta.cmput301f18t11.medicam.activities;

import android.content.Intent;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageButton;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.camerakit.CameraKit;
import com.camerakit.CameraKitView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


import ca.ualberta.cmput301f18t11.medicam.R;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.InstancePhotoPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.models.PatientRecord;
import ca.ualberta.cmput301f18t11.medicam.models.attachments.InstancePhoto;

public class CustomCameraActivity extends AppCompatActivity {

    private static final String IMAGE_FILE_PATH = "image_file_path";

    private CameraKitView mCameraKitView;
    private AppCompatButton mCaptureButton;
    private ImageView mCameraOverlay;
    private ToggleButton mOverlayToggle;
    private AppCompatImageButton mFacingToggle;

    private File mOutputFile;
    private PatientRecord mRecord;

    private InstancePhotoPersistenceController mInstancePhotoPersistenceController =
            new InstancePhotoPersistenceController();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRecord = (PatientRecord) getIntent().getSerializableExtra("RECORD");
        setContentView(R.layout.activity_custom_camera);

        try {
            mOutputFile = createImageFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mCameraKitView = findViewById(R.id.camera_view);
        mCaptureButton = findViewById(R.id.button_capture_photo);
        mOverlayToggle = findViewById(R.id.toggle_camera_overlay);
        mCameraOverlay = findViewById(R.id.camera_overlay);
        mFacingToggle = findViewById(R.id.switch_facing);
        mFacingToggle.setImageResource(R.drawable.ic_camera_front_black_24dp);


        InstancePhoto instancePhoto = mInstancePhotoPersistenceController.load(mRecord.getMostRecentPhoto(),
                this);
        if (instancePhoto != null) {
            mCameraOverlay.setImageBitmap(instancePhoto.getPhoto());
        } else {
            mCameraOverlay.setImageResource(R.drawable.ic_muscles_arm_svgrepo_com);
        }

        mCameraOverlay.setAlpha(0.35f);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mCameraKitView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCameraKitView.onResume();
    }

    @Override
    protected void onPause() {
        mCameraKitView.onPause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        mCameraKitView.onStop();
        super.onStop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mCameraKitView.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    public void capturePhoto (View view) {
        mCameraKitView.captureImage(new CameraKitView.ImageCallback() {
            @Override
            public void onImage(CameraKitView cameraKitView, final byte[] imageBytes) {
                try{
                    FileOutputStream outputStream = new FileOutputStream(mOutputFile.getPath());
                    outputStream.write(imageBytes);
                    outputStream.close();
                    showToast("Saved :" + mOutputFile.toString());
                } catch (IOException e ){
                    e.printStackTrace();
                }
                retActivity();

            }
        });
    }

    public void switchFacing (View view) {
        if(mCameraKitView.getFacing() == CameraKit.FACING_BACK){
            mCameraKitView.toggleFacing();
            mFacingToggle.setImageResource(R.drawable.ic_camera_rear_black_24dp);
        } else {
            mCameraKitView.toggleFacing();
            mFacingToggle.setImageResource(R.drawable.ic_camera_front_black_24dp);
        }

    }

    public void toggleCameraOverlay(View view){
        boolean isChecked = ((ToggleButton) view).isChecked();

        if (isChecked) {
            mCameraOverlay.setVisibility(View.VISIBLE);
        } else {
            mCameraOverlay.setVisibility(View.INVISIBLE);
        }
    }

    //TODO:Create and check a record specific folder
    File createOutputFilePath() throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();


        File directory = new File(
                root
                        + File.separator
                        + "medicam");
        File parentDir = directory.getParentFile();

        if(!directory.exists()) {
            if(!directory.isDirectory()) {
                directory.mkdirs();
            }
        }

        return File.createTempFile(String.format("IMG_%s", timeStamp), ".jpg", directory );
    }

    private File createImageFile() throws IOException {
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        String timesStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File image = File.createTempFile("medicam_" + timesStamp,".jpg", storageDir);

        return image;
    }

    private void showToast(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void retActivity(){
        Intent returnIntent = new Intent();
        returnIntent.putExtra(IMAGE_FILE_PATH, mOutputFile.toString());
        setResult(RESULT_OK,returnIntent);
        finish();
    }
}
