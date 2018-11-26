package kz.kartel.dutyscheduler.components.duty.forms;

import java.util.Date;

public class CreateDutyForm {

    private Long dutyId;
    private Long userId;
    private Date date;
    private Integer dutyType;
    private Long calId;

    public Long getDutyId() {
        return dutyId;
    }
    public void setDutyId(Long dutyId) {
        this.dutyId = dutyId;
    }

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getDutyType() {
        return dutyType;
    }
    public void setDutyType(Integer dutyType) {
        this.dutyType = dutyType;
    }

    public Long getCalId() {
        return calId;
    }
    public void setCalId(Long calId) {
        this.calId = calId;
    }
}
