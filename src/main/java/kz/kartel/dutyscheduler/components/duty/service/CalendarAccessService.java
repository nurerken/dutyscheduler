package kz.kartel.dutyscheduler.components.duty.service;

import kz.kartel.dutyscheduler.components.calendar.model.Calendar;
import kz.kartel.dutyscheduler.components.duty.forms.CreateDutyForm;
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

import java.util.ArrayList;
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
}
