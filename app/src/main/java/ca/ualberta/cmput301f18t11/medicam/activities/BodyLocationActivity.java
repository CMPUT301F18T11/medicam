package ca.ualberta.cmput301f18t11.medicam.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import ca.ualberta.cmput301f18t11.medicam.R;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.InstancePhotoPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.models.attachments.InstancePhoto;
import ca.ualberta.cmput301f18t11.medicam.models.attachments.ReferencePhoto;

public class BodyLocationActivity extends AppCompatActivity {

    private String mode;

    private Button confirmButton;
    private ReferencePhoto photo;
    private ImageView imageView;
    private InstancePhotoPersistenceController instancePhotoPersistenceController =
            new InstancePhotoPersistenceController();
    private InstancePhoto displayedPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_referencephoto);

        confirmButton = findViewById(R.id.displayReferencePhotoConfirmButton);
        imageView = findViewById(R.id.displayReferencePhotoImageView);

        photo = (ReferencePhoto) getIntent().getExtras().getSerializable("photo");
        if (photo != null) {
            displayedPhoto = instancePhotoPersistenceController.load(photo.getPhotoUUID(),
                    this);
            imageView.setImageBitmap(displayedPhoto.getPhoto());
        }

        mode = getIntent().getStringExtra("mode");
        if (mode.equals("create")) {
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO
                }
            });

            confirmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //todo
                }
            });
        } else if (mode.equals("view")) {
            confirmButton.setVisibility(View.GONE);
            displayMarker();
        }
        
    }

    private void displayMarker() {
        //todo
    }
}
