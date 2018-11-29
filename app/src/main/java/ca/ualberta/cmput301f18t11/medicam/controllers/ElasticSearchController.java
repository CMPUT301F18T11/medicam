package ca.ualberta.cmput301f18t11.medicam.controllers;

import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.io.IOException;
import java.util.UUID;

import ca.ualberta.cmput301f18t11.medicam.models.abstracts.PersistedModel;
import io.searchbox.client.JestResult;
import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.Search;


/** ElasticSearchController
 *  Controller for generating ElasticSearch related tasks
 *  Tasks will asynchronously perform certain server related functions
 *
 */
public class ElasticSearchController {

    // Project Constant URLs
    private static String server_url = "http://cmput301.softwareprocess.es:8080";
    private static String index_url  = "cmput301f18t11test";

    // Jest Client Singleton
    private static JestDroidClient client;

    public static void setIndex_url(String new_index_url) {
        index_url = new_index_url;
    }


    /** SaveObjectsTask
     *  Task used for saving an object of class T.
     *  General steps for use: create task, use execute method
     *  with object to store as input.
     *  Can check result of execution for success or failure.
     */
    public static class SaveObjectsTask<T extends PersistedModel> extends AsyncTask<T,Void,Boolean> {
        private String type_url;

        public SaveObjectsTask(String type_url) {
            this.type_url = type_url;
        }

        @Override
        protected Boolean doInBackground(T... objects) {
            verifySettings();

            for (T object: objects) {
                Index i = new Index.Builder(object).index(index_url).type(type_url).id(object.getUuid()).build();

                try
                {
                    DocumentResult result  = client.execute(i);
                }

                catch (IOException e)
                {
                    e.printStackTrace();
                    return false;
                }

            }

            return true;
        }
    }

    /** GetObjectsTask
     *  Task used for obtaining objects with a specific id and type from server.
     *  Constructed by first identifying the type string used by server
     *  Task is used by calling execute method on an array of string ids (param)
     *  Output is a JestResult obtained from the result method of the task
     *
     *  Objects must be reconstructed from JestResult using getSourceAsObject
     *  or getSourceAsObjectList methods
     */
    public static class GetObjectsTask extends AsyncTask<String, Void, JestResult> {

        private String type_url;

        public GetObjectsTask(String type_url) {
            this.type_url = type_url;
        }

        @Override
        protected JestResult doInBackground(String... id_params) {
            verifySettings();

            for (String id: id_params) {
                Get get = new Get.Builder(index_url, id).type(type_url).build();

                try {
                    JestResult result = client.execute(get);
                    if (result.isSucceeded()) {
                        return result;
                    }
                    else {
                        Log.i("Error", "The search query failed to find any tweets that matched");
                    }
                } catch (Exception e) {
                    Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
                }
            }
            return null;
        }
    }

    /** DeleteObjectsTask
     *  Task used for deletion of objects from server using type identifier and id
     *  Constructed with type identifier
     *  Task used with execute method, inputting an array of ids
     *  List of ids must all be of the type used in constructor.
     *
     *  ALL TASKS SHOULD ONLY BE USED ONCE AND THEN DISCARDED
     */
    public static class DeleteObjectsTask extends AsyncTask<String, Void, Boolean> {

        private String type_url;

        public DeleteObjectsTask(String type_url) {
            this.type_url = type_url;
        }

        @Override
        protected Boolean doInBackground(String ... ids)
        {
            verifySettings();

            for (String id: ids) {
                try {
                    DocumentResult result  = client.execute(new Delete.Builder(id).index(index_url).type(type_url).build());
                }
                catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }

            return true;
        }

    }

    public static class SearchObjectsTask extends AsyncTask<String, Void, JestResult> {


        // Replace "%phrase" for searching
        private String search_query = "{" +
                "  \"size\": 100," +
                "  \"query\": {" +
                "    \"multi_match\" : {" +
                "      \"query\":    \"%phrase%\", " +
                "      \"fields\": [ \"title\", \"description\" ] " +
                "    }" +
                "  }" +
                "}";

        private String type_url;

        public SearchObjectsTask(String type_url) {
            this.type_url = type_url;
        }

        @Override
        protected JestResult doInBackground(String ... search_phrases)
        {
            verifySettings();

            for (String phrase: search_phrases) {
                try {
                    search_query = search_query.replace("%phrase%", phrase);

                    Search search = new Search.Builder(search_query).addIndex(index_url).addType(type_url).build();

                    JestResult result  = client.execute(search);

                    if (result.isSucceeded()) {
                     return result;
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
            return null;
        }
    }



    // Generator For Jest Client Singleton
    private static void verifySettings() {
        if (client == null) {
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder(server_url);
            DroidClientConfig config = builder.build();
            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }
}
