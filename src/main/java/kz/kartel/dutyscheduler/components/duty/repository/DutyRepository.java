package kz.kartel.dutyscheduler.components.duty.repository;

import kz.kartel.dutyscheduler.components.duty.model.Duty;
import kz.kartel.dutyscheduler.components.vacation.model.UserDuty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DutyRepository extends JpaRepository<Duty, Long>, JpaSpecificationExecutor<Duty> {

    @Query("select uv from UserDuty uv")
    public List<UserDuty> getUserDuties();

    @Procedure(name="user_duty_by_date")
    public List<UserDuty> getUserDutiesByDate(@Param("inParam1") String inParam1);
}
