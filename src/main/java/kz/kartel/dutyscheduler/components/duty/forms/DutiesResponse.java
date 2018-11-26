package kz.kartel.dutyscheduler.components.duty.forms;

import kz.kartel.dutyscheduler.components.vacation.model.UserDuty;

import java.util.List;

public class DutiesResponse {
    private Long calendarId;
    private String calendarName;
    private List<UserDuty> userDuties;

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

    public List<UserDuty> getUserDuties() {
        return userDuties;
    }
    public void setUserDuties(List<UserDuty> userDuties) {
        this.userDuties = userDuties;
    }
}