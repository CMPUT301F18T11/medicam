package ca.ualberta.cmput301f18t11.medicam.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import ca.ualberta.cmput301f18t11.medicam.R;
import ca.ualberta.cmput301f18t11.medicam.controllers.ElasticSearchController;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }


    @Override
    protected void onStart() {
        super.onStart();

        DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080");
        DroidClientConfig config = builder.build();

        JestClientFactory factory = new JestClientFactory();
        factory.setDroidClientConfig(config);


        JestDroidClient client = (JestDroidClient) factory.getObject();


        // Foo f = new Foo("Hello World", "Shouyang");

        // ElasticSearchController.SaveObjectsTask addFooTask = new ElasticSearchController.SaveObjectsTask();

        // addFooTask.execute(f);

//        Foo f = new Foo("TESTASTR", "123123123");
//        Bar b = new Bar("adasdlkaj", "ABCDE");

//        ElasticSearchController.SaveObjectsTask addFooTask1 = new ElasticSearchController.SaveObjectsTask("Foo");
//        ElasticSearchController.SaveObjectsTask addFooTask2 = new ElasticSearchController.SaveObjectsTask("Bar");

        //addFooTask1.execute(f);
        //addFooTask2.execute(b);
//
//        List<Bar> Foos;
//
//        ElasticSearchController.GetObjectsTask getFoosTask = new ElasticSearchController.GetObjectsTask("Bar");
//
//        getFoosTask.execute("ABCDE");
//
//        try
//        {
//            Foos =  getFoosTask.get().getSourceAsObjectList(Bar.class);
//
//            int x = 1;
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }

        ElasticSearchController.DeleteObjectsTask deleteFoosTask = new ElasticSearchController.DeleteObjectsTask("Bar");

        deleteFoosTask.execute("ABCDE");


    }
}
