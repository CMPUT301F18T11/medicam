package ca.ualberta.cmput301f18t11.medicam.controllers.per_model;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.FileReader;
import java.util.ArrayList;

import ca.ualberta.cmput301f18t11.medicam.controllers.ElasticSearchController;
import ca.ualberta.cmput301f18t11.medicam.controllers.InternalStorageController;
import ca.ualberta.cmput301f18t11.medicam.controllers.abstracts.PersistenceController;
import ca.ualberta.cmput301f18t11.medicam.models.CareProvider;
import ca.ualberta.cmput301f18t11.medicam.models.abstracts.Record;
import io.searchbox.client.JestResult;

/**
    Persistence controller for Record objects
    See PersistenceController for documentation
 */
public class RecordPersistenceController extends PersistenceController<Record> {

    @Override
    public Record load(String id, Context context)
    {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting())
        {
            return loadFromREST(id);
        }

        return loadFromStorage(id, context);
    }


    @Override
    public Record loadFromREST(String id)
    {
        ElasticSearchController.GetObjectsTask task = new ElasticSearchController.GetObjectsTask(getTypeURL());
        try
        {
            JestResult result = task.execute(id).get();
            return result.getSourceAsObjectList(Record.class).get(0);
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Record loadFromStorage(String id, Context context)
    {
        InternalStorageController.GetObjectsTask task = new InternalStorageController.GetObjectsTask(context);
        try
        {
            ArrayList<FileReader> readers = task.execute(id).get();
            return gson.fromJson(readers.get(0), Record.class);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public String getTypeURL() {
        return "Record";
    }


}
