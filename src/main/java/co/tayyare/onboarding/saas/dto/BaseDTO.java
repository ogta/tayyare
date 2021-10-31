package co.tayyare.onboarding.saas.dto;

import lombok.Getter;
import lombok.Setter;

public class BaseDTO {

    @Getter
    @Setter
    private int responseCode;

    @Getter
    @Setter
    private String responseDescription;

}
