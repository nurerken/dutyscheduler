package kz.kartel.dutyscheduler.components.calendar.model;


import javax.persistence.*;

@Entity
@Table(name="calendar")
public class Calendar {
    private Long id;
    private String name;

    @Id
    @GeneratedValue
    @Column(name="cal_id")
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @Column(name="name")
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
