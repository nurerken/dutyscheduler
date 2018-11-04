package kz.kartel.dutyscheduler.components.vacation.repository;

import kz.kartel.dutyscheduler.components.vacation.model.UserVacation;
import kz.kartel.dutyscheduler.components.vacation.model.Vacation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacationRepository extends JpaRepository<Vacation, Long>, JpaSpecificationExecutor<Vacation> {

    @Query("select uv from UserVacation uv")
    public List<UserVacation> getUserVacations();
}
