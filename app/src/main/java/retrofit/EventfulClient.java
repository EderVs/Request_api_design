package retrofit;

import com.esperando_la.first_material_design.EventfulContract;

/**
 * Created by eder on 13/02/2015.
 */
public class EventfulClient {

    private EventfulService apiContract;

    public EventfulClient() {

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.BASIC)
                .setEndpoint(EventfulContract.BASE_URL)
                .build();

        apiContract = restAdapter.create(EventfulService.class);
    }

    public EventfulService getApiContract() {
        return apiContract;
    }
}
