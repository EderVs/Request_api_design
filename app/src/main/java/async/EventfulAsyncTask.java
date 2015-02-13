package async;

import android.os.AsyncTask;
import android.util.Log;

import com.esperando_la.first_material_design.EventfulContract;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import Model.Event;

/**
 * Created by eder on 12/02/2015.
 */
public class EventfulAsyncTask extends AsyncTask<String, Void, ArrayList<Event>> {

    EventFulListener responseListener;

    public static final String LOG_TAG = EventfulAsyncTask.class.getSimpleName();

    public EventfulAsyncTask(EventFulListener responseListener){
        this.responseListener = responseListener;
    }

    @Override
    protected ArrayList<Event> doInBackground(String... params) {

        if(params == null)
        {
            responseListener.onError();
            return null;
        }

        HttpURLConnection urlConnection = null;
        String eventfulResponse;
        BufferedReader reader = null;
        String Location = params[0];

        try
        {
            //TODO: Quitar Hardcode
            URL urlEventful = new URL(EventfulContract.getSearchEventsUrl(Location));

            //Abrimos la conexión
            urlConnection = (HttpURLConnection) urlEventful.openConnection();
            //Asignamos el método de la url
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            //El buffer que almacena la respuesta
            InputStream inputStream = urlConnection.getInputStream();
            //El objeto que construye la respuesta
            StringBuilder responseBuilder = new StringBuilder();

            if(inputStream == null)
            {
                responseListener.onError();
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null)
            {
                responseBuilder.append(line).append("\n");
            }

            if(responseBuilder.length() == 0)
            {
                responseListener.onError();
                return null;
            }

            eventfulResponse = responseBuilder.toString();
        }
        catch(IOException e)
        {
            responseListener.onError();
            return null;
        }
        finally
        {
            if(urlConnection != null)
            {
                urlConnection.disconnect();
            }

            if(reader != null)
            {
                try
                {
                    reader.close();
                }
                catch (final IOException e)
                {
                    responseListener.onError();
                }
            }
        }

        //TODO: hacer un refactor
        try {
            return EventfulContract.parseEventsFromString(eventfulResponse);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Event> events) {
        super.onPostExecute(events);
        responseListener.onResponseSuccess(events);
    }

    public interface EventFulListener{
        public void onResponseSuccess(ArrayList<Event> events);
        public void onError();
    }
}
