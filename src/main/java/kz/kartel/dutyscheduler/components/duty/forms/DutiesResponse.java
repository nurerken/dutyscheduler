package kz.kartel.dutyscheduler.components.duty.forms;

import java.util.List;
import kz.kartel.dutyscheduler.components.calendar.model.Calendar;
import kz.kartel.dutyscheduler.components.special_date.DutyStatistics;

public class DutiesResponse {

    private List<Week> weeks;
    private List<Calendar> calendars;
    private List<DutyStatistics> dutyStatistics;

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

    public List<DutyStatistics> getDutyStatistics() {
        return dutyStatistics;
    }
    public void setDutyStatistics(List<DutyStatistics> dutyStatistics) {
        this.dutyStatistics = dutyStatistics;
    }
}
