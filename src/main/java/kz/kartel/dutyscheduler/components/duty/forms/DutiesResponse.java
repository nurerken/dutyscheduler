package kz.kartel.dutyscheduler.components.duty.forms;

import java.util.List;
import kz.kartel.dutyscheduler.components.calendar.model.Calendar;

public class DutiesResponse {

    private List<Week> weeks;
    private List<Calendar> calendars;

    public List<Week> getWeeks() {
        return weeks;
    }
    public void setWeeks(List<Week> weeks) {
        this.weeks = weeks;
    }

    public List<Calendar> getCalendars() {
        return calendars;
    }
    public void setCalendars(List<Calendar> calendars) {
        this.calendars = calendars;
    }
}
