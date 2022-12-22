package courses.model;

import java.time.LocalTime;

public class LinesLogFile {

    private final LocalTime time;
    private final String activity;

    public LinesLogFile(LocalTime time, String activity) {
        this.time = time;
        this.activity = activity;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getActivity() {
        return activity;
    }
}
