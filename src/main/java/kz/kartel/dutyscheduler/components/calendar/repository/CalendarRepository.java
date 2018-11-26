package kz.kartel.dutyscheduler.components.calendar.repository;

import kz.kartel.dutyscheduler.components.calendar.model.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Repository
public interface CalendarRepository extends JpaRepository<Calendar, Long> {

    @Query("select c from Calendar c")
    public List<Calendar> getCalendars();

    @Query("select c from Calendar c where c.id = :id")
    public Calendar getCalendarById(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value="insert into calendar (name) values (:name)", nativeQuery = true)
    public void saveCalendar(@Param("name") String name);

    @Modifying
    @Transactional
    @Query(value="update Calendar set name = :name where id = :id")
    public void updateCalendar(@Param("name") String name, @Param("id") Long id);

    @Modifying
    @Transactional
    @Query("delete from Calendar where id = :id")
    public void deleteCalendar(@Param("id") Long calId);
}
