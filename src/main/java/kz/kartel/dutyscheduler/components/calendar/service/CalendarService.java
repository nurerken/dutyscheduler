package kz.kartel.dutyscheduler.components.calendar.service;

import kz.kartel.dutyscheduler.components.calendar.forms.CreateCalendarForm;
import kz.kartel.dutyscheduler.components.calendar.model.Calendar;
import kz.kartel.dutyscheduler.components.calendar.repository.CalendarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CalendarService {

    @Autowired
    private CalendarRepository calendarRepository;

    public Calendar getCalendarById(Long id){
        return calendarRepository.getCalendarById(id);
    }

    public List<Calendar> getCalendarsAll(){
        return calendarRepository.getCalendars();
    }

    public void saveCalendar(CreateCalendarForm createCalendarForm){
        calendarRepository.saveCalendar(createCalendarForm.getName());
    }

    public void updateCalendar(CreateCalendarForm createCalendarForm){
        calendarRepository.updateCalendar(createCalendarForm.getName(), createCalendarForm.getId());
    }

    public void deleteCalenar(Long calId){
        calendarRepository.deleteCalendar(calId);
    }
}
