package kz.kartel.dutyscheduler.components.duty.forms;

public class DutyInfo {
    private Integer id;
    private String date;
    private Integer type;
    private Integer userId;
    private Integer calId;
    private String comments;

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public Integer getType() {
        return type;
    }
    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCalId() {
        return calId;
    }
    public void setCalId(Integer calId) {
        this.calId = calId;
    }
}
