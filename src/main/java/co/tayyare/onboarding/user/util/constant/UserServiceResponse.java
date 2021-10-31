package co.tayyare.onboarding.user.util.constant;

public enum UserServiceResponse {

    SUCCESS(0, "Success"),
    RECORD_NOT_FOUND(10, "Record Not Found"),
    USER_NOT_FOUND(20, "User Not Found"),
    USERNAME_OR_MAIL_ALREADY_EXIST(21,"Username/E-mail/Phone is already taken!"),
    EMAIL_REQUIRED(30, "E-mail address is required!"),
    PHONE_REQUIRED(31,"Phone is required!"),
    USERNAME_REQUIRED(32,"Username is required!");

    private int responseCode;
    private String responseDescription;

    UserServiceResponse(int responseCode, String responseDescription) {
        this.responseCode = responseCode;
        this.responseDescription = responseDescription;
    }

    public int getResponseCode() {
        return this.responseCode;
    }

    public String getResponseDescription() {
        return this.responseDescription;
    }
}
