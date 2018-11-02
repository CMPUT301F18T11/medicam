package ca.ualberta.cmput301f18t11.medicam;

import java.util.UUID;

public class CareProviderRecord extends Record {
    private UUID care_provider;

    public UUID getCare_provider() {
        return super.getUuid();
    }
}
