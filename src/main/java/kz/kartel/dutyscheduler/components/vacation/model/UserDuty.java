package kz.kartel.dutyscheduler.components.vacation.model;

import javax.persistence.*;

@Entity
@Table(name="user_duties")
public class UserDuty {
    private Long userId;
    private String firstName;
    private String lastName;
    private String responsibleSystems;
    private String duties;

    @Id
    @Column(name="user_id")
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Column(name="firstname")
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name="lastname")
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name="json_object_agg")
    public String getDuties() {
        return duties;
    }
    public void setDuties(String duties) {
        this.duties = duties;
    }

    public String getResponsibleSystems() {
        return responsibleSystems;
    }
    public void setResponsibleSystems(String responsibleSystems) {
        this.responsibleSystems = responsibleSystems;
    }
}
