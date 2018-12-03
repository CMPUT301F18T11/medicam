package ca.ualberta.cmput301f18t11.medicam.controllers.per_model;

import android.content.Context;
import android.net.ConnectivityManager;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ca.ualberta.cmput301f18t11.medicam.controllers.ElasticSearchController;
import ca.ualberta.cmput301f18t11.medicam.controllers.InternalStorageController;
import ca.ualberta.cmput301f18t11.medicam.controllers.abstracts.PersistenceController;
import ca.ualberta.cmput301f18t11.medicam.models.Patient;
import ca.ualberta.cmput301f18t11.medicam.models.PatientRecord;
import ca.ualberta.cmput301f18t11.medicam.models.Problem;
import ca.ualberta.cmput301f18t11.medicam.models.abstracts.Record;
import ca.ualberta.cmput301f18t11.medicam.models.attachments.Geolocation;
import io.searchbox.client.JestResult;

/**
    Persistence controller for Record objects
    See PersistenceController for documentation
 */
public class PatientRecordPersistenceController extends PersistenceController<PatientRecord> {

    @Override
    public PatientRecord loadFromREST(String id)
    {
        ElasticSearchController.GetObjectsTask task = new ElasticSearchController.GetObjectsTask(getTypeURL());
        try
        {
            JestResult result = task.execute(id).get();
            return result.getSourceAsObjectList(PatientRecord.class).get(0);
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public PatientRecord loadFromStorage(String id, Context context)
    {
        InternalStorageController.GetObjectsTask task = new InternalStorageController.GetObjectsTask(context);
        try
        {
            ArrayList<FileReader> readers = task.execute(id).get();
            return gson.fromJson(readers.get(0), PatientRecord.class);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public List<PatientRecord> searchFromREST(String search_phrase, String problem_uuid)
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
            return result.getSourceAsObjectList(PatientRecord.class);
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public List<PatientRecord> searchGeoLocationFromREST(double lat, double lon, int distance, String problem_uuid)
    {
        ElasticSearchController.SearchObjectsTask task = new ElasticSearchController.SearchObjectsTask(getTypeURL());
        try
        {
            String search_query = "{" +
                    "  \"query\": {" +
                    "    \"bool\" : {" +
                    "      \"must\" : {" +
                    "        \"match\" : {\"problemUUID\" : \"%record_problem_uuid%\"}" +
                    "      }" +
                    "    }" +
                    "  }," +
                    "  \"filter\" : {" +
                    "    \"geo_distance\" : {" +
                    "      \"distance\" : \"%kms_away% km\"," +
                    "      \"location\" : {" +
                    "        \"lat\" : \"%search_lat%\"," +
                    "        \"lon\" : \"%search_lon%\"" +
                    "      }" +
                    "    }" +
                    "  }" +
                    "}";

            search_query = search_query.replace("%search_lat%", Double.toString(lat));
            search_query = search_query.replace("%search_lon%", Double.toString(lon));
            search_query = search_query.replace("%kms_away%", Integer.toString(distance));
            search_query = search_query.replace("%record_problem_uuid%", problem_uuid);

            JestResult result = task.execute(search_query).get();
            return result.getSourceAsObjectList(PatientRecord.class);
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public List<PatientRecord> searchBodyLocationFromREST(String bodylocation_uid, String problem_uuid)
    {
        ElasticSearchController.SearchObjectsTask task = new ElasticSearchController.SearchObjectsTask(getTypeURL());
        try
        {
            String search_query = "{ " +
                    "  \"size\": 100," +
                    "  \"query\": {" +
                    "    \"bool\": {" +
                    "      \"must\": " +
                    "      [{\"multi_match\" : {\"query\":    \"%search_bodylocation_uuid%\", \"fields\": [ \"photoUUID\", \"bodyLocation.referencePhoto.photoUUID\" ]}}," +
                    "       {\"match\": {\"problemUUID\": \"%search_problem_uuid%\"}}]" +
                    "    }" +
                    "  }" +
                    "}";

            search_query = search_query.replace("%search_bodylocation_uuid%", bodylocation_uid);
            search_query = search_query.replace("%search_problem_uuid%", problem_uuid);

            JestResult result = task.execute(search_query).get();
            return result.getSourceAsObjectList(PatientRecord.class);
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public String getTypeURL() {
        return "PatientRecord";
    }


}
