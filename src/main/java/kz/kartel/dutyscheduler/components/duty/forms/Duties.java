package kz.kartel.dutyscheduler.components.duty.forms;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

class Duty {
    @JsonProperty("duty_id")
    private Integer dutyId;

    @JsonProperty("duty_type")
    private Integer dutyType;

    @JsonProperty("duty_date")
    private String dutyDate;

    @JsonProperty("comments")
    private String comments;

    public Integer getDutyId() {
        return dutyId;
    }

    public void setDutyId(Integer dutyId) {
        this.dutyId = dutyId;
    }

    public Integer getDutyType() {
        return dutyType;
    }

    public void setDutyType(Integer dutyType) {
        this.dutyType = dutyType;
    }

    public String getDutyDate() {
        return dutyDate;
    }

    public void setDutyDate(String dutyDate) {
        this.dutyDate = dutyDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}

public class Duties{
    @JsonProperty("duties")
    private List<Duty> duties;

    public List<Duty> getDuties() {
        return duties;
    }
    public void setDuties(List<Duty> duties) {
        this.duties = duties;
    }
}
