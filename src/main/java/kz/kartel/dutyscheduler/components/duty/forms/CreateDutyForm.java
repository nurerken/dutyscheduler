package kz.kartel.dutyscheduler.components.duty.forms;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class CreateDutyForm {

    private Long dutyId;
    @NotNull(message = "UserId is null")
    private Long userId;
    @NotNull(message = "Date is null")
    private Date date;
    @NotNull(message = "DutyType is null")
    private Integer dutyType;
    @NotNull(message = "CalId is null")
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
