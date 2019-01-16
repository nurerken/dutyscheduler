package kz.kartel.dutyscheduler.components.duty.service;

import kz.kartel.dutyscheduler.components.duty.forms.CreateDutyForm;
import kz.kartel.dutyscheduler.components.duty.forms.Week;
import kz.kartel.dutyscheduler.components.duty.model.CalendarAccess;
import kz.kartel.dutyscheduler.components.duty.model.Duty;
import kz.kartel.dutyscheduler.components.duty.repository.CalendarAccessRepository;
import kz.kartel.dutyscheduler.components.duty.repository.DutyRepository;
import kz.kartel.dutyscheduler.components.user.repository.UserRepository;
import kz.kartel.dutyscheduler.components.vacation.model.UserDuty;
import kz.kartel.dutyscheduler.components.vacation.service.VacationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class CalendarAccessService {

    @Autowired
    private CalendarAccessRepository calendarAccessRepository;

    public CalendarAccess getAccessBy(String userEmail, Long calendarId){
        return calendarAccessRepository.getAccessBy(userEmail, calendarId);
    }

    public boolean hasWriteAccess(String userEmail, Long calendarId){
        CalendarAccess calendarAccess = getAccessBy(userEmail, calendarId);
        return calendarAccess != null && calendarAccess.getRoleId().equals(1);
    }

    public boolean hasReadAccess(String userEmail, Long calendarId){
        CalendarAccess calendarAccess = getAccessBy(userEmail, calendarId);
        return calendarAccess != null && (calendarAccess.getRoleId().equals(1) || calendarAccess.getRoleId().equals(2));
    }

    public Date getFirstMonday(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        calendar.add(Calendar.DAY_OF_YEAR, -((calendar.get(Calendar.DAY_OF_WEEK) == 1 ? 8 : calendar.get(Calendar.DAY_OF_WEEK)) - 2));
        return calendar.getTime();
    }

    public Date getLastSunday(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

        calendar.add(Calendar.DAY_OF_YEAR, (8 - (calendar.get(Calendar.DAY_OF_WEEK) == 1 ? 8 : calendar.get(Calendar.DAY_OF_WEEK))));
        return calendar.getTime();
    }

    public Date getFirstDateOfMonth(int year, int month){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    public Date getLastDateOfMonth(int year, int month){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }
}
