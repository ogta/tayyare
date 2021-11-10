package co.tayyare.onboarding.user.util.constant;

public enum UserServiceResponse {

    SUCCESS(0, "Success"),
    USER_NOT_FOUND(20, "User Not Found"),
    USERNAME_OR_MAIL_ALREADY_EXIST(21,"Username/E-mail/Phone is already taken!"),
    EMAIL_REQUIRED(22, "E-mail address is required!"),
    PHONE_REQUIRED(23,"Phone is required!"),
    USERNAME_REQUIRED(24,"Username is required!"),
    USER_TYPE_REQUIRED(25,"User type is required!"),
    EMAIL_REQUIRED_ADMIN_ROLE(26,"Email is required for admin role!");

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
