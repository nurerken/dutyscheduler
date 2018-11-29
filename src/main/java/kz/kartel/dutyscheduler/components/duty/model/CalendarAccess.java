package kz.kartel.dutyscheduler.components.duty.model;


import kz.kartel.dutyscheduler.components.calendar.model.Calendar;
import kz.kartel.dutyscheduler.components.user.model.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="cal_access")
public class CalendarAccess {
    private Long id;
    private Calendar calendar;
    private User user;
    private Integer roleId;

    @Id
    @GeneratedValue
    @Column(name="access_id")
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

    @Column(name="role_id")
    public Integer getRoleId() {
        return roleId;
    }
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cal_id")
    public Calendar getCalendar() {
        return calendar;
    }
    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }
}
