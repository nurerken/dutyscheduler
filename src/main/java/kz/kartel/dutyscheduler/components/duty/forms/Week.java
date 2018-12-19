package kz.kartel.dutyscheduler.components.duty.forms;

import java.util.List;

public class Week {
    private List<String> dates;
    private List<DutyUser> users;

    public List<String> getDates() {
        return dates;
    }
    public void setDates(List<String> dates) {
        this.dates = dates;
    }

    public List<DutyUser> getUsers() {
        return users;
    }
    public void setUsers(List<DutyUser> users) {
        this.users = users;
    }
}
