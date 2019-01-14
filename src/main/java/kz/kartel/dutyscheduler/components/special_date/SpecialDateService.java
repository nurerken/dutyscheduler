package kz.kartel.dutyscheduler.components.special_date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
