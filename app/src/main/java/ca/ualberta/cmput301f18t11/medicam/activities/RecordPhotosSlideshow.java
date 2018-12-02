package ca.ualberta.cmput301f18t11.medicam.activities;

import android.graphics.Bitmap;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import ca.ualberta.cmput301f18t11.medicam.R;
import ca.ualberta.cmput301f18t11.medicam.utils.ImageAdapter;

public class RecordPhotosSlideshow extends AppCompatActivity {

    private ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_photos_slideshow);


        ViewPager viewpage = findViewById(R.id.viewPager);
        ImageAdapter adapter = new ImageAdapter(this, bitmaps);
        viewpage.setAdapter(adapter);
    }


}
