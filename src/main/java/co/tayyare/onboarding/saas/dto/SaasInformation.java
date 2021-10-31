package co.tayyare.onboarding.saas.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

public class SaasInformation extends BaseDTO {

    @Getter
    @Setter
    private long saasId;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private Integer status;

    @Getter
    @Setter
    private String pack;

    @Getter
    @Setter
    private String saasToken;

    @Getter
    @Setter
    private long userId;

    @Getter
    @Setter
    private String uniqueValue;

}
