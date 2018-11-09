package ca.ualberta.cmput301f18t11.medicam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.util.ArrayList;
import java.util.List;

import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;

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

        // FooController.AddFooTask addFooTask = new FooController.AddFooTask();

        // addFooTask.execute(f);

//        Foo f = new Foo("TESTASTR", "123123123");
//        Bar b = new Bar("adasdlkaj", "ABCDE");

//        FooController.AddFooTask addFooTask1 = new FooController.AddFooTask("Foo");
//        FooController.AddFooTask addFooTask2 = new FooController.AddFooTask("Bar");

        //addFooTask1.execute(f);
        //addFooTask2.execute(b);
//
//        List<Bar> Foos;
//
//        FooController.GetFoosTask getFoosTask = new FooController.GetFoosTask("Bar");
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

        FooController.DeleteFoosTask deleteFoosTask = new FooController.DeleteFoosTask("Bar");

        deleteFoosTask.execute("ABCDE");


    }
}
