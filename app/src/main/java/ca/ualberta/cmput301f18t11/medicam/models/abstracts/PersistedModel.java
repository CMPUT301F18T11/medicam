package ca.ualberta.cmput301f18t11.medicam.models.abstracts;

import android.content.Context;

import java.io.Serializable;
import java.util.UUID;

public abstract class PersistedModel implements Serializable {

    protected Boolean hasChangedOffline = false;
    protected UUID uuid;

    public PersistedModel(UUID uuid) {
        this.uuid = uuid;
    }

    public PersistedModel() {
        uuid = UUID.randomUUID();
    }

    public UUID getUuid() { return uuid; }


    public void createUuid() {
        if (uuid == null){
            //create a fresh UUID
            this.uuid = UUID.randomUUID();
        }
    }
}
