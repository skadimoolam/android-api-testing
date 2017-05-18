package dev.adi.testapp;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class HttpActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    private TextView tvRunOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http);

        tvRunOutput = (TextView) findViewById(R.id.tv_run_output);
        tvRunOutput.setText("");
    }

    public void runClickHandler(View view) {
//        tvRunOutput.append("Button clicked\n");

//        MyAsyncTask task = new MyAsyncTask();
//        task.execute("String 1", "String 2", "String 2");

        getSupportLoaderManager().restartLoader( 0, null, this).forceLoad();
    }

    public void clearClickHandler(View view) {
        tvRunOutput.setText("");
    }




    private static class MyTaskLoader extends AsyncTaskLoader<String> {

        public MyTaskLoader(Context context) {
            super(context);
        }

        @Override
        public String loadInBackground() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return "from the loader";
        }

        @Override
        public void deliverResult(String data) {
            data += ", deliverd";
            super.deliverResult(data);
        }
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        tvRunOutput.append("creating loader\n");
        return new MyTaskLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        tvRunOutput.append("loader done, returned: " + data + "\n");
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }



    private class MyAsyncTask extends AsyncTask<String, String, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            for (String string : strings) {
                publishProgress(string);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            tvRunOutput.append(values[0] + "\n");
        }
    }
}
