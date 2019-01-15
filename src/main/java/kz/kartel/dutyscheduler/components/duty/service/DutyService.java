package kz.kartel.dutyscheduler.components.duty.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.kartel.dutyscheduler.components.calendar.model.Calendar;
import kz.kartel.dutyscheduler.components.calendar.service.CalendarService;
import kz.kartel.dutyscheduler.components.duty.forms.*;
import kz.kartel.dutyscheduler.components.duty.model.Duty;
import kz.kartel.dutyscheduler.components.duty.repository.DutyRepository;
import kz.kartel.dutyscheduler.components.special_date.SpecialDate;
import kz.kartel.dutyscheduler.components.special_date.SpecialDateService;
import kz.kartel.dutyscheduler.components.user.repository.UserRepository;
import kz.kartel.dutyscheduler.components.user.service.UserService;
import kz.kartel.dutyscheduler.components.vacation.model.UserDuty;
import kz.kartel.dutyscheduler.components.vacation.repository.VacationRepository;
import kz.kartel.dutyscheduler.components.vacation.service.VacationService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DutyService {

    @Autowired
    private DutyRepository dutyRepository;

    @Autowired
    private CalendarService calendarService;

    @Autowired
    CalendarAccessService calendarAccessService;

    @Autowired
    UserService userService;

    @Autowired
    SpecialDateService specialDateService;

    public Duty getById(Long id){
        return dutyRepository.getDutyById(id);
    }

    public List<UserDuty> getUsersDutiesAll(){
        return dutyRepository.getUserDuties();
    }

    public List<UserDuty> getUsersDutiesByDate(Date date1, Date date2, Long calId){
        List<Object[]> objects = dutyRepository.getUserDutiesByDate(date1, date2, calId.intValue());

        List<UserDuty> duties = new ArrayList<>();
        for(Object[] object : objects){
            UserDuty userDuty = new UserDuty();
            userDuty.setUserId(Long.parseLong(object[0] + ""));
            userDuty.setFirstName((String)object[1]);
            userDuty.setLastName((String)object[2]);
            userDuty.setDuties((String)object[3]);
            duties.add(userDuty);
        }
        return duties;
    }

    public DutiesResponse getDutiesResponse(Date date1, Date date2, Long calId){

        DutiesResponse dutiesResponse = new DutiesResponse();

        List<Calendar> calendars = calendarService.getCalendarsAll();
        dutiesResponse.setCalendars(calendars);

        List<UserDuty> usesDuties = getUsersDutiesByDate(date1, date2, calId);
        dutiesResponse.setWeeks(getWeeks(date1, date2, usesDuties, calId.intValue()));

        dutiesResponse.setDutyStatistics(specialDateService.getDutyStatistics(date1, date2, calId));
        return dutiesResponse;
    }

    public List<Week> getWeeks(Date date1, Date date2, List<UserDuty> userDuties, Integer calId){
        List<Week> weeks = new ArrayList<>();

        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTime(date1);

        List<Date> holidayDates = specialDateService.getSpecialDates(date1, date2, 1);
        List<Date> workingWeekends = specialDateService.getSpecialDates(date1, date2, 2);

        int cnt = 0;
        Week week = null;
        while(date1.compareTo(date2) <= 0){
            if(cnt % 7 == 0){
                week = new Week();
                week.setDates(new ArrayList<>());

                List<DutyUser> dutyUsers = new ArrayList<>();
                for (UserDuty userDuty : userDuties){
                    DutyUser dutyUser = new DutyUser();
                    dutyUser.setId(userDuty.getUserId().intValue());
                    dutyUser.setName(userDuty.getLastName() + " " + userDuty.getFirstName());
                    //dutyUser.setEmail();
                    dutyUser.setDuties(new ArrayList<>());
                    dutyUsers.add(dutyUser);
                }
                week.setUsers(dutyUsers);
            }

            try {
                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                String strDate1 = dateFormat.format(date1);
                week.getDates().add(strDate1);

                for (DutyUser dutyUser : week.getUsers()) {
                    for (UserDuty userDuty : userDuties) {

                        if(userDuty.getUserId().intValue()== dutyUser.getId().intValue()){

                            DutyInfo dutyInfo = new DutyInfo();
                            dutyInfo.setDate(strDate1);
                            dutyInfo.setUserId(dutyUser.getId());
                            dutyInfo.setCalId(calId);

                            java.util.Calendar c1 = java.util.Calendar.getInstance();
                            c1.setTime(date1);
                            if (c1.get(java.util.Calendar.DAY_OF_WEEK) == java.util.Calendar.SATURDAY || c1.get(java.util.Calendar.DAY_OF_WEEK) == java.util.Calendar.SUNDAY) {
                                dutyInfo.setDayType(1);

                                for (Date date: workingWeekends){
                                    if(DateUtils.isSameDay(date1, date)) {
                                        dutyInfo.setDayType(2);
                                        break;
                                    }
                                }
                            }
                            else {
                                dutyInfo.setDayType(2);

                                for (Date date: holidayDates){
                                    if(DateUtils.isSameDay(date1, date)){
                                        dutyInfo.setDayType(1);
                                        break;
                                    }
                                }
                            }

                            String dutiesString = userDuty.getDuties();

                            boolean isDuty = false;
                            if(dutiesString != null && dutiesString.length() > 0){
                                dutiesString = dutiesString.substring(1, dutiesString.length()-1);
                                String dutyInfos[] = dutiesString.split(",");

                                dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                String strDate = dateFormat.format(date1);

                                for (int i = 0; i < dutyInfos.length; i++){
                                    String dutyData[] = dutyInfos[i].split("\\|");
                                    String date = dutyData[2].split(":")[1].replace("\"", "");
                                    if(strDate.equals(date)){
                                        String dId = dutyData[0].split(" : ")[0].replace("\"", "").split(":")[1];
                                        dutyInfo.setId(Integer.parseInt(dId));

                                        String dType = dutyData[0].split(" : ")[1].replace("\"", "").split(":")[1];
                                        dutyInfo.setType(Integer.parseInt(dType));

                                        dutyInfo.setComments(dutyData[2].split(":")[1]);
                                        isDuty = true;
                                        break;
                                    }
                                }
                            }

                            if(!isDuty){
                                dutyInfo.setType(-1);
                                dutyInfo.setId(null);
                                dutyInfo.setComments(null);
                            }

                            dutyUser.getDuties().add(dutyInfo);
                        }
                    }
                }

            }catch (Exception ex){
                System.out.println(ex);
            }

            calendar.add(java.util.Calendar.DAY_OF_YEAR, 1);
            date1 = calendar.getTime();
            cnt++;

            if(cnt % 7 == 0){
                weeks.add(week);
            }
        }

        return weeks;
    }

    public Duty getDuty(Long userId, Date date, Long calId){
        Duty duty = dutyRepository.getOnDutyUserId(userId, date, calId);
        return duty;
    }

    public boolean isUserOnDuty(Long userId, Date date, Long calId){
        Duty duty = dutyRepository.getOnDutyUserId(userId, date, calId);
        return duty != null && !duty.getDutyType().equals(0);
    }

    public boolean isUserOnVacation(Long userId, Date date, Long calId){
        Duty duty = dutyRepository.getOnDutyUserId(userId, date, calId);
        return duty != null && duty.getDutyType().equals(0);
    }

    public Long saveDuty(CreateDutyForm dutyForm){
        Duty duty = new Duty();
        duty.setUser(userService.getUserById(dutyForm.getUserId()));
        duty.setDate(dutyForm.getDate());
        duty.setDutyType(dutyForm.getDutyType());
        duty.setCalendar(calendarService.getCalendarById(dutyForm.getCalId()));
        dutyRepository.save(duty);
        return duty.getId();
        ///dutyRepository.saveDuty(dutyForm.getUserId(), dutyForm.getDate(), dutyForm.getDutyType(), dutyForm.getCalId());
    }

    public void updateDuty(CreateDutyForm createDutyForm){
        dutyRepository.updateDuty(createDutyForm.getUserId(), createDutyForm.getDate(), createDutyForm.getDutyType(), createDutyForm.getCalId(), createDutyForm.getDutyId());
    }

    public void deleteDuty(Long dutyId){
        dutyRepository.deleteDuty(dutyId);
    }
}
