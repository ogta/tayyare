package co.tayyare.onboarding.token.util.constant;

public enum TokenServiceResponse {

    SUCCESS(0, "Success"),
    USERNAME_PASSWORD_WRONG(2001,"Username / Password is not match!"),
    TOKEN_IS_NOT_VALID(2002,"Token is not valid!"),
    TOKEN_IS_EXPIRED(2003,"Token is expired!"),
    PASSIVE_USER(2004,"User status is not active!");

    private int responseCode;
    private String responseDescription;

    TokenServiceResponse(int responseCode, String responseDescription) {
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
