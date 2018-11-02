package ca.ualberta.cmput301f18t11.medicam;

import java.io.Serializable;
import java.util.UUID;

public abstract class PersistedModel implements Serializable {
    protected String index;
    protected String type;
    protected UUID uuid;
    protected String stored_hash;

    public PersistedModel load() {
        return null;
    }

    public void save() {

    }

    public void delete() {

    }
}
