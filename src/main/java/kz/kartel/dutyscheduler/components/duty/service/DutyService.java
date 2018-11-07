package kz.kartel.dutyscheduler.components.duty.service;

import kz.kartel.dutyscheduler.components.duty.model.Duty;
import kz.kartel.dutyscheduler.components.duty.repository.DutyRepository;
import kz.kartel.dutyscheduler.components.vacation.model.UserDuty;
import kz.kartel.dutyscheduler.components.vacation.repository.VacationRepository;
import kz.kartel.dutyscheduler.components.vacation.service.VacationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DutyService {

    @Autowired
    private DutyRepository dutyRepository;

    @Autowired
    private VacationRepository vacationRepository;

    @Autowired
    private VacationService vacationService;

    public List<UserDuty> getUsersDutiesAll(){
        return dutyRepository.getUserDuties();
    }

    public List<UserDuty> getUsersDutiesByDate(String param1){
        return dutyRepository.getUserDutiesByDate(param1);
    }

    public boolean isUserOnDuty(Integer userId, Date date){
        return dutyRepository.getDutyUserId(userId, date) != null;
    }

    public void saveDuty(){

    }
}
