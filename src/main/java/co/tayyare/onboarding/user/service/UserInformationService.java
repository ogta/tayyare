package co.tayyare.onboarding.user.service;

import co.tayyare.onboarding.saas.dao.DAOSaasInfo;
import co.tayyare.onboarding.saas.dto.SaasInformation;
import co.tayyare.onboarding.saas.repository.ISaasInformationRepository;
import co.tayyare.onboarding.saas.util.constant.SaasServiceResponse;
import co.tayyare.onboarding.user.dao.DAOUserInfo;
import co.tayyare.onboarding.user.dto.UserInformation;
import co.tayyare.onboarding.user.repository.IUserInformationRepository;
import co.tayyare.onboarding.user.util.constant.SaasUniqueValue;
import co.tayyare.onboarding.user.util.constant.UserServiceResponse;
import co.tayyare.onboarding.user.util.constant.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Service
public class UserInformationService implements IUserInformationService {

    @Autowired
    private IUserInformationRepository userInformationRepository;

    @Autowired
    private ISaasInformationRepository saasInformationRepository;

    @Override
    public UserInformation createSaasUserInformation(UserInformation userInformation) {
        DAOSaasInfo saasInfo = saasInformationRepository.findBySaasID(userInformation.getSaasId());
        if (saasInfo == null) {
            UserInformation response = new UserInformation();
            response.setResponseCode(SaasServiceResponse.SAAS_NOT_FOUND.getResponseCode());
            response.setResponseDescription(SaasServiceResponse.SAAS_NOT_FOUND.getResponseDescription());
            return response;
        }

        UserInformation checkUniqueValues = checkUniqueValues(userInformation, saasInfo);
        if (checkUniqueValues != null) return checkUniqueValues;

        DAOUserInfo existUser = saasUserExist(userInformation, saasInfo);

        if (existUser != null) {
            UserInformation response = new UserInformation();
            response.setResponseCode(UserServiceResponse.USERNAME_OR_MAIL_ALREADY_EXIST.getResponseCode());
            response.setResponseDescription(UserServiceResponse.USERNAME_OR_MAIL_ALREADY_EXIST.getResponseDescription());
            return response;
        }

        UUID mailHash = UUID.randomUUID();

        DAOUserInfo userInfo = new DAOUserInfo();
        userInfo.setEmail(userInformation.getEmail());
        userInfo.setUsername(userInformation.getUsername());
        userInfo.setPassword(userInformation.getPassword());
        userInfo.setDeviceId(userInformation.getDeviceId());
        userInfo.setPhone(userInformation.getPhone());
        userInfo.setSaasInfo(saasInfo);
        userInfo.setStatus(UserStatus.ACTIVE.value);
        userInfo.setMailHash(mailHash.toString());

        DAOUserInfo user = userInformationRepository.save(userInfo);

        userInformation.setUserId(user.getUserId());
        userInformation.setStatus(user.getStatus());
        userInformation.setResponseCode(UserServiceResponse.SUCCESS.getResponseCode());
        userInformation.setResponseDescription(UserServiceResponse.SUCCESS.getResponseDescription());

        return userInformation;

    }

    private UserInformation checkUniqueValues(UserInformation userInformation, DAOSaasInfo saasInfo) {
        if (saasInfo.getUniqueValue().equals(SaasUniqueValue.EMAIL.value) && !StringUtils.hasText(userInformation.getEmail())) {
            UserInformation response = new UserInformation();
            response.setResponseCode(UserServiceResponse.EMAIL_REQUIRED.getResponseCode());
            response.setResponseDescription(UserServiceResponse.EMAIL_REQUIRED.getResponseDescription());
            return response;
        } else if (saasInfo.getUniqueValue().equals(SaasUniqueValue.PHONE.value) && !StringUtils.hasText(userInformation.getPhone())) {
            UserInformation response = new UserInformation();
            response.setResponseCode(UserServiceResponse.PHONE_REQUIRED.getResponseCode());
            response.setResponseDescription(UserServiceResponse.PHONE_REQUIRED.getResponseDescription());
            return response;
        } else {
            if (!StringUtils.hasText(userInformation.getUsername())) {
                UserInformation response = new UserInformation();
                response.setResponseCode(UserServiceResponse.USERNAME_REQUIRED.getResponseCode());
                response.setResponseDescription(UserServiceResponse.USERNAME_REQUIRED.getResponseDescription());
                return response;
            }
        }

        return null;
    }

    private DAOUserInfo saasUserExist(UserInformation userInformation, DAOSaasInfo saasInfo) {

        DAOUserInfo existUser = null;

        if (saasInfo.getUniqueValue().equals(SaasUniqueValue.EMAIL.value)) {
            existUser = userInformationRepository.findByEmailAndSaasInfo(userInformation.getEmail(), saasInfo);
        } else if (saasInfo.getUniqueValue().equals(SaasUniqueValue.PHONE.value)) {
            existUser = userInformationRepository.findByPhoneAndSaasInfo(userInformation.getPhone(), saasInfo);
        } else {
            existUser = userInformationRepository.findByUsernameAndSaasInfo(userInformation.getUsername(), saasInfo);
        }

        return existUser;
    }

}
