package kz.kartel.dutyscheduler.controllers;

import kz.kartel.dutyscheduler.components.calendar.model.Calendar;
import kz.kartel.dutyscheduler.components.calendar.service.CalendarService;
import kz.kartel.dutyscheduler.components.duty.forms.CreateCommentForm;
import kz.kartel.dutyscheduler.components.duty.forms.CreateDutyForm;
import kz.kartel.dutyscheduler.components.duty.forms.DutiesResponse;
import kz.kartel.dutyscheduler.components.duty.model.Comment;
import kz.kartel.dutyscheduler.components.duty.model.Duty;
import kz.kartel.dutyscheduler.components.duty.service.CalendarAccessService;
import kz.kartel.dutyscheduler.components.duty.service.CommentService;
import kz.kartel.dutyscheduler.components.duty.service.DutyService;
import kz.kartel.dutyscheduler.components.user.model.User;
import kz.kartel.dutyscheduler.components.user.service.UserService;
import kz.kartel.dutyscheduler.components.vacation.service.VacationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
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

    @Autowired
    private CalendarAccessService calendarAccessService;

    @Autowired
    private CommentService commentService;

    //////////////////registration//////////////////////////
    @RequestMapping(value = "/users/sign-up", method = RequestMethod.POST)
    public ResponseEntity users(@RequestBody @Valid User user) {
        if(userService.getUserByEmail(user.getEmail()) != null){
            return new ResponseEntity("User with email: " + user.getEmail() + " already registered.", HttpStatus.CONFLICT);
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
        return new ResponseEntity("userId: " + user.getId(), HttpStatus.CREATED);
    }

    ///////////////Duty////////////////////
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
        Calendar calendar = calendarService.getCalendarById(calId);
        dutiesResponse.setCalendarName(calendar != null ? calendar.getName() : "");
        dutiesResponse.setUserDuties(usesDuties);

        return new ResponseEntity(dutiesResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/duty", method = RequestMethod.POST)
    public ResponseEntity<?> createDuty(@RequestBody @Valid CreateDutyForm createDutyForm) {

        String userName = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!calendarAccessService.hasWriteAccess(userName, createDutyForm.getCalId())){
            return new ResponseEntity(new String("Error. You don't have write access to this calendar"), HttpStatus.FORBIDDEN);
        }

        if(userService.getUserById(createDutyForm.getUserId()) == null){
            return new ResponseEntity(new String("Error. A User with id " + createDutyForm.getUserId() + " doesn't exist."), HttpStatus.NOT_ACCEPTABLE);
        }
        else if (dutyService.isUserOnDuty(createDutyForm.getUserId(), createDutyForm.getDate(), createDutyForm.getCalId())) {
            return new ResponseEntity(new String("Error. A User with id " + createDutyForm.getUserId() + " is already on duty given day with calendar id:" + createDutyForm.getCalId()), HttpStatus.CONFLICT);
        }
        else if(dutyService.isUserOnVacation(createDutyForm.getUserId(), createDutyForm.getDate(), createDutyForm.getCalId())){
            return new ResponseEntity(new String("Error. A User with id " + createDutyForm.getUserId() + " is on vacation given day with calendar id:" + createDutyForm.getCalId()), HttpStatus.CONFLICT);
        }

        dutyService.saveDuty(createDutyForm);
        return new ResponseEntity<>("OK. Created.", HttpStatus.CREATED);
    }

    @RequestMapping(value = "/duty/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteDuty(@PathVariable("id") long dutyId) {

        Duty duty = dutyService.getById(dutyId);
        if(duty == null){
            return new ResponseEntity(new String("Error. Duty with id " + dutyId + " doesn't exist."), HttpStatus.NOT_FOUND);
        }

        String userName = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!calendarAccessService.hasWriteAccess(userName, duty.getCalendar().getId())){
            return new ResponseEntity(new String("Error. You don't have write access to this calendar"), HttpStatus.FORBIDDEN);
        }

        dutyService.deleteDuty(dutyId);
        return new ResponseEntity<>("OK. Deleted.", HttpStatus.NO_CONTENT);
    }

    //////////////Comment//////////////////
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public ResponseEntity<?> createDuty(@RequestBody CreateCommentForm createCommentForm) {

        String userName = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Duty duty = dutyService.getById(createCommentForm.getDutyId());
        if(duty == null){
            return new ResponseEntity<>("No duty with id:" + createCommentForm.getDutyId(), HttpStatus.NOT_ACCEPTABLE);
        }

        if(!calendarAccessService.hasWriteAccess(userName, duty.getCalendar().getId())){
            return new ResponseEntity(new String("Error. You don't have write access to this calendar"), HttpStatus.FORBIDDEN);
        }

        createCommentForm.setDate(new Date());
        commentService.save(createCommentForm);
        return new ResponseEntity<>("OK. Created.", HttpStatus.CREATED);
    }

    @RequestMapping(value = "/comment/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteComment(@PathVariable("id") long commentId) {

        Comment comment = commentService.getCommentById(commentId);
        if(comment == null){
            return new ResponseEntity(new String("Error. Comment with id " + comment + " doesn't exist."), HttpStatus.NOT_FOUND);
        }

        String userName = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!calendarAccessService.hasWriteAccess(userName, comment.getDuty().getCalendar().getId())){
            return new ResponseEntity(new String("Error. You don't have write access to this calendar"), HttpStatus.FORBIDDEN);
        }

        commentService.delete(commentId);
        return new ResponseEntity<>("OK. Deleted.", HttpStatus.NO_CONTENT);
    }

}
