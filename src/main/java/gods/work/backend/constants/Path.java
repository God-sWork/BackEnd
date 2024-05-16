package gods.work.backend.constants;

public class Path {
    public static final String API = "/api";

    public static final String LOGIN = API + "/login";
    public static final String LOGOUT = API + "/logout";
    public static final String TOKEN = API + "/token";

    public static final String TRAINER = API + "/trainer";
    public static final String LOGIN_TRAINER = TRAINER + "/login_trainer";
    public static final String TRAINER_BY_ID = TRAINER + "/{trainerId}";
    public static final String SIGNUP = TRAINER + "/signup";
    public static final String UPDATE = TRAINER + "/{trainerId}";
    public static final String FIND_LOGIN_ID = TRAINER + "/find-login-id/{email}";
    public static final String FIND_PASSWORD = TRAINER + "/find-password";
    public static final String WITHDRAWAL = TRAINER + "/withdrawal";
}
