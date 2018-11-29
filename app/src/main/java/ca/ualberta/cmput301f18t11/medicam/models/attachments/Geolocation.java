package ca.ualberta.cmput301f18t11.medicam.models.attachments;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


public class Geolocation implements Serializable {
    private double latitude;
    private double longitude;

    public Geolocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    protected Geolocation(Parcel in) {
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    private void readObject(
            ObjectInputStream inputStream
    ) throws ClassNotFoundException, IOException {
        inputStream.defaultReadObject();
    }

    private void writeObject(
            ObjectOutputStream outputStream
    ) throws IOException {
        outputStream.defaultWriteObject();
    }
}
