package kz.kartel.dutyscheduler.components.duty.forms;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class CreateCommentForm {

    @NotNull(message = "dutyId is null")
    private Long dutyId;

    @NotNull(message = "text is null")
    private String text;

    private Date date;

    public Long getDutyId() {
        return dutyId;
    }
    public void setDutyId(Long dutyId) {
        this.dutyId = dutyId;
    }

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
}
