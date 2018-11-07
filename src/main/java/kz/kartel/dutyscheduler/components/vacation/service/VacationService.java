package kz.kartel.dutyscheduler.components.vacation.service;

import kz.kartel.dutyscheduler.components.vacation.model.Vacation;
import kz.kartel.dutyscheduler.components.vacation.repository.VacationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class VacationService {

    @Autowired
    private VacationRepository vacationRepository;

    public boolean isUserOnVacation(Long userId, Date date){
        return vacationRepository.getOnVacationUserId(userId, date) != null;
    }
}
