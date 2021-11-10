package co.tayyare.onboarding.saas.dto;

import co.tayyare.onboarding.base.dto.BaseDTO;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class SaasInformation extends BaseDTO {

    @Getter
    @Setter
    private Long saasId;

    @NotNull(message = "SaaS name is not null!")
    @NotBlank(message = "SaaS name is not blank!")
    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private Integer status;

    @NotNull(message = "Pack name is not null!")
    @NotBlank(message = "Pack name is not blank!")
    @Getter
    @Setter
    private String pack;

    @Getter
    @Setter
    private String saasToken;

    @Getter
    @Setter
    private Long userId;

    @NotNull(message = "SaaS unique value is not null!")
    @NotBlank(message = "Saas unique value is not blank!")
    @Getter
    @Setter
    private String uniqueValue;

}
