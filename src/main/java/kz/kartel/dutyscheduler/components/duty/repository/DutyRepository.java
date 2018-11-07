package kz.kartel.dutyscheduler.components.duty.repository;

import kz.kartel.dutyscheduler.components.duty.model.Duty;
import kz.kartel.dutyscheduler.components.vacation.model.UserDuty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface DutyRepository extends JpaRepository<Duty, Long>, JpaSpecificationExecutor<Duty> {

    @Query("select ud from UserDuty ud")
    public List<UserDuty> getUserDuties();

    @Procedure(name="user_duty_by_date")
    public List<UserDuty> getUserDutiesByDate(@Param("inParam1") String inParam1);

    @Query("select ud.id from Duty ud where ud.id = :userId and ud.date = :date")
    public Integer getDutyUserId(@Param("userId") Integer userId, @Param("date") Date date);
}
