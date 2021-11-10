package co.tayyare.onboarding.token.dto;

import co.tayyare.onboarding.base.dto.BaseDTO;
import co.tayyare.onboarding.user.util.constant.UserType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ValidationTokenDTO extends BaseDTO {

    @NotNull(message = "Token is not null!")
    @NotBlank(message = "Token is not blank!")
    @Getter
    @Setter
    private String token;

    @Getter
    @Setter
    private String saasToken;

    @NotNull(message = "User type is not null!")
    @Getter
    @Setter
    private UserType userType;

}
