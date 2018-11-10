package ca.ualberta.cmput301f18t11.medicam.controllers;

import android.content.Context;

import com.google.gson.Gson;

import ca.ualberta.cmput301f18t11.medicam.PersistedModel;
import ca.ualberta.cmput301f18t11.medicam.models.Patient;

abstract class PersistenceController<T extends PersistedModel> {
    protected Gson gson = new Gson();

    public void save(T item, Context context)
    {
        saveToREST(item);
        saveToStorage(item, context);
    }

    public void saveToREST(T item) {
        ElasticSearchController.SaveObjectsTask<T> task = new ElasticSearchController.SaveObjectsTask(getTypeURL());

        task.execute(item);
    }

    public void saveToStorage(T item, Context context) {
        InternalStorageController.SaveObjectsTask<T> task = new InternalStorageController.SaveObjectsTask(context);

        task.execute(item);
    }

    public abstract T loadFromREST(String id);

    public abstract T loadFromStorage(String id);

    public abstract String getTypeURL();
}
