package ca.ualberta.cmput301f18t11.medicam.controllers;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import ca.ualberta.cmput301f18t11.medicam.models.abstracts.PersistedModel;


/** InternalStorageController
 *
 */
public class InternalStorageController {

    private static Gson gson = new Gson();

    private static String file_suffix = ".json";

    /** SaveObjectsTask
     *
     */
    public static class SaveObjectsTask<Type extends PersistedModel> extends AsyncTask<Type,Void,Boolean> {

        private Context context;

        public SaveObjectsTask(Context context) {
            this.context = context;
        }

        @Override
        protected Boolean doInBackground(Type... object_params) {

            for (Type object: object_params)
            {
                try
                {

                    File save_file = new File(context.getFilesDir(), getModelSavePath(object));

                    if (!save_file.exists()) {
                        save_file.createNewFile();
                    }

                    FileWriter writer = new FileWriter(save_file, false);

                    writer.write(gson.toJson(object));
                    writer.close();
                }

                catch (Exception e)
                {
                    e.printStackTrace();
                    return false;
                }
            }

            return true;
        }
    }

    /** GetObjectsTask
     *
     */
    public static class GetObjectsTask extends AsyncTask<String, Void, ArrayList<FileReader>> {

        private Context context;

        public GetObjectsTask(Context context)
        {
            this.context = context;
        }

        @Override
        protected ArrayList<FileReader> doInBackground(String... id_params) {
            ArrayList<FileReader> output = new ArrayList<>();

            for (String id: id_params)
            {
                try
                {

                    File save_file = new File(context.getFilesDir(), getModelSavePath(id));

                    FileReader reader = new FileReader(save_file);

                    output.add(reader);
                }

                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            return output;
        }
    }

    /** DeleteObjectsTask
     *
     */
    public static class DeleteObjectsTask extends AsyncTask<String, Void, Boolean> {

        private Context context;

        public DeleteObjectsTask(Context context)
        {
            this.context = context;
        }

        @Override
        protected Boolean doInBackground(String ... id_params)
        {
            for (String id: id_params)
            {
                File save_file = new File(context.getFilesDir(), getModelSavePath(id));

                if (save_file.exists() )
                {
                    save_file.delete();
                }
            }
            return true;
        }

    }

    // Helper Function
    private static String getModelSavePath(PersistedModel model)
    {
        return model.getUuid() + file_suffix;
    }

    private static  String getModelSavePath(String id)
    {
        return id + file_suffix;
    }


}
