package ca.ualberta.cmput301f18t11.medicam;

import java.io.Serializable;
import java.util.Observable;
import java.util.UUID;

public abstract class PersistedModel extends Observable implements Serializable {
    private String index;
    private String type;
    private UUID uuid;
    private String stored_hash;

    public PersistedModel load() {
        return null;
    }

    public void save() {

    }

    public void delete() {

    }
}
