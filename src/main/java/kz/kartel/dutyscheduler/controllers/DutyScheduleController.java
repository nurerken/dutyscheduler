package kz.kartel.dutyscheduler.controllers;

import kz.kartel.dutyscheduler.components.user.service.UserService;
import kz.kartel.dutyscheduler.components.vacation.model.Vacation;
import kz.kartel.dutyscheduler.components.vacation.service.VacationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class DutyScheduleController {

    @Autowired
    private UserService userService;

    @Autowired
    private VacationService vacationService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity users() {
        List users = userService.getAllUsers();
        return new ResponseEntity(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/vacations", method = RequestMethod.GET)
    public ResponseEntity vacations() {
        List userVacations = vacationService.getAllUserVacations();
        return new ResponseEntity(userVacations, HttpStatus.OK);
    }
}

/**
 *
 select u.user_id, u.firstname, u.lastname,
 json_agg(json_build_object('date',d.duty_date, 'duty_type', d.duty_type)) filter (where d.duty_date is not null and d.duty_date > '04-11-2018'),
 array_agg(distinct v.date) filter (where v.date is not null) as vacation
 from users u
 left join duties d on u.user_id = d.user_id
 left join vacations v on u.user_id = v.user_id
 group by u.user_id


 select u.user_id, u.firstname, u.lastname,
 json_object_agg(distinct d.duty_date, d.duty_type) filter (where d.duty_date is not null and d.duty_date > '04-11-2018'),
 array_agg(distinct v.date) filter (where v.date is not null) as vacation
 from users u
 left join duties d on u.user_id = d.user_id
 left join vacations v on u.user_id = v.user_id
 group by u.user_id

 **/