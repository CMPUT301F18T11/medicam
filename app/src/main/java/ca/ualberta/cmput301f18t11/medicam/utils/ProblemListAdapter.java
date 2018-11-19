package ca.ualberta.cmput301f18t11.medicam.utils;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ca.ualberta.cmput301f18t11.medicam.R;
import ca.ualberta.cmput301f18t11.medicam.models.Problem;

/**
 * Created by User on 11/18/2018.
 */
//Taken from https://www.youtube.com/watch?v=E6vE8fqQPTE
public class ProblemListAdapter extends ArrayAdapter<Problem> {

    private static final String TAG = "ProblemListAdapter";

    private Context mContext;
    private int mResource;

    /**
     * Default constructor for the PersonListAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public ProblemListAdapter(Context context, int resource, ArrayList<Problem> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the persons information
        String title = getItem(position).getTitle();
        Date date = getItem(position).getDate();
        String description = getItem(position).getDescription();

        //Create the person object with the information
        Problem problem = new Problem(title,date, description);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvTitle = convertView.findViewById(R.id.adapterview_title);
        TextView tvDate = convertView.findViewById(R.id.adapterview_date);
        TextView tvDescription = convertView.findViewById(R.id.adapterview_count);

        tvTitle.setText(title);
        tvDate.setText(date.toString());
        tvDescription.setText(description);

        return convertView;
    }
}
























