package retrofit;

import com.esperando_la.first_material_design.EventfulContract;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by eder on 13/02/2015.
 */
public class EventsRequestModel {

    public class EventsModelResponse {
        @SerializedName(EventfulContract.JSON_KEY_EVENTS)
        EventsMainSection eventsMainSection;

        public List<EventGson> getListEvents (){
            return eventsMainSection.eventsPackage.listEvents;
        }
    }

    private class EventsMainSection {
        @SerializedName(EventfulContract.JSON_KEY_PACKAGE_EVENTS)
        EventsPackage eventsPackage;
    }

    private class EventsPackage {
        @SerializedName(EventfulContract.JSON_KEY_EVENTS)
        List<EventGson> listEvents;
    }

    public class EventGson {
        @SerializedName(EventfulContract.JSON_KEY_TITLE_EVENT)
        String title;

        @SerializedName(EventfulContract.JSON_KEY_DATE_EVENT)
        String date;

        @SerializedName(EventfulContract.JSON_KEY_DESCRIPTION_EVENT)
        String description;

        public String getTitle() {
            return title;
        }

        public String getDate() {
            return date;
        }

        public String getDescription() {
            return description;
        }
    }
}
