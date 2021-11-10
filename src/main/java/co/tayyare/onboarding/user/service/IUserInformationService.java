package co.tayyare.onboarding.user.service;

import co.tayyare.onboarding.user.dto.UserInformation;

public interface IUserInformationService {

    UserInformation createSaasUserInformation(UserInformation userInformation);

    UserInformation createAdminUserInformation(UserInformation userInformation);
}
