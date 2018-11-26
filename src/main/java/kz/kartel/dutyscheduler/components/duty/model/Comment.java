package kz.kartel.dutyscheduler.components.duty.model;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="comments")
public class Comment {
    private Long id;
    private Duty duty;
    private String text;
    private Date insertDate;

    @Id
    @GeneratedValue
    @Column(name="com_id")
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="duty_id")
    public Duty getDuty() {
        return duty;
    }
    public void setDuty(Duty duty) {
        this.duty = duty;
    }

    @Column(name = "text")
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    @Column(name = "insert_date")
    public Date getInsertDate() {
        return insertDate;
    }
    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
    }
}
