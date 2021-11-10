package co.tayyare.onboarding.base.dto;

import lombok.Getter;
import lombok.Setter;

public class BaseDTO {

    @Getter
    @Setter
    private Integer responseCode;

    @Getter
    @Setter
    private String responseDescription;

}
