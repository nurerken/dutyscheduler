package kz.kartel.dutyscheduler.components.duty.service;

import kz.kartel.dutyscheduler.components.duty.model.Duty;
import kz.kartel.dutyscheduler.components.duty.repository.DutyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DutyService {

    @Autowired
    private DutyRepository dutyRepository;

    public List<Duty> getAllUsers(){
        return dutyRepository.findAll();
    }
}
