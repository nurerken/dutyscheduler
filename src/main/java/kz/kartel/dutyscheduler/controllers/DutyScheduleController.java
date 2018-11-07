package kz.kartel.dutyscheduler.controllers;

import kz.kartel.dutyscheduler.components.duty.model.Duty;
import kz.kartel.dutyscheduler.components.duty.service.DutyService;
import kz.kartel.dutyscheduler.components.user.service.UserService;
import kz.kartel.dutyscheduler.components.vacation.model.Vacation;
import kz.kartel.dutyscheduler.components.vacation.service.VacationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Date;
import java.util.List;

@Controller
public class DutyScheduleController {

    @Autowired
    private UserService userService;

    @Autowired
    private VacationService vacationService;

    @Autowired
    private DutyService dutyService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity users() {
        List users = userService.getAllUsers();
        return new ResponseEntity(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/duties", method = RequestMethod.GET)
    public ResponseEntity duties() {
        List usersDutiesAll = dutyService.getUsersDutiesAll();
        return new ResponseEntity(usersDutiesAll, HttpStatus.OK);
    }

    @RequestMapping(value = "/duties2", method = RequestMethod.GET)
    public ResponseEntity duties2() {
        List usersDutiesAll = dutyService.getUsersDutiesByDate("date1");
        return new ResponseEntity(usersDutiesAll, HttpStatus.OK);
    }

    @RequestMapping(value = "/duty", method = RequestMethod.POST)
    public ResponseEntity<?> createDuty(@RequestBody Duty duty) {
        Integer userId = null;
        Date date = null;

        if(userService.getAllUsers()){
            return new ResponseEntity(new String("Error. A User with id " + userId + " doesn't exist."), HttpStatus.CONFLICT);
        }
        else if (dutyService.isUserOnDuty(userId, date)) {
            return new ResponseEntity(new String("Unable to create. A User with name " + user.getName() + " already exist."), HttpStatus.CONFLICT);
        }
        else if(vacationService.isUserOnVacation(userId, date)){
            return new ResponseEntity(new String("Unable to create. A User with name " + user.getName() + " already exist."), HttpStatus.CONFLICT);
        }

        dutyService.saveD(user);
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
}
