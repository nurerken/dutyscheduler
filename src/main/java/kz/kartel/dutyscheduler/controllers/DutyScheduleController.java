package kz.kartel.dutyscheduler.controllers;

import kz.kartel.dutyscheduler.components.calendar.service.CalendarService;
import kz.kartel.dutyscheduler.components.duty.forms.CreateDutyForm;
import kz.kartel.dutyscheduler.components.duty.forms.DutiesResponse;
import kz.kartel.dutyscheduler.components.duty.service.DutyService;
import kz.kartel.dutyscheduler.components.user.model.User;
import kz.kartel.dutyscheduler.components.user.service.UserService;
import kz.kartel.dutyscheduler.components.vacation.service.VacationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
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

    @Autowired
    private CalendarService calendarService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @RequestMapping(value = "/users/sign-up", method = RequestMethod.POST)
    public ResponseEntity users(@RequestBody User user) {
        if(userService.getUserByEmail(user.getEmail()) != null){
            return new ResponseEntity("User with email: " + user.getEmail() + " already registered.", HttpStatus.CONFLICT);
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
        return new ResponseEntity("userId: " + user.getId(), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/dutiesByDate", method = RequestMethod.GET)
    public ResponseEntity dutiesByDate(@RequestParam("date1") String date1Str, @RequestParam("date2") String date2Str, @RequestParam(name = "calId") Long calId) {

        Date date1 = null, date2 = null;
        try{
            date1=new SimpleDateFormat("MM-dd-yyy").parse(date1Str);
            date2 =new SimpleDateFormat("MM-dd-yyy").parse(date2Str);
        }catch (Exception ex){
        }

        List usesDuties = dutyService.getUsersDutiesByDate(date1, date2, calId);
        DutiesResponse dutiesResponse = new DutiesResponse();
        dutiesResponse.setCalendarId(calId);
        dutiesResponse.setCalendarName(calendarService.getCalendarById(calId).getName());
        dutiesResponse.setUserDuties(usesDuties);

        return new ResponseEntity(dutiesResponse, HttpStatus.OK);
    }


    @RequestMapping(value = "/duty", method = RequestMethod.POST)
    public ResponseEntity<?> createDuty(@RequestBody CreateDutyForm createDutyForm) {

        if(userService.getUserById(createDutyForm.getUserId()) == null){
            return new ResponseEntity(new String("Error. A User with id " + createDutyForm.getUserId() + " doesn't exist."), HttpStatus.NOT_ACCEPTABLE);
        }
        else if (dutyService.isUserOnDuty(createDutyForm.getUserId(), createDutyForm.getDate(), createDutyForm.getCalId())) {
            return new ResponseEntity(new String("Error. A User with id " + createDutyForm.getUserId() + " is already on duty given day with calendar id:" + createDutyForm.getCalId()), HttpStatus.CONFLICT);
        }
        else if(vacationService.isUserOnVacation(createDutyForm.getUserId(), createDutyForm.getDate())){
            return new ResponseEntity(new String("Error. A User with id " + createDutyForm.getUserId() + " is on vacation given day."), HttpStatus.CONFLICT);
        }

        dutyService.saveDuty(createDutyForm);
        return new ResponseEntity<>("OK. Created.", HttpStatus.CREATED);
    }

    @RequestMapping(value = "/duty/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteDuty(@PathVariable("id") long dutyId) {

        if(dutyService.getById(dutyId) == null){
            return new ResponseEntity(new String("Error. Duty with id " + dutyId + " doesn't exist."), HttpStatus.NOT_FOUND);
        }

        dutyService.deleteDuty(dutyId);
        return new ResponseEntity<>("OK. Deleted.", HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/duty/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateDuty(@PathVariable("id") long dutyId, @RequestBody CreateDutyForm createDutyForm) {

        if(dutyService.getById(dutyId) == null){
            return new ResponseEntity(new String("Error. Duty with id " + createDutyForm.getDutyId() + " doesn't exist."), HttpStatus.NOT_FOUND);
        }
        /*else if (dutyService.isUserOnDuty(createDutyForm.getUserId(), createDutyForm.getDate())) {
            return new ResponseEntity(new String("Error. A User with id " + createDutyForm.getUserId() + " is already on duty given day."), HttpStatus.CONFLICT);
        }*/
        else if(vacationService.isUserOnVacation(createDutyForm.getUserId(), createDutyForm.getDate())){
            return new ResponseEntity(new String("Error. A User with id " + createDutyForm.getUserId() + " is on vacation given day."), HttpStatus.CONFLICT);
        }

        dutyService.updateDuty(createDutyForm);
        return new ResponseEntity<>("OK. Updated.", HttpStatus.OK);
    }
}
