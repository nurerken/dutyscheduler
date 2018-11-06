package kz.kartel.dutyscheduler.components.vacation.service;

import kz.kartel.dutyscheduler.components.vacation.model.UserVacation;
import kz.kartel.dutyscheduler.components.vacation.model.Vacation;
import kz.kartel.dutyscheduler.components.vacation.repository.VacationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VacationService {

    @Autowired
    private VacationRepository vacationRepository;

    public List<Vacation> getAllVacations(){
        return vacationRepository.findAll();
    }

    public List<UserVacation> getAllUserVacations(){
        return vacationRepository.getUserVacations();
    }

    public List<UserVacation> getUsersVacations(String param1){
        return vacationRepository.getUserVacationsByDate(param1);
    }
}
