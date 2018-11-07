package kz.kartel.dutyscheduler.components.vacation.model;

import kz.kartel.dutyscheduler.components.user.model.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="vacations")
public class Vacation {
    private Long id;
    private User user;
    private Date date;

    @Id
    @GeneratedValue()
    @Column(name="vacation_id")
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

    @Column(name="date")
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
}
