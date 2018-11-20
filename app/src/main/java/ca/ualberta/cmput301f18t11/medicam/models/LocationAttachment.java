package ca.ualberta.cmput301f18t11.medicam.models;

import android.location.Location;

import java.util.ArrayList;

import ca.ualberta.cmput301f18t11.medicam.models.abstracts.Attachment;

public class LocationAttachment extends Attachment {
    private Location location;
    private ArrayList<Double>map_coordinates = new ArrayList<Double>(2);


    public boolean nearby(Location oc, double dist){
        return false;
    }

    public ArrayList getMap_coordinates(){
        return map_coordinates;
    }

    public void setMap_coordinates(Double x,Double y){
        this.map_coordinates.add(x);
        this.map_coordinates.add(y);

    }

    public Location getLocation;
}
