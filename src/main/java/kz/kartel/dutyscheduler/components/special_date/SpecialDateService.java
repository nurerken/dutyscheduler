package kz.kartel.dutyscheduler.components.special_date;

import kz.kartel.dutyscheduler.components.duty.model.Duty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SpecialDateService {

    @Autowired
    SpecialDateRepository specialDateRepository;

    public List<Date> getAll(){
        return specialDateRepository.getAll();
    }

    public List<Date> getSpecialDates(Date date1, Date date2, Integer type){
        return specialDateRepository.getSpecialDates(date1, date2, type);
    }

    public List<DutyStatistics> getDutyStatistics(Date date1, Date date2, Long calId){
        List<Object[]> objects = specialDateRepository.getDutyStatistics(date1, date2, calId.intValue());

        List<DutyStatistics> dutyStatisticsList = new ArrayList<>();
        for(Object[] object : objects){
            DutyStatistics dutyStatistics = new DutyStatistics();
            dutyStatistics.setUserId(Integer.parseInt(object[0] + ""));
            dutyStatistics.setFullName((String)object[1]);
            dutyStatistics.setDutiesCnt(Integer.parseInt(object[2] + ""));
            dutyStatistics.setHolidayDutiesCnt(Integer.parseInt(object[3] + ""));
            dutyStatisticsList.add(dutyStatistics);
        }

        return dutyStatisticsList;
    }
}
