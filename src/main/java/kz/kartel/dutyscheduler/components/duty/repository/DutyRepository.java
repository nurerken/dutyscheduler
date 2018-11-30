package kz.kartel.dutyscheduler.components.duty.repository;

import kz.kartel.dutyscheduler.components.duty.model.Duty;
import kz.kartel.dutyscheduler.components.vacation.model.UserDuty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;


@Repository
public interface DutyRepository extends JpaRepository<Duty, Long>, JpaSpecificationExecutor<Duty> {

    @Query("select ud from UserDuty ud")
    public List<UserDuty> getUserDuties();

    @Query("select d from Duty d where d.id = :id")
    public Duty getDutyById(@Param("id") Long id);

    @Query("select ud from Duty ud where ud.user.id= :userId and ud.date = :date and ud.calendar.id = :calId")
    public Duty getOnDutyUserId(@Param("userId") Long userId, @Param("date") Date date, @Param("calId") Long calId);

    @Query(value = "select * from user_duty_by_date(:date1, :date2, :calId)", nativeQuery = true)
    public List<Object[]> getUserDutiesByDate(@Param("date1") Date date1, @Param("date2") Date date2, @Param("calId") Integer calId);

    @Modifying
    @Transactional
    @Query(value="insert into duties (user_id, duty_date, duty_type, cal_id) values (:user_id, :duty_date, :duty_type, :cal_id)", nativeQuery = true)
    public void saveDuty(@Param("user_id") Long userId, @Param("duty_date") Date duty_date, @Param("duty_type") Integer duty_type, @Param("cal_id") Long cal_id);

    @Modifying
    @Transactional
    @Query(value="update Duty set user.id = :userId, date = :date, dutyType = :dutyType, calendar.id = :calId where id = :id")
    public void updateDuty(@Param("userId") Long userId, @Param("date") Date date, @Param("dutyType") Integer dutyType, @Param("calId") Long calId, @Param("id") Long id);

    @Modifying
    @Transactional
    @Query("delete from Duty where id = :id")
    public void deleteDuty(@Param("id") Long dutyId);
}
