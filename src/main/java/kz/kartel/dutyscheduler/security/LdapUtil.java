package kz.kartel.dutyscheduler.security;

import javax.naming.ldap.LdapContext;
import javax.servlet.http.HttpSession;

public class LdapUtil {

    public static boolean isAuhtenticated(String login, String password, HttpSession httpSession) {

        try{
            LdapContext ctx = ActiveDirectory.getConnection(login, password, SecurityConstants.LDAP_URL);
            //ctx.close();
            httpSession.setAttribute(SecurityConstants.ldapContextKey, ctx);
            httpSession.setMaxInactiveInterval((int) SecurityConstants.EXPIRATION_TIME / 1000);
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
