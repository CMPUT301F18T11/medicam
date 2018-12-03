package ca.ualberta.cmput301f18t11.medicam.utils;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;

/** ImageAdapter used to display bitmap images used by photos slide show functions.
 */
public class ImageAdapter extends PagerAdapter {

    private Context mContext;
    private Bitmap bitmap;
    private ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>();

    public ImageAdapter(Context context, ArrayList<Bitmap> bitmaps) {
        mContext = context;
        this.bitmaps = bitmaps;
    }

    @Override
    public int getCount() {
        return bitmaps.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        ImageView imageview = new ImageView(mContext);
        imageview.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageview.setImageBitmap(this.bitmaps.get(position));
        container.addView(imageview, 0);
        return imageview;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ImageView) object);
    }
}