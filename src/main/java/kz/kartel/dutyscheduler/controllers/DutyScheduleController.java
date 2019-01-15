package kz.kartel.dutyscheduler.controllers;

import com.auth0.jwt.JWT;
import kz.kartel.dutyscheduler.components.calendar.service.CalendarService;
import kz.kartel.dutyscheduler.components.comment.Comment;
import kz.kartel.dutyscheduler.components.comment.Comments;
import kz.kartel.dutyscheduler.components.comment.GetCommentForm;
import kz.kartel.dutyscheduler.components.duty.forms.CreateCommentForm;
import kz.kartel.dutyscheduler.components.duty.forms.CreateDutyForm;
import kz.kartel.dutyscheduler.components.duty.forms.DutiesResponse;
import kz.kartel.dutyscheduler.components.duty.model.Duty;
import kz.kartel.dutyscheduler.components.duty.service.CalendarAccessService;
import kz.kartel.dutyscheduler.components.duty.service.CommentService;
import kz.kartel.dutyscheduler.components.duty.service.DutyService;
import kz.kartel.dutyscheduler.components.user.forms.LoginForm;
import kz.kartel.dutyscheduler.components.user.model.User;
import kz.kartel.dutyscheduler.components.user.service.UserService;
import kz.kartel.dutyscheduler.components.vacation.service.VacationService;
import kz.kartel.dutyscheduler.security.LdapUtil;
import kz.kartel.dutyscheduler.security.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.naming.ldap.LdapContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static kz.kartel.dutyscheduler.security.SecurityConstants.EXPIRATION_TIME;
import static kz.kartel.dutyscheduler.security.SecurityConstants.SECRET;
import static kz.kartel.dutyscheduler.security.SecurityConstants.TOKEN_PREFIX;

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

    //////////////////USER//////////////////////////
    @RequestMapping(value = SecurityConstants.SIGN_UP_URL, method = RequestMethod.POST)
    public ResponseEntity users(@RequestBody @Valid User user) {
        if(userService.getUserByEmail(user.getEmail()) != null){
            return new ResponseEntity("User with email: " + user.getEmail() + " already registered.", HttpStatus.CONFLICT);
        }

        //user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
        return new ResponseEntity("userId: " + user.getId(), HttpStatus.CREATED);
    }

    @RequestMapping(value = SecurityConstants.AUTHENTICATE_URL, method = RequestMethod.POST)
    public ResponseEntity authenticate(@RequestBody LoginForm loginForm, HttpSession httpSession) throws Exception{

        boolean isUserAuthenticated = LdapUtil.isAuhtenticated(loginForm.getEmail(), loginForm.getPassword(), httpSession, userService);

        if(isUserAuthenticated){
            if(userService.getUserByEmail(loginForm.getEmail()) == null) {
                userService.saveUser("","", loginForm.getEmail(), "", "", "", "");
            }

            String token = JWT.create()
                    .withSubject(loginForm.getEmail())
                    .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .sign(HMAC512(SECRET.getBytes()));
            return new ResponseEntity("{\n" + "\t\"JWT\":\"" + TOKEN_PREFIX + token + "\"}", HttpStatus.CREATED);
        }

        return new ResponseEntity("", HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = SecurityConstants.USER_URL, method = RequestMethod.GET)
    public ResponseEntity getUser(@RequestParam("userid") Long userId) {

        User user = userService.getUserById(userId);
        String email = user != null ? user.getEmail() : "";
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    ///////////////////currentuser//////////////////////////
    @RequestMapping(value = "/currentUser", method = RequestMethod.GET)
    public ResponseEntity getCurrentUser() {
        String userName = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String response = "{\"username\":\"" + (userName != null ? userName : "") + "\"}";
        return ResponseEntity.ok(response);
    }

    ///////////////DUTY////////////////////
    @RequestMapping(value = SecurityConstants.DUTIES_URL, method = RequestMethod.GET)
    public ResponseEntity dutiesByDate(@RequestParam("year") Integer year, @RequestParam("month") Integer month, @RequestParam(name = "calId") Long calId) {

        Date dateFirstMonday = calendarAccessService.getFirstMonday(year, month);
        Date dateLastSunday = calendarAccessService.getLastSunday(year, month);

        DutiesResponse dutiesResponse = dutyService.getDutiesResponse(dateFirstMonday, dateLastSunday, calId);
        return new ResponseEntity(dutiesResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/duty", method = RequestMethod.POST)
    public ResponseEntity<?> createDuty(@RequestBody @Valid CreateDutyForm createDutyForm) {

        String userName = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!calendarAccessService.hasWriteAccess(userName, createDutyForm.getCalId())){
            return new ResponseEntity(new String( "{\"Result\":\"Error. You don't have write access to this calendar\"}"), HttpStatus.OK);
        }

        if(userService.getUserById(createDutyForm.getUserId()) == null){
            return new ResponseEntity(new String("{\"Result\":\"Error. A User with id \"" + createDutyForm.getUserId() + "\" doesn't exist.\"}"), HttpStatus.OK);
        }

        Long dutyID = -1L;
        Duty duty = dutyService.getDuty(createDutyForm.getUserId(), createDutyForm.getDate(), createDutyForm.getCalId());
        if (duty != null){
            createDutyForm.setDutyId(duty.getId());

            if(duty.getDutyType().equals(createDutyForm.getDutyType())){
                dutyService.deleteDuty(createDutyForm.getDutyId());
            }
            else{
                dutyService.updateDuty(createDutyForm);
                dutyID = duty.getId();
            }
        }
        else{
            dutyID = dutyService.saveDuty(createDutyForm);
        }

        return new ResponseEntity<>("{\"Result\":\"SUCCESS\", \"dutyID\":\"" + dutyID + "\"}", HttpStatus.OK);
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

    //////////////COMMENT//////////////////
    @RequestMapping(value = SecurityConstants.COMMENTS_URL, method = RequestMethod.POST)
    public ResponseEntity commentsByDuty(@RequestBody GetCommentForm getCommentForm) throws Exception{

        Duty duty = dutyService.getDuty(getCommentForm.getUserId(), getCommentForm.getDate(), getCommentForm.getCalId());
        if(duty == null){
            return new ResponseEntity<>("{\"Result\":\"Error. No such duty\"}", HttpStatus.OK);
        }

        List<kz.kartel.dutyscheduler.components.duty.model.Comment> comments = commentService.getCommentsByDutyId(duty.getId());
        List<Comment> commentsLists = new ArrayList<>();
        for (kz.kartel.dutyscheduler.components.duty.model.Comment comment: comments){
            Comment commentsList = new Comment();
            commentsList.setId(comment.getId());
            commentsList.setText(comment.getText());
            String authorName = comment.getUser().getFirstName() + " " + comment.getUser().getLastName();
            commentsList.setAuthorName(authorName);

            DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            Date date = formatter.parse(formatter.format(comment.getInsertDate()));
            commentsList.setInsertDate(date);
            commentsLists.add(commentsList);
        }
        Comments commentsReturn = new Comments();
        commentsReturn.setComments(commentsLists);
        commentsReturn.setResult("SUCCESS");

        return new ResponseEntity(commentsReturn, HttpStatus.OK);
    }

    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public ResponseEntity<?> createComment(@RequestBody CreateCommentForm createCommentForm) {

        Duty duty = dutyService.getDuty(createCommentForm.getUserId(), createCommentForm.getDate(), createCommentForm.getCalId());
        if(duty == null){
            return new ResponseEntity<>("{\"Result\":\"Error. No such duty\"}", HttpStatus.OK);
        }

        String userName = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(!calendarAccessService.hasWriteAccess(userName, duty.getCalendar().getId())){
            return new ResponseEntity(new String("{\"Result\":\"Error. You don't have write access to this calendar\"}"), HttpStatus.OK);
        }

        commentService.save(duty.getId(), createCommentForm.getText(), new Date(), userService.getUserByEmail(userName).getId());
        return new ResponseEntity<>("{\"Result\":\"SUCCESS\"}", HttpStatus.CREATED);
    }

    @RequestMapping(value = "/comment/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteComment(@PathVariable("id") long commentId) {

        kz.kartel.dutyscheduler.components.duty.model.Comment comment = commentService.getCommentById(commentId);
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
