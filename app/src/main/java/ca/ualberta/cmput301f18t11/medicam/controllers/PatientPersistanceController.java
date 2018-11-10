package ca.ualberta.cmput301f18t11.medicam.controllers;

import android.content.Context;

import com.google.gson.Gson;

import java.io.FileReader;
import java.util.ArrayList;

import ca.ualberta.cmput301f18t11.medicam.models.Patient;
import io.searchbox.client.JestResult;

public class PatientPersistanceController {

    private static String type_url = "Patient";

    private Gson    gson = new Gson();
    private Context context;

    public PatientPersistanceController(Context context)
    {
        this.context = context;
    }




    public void save(Patient patient)
    {
        saveToREST(patient);
        saveToStorage(patient);
    }

    public void saveToREST(Patient patient) {
        ElasticSearchController.SaveObjectsTask<Patient> task = new ElasticSearchController.SaveObjectsTask(type_url);

        task.execute(patient);
    }

    public void saveToStorage(Patient patient) {
        InternalStorageController.SaveObjectsTask<Patient> task = new InternalStorageController.SaveObjectsTask(context);

        task.execute(patient);
    }


    public Patient loadFromREST(String id)
    {
        ElasticSearchController.GetObjectsTask task = new ElasticSearchController.GetObjectsTask(type_url);
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






}
