package kz.kartel.dutyscheduler.components.duty.service;

import kz.kartel.dutyscheduler.components.duty.forms.CreateDutyForm;
import kz.kartel.dutyscheduler.components.duty.model.Duty;
import kz.kartel.dutyscheduler.components.duty.repository.DutyRepository;
import kz.kartel.dutyscheduler.components.user.repository.UserRepository;
import kz.kartel.dutyscheduler.components.vacation.model.UserDuty;
import kz.kartel.dutyscheduler.components.vacation.repository.VacationRepository;
import kz.kartel.dutyscheduler.components.vacation.service.VacationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DutyService {

    @Autowired
    private DutyRepository dutyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VacationService vacationService;

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
            userDuty.setVacations((String)object[4]);
            duties.add(userDuty);
        }
        return duties;
    }

    public boolean isUserOnDuty(Long userId, Date date, Long calId){
        return dutyRepository.getOnDutyUserId(userId, date, calId.intValue()) != null;
    }

    public void saveDuty(CreateDutyForm dutyForm){
        dutyRepository.saveDuty(dutyForm.getUserId(), dutyForm.getDate(), dutyForm.getDutyType(), dutyForm.getCalId());
    }

    public void updateDuty(CreateDutyForm createDutyForm){
        dutyRepository.updateDuty(createDutyForm.getUserId(), createDutyForm.getDate(), createDutyForm.getDutyType(), createDutyForm.getCalId(), createDutyForm.getDutyId());
    }

    public void deleteDuty(Long dutyId){
        dutyRepository.deleteDuty(dutyId);
    }
}
