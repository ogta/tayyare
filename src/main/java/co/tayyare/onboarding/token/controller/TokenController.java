package co.tayyare.onboarding.token.controller;

import co.tayyare.onboarding.base.dto.BaseDTO;
import co.tayyare.onboarding.base.helper.ExceptionHelper;
import co.tayyare.onboarding.token.dto.TokenDTO;
import co.tayyare.onboarding.token.dto.ValidationTokenDTO;
import co.tayyare.onboarding.token.service.ITokenService;
import co.tayyare.onboarding.user.util.constant.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/token")
public class TokenController extends ExceptionHelper {

    @Autowired
    private ITokenService iTokenService;

    @PostMapping("/")
    public ResponseEntity createToken(@Validated @RequestBody TokenDTO tokenDTO) {

        TokenDTO tokenCreateResult = iTokenService.createToken(tokenDTO);

        ResponseEntity response = checkResponseCode(tokenCreateResult);
        if (response != null) return response;

        return new ResponseEntity(tokenCreateResult, HttpStatus.CREATED);
    }

    @PostMapping("/validate")
    public ResponseEntity validateToken(@Validated @RequestBody ValidationTokenDTO tokenDTO) {

        BaseDTO tokenCreateResult;

        if (tokenDTO.getUserType() == UserType.ADMIN) {
            tokenCreateResult = iTokenService.validateAdminToken(tokenDTO);
        } else {
            tokenCreateResult = iTokenService.validateSaaSToken(tokenDTO);
        }

        ResponseEntity response = checkResponseCode(tokenCreateResult);
        if (response != null) return response;

        return new ResponseEntity(tokenCreateResult, HttpStatus.CREATED);
    }

}
