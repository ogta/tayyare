package co.tayyare.onboarding.token.dto;

import co.tayyare.onboarding.user.dto.UserInformation;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class TokenDTO extends UserInformation {

    @Getter
    @Setter
    private Long tokenId;

    @Getter
    @Setter
    private Long userId;

    @Getter
    @Setter
    private String token;

    @Getter
    @Setter
    private Date expireDate;

    @Getter
    @Setter
    private int tryCount;

    @Getter
    @Setter
    private int isValidToken;

    @Getter
    @Setter
    private String saasToken;

}
