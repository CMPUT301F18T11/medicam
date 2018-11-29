package ca.ualberta.cmput301f18t11.medicam.controllers;

import android.content.Context;
import android.content.Intent;

import ca.ualberta.cmput301f18t11.medicam.activities.MapsActivity;
import ca.ualberta.cmput301f18t11.medicam.models.attachments.Geolocation;

public class GeolocationController {

    public static Intent selectLocation(Context context) {
        Intent intent = new Intent(context, MapsActivity.class);
        intent.putExtra("mode", "selection");

        return intent;
    }

    public static Intent selectLocation(Context context, Geolocation current) {
        Intent intent = new Intent(context, MapsActivity.class);
        intent.putExtra("mode", "selection");
        intent.putExtra("latitude", current.getLatitude());
        intent.putExtra("longitude", current.getLongitude());

        return intent;
    }

}
