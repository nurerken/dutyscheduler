package kz.kartel.dutyscheduler.security;

public class SecurityConstants {
    public static final String SECRET = "mOz34d03laxAHETXccc";
    public static final long EXPIRATION_TIME = 10*24*60*60*1000; // 10 day(s)
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/register";
    public static final String DUTIES_URL = "/dutiesByDate";
    public static final String AUTHENTICATE_URL = "/authenticate";
    public static final String COMMENTS_URL = "/commentsByDuty";
    public static final String USER_URL = "/user";

    public static final String ldapContextKey = "ldapContext";

    public static final String LDAP_URL = "ldap://172.28.2.10:389";
}