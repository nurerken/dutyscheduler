package kz.kartel.dutyscheduler.components.duty.model;

import kz.kartel.dutyscheduler.components.user.model.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="duties")
public class Duty {
    private Long id;
    private User user;
    private Date date;
    private Integer dutyType;

    @Id
    @GeneratedValue
    @Column(name="duty_id")
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    @Column(name="duty_date")
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    @Column(name="duty_type")
    public Integer getDutyType() {
        return dutyType;
    }
    public void setDutyType(Integer dutyType) {
        this.dutyType = dutyType;
    }
}
