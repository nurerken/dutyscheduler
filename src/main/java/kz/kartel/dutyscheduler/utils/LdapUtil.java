package kz.kartel.dutyscheduler.utils;

import kz.kartel.dutyscheduler.components.user.model.User;
import kz.kartel.dutyscheduler.components.user.service.UserService;
import kz.kartel.dutyscheduler.security.ActiveDirectory;
import kz.kartel.dutyscheduler.security.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.naming.ldap.LdapContext;
import javax.servlet.http.HttpSession;
import java.util.List;

public class LdapUtil {

    @Autowired
    static UserService userService;

    public static boolean isAuhtenticated(String login, String password, HttpSession httpSession, UserService userService) {

        try{
            LdapContext ctx = ActiveDirectory.getConnection(login, password, SecurityConstants.LDAP_URL);
            //ctx.close();
            // httpSession.setAttribute(SecurityConstants.ldapContextKey, ctx);
            // httpSession.setMaxInactiveInterval((int) SecurityConstants.EXPIRATION_TIME / 1000);
            Thread thread = new Thread(new UserUpdateThread(ctx, userService));
            thread.start();
            return true;
        }
        catch(Exception e){
            //Failed to authenticate user!
            e.printStackTrace();
        }

        return false;
    }

    public static String getUserInfo(String userName, LdapContext ldapContext){
        String userInfo = "";
        try {
            if(userName.contains("@")){
                userName = userName.split("@")[0];
            }
            userInfo = ActiveDirectory.getUserInfo(userName, ldapContext);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return userInfo;
    }
}

class UserUpdateThread implements Runnable{

    LdapContext ldapContext = null;

    UserService userService;

    public UserUpdateThread(LdapContext ldapContext, UserService userService){
        this.ldapContext = ldapContext;
        this.userService = userService;
    }

    @Override
    public void run() {
        try{
            List<User> users = userService.getAllUsers();
            for (User user: users) {
                String userInfo = LdapUtil.getUserInfo(user.getEmail(), ldapContext);

                if(!StringUtils.isEmpty(userInfo)){
                    String userInfos[] = userInfo.split("\\|");
                    userService.updateUser(!StringUtils.isEmpty(userInfos[0]) && !userInfos[0].equals("null") ? userInfos[0].split(":")[1].trim() : "",
                            !StringUtils.isEmpty(userInfos[1]) && !userInfos[1].equals("null") ? userInfos[1].split(":")[1].trim() : "",
                            !StringUtils.isEmpty(userInfos[2]) && !userInfos[2].equals("null") ? userInfos[2].split(":")[1].trim() : "",
                            !StringUtils.isEmpty(userInfos[3]) && !userInfos[3].equals("null") ? userInfos[3].split(":")[1].trim() : "",
                            !StringUtils.isEmpty(userInfos[4]) && !userInfos[4].equals("null") ? userInfos[4].split(":")[1].trim() : "",
                            !StringUtils.isEmpty(userInfos[5]) && !userInfos[5].equals("null") ? userInfos[5].split(":")[1].trim() : "",
                            user.getEmail());
                }
            }

            ldapContext.close();
        }catch (Exception ex){
            System.out.println(ex);
        }
    }
}
