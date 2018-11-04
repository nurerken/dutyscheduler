package kz.kartel.dutyscheduler.components.vacation.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_vacations")
public class UserVacation {
    private Long userId;
    private String firstName;
    private String lastName;
    private String duties;
    private String vacations;

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

    @Column(name="vacation")
    public String getVacations() {
        return vacations;
    }
    public void setVacations(String vacations) {
        this.vacations = vacations;
    }
}
