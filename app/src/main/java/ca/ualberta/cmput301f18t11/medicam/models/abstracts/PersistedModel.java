package ca.ualberta.cmput301f18t11.medicam.models.abstracts;

import android.content.Context;

import java.io.Serializable;
import java.util.UUID;

public abstract class PersistedModel implements Serializable {

    protected Boolean hasChangedOffline = false;
    protected String uuid;

    public PersistedModel(String uuid) {
        this.uuid = uuid;
    }

    public PersistedModel() {
        uuid = UUID.randomUUID().toString();
    }

    public String getUuid() { return uuid; }


    public void createUuid() {
        if (uuid == null){
            //create a fresh UUID
            this.uuid = UUID.randomUUID().toString();
        }
    }
}
