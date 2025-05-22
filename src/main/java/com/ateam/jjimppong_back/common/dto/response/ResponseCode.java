package com.ateam.jjimppong_back.common.dto.response;

public interface ResponseCode {
    String SUCCESS = "SU";
    String VALIDATION_FAIL = "VF";
    String SIGN_IN_FAIL = "SF";
    String EXIST_USER = "EU";
    String DATABASE_ERROR  = "DBE";
    String DUPLICATIED_EMAIL = "DE";
    String MAIL_SEND_FAIL = "MF";
    String AUTH_FAIL = "AF";
    String DUPLICATED_EMAIL = "DE";
    String NO_EXIST_BOARD = "NB";
    String PASSWORD_NOT_MATCHED = "PN";
    String USER_NOT_FOUND = "UF";
    String SNS_NEED_INFO = "SNI";
    String NOT_EXIST_USER = "NEU";
    String SNS_NOT_FOUND = "SNF";
    
    String NO_PERMISSION = "NP";

    String MAIL_SEND_FAILED = "MF";

    String NO_EXIST_COMMENT = "NC";
}
