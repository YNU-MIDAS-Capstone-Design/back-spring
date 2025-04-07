package backend.spring.common;

public interface ResponseMessage {
    // HTTP Status 200
    String SUCCESS = "Success.";
    String EMPTY_RESULT = "Empty result.";

    // HTTP Status 400
    String VALUDATION_FAILED = "Validation Failed";
    String DUPLICATE_EMAIL = "Duplicate email " +
            "" +
            "";
    String DUPLICATE_NICKNAME = "Duplicate nickname";
    String NOT_EXISTED_USER = "This user does not exist";
    String NOT_EXISTED_PROJECT = "This project does not exist.";
    String NOT_EXISTED_TEAM = "This team does not exist.";
    String NOT_EXISTED_ID = "This ID does not exist.";
    String MISSING_REQUIRED_DATA = "A required data is missing.";

    // HTTP Status 401
    String SIGN_IN_FAIL = "Login information mismatch.";
    String AUTHORIZATION_FAIL = "Authorization Failed.";
    String LOGIN_FAIL = "Login failed";

    // HTTP Status 403
    String NO_PERMISSTION = "Do not have permission.";
    String NOT_MATCH_USER = "The user does not match.";

    // HTTP Status 500
    String DATABASE_ERROR = "Datatbase error.";
}
