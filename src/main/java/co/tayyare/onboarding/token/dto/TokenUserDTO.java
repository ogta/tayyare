package co.tayyare.onboarding.token.dto;

import co.tayyare.onboarding.user.dto.UserInformation;
import lombok.Getter;
import lombok.Setter;

public class TokenUserDTO extends ValidationTokenDTO {

    @Getter
    @Setter
    UserInformation userInformation;

}
