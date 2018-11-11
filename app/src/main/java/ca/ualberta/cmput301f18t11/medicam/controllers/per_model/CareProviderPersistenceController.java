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
    public CareProvider loadFromREST(UUID id)
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
    public CareProvider loadFromStorage(UUID id, Context context)
    {
        InternalStorageController.GetObjectsTask task = new InternalStorageController.GetObjectsTask(context);
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
    public String getTypeURL() {
        return "CareProvider";
    }


}
