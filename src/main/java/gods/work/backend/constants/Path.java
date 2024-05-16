package gods.work.backend.constants;

public class Path {
    public static final String API = "/api";

    public static final String LOGIN = API + "/login";
    public static final String LOGOUT = API + "/logout";
    public static final String TOKEN = API + "/token";

    public static final String TRAINER = API + "/trainer";
    public static final String GET_BY_ID = TRAINER + "/{trainerId}";
    public static final String SIGNUP = TRAINER + "/signup";
    public static final String UPDATE = TRAINER + "/{trainerId}";
    public static final String LOGIN_ID = TRAINER + "/login-id/{email}";
    public static final String PASSWORD = TRAINER + "/password";
    public static final String WITHDRAWAL = TRAINER + "/withdrawal";
}
