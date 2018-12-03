package ca.ualberta.cmput301f18t11.medicam.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import ca.ualberta.cmput301f18t11.medicam.R;
import ca.ualberta.cmput301f18t11.medicam.models.attachments.Geolocation;

/**
 * Functionality used from https://developers.google.come/maps/documentation/android-sdk/
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener, GoogleMap.OnMyLocationButtonClickListener {
    private static final int MY_LOCATION_REQUEST_CODE = 1;

    private GoogleMap mMap;
    public static Geolocation location;
    private String mode;
    private Marker currentMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Intent intent = getIntent();
        mode = intent.getStringExtra("mode");

        location = new Geolocation(intent.getDoubleExtra("latitude", 0),
                intent.getDoubleExtra("longitude", 0));

        if (location.getLatitude() == 0 && location.getLongitude() == 0) {
            location = null;
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng startingMarker;
        if (location == null) {

            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                String[] permissions = new String[1];
                permissions[0] = Manifest.permission.ACCESS_FINE_LOCATION;
                ActivityCompat.requestPermissions(this, permissions, MY_LOCATION_REQUEST_CODE);
                return;
            }
            mMap.setMyLocationEnabled(true);
            startingMarker = getCurrentLocation();
            location = new Geolocation(startingMarker.latitude, startingMarker.longitude);
        } else {
            startingMarker = new LatLng(location.getLatitude(), location.getLongitude());

        }
        currentMarker = mMap.addMarker(new MarkerOptions().position(startingMarker).title("Record Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(startingMarker));

        if (mode.equals("selection")) {
            mMap.setOnMapClickListener(this);
            mMap.setOnMarkerClickListener(this);
            mMap.setOnMyLocationButtonClickListener(this);
        }
    }

    private LatLng getCurrentLocation() {
        LocationManager lm = (LocationManager) getSystemService(this.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            return new LatLng(0,0);
        }

        Location myLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (myLocation == null) {
            return new LatLng(0,0);
        }
        double longitude = myLocation.getLongitude();
        double latitude = myLocation.getLatitude();
        return new LatLng(latitude, longitude);
    }




    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_LOCATION_REQUEST_CODE) {
            if (permissions.length == 1 &&
                    permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
            } else {
                // TODO exit activity?
            }
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        location = new Geolocation(latLng.latitude, latLng.longitude);
        currentMarker.remove();
        currentMarker = mMap.addMarker(new MarkerOptions().position(latLng).title("Record Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.remove();
        location = null;
        return false;
    }

    @Override
    public boolean onMyLocationButtonClick() {
        LatLng currentLocation = getCurrentLocation();
        location = new Geolocation(currentLocation.latitude, currentLocation.longitude);
        currentMarker.remove();
        currentMarker = mMap.addMarker(new MarkerOptions().position(currentLocation).title("Record Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
        return false;
    }
}
