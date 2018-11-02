package ca.ualberta.cmput301f18t11.medicam;

import java.util.UUID;

public class CareProviderRecord extends Record{
    private UUID care_provider;

    public CareProviderRecord(){
        this.createUuid();
    }
    public UUID getCare_provider() {
        return super.getUuid();
    }


    public void setCare_provider (UUID assigned_care_provider) throws ReassignmentException {
        if(this.care_provider == null){
            this.care_provider = assigned_care_provider;
        }else {
            throw new ReassignmentException("This record is already attributed to care provider with UUID: " + care_provider.toString());
        }
    }
}
