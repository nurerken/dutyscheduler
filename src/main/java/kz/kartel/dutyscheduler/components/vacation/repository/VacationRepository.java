package kz.kartel.dutyscheduler.components.vacation.repository;

import kz.kartel.dutyscheduler.components.vacation.model.Vacation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface VacationRepository extends JpaRepository<Vacation, Long>, JpaSpecificationExecutor<Vacation> {

    @Query("select v.id from Vacation v where v.id = :userId and ud.date = :date")
    public Integer getOnVacationUserId(@Param("userId") Integer userId, @Param("date") Date date);
}
