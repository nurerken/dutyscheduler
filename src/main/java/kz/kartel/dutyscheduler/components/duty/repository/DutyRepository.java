package kz.kartel.dutyscheduler.components.duty.repository;

import kz.kartel.dutyscheduler.components.duty.model.Duty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface DutyRepository extends JpaRepository<Duty, Long>, JpaSpecificationExecutor<Duty> {

}
