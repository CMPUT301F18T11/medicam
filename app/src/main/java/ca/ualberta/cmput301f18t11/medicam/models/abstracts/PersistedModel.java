package ca.ualberta.cmput301f18t11.medicam.models.abstracts;

import android.content.Context;

import java.io.Serializable;
import java.util.UUID;

public abstract class PersistedModel implements Serializable {

    protected String uuid = UUID.randomUUID().toString();

    protected String stored_hash;

    protected String index;

    protected String type;

    //These are mostly data access and storage methods.
    // Should they be abstract?
    public static PersistedModel load(){return null;/*idk what this does*/}
    public void save(Context context){}
    public void delete(){}
    public static PersistedModel loadFromREST(){return null;/*query elastic search*/}
    public static PersistedModel loadFromStorage(){return null;/*access internal storage*/}

    public void saveToREST() {}
    public void saveToStorage(Context context){}


    public void deleteFromREST(){}
    public void deleteFromStorage(){}

    //get methods
    public String getIndex(){ return index; }
    public String getType(){ return type; }
    public String getUuid() { return uuid; }
    public String getStored_hash() { return stored_hash; }

    //set methods
    public void setType(String type) { this.type = type; }
    public void setIndex(String index) { this.index = index; }

    //hash generation
    public void createStored_hash() {
        if (stored_hash.isEmpty()){
            //do some hash generation algorithm
        }
    }

    public void createUuid() {
        if (uuid == null){
            //create a fresh UUID
            this.uuid = UUID.randomUUID().toString();
        }
    }
}
