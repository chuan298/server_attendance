package com.api.btlwsandroid.constant;
// Author: anhnv

public class ErrorCode {

    public static final String SUCCESS                        = "00";
    public static final String SUCCESS_DESCRIPTION            = "Successful!";

    public static final String INVALID_INPUT_CODE             = "01";
    public static final String INVALID_INPUT_MESSAGE          = "Invalid Data Input";

    public static final String NOT_ACTIVE_ENTITY_CODE         = "02";
    public static final String NOT_ACTIVE_ENTITY_MESSAGE      = "Not Active";

    public static final String NOT_FOUND_ENTITY_CODE          = "03";
    public static final String NOT_FOUND_ENTITY_MESSAGE       = "Not Found Data";

    public static final String DUPLICATE_ENTITY_CODE          = "04";
    public static final String DUPLICATE_ENTITY_MESSAGE       = "Duplicated Data";

    public static final String LOCKED_ENTITY_CODE             = "05";
    public static final String LOCKED_ENTITY_MESSAGE          = "Locked Entity";

    public static final String EXPIRED_ENTITY_CODE            = "06";
    public static final String EXPIRED_ENTITY_MESSAGE         = "Expired Data";

    public static final String FAILED_TO_EXECUTE              = "07";
    public static final String FAILED_TO_EXECUTE_DESCRIPTION  = "Failed to execute!";

    public static final String CONNECTION_TIMEOUT             = "08";
    public static final String CONNECTION_TIMEOUT_DESCRIPTION = "Connection timeout!";

    public static final String USED_ENTITY_CODE               = "09";
    public static final String USED_ENTITY_MESSAGE            = "Used Data";

    public static final String NOT_AVAI_ENTITY_CODE           = "10";
    public static final String NOT_AVAI_ENTITY_MESSAGE        = "Not Available Data";

    public static final String ENCRYPT_CODE                   = "11";
    public static final String ENCRYPT_MESSAGE                = "Encrypt Error";

    public static final String DECRYPT_CODE                   = "12";
    public static final String DECRYPT_MESSAGE                = "Decrypt Error";

    public static final String EMAIL_FORMAT_CODE              = "13";
    public static final String EMAIL_FORMAT_MESSAGE           = "Email format is invalid";

    public static final String DATE_FORMAT_CODE               = "14";
    public static final String DATE_FORMAT_MESSAGE            = "Date format is invalid";

    public static final String NUMBER_FORMAT_CODE             = "15";
    public static final String NUMBER_FORMAT_MESSAGE          = "Number format is invalid";

    public static final String ACCESS_RESOURCES_CODE          = "16";
    public static final String ACCESS_RESOURCES_MESSAGE       = "No permission to access";

    public static final String UNKNOWN_ERROR                  = "99";
    public static final String UNKNOWN_ERROR_MESSAGE          = "Internal errors";
}
