package kz.kartel.dutyscheduler.components.special_date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SpecialDateRepository extends JpaRepository<SpecialDate, Long> {

    @Query("select sd from SpecialDate sd")
    public List<Date> getAll();

    @Query("select sd.date from SpecialDate sd where sd.date >= :date1 and sd.date <= :date2 and sd.type = :type")
    public List<Date> getSpecialDates(@Param("date1") Date date1, @Param("date2") Date date2, @Param("type") Integer type);
}
