package co.tayyare.onboarding.user.controller;

import co.tayyare.onboarding.base.helper.ExceptionHelper;
import co.tayyare.onboarding.user.dto.UserInformation;
import co.tayyare.onboarding.user.service.IUserInformationService;
import co.tayyare.onboarding.user.util.constant.UserServiceResponse;
import co.tayyare.onboarding.user.util.constant.UserType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserInformationController extends ExceptionHelper {

    private final IUserInformationService userInformationService;

    public UserInformationController(IUserInformationService userInformationService) {
        this.userInformationService = userInformationService;
    }

    @PostMapping("/")
    public ResponseEntity createUserInformation(@Validated @RequestBody UserInformation userInformation) {
        UserInformation userInfo = null;
        if (userInformation.getUserType() == UserType.ADMIN) {
            if (!StringUtils.hasText(userInformation.getEmail())) {
                userInfo = new UserInformation();
                userInfo.setResponseCode(UserServiceResponse.EMAIL_REQUIRED_ADMIN_ROLE.getResponseCode());
                userInfo.setResponseDescription(UserServiceResponse.EMAIL_REQUIRED_ADMIN_ROLE.getResponseDescription());
            } else {
                userInfo = userInformationService.createAdminUserInformation(userInformation);
            }
        } else if (userInformation.getUserType() == UserType.SAAS_USER) {
            userInfo = userInformationService.createSaasUserInformation(userInformation);
        }

        ResponseEntity response = checkResponseCode(userInfo);
        if (response != null) return response;

        return new ResponseEntity(userInfo, HttpStatus.CREATED);
    }

}
