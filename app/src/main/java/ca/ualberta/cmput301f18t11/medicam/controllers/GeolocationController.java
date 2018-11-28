package ca.ualberta.cmput301f18t11.medicam.controllers;

import android.content.Context;
import android.content.Intent;
import android.location.Location;

import ca.ualberta.cmput301f18t11.medicam.activities.MapsActivity;

public class GeolocationController {

    public static Intent selectLocation(Context context) {
        Intent intent = new Intent(context, MapsActivity.class);
        intent.putExtra("mode", "selection");

        return intent;
    }

    public static Intent selectLocation(Context context, Location current) {
        Intent intent = new Intent(context, MapsActivity.class);
        intent.putExtra("mode", "selection");
        intent.putExtra("startinglocation", current);

        return intent;
    }

}
