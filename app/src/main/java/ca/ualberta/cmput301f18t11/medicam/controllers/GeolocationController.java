package ca.ualberta.cmput301f18t11.medicam.controllers;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import ca.ualberta.cmput301f18t11.medicam.activities.MapsActivity;
import ca.ualberta.cmput301f18t11.medicam.models.Patient;
import ca.ualberta.cmput301f18t11.medicam.models.attachments.Geolocation;


/** GeolocationController
 *  Proxy Object for calling google maps API for certain intent actions.
 */
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


    public static Intent viewLocation(Context context, Geolocation current) {
        Intent intent = new Intent(context, MapsActivity.class);
        intent.putExtra("mode", "viewing");
        intent.putExtra("latitude", current.getLatitude());
        intent.putExtra("longitude", current.getLongitude());

        return intent;
    }

    public static Intent viewLocations(Context context, Patient patient) {
        Intent intent = new Intent(context, MapsActivity.class);
        intent.putExtra("mode", "viewmultiple");
        intent.putExtra("patient", patient.getUuid());
        return intent;
    }
}
