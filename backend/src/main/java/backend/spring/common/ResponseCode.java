package backend.spring.common;

public interface ResponseCode {

    // HTTP Status 200
    String SUCCESS = "SU";

    // HTTP Status 400
    String VALUDATION_FAILED = "VF";
    String DUPLICATE_EMAIL = "DE"; //409
    String DUPLICATE_NICKNAME = "DN"; //409
    String NOT_EXISTED_USER = "NEU";
    String NOT_EXISTED_PROJECT = "NEP";

    // HTTP Status 401
    String SIGN_IN_FAIL = "SF";
    String AUTHORIZATION_FAIL = "AF";
        String LOGIN_FAIL = "AF";

    // HTTP Status 403
    String NO_PERMISSTION = "NP";

    // HTTP Status 500
    String DATABASE_ERROR = "DBE";

}
