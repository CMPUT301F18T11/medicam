package ca.ualberta.cmput301f18t11.medicam.controllers.per_model;

import android.content.Context;
import android.net.ConnectivityManager;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.UUID;

import ca.ualberta.cmput301f18t11.medicam.controllers.ElasticSearchController;
import ca.ualberta.cmput301f18t11.medicam.controllers.InternalStorageController;
import ca.ualberta.cmput301f18t11.medicam.controllers.abstracts.PersistenceController;
import ca.ualberta.cmput301f18t11.medicam.models.CareProvider;
import io.searchbox.client.JestResult;

/**
    Persistence controller for CareProvider objects
    See PersistenceController for documentation
 */

public class CareProviderPersistenceController extends PersistenceController<CareProvider> {

    @Override
    protected void saveToStorage(CareProvider item, Context context) {
        InternalStorageController.SaveObjectsTask<CareProvider> task =
                new InternalStorageController.SaveObjectsTask<>(context, getTypeURL());
        task.execute(item);
    }

    @Override
    public CareProvider loadFromREST(String id)
    {
        ElasticSearchController.GetObjectsTask task = new ElasticSearchController.GetObjectsTask(getTypeURL());
        try
        {
            JestResult result = task.execute(id).get();
            return result.getSourceAsObjectList(CareProvider.class).get(0);
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public CareProvider loadFromStorage(String id, Context context)
    {
        InternalStorageController.GetObjectsTask task = new InternalStorageController.GetObjectsTask(context, getTypeURL());
        try
        {
            ArrayList<FileReader> readers = task.execute(id).get();
            return gson.fromJson(readers.get(0), CareProvider.class);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void deleteFromStorage(CareProvider item, Context context) {
        InternalStorageController.DeleteObjectsTask task =
                new InternalStorageController.DeleteObjectsTask(context, getTypeURL());
        task.execute(item.getUuid());
    }

    @Override
    public String getTypeURL() {
        return "CareProvider";
    }


}
