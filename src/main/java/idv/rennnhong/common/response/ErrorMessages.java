package idv.rennnhong.common.response;

import java.util.HashMap;

public enum ErrorMessages {


    MISSING_REQUIRED_FIELD(40001, "MISSING_REQUIRED_FIELD"),
    INVALID_FIELDS_REQUEST(40002, "INVALID_FIELDS_REQUEST"),
    RECORD_ALREADY_EXISTS(40003, "RECORD_ALREADY_EXISTS"),
    COULD_NOT_UPDATE_RESOURCE(40004, "COULD_NOT_UPDATE_RESOURCE"),
    COULD_NOT_DELETE_RECORD(40005, "COULD_NOT_DELETE_RECORD"),
    COULD_NOT_CREATE_REQUEST(40006, "COULD_NOT_CREATE_REQUEST"),
    DATA_EXIST_RELATED(40007, "DATA_EXIST_RELATED"),
    RECORD_NOT_EXIST(40008, "RECORD_NOT_EXIST"),
    RELATION_DATA_NOT_EXIST(40009, "RELATION_DATA_NOT_EXIST"),
    REQUEST_DUPLICATE_DATA(40010, "REQUEST_DUPLICATE_DATA"),

    REQUEST_MAX_UPLOAD_SIZE_EXCEED(40011, "MAX_UPLOAD_SIZE_EXCEED"),
    AUTHENTICATION_FAILED(40101, "AUTHENTICATION_FAILED"),

    FORBIDDEN(40301, "FORBIDDEN"),
    FORBIDDEN_API_NO_AUTH(40302, "FORBIDDEN_API_NO_AUTH"),

    RESOURCE_NOT_FOUND(40401, "RESOURCE_NOT_FOUND"),

    HTTP_REQUEST_METHOD_NOT_SUPPORTED(40501, "HTTP_REQUEST_METHOD_NOT_SUPPORTED"),

    INTERNAL_SERVER_ERROR(50001, "INTERNAL_SERVER_ERROR"),
    NULL_POINTER_EXCEPTION(50002, "NULL_POINTER_EXCEPTION");

    private int code;
    private String errorMessage;

    ErrorMessages(int errorCode, String errorMessage) {
        this.code = errorCode;
        this.errorMessage = errorMessage;
    }

    /**
     * @return the errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @param errorMessage the errorMessage to set
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * 取得 Error code
     *
     * @return
     */
    public int getCode() {
        return code;
    }

    /**
     * 設定 Error code
     *
     * @param code
     */
    public void setCode(int code) {
        this.code = code;
    }

    public Object toObject() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("ErrorCode", getCode());
        hashMap.put("ErrorMessage", getErrorMessage());
        return hashMap;
    }

    public Object toObjectWithPrompt(Object errorPrompt) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("ErrorCode", getCode());
        hashMap.put("ErrorMessage", getErrorMessage());
        hashMap.put("ErrorPrompt", errorPrompt);
        return hashMap;
    }
}
