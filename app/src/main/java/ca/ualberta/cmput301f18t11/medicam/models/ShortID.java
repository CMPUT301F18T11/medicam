package ca.ualberta.cmput301f18t11.medicam.models;

import java.util.Random;

import ca.ualberta.cmput301f18t11.medicam.models.abstracts.PersistedModel;


/** ShortID is a key-value pair that maps a short integer uuid to a longer uuid string.
 *  The integer generated is bounded by the interval [MIN_INT, MIN_INT + RAND_BOUND]
 */
public class ShortID extends PersistedModel {

    private static int MIN_INT    = 1000;
    private static int RAND_BOUND = 8000;


    // Long Form UUID of another object.
    private String reference_uuid;

    // ShortID must be created by passing another object's uuid.
    public ShortID(String reference_uuid)
    {
        this.uuid = this.getRandomNumberString();
        this.reference_uuid = reference_uuid;
    }

    public String getReference_uuid() {
        return reference_uuid;
    }

    private String getRandomNumberString()
    {
        Random rand = new Random();
        return Integer.toString(rand.nextInt(RAND_BOUND) + MIN_INT);
    }


}
