package co.tayyare.onboarding.saas.util.constant;

public enum SaasStatus {
    PASSIVE(0),
    ACTIVE(1);

    public final int value;

    SaasStatus(int val) {
        this.value = val;
    }
}
