package ca.ualberta.cmput301f18t11.medicam.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;

import java.util.ArrayList;
import java.util.List;

import ca.ualberta.cmput301f18t11.medicam.R;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.InstancePhotoPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.controllers.per_model.PatientRecordPersistenceController;
import ca.ualberta.cmput301f18t11.medicam.models.PatientRecord;
import ca.ualberta.cmput301f18t11.medicam.models.attachments.InstancePhoto;
import ca.ualberta.cmput301f18t11.medicam.utils.ImageAdapter;

import static android.graphics.BitmapFactory.decodeByteArray;

public class RecordPhotosSlideshow extends AppCompatActivity {

    private ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>();
    private PatientRecord record = new PatientRecord();
    private PatientRecordPersistenceController recordController = new PatientRecordPersistenceController();
    private InstancePhotoPersistenceController instancePhotoPersistenceController =
            new InstancePhotoPersistenceController();
    private List<String> photoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_photos_slideshow);

        fetchPrevious();
        ViewPager viewpage = findViewById(R.id.viewPager);
        ImageAdapter adapter = new ImageAdapter(this, bitmaps);
        viewpage.setAdapter(adapter);
    }

    public void fetchPrevious(){
        Intent intent = getIntent();
        String recordUUID = intent.getStringExtra("previous");
        record = recordController.load(recordUUID,this);
        this.photoList = record.getPhotoList();

        for (int i = 0; i < this.photoList.size(); i++){
            InstancePhoto instancePhoto = instancePhotoPersistenceController.load(photoList.get(i),
                    this);

            Bitmap bitmap = instancePhoto.getPhoto();
            bitmaps.add(bitmap);
        }
    }


}
