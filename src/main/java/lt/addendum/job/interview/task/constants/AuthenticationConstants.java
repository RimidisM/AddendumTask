package lt.addendum.job.interview.task.constants;

public class AuthenticationConstants {

    private AuthenticationConstants() {
    }

    //Basic Auth
    public static final String INCORRECT_CREDENTIALS = "Incorrect credentials";
    public static final String MISSING_CREDENTIALS = "Request is missing authentication credentials";
    public static final String MISSING_CREDENTIALS_MSG = "Authentication faild due to missing authentication" +
            " credentials. Ensure that the username and password included in the request";
    public static final String INCORRECT_CREDENTIALS_MSG = "Authentication faild due to incorrect authentication" +
            " credentials. Ensure that the username and password included in the request are correct";
}
