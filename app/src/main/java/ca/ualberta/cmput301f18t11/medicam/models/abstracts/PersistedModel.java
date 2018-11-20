package ca.ualberta.cmput301f18t11.medicam.models.abstracts;

import android.content.Context;

import java.io.Serializable;
import java.util.UUID;

/**
 * This object represents that base functionality that every other model object in the app
 * will extend.
 * <p>
 * That is, each model object should be able to be uniquely identified by its uuid
 * and should have a dirty bit 'hasChangedOffline' to tell if it has been changed while offline
 * and thus needs to be updated in the server.
 */
public abstract class PersistedModel implements Serializable {

    protected Boolean hasChangedOffline = false;
    protected String uuid;

    /**
     * Constructor that sets the objects uuid to a user defined value.
     *
     * @param uuid a string that is used to uniquely identify this object.
     */
    public PersistedModel(String uuid) {
        this.uuid = uuid;
    }

    /**
     * Constructor for when the uuid is not specifically defined. Takes no parameters.
     */
    public PersistedModel() {
        uuid = UUID.randomUUID().toString();
    }

    /**
     * Get this objects Universal Unique Identifier.
     *
     * @return a string that is used to uniquely identify this object
     */
    public String getUuid() { return uuid; }

    /**
     * For setting the uuid of the object randomly only if, for some reason, it is set to null.
     */
    public void createUuid() {
        if (uuid == null){
            //create a fresh UUID
            this.uuid = UUID.randomUUID().toString();
        }
    }
}
