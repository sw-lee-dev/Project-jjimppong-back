package com.ateam.jjimppong_back.common.dto.response;

public interface ResponseMessage {
    String SUCCESS = "Success.";
    String VALIDATION_FAIL = "Validation Fail.";
    String SIGN_IN_FAIL = "Sign in Fail";
    String EXIST_USER = "Exist User.";
    String DATABASE_ERROR  = "Database Error.";
    String DUPLICATIED_EMAIL = "Duplicatied Email";
    String MAIL_SEND_FAIL = "Mail send Fail";
    String AUTH_FAIL = "Auth Fail";
    String DUPLICATED_EMAIL = "Duplicated Email";
    String NO_EXIST_BOARD = "No Exist Board.";
    String PASSWORD_NOT_MATCHED = "Password Not Matched";
    String USER_NOT_FOUND = "User Not Found";
    String SNS_NEED_INFO = "Sns Need Info";
    
    String NO_PERMISSION = "No Permission.";

    String MAIL_SEND_FAILED = "Mail send Failed";

    String NO_EXIST_COMMENT = "No Exist Comment.";
}
