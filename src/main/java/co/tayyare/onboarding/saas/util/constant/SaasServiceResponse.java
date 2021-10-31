package co.tayyare.onboarding.saas.util.constant;

public enum SaasServiceResponse {

    SUCCESS(0, "Success"),
    RECORD_NOT_FOUND(10, "Record Not Found"),
    SAAS_NOT_FOUND(30, "Saas Not Found");

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
