package kz.kartel.dutyscheduler.components.special_date;

public class DutyStatistics {

    private Integer userId;
    private String  fullName;
    private Integer dutiesCnt;
    private Integer holidayDutiesCnt;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getDutiesCnt() {
        return dutiesCnt;
    }

    public void setDutiesCnt(Integer dutiesCnt) {
        this.dutiesCnt = dutiesCnt;
    }

    public Integer getHolidayDutiesCnt() {
        return holidayDutiesCnt;
    }

    public void setHolidayDutiesCnt(Integer holidayDutiesCnt) {
        this.holidayDutiesCnt = holidayDutiesCnt;
    }
}
