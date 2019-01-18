package kz.kartel.dutyscheduler.components.duty.forms;

import java.util.List;

public class DutyUser {
    private Integer id;
    private String name;
    private String responsibleSystems;
    private List<DutyInfo> duties;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public List<DutyInfo> getDuties() {
        return duties;
    }
    public void setDuties(List<DutyInfo> duties) {
        this.duties = duties;
    }

    public String getResponsibleSystems() {
        return responsibleSystems;
    }
    public void setResponsibleSystems(String responsibleSystems) {
        this.responsibleSystems = responsibleSystems;
    }
}
