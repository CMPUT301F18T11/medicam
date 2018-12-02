package ca.ualberta.cmput301f18t11.medicam.controllers.per_model;

import android.content.Context;

import java.io.FileReader;
import java.util.ArrayList;

import ca.ualberta.cmput301f18t11.medicam.controllers.ElasticSearchController;
import ca.ualberta.cmput301f18t11.medicam.controllers.InternalStorageController;
import ca.ualberta.cmput301f18t11.medicam.controllers.abstracts.PersistenceController;
import ca.ualberta.cmput301f18t11.medicam.models.attachments.InstancePhoto;
import io.searchbox.client.JestResult;

public class InstancePhotoPersistenceController extends PersistenceController<InstancePhoto> {

    @Override
    public InstancePhoto loadFromREST(String id) {
        ElasticSearchController.GetObjectsTask task = new ElasticSearchController.GetObjectsTask(getTypeURL());
        try
        {
            JestResult result = task.execute(id).get();
            return result.getSourceAsObjectList(InstancePhoto.class).get(0);
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public InstancePhoto loadFromStorage(String id, Context context) {
        InternalStorageController.GetObjectsTask task = new InternalStorageController.GetObjectsTask(context);
        try
        {
            ArrayList<FileReader> readers = task.execute(id).get();
            return gson.fromJson(readers.get(0), InstancePhoto.class);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String getTypeURL() {
        return "InstancePhoto";
    }
}
