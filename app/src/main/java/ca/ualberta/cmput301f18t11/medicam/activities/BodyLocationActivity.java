package ca.ualberta.cmput301f18t11.medicam.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import ca.ualberta.cmput301f18t11.medicam.R;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.InstancePhotoPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.models.attachments.BodyLocation;
import ca.ualberta.cmput301f18t11.medicam.models.attachments.InstancePhoto;
import ca.ualberta.cmput301f18t11.medicam.models.attachments.ReferencePhoto;

public class BodyLocationActivity extends AppCompatActivity {

    private String mode;

    private Button confirmButton;
    private ReferencePhoto photo;
    private BodyLocation bodyLocation;
    private ImageView imageView;
    private InstancePhotoPersistenceController instancePhotoPersistenceController =
            new InstancePhotoPersistenceController();
    private InstancePhoto displayedPhoto;
    private Pair<Float, Float> imageCoordinates = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_referencephoto);

        confirmButton = findViewById(R.id.displayReferencePhotoConfirmButton);
        imageView = findViewById(R.id.displayReferencePhotoImageView);




        mode = getIntent().getStringExtra("mode");
        if (mode.equals("create")) {

            photo = (ReferencePhoto) getIntent().getExtras().getSerializable("photo");
            if (photo != null) {
                displayedPhoto = instancePhotoPersistenceController.load(photo.getPhotoUUID(),
                        this);
                imageView.setImageBitmap(displayedPhoto.getPhoto());
            }

            imageView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    imageCoordinates = new Pair<>(event.getX()/5,
                            event.getY()/5);
                    displayMarker();
                    return false;
                }


            });

            confirmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (imageCoordinates == null) {
                        Toast.makeText(BodyLocationActivity.this,
                                "Please tap the screen to select a location", 5).show();
                        return;
                    }
                    BodyLocation result = new BodyLocation(imageCoordinates, photo);
                    Intent intent = new Intent();
                    intent.putExtra("new", result);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
        } else if (mode.equals("view")) {

            bodyLocation = (BodyLocation) getIntent().getExtras().getSerializable("photo");
            displayedPhoto = instancePhotoPersistenceController.load(
                    bodyLocation.getReferencePhoto().getPhotoUUID(), this);
            imageCoordinates = bodyLocation.getImageCoordinates();
            confirmButton.setVisibility(View.GONE);
            displayMarker();
        }
        
    }

    private void displayMarker() {
        if (imageCoordinates == null) {
            return;
        }
        Bitmap bMap = displayedPhoto.getPhoto();

        bMap = bMap.copy(bMap.getConfig(), true);

        Canvas canvas = new Canvas(bMap);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        canvas.drawBitmap(bMap, new Matrix(), null);
        canvas.drawCircle(imageCoordinates.first-13, imageCoordinates.second+4, 5, paint);
        imageView.setImageBitmap(bMap);
    }
}
