package ca.ualberta.cmput301f18t11.medicam.controllers;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.UUID;

import ca.ualberta.cmput301f18t11.medicam.models.abstracts.PersistedModel;


/** InternalStorageController
 *  Controller used for handling internal storage calls.
 *  Contains tasks for saving, loading, and deletion
 *  Tasks must be constructed and then executed.
 *
 *  ALL TASKS SHOULD ONLY BE USED ONCE AND THEN DISCARDED.
 */
public class InternalStorageController {

    private static Gson gson = new Gson();

    private static String file_suffix = ".json";

    /** SaveObjectsTask
     *  Task used for saving objects to internal storage.
     *  Task is first constructed with current context.
     *  Use execute method to perform task
     *  Result of task execution Boolean indicating success or failure
     */
    public static class SaveObjectsTask<T extends PersistedModel> extends AsyncTask<T,Void,Boolean> {

        private Context context;

        public SaveObjectsTask(Context context) {
            this.context = context;
        }

        @Override
        protected Boolean doInBackground(T... object_params) {

            for (T object: object_params)
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
     *  Task for obtaining objects from internal storage.
     *  Task constructed using current Context
     *
     *  Use execute method with list of ids to obtain.
     *  Result of task execution is a list of FileReaders
     *  FileReader should be handled using Gson fromJson method
     *  using the expected class to reconstruct object.
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
                    //TODO: proper exception handling
                    e.printStackTrace();
                }
            }
            return output;
        }
    }

    /** DeleteObjectsTask
     *  Task for deletion of objects from internal storage.
     *  Task constructed using current context.
     *
     *  Using execute method with a list of ids to delete will perform task
     *  Deletes files with name = id
     *  returns true if no error
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
