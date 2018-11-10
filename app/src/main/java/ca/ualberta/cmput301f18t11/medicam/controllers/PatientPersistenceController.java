package ca.ualberta.cmput301f18t11.medicam.controllers;

import android.content.Context;

import com.google.gson.Gson;

import java.io.FileReader;
import java.util.ArrayList;

import ca.ualberta.cmput301f18t11.medicam.models.Patient;
import io.searchbox.client.JestResult;

public class PatientPersistenceController extends PersistenceController<Patient> {

    private Context context;

    public PatientPersistenceController(Context context)
    {
        this.context = context;
    }


    @Override
    public Patient loadFromREST(String id)
    {
        ElasticSearchController.GetObjectsTask task = new ElasticSearchController.GetObjectsTask(getTypeURL());
        try
        {
            JestResult result = task.execute(id).get();
            return result.getSourceAsObjectList(Patient.class).get(0);
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Patient loadFromStorage(String id)
    {
        InternalStorageController.GetObjectsTask task = new InternalStorageController.GetObjectsTask(context);
        try
        {
            ArrayList<FileReader> readers = task.execute(id).get();
            return gson.fromJson(readers.get(0), Patient.class);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public String getTypeURL() {
        return "Patient";
    }


}
