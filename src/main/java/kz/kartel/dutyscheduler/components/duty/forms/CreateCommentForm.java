package kz.kartel.dutyscheduler.components.duty.forms;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class CreateCommentForm {

    private Long userId;
    private Long calId;
    private Date date;
    private String text;

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCalId() {
        return calId;
    }
    public void setCalId(Long calId) {
        this.calId = calId;
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
