package kz.kartel.dutyscheduler.components.duty.repository;

import kz.kartel.dutyscheduler.components.duty.model.CalendarAccess;
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
public interface CalendarAccessRepository extends JpaRepository<CalendarAccess, Long> {

    @Query("select ca from CalendarAccess ca join ca.user u where u.email = :userEmail and ca.calendar.id = :calId")
    public CalendarAccess getAccessBy(@Param("userEmail") String email, @Param("calId") Long calId);
}
