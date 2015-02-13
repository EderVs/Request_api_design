package fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.esperando_la.first_material_design.EventfulContract;
import com.esperando_la.first_material_design.R;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import Model.Event;
import async.EventfulAsyncTask;
import retrofit.Callback;
import retrofit.EventfulClient;
import retrofit.EventsRequestModel;
import retrofit.RetrofitError;
import volley.VolleyClient;

/**
 * Created by eder on 12/02/2015.
 */
public class MainFragment extends Fragment
        implements EventfulAsyncTask.EventFulListener,
        View.OnClickListener {
    private EditText mTextLocation;

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_actvity, container, false);
        mTextLocation = (EditText) rootView.findViewById(R.id.EditText_where);
        Button btnAsync = (Button) rootView.findViewById(R.id.btn_asynctask);
        Button btnVolley = (Button) rootView.findViewById(R.id.btn_volley);
        Button btnRetrofit= (Button) rootView.findViewById(R.id.btn_retrofit);

        btnVolley.setOnClickListener(this);
        btnAsync.setOnClickListener(this);
        btnRetrofit.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onResponseSuccess(ArrayList<Event> events) {
        //Que hacer cuando la peticion se haya completado
        for (int i = 0; i < events.size(); i++) {
            Log.i("Event", events.get(i).getTitle());
        }
    }

    @Override
    public void onError() {
        //Que hacer en caso de error
    }

    @Override
    public void onClick(View viewClicked) {
        switch (viewClicked.getId()) {
            case R.id.btn_asynctask:

                executeRequestByAsyncTask();
                break;
            case R.id.btn_volley:
                executeRequestWithVolley();
                break;
            case R.id.btn_retrofit:
                executeRequestWithRetrofit();
                break;
        }
    }

    private void executeRequestWithRetrofit() {

        String location = mTextLocation.getText().toString();
        if (location.isEmpty()) {
            //TODO: Mostrar un mensaje al usuario de que no ha ingresado nada
            return;
        }

        EventfulClient retrofitClient = new EventfulClient();

        retrofitClient.getApiContract().findEvents(EventfulContract.APP_KEY , EventfulContract.VALUE_THIS_WEEK , location,
                new Callback<EventsRequestModel.EventsModelResponse>() {
                    @Override
                    public void success(EventsRequestModel.EventsModelResponse eventsModelResponse,
                                        retrofit.client.Response response) {
                        List<EventsRequestModel.EventGson> listEvents = eventsModelResponse.getListEvents();

                        for (int i = 0; i < listEvents.size(); i++) {
                            Log.i("Event: ", listEvents.get(i).getTitle());

                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
    }

    private void executeRequestWithVolley() {
        String location = mTextLocation.getText().toString();
        if (location.isEmpty()) {
            //TODO: Mostrar un mensaje al usuario de que no ha ingresado nada
            return;
        }
        StringRequest eventfulRequest = new StringRequest(Request.Method.GET,
                EventfulContract.getSearchEventsUrl(location),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            ArrayList<Event> eventList = EventfulContract.parseEventsFromString(response);
                            onResponseSuccess(eventList);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        VolleyClient.getInstance(getActivity())
                .addToRequestQueue(eventfulRequest);
    }

    private void executeRequestByAsyncTask() {
        if (!mTextLocation.getText().toString().isEmpty()) {
            new EventfulAsyncTask(this).execute(mTextLocation.getText().toString());
        }
    }
}
