package ca.ualberta.cmput301f18t11.medicam.controllers;

import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import io.searchbox.client.JestResult;
import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;


/** ElasticSearchController
 *
 */
public class ElasticSearchController {

    // Project Constant URLs
    private static String server_url = "http://cmput301.softwareprocess.es:8080";
    private static String index_url  = "cmput301f18t11test";

    // Jest Client Singleton
    private static JestDroidClient client;


    /** SaveObjectsTask
     *
     */
    public static class SaveObjectsTask<Type> extends AsyncTask<Type,Void,Void> {
        private String type_url;

        public SaveObjectsTask(String type_url) {
            this.type_url = type_url;
        }

        @Override
        protected Void doInBackground(Type... objects) {
            verifySettings();

            for (Type object: objects) {
                Index i = new Index.Builder(object).index(index_url).type(type_url).build();

                try {
                    DocumentResult result  = client.execute(i);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return null;
        }
    }

    /** GetObjectsTask
     *
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
     *
     */
    public static class DeleteObjectsTask extends AsyncTask<String, Void, Void> {

        private String type_url;

        public DeleteObjectsTask(String type_url) {
            this.type_url = type_url;
        }

        @Override
        protected Void doInBackground(String ... ids)
        {
            verifySettings();

            for (String id: ids) {
                try {
                    DocumentResult result  = client.execute(new Delete.Builder(id).index(index_url).type(type_url).build());
                }
                catch (Exception e) {
                    e.printStackTrace();
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
