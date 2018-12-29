package kz.kartel.dutyscheduler.components.comment;

import java.util.Date;

public class GetCommentForm {

    private Long userId;
    private Long calId;
    private Date date;

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
}
