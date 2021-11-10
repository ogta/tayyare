package co.tayyare.onboarding.token.service;

import co.tayyare.onboarding.base.dto.BaseDTO;
import co.tayyare.onboarding.token.dto.TokenDTO;
import co.tayyare.onboarding.token.dto.ValidationTokenDTO;

public interface ITokenService {

    TokenDTO createToken(TokenDTO tokenDTO);

    BaseDTO validateAdminToken(ValidationTokenDTO tokenDTO);

    BaseDTO validateSaaSToken(ValidationTokenDTO tokenDTO);
}
