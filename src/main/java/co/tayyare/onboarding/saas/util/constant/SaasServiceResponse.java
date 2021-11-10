package co.tayyare.onboarding.saas.util.constant;

public enum SaasServiceResponse {

    SUCCESS(0, "Success"),
    SAAS_NOT_FOUND(1001, "SaaS Not Found"),
    SAAS_NAME_ALREADY_EXIST(1001, "SaaS name is already exist!");

    private int responseCode;
    private String responseDescription;

    SaasServiceResponse(int responseCode, String responseDescription) {
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
