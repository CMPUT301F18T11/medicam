package ca.ualberta.cmput301f18t11.medicam.controllers.abstracts;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;

import ca.ualberta.cmput301f18t11.medicam.models.abstracts.PersistedModel;
import ca.ualberta.cmput301f18t11.medicam.controllers.ElasticSearchController;
import ca.ualberta.cmput301f18t11.medicam.controllers.InternalStorageController;

public abstract class PersistenceController<T extends PersistedModel> {
    protected Gson gson = new Gson();

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

    public void saveToREST(T item) {
        ElasticSearchController.SaveObjectsTask<T> task = new ElasticSearchController.SaveObjectsTask(getTypeURL());
        task.execute(item);
    }

    public void saveToStorage(T item, Context context) {
        InternalStorageController.SaveObjectsTask<T> task = new InternalStorageController.SaveObjectsTask(context);
        task.execute(item);
    }

    public abstract T load(String id, Context context);

    public abstract T loadFromREST(String id);

    public abstract T loadFromStorage(String id, Context context);


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

    public void deleteFromREST(T item)
    {
        ElasticSearchController.DeleteObjectsTask task = new ElasticSearchController.DeleteObjectsTask(getTypeURL());
        task.execute(item.getUuid());
    }

    public void deleteFromStorage(T item, Context context)
    {
        InternalStorageController.DeleteObjectsTask task = new InternalStorageController.DeleteObjectsTask(context);
        task.execute(item.getUuid());
    }


    public abstract String getTypeURL();
}
