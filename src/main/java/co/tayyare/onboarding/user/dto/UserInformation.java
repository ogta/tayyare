package co.tayyare.onboarding.user.dto;

import co.tayyare.onboarding.saas.dto.BaseDTO;
import lombok.Getter;
import lombok.Setter;

public class UserInformation extends BaseDTO {

    @Getter
    @Setter
    private long userId;

    @Getter
    @Setter
    private String username;

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
    private long saasId;

}
