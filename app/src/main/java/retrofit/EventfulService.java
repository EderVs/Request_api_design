package retrofit;

import com.esperando_la.first_material_design.EventfulContract;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by eder on 13/02/2015.
 */
public interface EventfulService {
    @GET(EventfulContract.URL_EVENTS_SEARCH)
    public void findEvents(@Query(EventfulContract.PARAM_APP_KEY) String appKey,
                           @Query(EventfulContract.PARAM_DATE) String date,
                           @Query(EventfulContract.PARAM_LOCATION) String location,
                           retrofit.Callback<EventsRequestModel.EventsModelResponse> eventsCallback);

    }
