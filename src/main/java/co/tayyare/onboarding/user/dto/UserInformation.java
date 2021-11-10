package co.tayyare.onboarding.user.dto;

import co.tayyare.onboarding.base.dto.BaseDTO;
import co.tayyare.onboarding.user.util.constant.UserType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UserInformation extends BaseDTO {

    @Getter
    @Setter
    private Long userId;

    @Getter
    @Setter
    private String username;

    @NotNull(message = "Password is not null!")
    @NotBlank(message = "Password is not blank!")
    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private int status;

    @Getter
    @Setter
    private String phone;

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String deviceId;

    @Getter
    @Setter
    private Long saasId;

    @NotNull(message = "User type is not null!")
    @Getter
    @Setter
    private UserType userType;


    @Getter
    @Setter
    private String saasToken;

}
