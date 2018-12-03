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
import ca.ualberta.cmput301f18t11.medicam.models.CareProviderRecord;
import ca.ualberta.cmput301f18t11.medicam.models.PatientRecord;
import ca.ualberta.cmput301f18t11.medicam.models.abstracts.Record;
import io.searchbox.client.JestResult;

/**
    Persistence controller for Care Provider Record objects
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

    /** Searches records on Elastic Search REST API by search phrase in the title and description.
     *  Only searches records related to the problem defined by problem_uuid.
     *
     * @param search_phrase Phrase to search by
     * @param problem_uuid Problem UUId returned records should relate to.
     * @return List of relevant care provider records as defined by Elastic Search
     */
    public List<CareProviderRecord> searchFromREST(String search_phrase, String problem_uuid)
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
                    "       {\"match\": {\"problemUUID\": \"%search_problem_uuid%\"}}]" +
                    "    }" +
                    "  }" +
                    "}";

            search_query = search_query.replace("%search_phrase%", search_phrase);
            search_query = search_query.replace("%search_problem_uuid%", problem_uuid);

            JestResult result = task.execute(search_query).get();
            return result.getSourceAsObjectList(CareProviderRecord.class);
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
