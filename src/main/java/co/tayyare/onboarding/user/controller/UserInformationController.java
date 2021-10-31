package co.tayyare.onboarding.user.controller;


import co.tayyare.onboarding.base.ExceptionHelper;
import co.tayyare.onboarding.user.dto.UserInformation;
import co.tayyare.onboarding.user.service.IUserInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserInformationController extends ExceptionHelper {

    @Autowired
    private IUserInformationService userInformationService;

    @PostMapping("/")
    public ResponseEntity createUserInformation(@RequestBody UserInformation userInformation) {
        UserInformation userInfo = userInformationService.createSaasUserInformation(userInformation);

        ResponseEntity response = checkResponseCode(userInfo);
        if (response != null) return response;

        return new ResponseEntity(userInfo, HttpStatus.CREATED);
    }

}
