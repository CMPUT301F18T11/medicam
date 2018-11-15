package ca.ualberta.cmput301f18t11.medicam.controllers.per_model;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.UUID;

import ca.ualberta.cmput301f18t11.medicam.controllers.ElasticSearchController;
import ca.ualberta.cmput301f18t11.medicam.controllers.InternalStorageController;
import ca.ualberta.cmput301f18t11.medicam.controllers.abstracts.PersistenceController;
import ca.ualberta.cmput301f18t11.medicam.models.CareProvider;
import ca.ualberta.cmput301f18t11.medicam.models.CareProviderRecord;
import ca.ualberta.cmput301f18t11.medicam.models.abstracts.Record;
import io.searchbox.client.JestResult;

/**
    Persistence controller for Record objects
    See PersistenceController for documentation
 */
public class CareProviderRecordPersistenceController extends PersistenceController<CareProviderRecord> {


    @Override
    public CareProviderRecord loadFromREST(String id)
    {
        ElasticSearchController.GetObjectsTask task = new ElasticSearchController.GetObjectsTask(getTypeURL());
        try
        {
            JestResult result = task.execute(id).get();
            return result.getSourceAsObjectList(CareProviderRecord.class).get(0);
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public CareProviderRecord loadFromStorage(String id, Context context)
    {
        InternalStorageController.GetObjectsTask task = new InternalStorageController.GetObjectsTask(context);
        try
        {
            ArrayList<FileReader> readers = task.execute(id).get();
            return gson.fromJson(readers.get(0), CareProviderRecord.class);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public String getTypeURL() {
        return "CareProviderRecord";
    }


}
