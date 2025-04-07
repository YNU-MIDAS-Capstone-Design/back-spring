package backend.spring.common;

public interface ResponseCode {

    // HTTP Status 200
    String SUCCESS = "SU";

    // HTTP Status 400
    String VALUDATION_FAILED = "VF";
    String DUPLICATE_EMAIL = "DE";
    String DUPLICATE_NICKNAME = "DN";
    String NOT_EXISTED_USER = "NU";
    String NOT_EXISTED_PROJECT = "NEP";
    String NOT_EXISTED_TEAM = "NET";
    String NOT_EXISTED_ID = "NEI";
    String MISSING_REQUIRED_DATA = "MRD";

    // HTTP Status 401
    String SIGN_IN_FAIL = "SF";
    String AUTHORIZATION_FAIL = "AF";

    // HTTP Status 403 (사용자가 권한이 없음)
    String NO_PERMISSTION = "NP";
    String NOT_MATCH_USER = "NMU";

    // HTTP Status 500
    String DATABASE_ERROR = "DBE";

}
