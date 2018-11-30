package ca.ualberta.cmput301f18t11.medicam.controllers.per_model;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ca.ualberta.cmput301f18t11.medicam.controllers.ElasticSearchController;
import ca.ualberta.cmput301f18t11.medicam.controllers.InternalStorageController;
import ca.ualberta.cmput301f18t11.medicam.controllers.abstracts.PersistenceController;
import ca.ualberta.cmput301f18t11.medicam.models.CareProvider;
import ca.ualberta.cmput301f18t11.medicam.models.Problem;
import io.searchbox.client.JestResult;

/**
    Persistence controller for Problem objects
    See PersistenceController for documentation
 */
public class ProblemPersistenceController extends PersistenceController<Problem> {

    @Override
    public Problem loadFromREST(String id)
    {
        ElasticSearchController.GetObjectsTask task = new ElasticSearchController.GetObjectsTask(getTypeURL());
        try
        {
            JestResult result = task.execute(id).get();
            return result.getSourceAsObjectList(Problem.class).get(0);
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Problem loadFromStorage(String id, Context context)
    {
        InternalStorageController.GetObjectsTask task = new InternalStorageController.GetObjectsTask(context);
        try
        {
            ArrayList<FileReader> readers = task.execute(id).get();
            return gson.fromJson(readers.get(0), Problem.class);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public List<Problem> searchFromREST(String search_phrase, String creator_uuid)
    {
        ElasticSearchController.SearchObjectsTask task = new ElasticSearchController.SearchObjectsTask(getTypeURL());
        try
        {
            String search_query = "{ " +
                    "  \"size\": 100," +
                    "  \"query\": {" +
                    "    \"bool\": {" +
                    "      \"must\": " +
                    "      [{\"multi_match\" : {\"query\":    \"%search_phrase%\", \"fields\": [ \"title\", \"description\" ]}}," +
                    "       {\"match\": {\"creatorUUID\": \"%search_creator_uuid%\"}}]" +
                    "    }" +
                    "  }" +
                    "}";

            search_query = search_query.replace("%search_phrase%", search_phrase);
            search_query = search_query.replace("%search_creator_uuid%", creator_uuid);

            JestResult result = task.execute(search_query).get();
            return result.getSourceAsObjectList(Problem.class);
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }





    @Override
    public String getTypeURL() {
        return "Problem";
    }


}
