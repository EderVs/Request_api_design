package Model;

/**
 * Created by eder on 12/02/2015.
 */
public class Event {
    String title;
    String date;
    String description;

    public Event(String title, String date, String description){
        this.title = title;
        this.date = date;
        this.description = description;
    }

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
