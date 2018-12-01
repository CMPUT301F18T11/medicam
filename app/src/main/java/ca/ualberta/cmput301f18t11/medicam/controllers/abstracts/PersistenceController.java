package ca.ualberta.cmput301f18t11.medicam.controllers.abstracts;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;

import java.util.UUID;

import ca.ualberta.cmput301f18t11.medicam.models.abstracts.PersistedModel;
import ca.ualberta.cmput301f18t11.medicam.controllers.ElasticSearchController;
import ca.ualberta.cmput301f18t11.medicam.controllers.InternalStorageController;

/**
    Extend class for functionality with each object which needs to be stored
    The saving classes can be made generic since the class is not required
 */
public abstract class PersistenceController<T extends PersistedModel> {
    protected Gson gson = new Gson();


    /**
        Saves object T to local storage and to server
        Should not be overridden
        inputs: item T to be saved, and context of current activity
     */
    public void save(T item, Context context)
    {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo active_network = cm.getActiveNetworkInfo();

        if (active_network != null && active_network.isConnectedOrConnecting())
        {
            saveToREST(item);
        }

        saveToStorage(item, context);
    }

    /**
        Helper method for the save method.
        Handles saving to REST server.
     */
    private void saveToREST(T item) {
        ElasticSearchController.SaveObjectsTask<T> task = new ElasticSearchController.SaveObjectsTask(getTypeURL());
        task.execute(item);
    }
    /**
        Helper method for the save method.
        Handles saving to internal storage.
     */
    protected void saveToStorage(T item, Context context) {
        InternalStorageController.SaveObjectsTask<T> task = new InternalStorageController.SaveObjectsTask(context);
        task.execute(item);
    }

    /**
        Loads object T from internal storage or server.
        Loading priority might depend on object being loaded.

        Rely on helper methods to perform work
        Inputs: id of the object, context of current activity
        Outputs: Object with same id as input
     */
    public  T load(String id, Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting())
        {
            T result = loadFromREST(id);
            return result;
        }

        return loadFromStorage(id, context);
    }

    /**
        Loads object T from server.
        Helper method to load.
     */
    public abstract T loadFromREST(String id);

    /**
        Loads object T from storage
        Helper method to load.
     */
    public abstract T loadFromStorage(String id, Context context);


    /**
        Deletes item T from both internal storage and server
        TODO: stagger server deletion if no internet connectivity
     */
    public void delete(T item, Context context)
    {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo active_network = cm.getActiveNetworkInfo();

        if (active_network != null && active_network.isConnectedOrConnecting())
        {
            deleteFromREST(item);
        }

        deleteFromStorage(item, context);
    }

    /**
        Helper method to delete.
        Handles deletion from server.
     */
    public void deleteFromREST(T item)
    {
        ElasticSearchController.DeleteObjectsTask task = new ElasticSearchController.DeleteObjectsTask(getTypeURL());
        task.execute(item.getUuid());
    }

    /**
        Helper method to delete.
        Handles deletion from storage.
     */
    public void deleteFromStorage(T item, Context context)
    {
        InternalStorageController.DeleteObjectsTask task = new InternalStorageController.DeleteObjectsTask(context);
        task.execute(item.getUuid());
    }

    /**
        Essential method for server functionality.
        Should return String describing the type to the REST server
        Will be used in the {type} part of the identification on the server.
     */
    public abstract String getTypeURL();
}
