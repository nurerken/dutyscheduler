package kz.kartel.dutyscheduler.components.duty.forms;

import kz.kartel.dutyscheduler.components.vacation.model.UserDuty;

import java.util.List;

public class DutiesResponse {
    private Long calendarId;
    private String calendarName;

    private List<Week> weeks;

    public Long getCalendarId() {
        return calendarId;
    }
    public void setCalendarId(Long calendarId) {
        this.calendarId = calendarId;
    }

    public String getCalendarName() {
        return calendarName;
    }
    public void setCalendarName(String calendarName) {
        this.calendarName = calendarName;
    }

    public List<Week> getWeeks() {
        return weeks;
    }
    public void setWeeks(List<Week> weeks) {
        this.weeks = weeks;
    }
}
