package com.api.btlwsandroid.constant;

/**
 * Author : Nguyen Viet Anh
 * Email: anhnv@vnpay.vn
 */

public class Constant {

    public static final Long ID_NONE = 1L;
    public static final String MESSAGE_SUCCESS = "Thành công";
    public static final String CODE_FAIL = "07";

    public static final String MESSAGE_NOT_VALID_NUMBER = " phải là số nguyên";
    public static final String MESSAGE_MUST_NOT_EMPTY = "Bắt buộc phải nhập thông tin ";
    public static final String MESSAGE_FAIL_TO_EXECUTE = "";

    public static String getMessageInvalidMaxLength(String input, int length) {
        return "Độ dài của " + input + " không được quá " + length + " ký tự";
    }


}
