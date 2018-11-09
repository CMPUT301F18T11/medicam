package ca.ualberta.cmput301f18t11.medicam;

import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.util.ArrayList;
import java.util.List;

import io.searchbox.client.JestResult;
import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

public class FooController{

    private static JestDroidClient client;


    public static class AddFooTask<T> extends AsyncTask<T,Void,Void> {

            public String type;


            public  AddFooTask(String type)
            {
                this.type = type;
            }

            @Override
            protected Void doInBackground(T ... foos)
            {
                verifySettings();

                for (T f: foos)
                {


                    Index i = new Index.Builder(f).index("cmput301f18t11test").type(type).build();

                    try
                    {
                        DocumentResult result  = client.execute(i);
                    }

                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                return null;
            }
        }

    public static class GetFoosTask extends AsyncTask<String, Void, JestResult> {

        private String type;

        public GetFoosTask(String type)
        {
            this.type = type;
        }


        @Override
        protected JestResult doInBackground(String... search_parameters) {
            verifySettings();

            for (String s: search_parameters)
            {
                // TODO Build the query
                Get get = new Get.Builder("cmput301f18t11test", s).type(type).build();

                try {
                    // TODO get the results of the query
                    JestResult result = client.execute(get);
                    if (result.isSucceeded()) {
                        return result;
                    } else {
                        Log.i("Error", "The search query failed to find any tweets that matched");
                    }
                } catch (Exception e) {
                    Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
                }
            }

            return null;
        }
    }

    public static class DeleteFoosTask extends AsyncTask<String, Void, Void> {

        private String type;

        public DeleteFoosTask(String type)
        {
            this.type = type;
        }

        @Override
        protected Void doInBackground(String ... ids)
        {
            verifySettings();

            for (String id: ids)
            {

                try
                {
                    DocumentResult result  = client.execute(new Delete.Builder(id).index("cmput301f18t11test").type(type).build());
                }

                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            return null;
        }

    }

    private static void verifySettings() {
        if (client == null) {
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080");
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }


}
