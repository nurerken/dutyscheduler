package kz.kartel.dutyscheduler.components.special_date;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="special_dates")
public class SpecialDate {
    private Integer id;
    private Date date;
    private Integer type;

    @Id
    @GeneratedValue
    @Column(name="id")
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name="date")
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    @Column(name="type")
    public Integer getType() {
        return type;
    }
    public void setType(Integer type) {
        this.type = type;
    }
}
