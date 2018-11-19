package ca.ualberta.cmput301f18t11.medicam.models;

import java.util.UUID;

import ca.ualberta.cmput301f18t11.medicam.exceptions.ReassignmentException;
import ca.ualberta.cmput301f18t11.medicam.models.abstracts.Record;

public class CareProviderRecord extends Record {
    private String care_provider;

    public CareProviderRecord(){
        super();
        this.createUuid();
    }

    public CareProviderRecord(String uuid) {
        super(uuid);
    }

    public String getCare_provider() {
        return care_provider;
    }

    public void setCare_provider (String assigned_care_provider) throws ReassignmentException {
        if(this.care_provider == null){
            this.care_provider = assigned_care_provider;
        }else {
            throw new ReassignmentException("This record is already attributed to care provider with UUID: " + care_provider.toString());
        }
    }

    public String toString(){
        return "Doctor Says: " + getTitle() + "\n"+"Comment: " + getDescription()+"\n";
    }
}
