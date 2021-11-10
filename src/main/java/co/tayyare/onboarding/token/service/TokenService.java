package co.tayyare.onboarding.token.service;

import co.tayyare.onboarding.saas.dao.DAOSaasInfo;
import co.tayyare.onboarding.saas.repository.ISaasInformationRepository;
import co.tayyare.onboarding.saas.util.constant.SaasServiceResponse;
import co.tayyare.onboarding.token.dao.DAOToken;
import co.tayyare.onboarding.token.dto.TokenDTO;
import co.tayyare.onboarding.token.dto.TokenUserDTO;
import co.tayyare.onboarding.token.dto.ValidationTokenDTO;
import co.tayyare.onboarding.token.repository.ITokenRepository;
import co.tayyare.onboarding.token.util.constant.TokenServiceResponse;
import co.tayyare.onboarding.user.dao.DAOUserInfo;
import co.tayyare.onboarding.user.dto.UserInformation;
import co.tayyare.onboarding.user.repository.IUserInformationRepository;
import co.tayyare.onboarding.user.util.constant.SaasUniqueValue;
import co.tayyare.onboarding.user.util.constant.UserServiceResponse;
import co.tayyare.onboarding.user.util.constant.UserStatus;
import co.tayyare.onboarding.user.util.constant.UserType;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class TokenService implements ITokenService {

    private final ITokenRepository tokenRepository;

    private final IUserInformationRepository userInformationRepository;

    private final ISaasInformationRepository saasInformationRepository;

    public TokenService(ITokenRepository tokenRepository, IUserInformationRepository userInformationRepository, ISaasInformationRepository saasInformationRepository) {
        this.tokenRepository = tokenRepository;
        this.userInformationRepository = userInformationRepository;
        this.saasInformationRepository = saasInformationRepository;
    }

    @Override
    public TokenDTO createToken(TokenDTO tokenDTO) {

        DAOUserInfo existUser = null;

        if (tokenDTO.getUserType() == UserType.ADMIN) {
            existUser = adminUserExist(tokenDTO);
        } else if (tokenDTO.getUserType() == UserType.SAAS_USER) {
            DAOSaasInfo saasInfo = saasInformationRepository.findBySaasToken(tokenDTO.getSaasToken());
            if (saasInfo == null) {
                TokenDTO response = new TokenDTO();
                response.setResponseCode(SaasServiceResponse.SAAS_NOT_FOUND.getResponseCode());
                response.setResponseDescription(SaasServiceResponse.SAAS_NOT_FOUND.getResponseDescription());
                return response;
            }

            TokenDTO checkUniqueValues = checkUniqueValueValidation(tokenDTO, saasInfo);
            if (checkUniqueValues != null) return checkUniqueValues;

            existUser = saasUserExist(tokenDTO, saasInfo);
        }

        if (existUser == null) {
            TokenDTO response = new TokenDTO();
            response.setResponseCode(UserServiceResponse.USER_NOT_FOUND.getResponseCode());
            response.setResponseDescription(UserServiceResponse.USER_NOT_FOUND.getResponseDescription());
            return response;
        }

        if (existUser != null && existUser.getStatus() != UserStatus.ACTIVE.value) {
            TokenDTO response = new TokenDTO();
            response.setResponseCode(TokenServiceResponse.PASSIVE_USER.getResponseCode());
            response.setResponseDescription(TokenServiceResponse.PASSIVE_USER.getResponseDescription());
            return response;
        }

        if (existUser != null && !existUser.getPassword().equals(tokenDTO.getPassword())) {
            TokenDTO response = new TokenDTO();
            response.setResponseCode(TokenServiceResponse.USERNAME_PASSWORD_WRONG.getResponseCode());
            response.setResponseDescription(TokenServiceResponse.USERNAME_PASSWORD_WRONG.getResponseDescription());
            return response;
        }

        existTokenStatusUpdate(existUser);

        UUID token = UUID.randomUUID();

        DAOToken tokenDAO = new DAOToken();
        tokenDAO.setToken(token.toString());
        tokenDAO.setExpireDate(tokenDTO.getExpireDate());
        tokenDAO.setIsValidToken(1);
        tokenDAO.setTryCount(0);
        tokenDAO.setUserInfo(existUser);
        tokenDAO = tokenRepository.save(tokenDAO);
        tokenDTO.setToken(tokenDAO.getToken());
        tokenDTO.setIsValidToken(tokenDAO.getIsValidToken());
        tokenDTO.setTryCount(tokenDAO.getTryCount());
        tokenDTO.setResponseCode(TokenServiceResponse.SUCCESS.getResponseCode());
        tokenDTO.setUserType(tokenDTO.getUserType());
        tokenDTO.setResponseDescription(TokenServiceResponse.SUCCESS.getResponseDescription());
        tokenDTO.setPassword(null);
        return tokenDTO;
    }

    private void existTokenStatusUpdate(DAOUserInfo existUser) {
        List<DAOToken> tokenList = tokenRepository.findAllByUserInfoAndIsValidToken(existUser, 1);

        for (DAOToken t : tokenList) {
            t.setIsValidToken(0);
            tokenRepository.save(t);
        }
    }

    @Override
    public TokenUserDTO validateAdminToken(ValidationTokenDTO tokenDTO) {

        DAOToken token = tokenRepository.findByTokenAndIsValidToken(tokenDTO.getToken(), 1);
        TokenUserDTO response = new TokenUserDTO();
        if (token == null) {
            response.setResponseCode(TokenServiceResponse.TOKEN_IS_NOT_VALID.getResponseCode());
            response.setResponseDescription(TokenServiceResponse.TOKEN_IS_NOT_VALID.getResponseDescription());
            return response;
        }

        if (token.getExpireDate() != null && token.getExpireDate().before(new Date())) {
            response.setResponseCode(TokenServiceResponse.TOKEN_IS_EXPIRED.getResponseCode());
            response.setResponseDescription(TokenServiceResponse.TOKEN_IS_EXPIRED.getResponseDescription());
            return response;
        }

        UserInformation userInformation = new UserInformation();
        BeanUtils.copyProperties(token.getUserInfo(), userInformation);


        userInformation.setPassword(null);

        response.setUserInformation(userInformation);
        response.setResponseCode(TokenServiceResponse.SUCCESS.getResponseCode());
        response.setResponseDescription(TokenServiceResponse.SUCCESS.getResponseDescription());
        return response;

    }

    @Override
    public TokenUserDTO validateSaaSToken(ValidationTokenDTO tokenDTO) {

        DAOToken token = tokenRepository.findByTokenAndIsValidToken(tokenDTO.getToken(), 1);

        TokenUserDTO response = new TokenUserDTO();
        if (token == null) {
            response.setResponseCode(TokenServiceResponse.TOKEN_IS_NOT_VALID.getResponseCode());
            response.setResponseDescription(TokenServiceResponse.TOKEN_IS_NOT_VALID.getResponseDescription());
            return response;
        }

        if (token.getExpireDate() != null && token.getExpireDate().before(new Date())) {
            response.setResponseCode(TokenServiceResponse.TOKEN_IS_EXPIRED.getResponseCode());
            response.setResponseDescription(TokenServiceResponse.TOKEN_IS_EXPIRED.getResponseDescription());
            return response;
        }

        if (!token.getUserInfo().getSaasInfo().getSaasToken().equals(tokenDTO.getSaasToken())) {
            response.setResponseCode(TokenServiceResponse.TOKEN_IS_NOT_VALID.getResponseCode());
            response.setResponseDescription(TokenServiceResponse.TOKEN_IS_NOT_VALID.getResponseDescription());
            return response;
        }

        UserInformation userInformation = new UserInformation();
        BeanUtils.copyProperties(token.getUserInfo(), userInformation);

        userInformation.setPassword(null);
        response.setUserInformation(userInformation);
        response.setResponseCode(TokenServiceResponse.SUCCESS.getResponseCode());
        response.setResponseDescription(TokenServiceResponse.SUCCESS.getResponseDescription());
        return response;

    }

    private TokenDTO checkUniqueValueValidation(TokenDTO userInformation, DAOSaasInfo saasInfo) {
        if (saasInfo.getUniqueValue().equals(SaasUniqueValue.EMAIL.value) && !StringUtils.hasText(userInformation.getEmail())) {
            TokenDTO response = new TokenDTO();
            response.setResponseCode(UserServiceResponse.EMAIL_REQUIRED.getResponseCode());
            response.setResponseDescription(UserServiceResponse.EMAIL_REQUIRED.getResponseDescription());
            return response;
        } else if (saasInfo.getUniqueValue().equals(SaasUniqueValue.PHONE.value) && !StringUtils.hasText(userInformation.getPhone())) {
            TokenDTO response = new TokenDTO();
            response.setResponseCode(UserServiceResponse.PHONE_REQUIRED.getResponseCode());
            response.setResponseDescription(UserServiceResponse.PHONE_REQUIRED.getResponseDescription());
            return response;
        } else if (saasInfo.getUniqueValue().equals(SaasUniqueValue.USERNAME.value) && !StringUtils.hasText(userInformation.getUsername())) {
            TokenDTO response = new TokenDTO();
            response.setResponseCode(UserServiceResponse.USERNAME_REQUIRED.getResponseCode());
            response.setResponseDescription(UserServiceResponse.USERNAME_REQUIRED.getResponseDescription());
            return response;
        }

        return null;
    }

    private DAOUserInfo saasUserExist(UserInformation userInformation, DAOSaasInfo saasInfo) {

        if (saasInfo.getUniqueValue().equals(SaasUniqueValue.EMAIL.value)) {
            return userInformationRepository.findByEmailAndSaasInfo(userInformation.getEmail(), saasInfo);
        } else if (saasInfo.getUniqueValue().equals(SaasUniqueValue.PHONE.value)) {
            return userInformationRepository.findByPhoneAndSaasInfo(userInformation.getPhone(), saasInfo);
        } else if (saasInfo.getUniqueValue().equals(SaasUniqueValue.USERNAME.value)) {
            return userInformationRepository.findByUsernameAndSaasInfo(userInformation.getUsername(), saasInfo);
        }

        return null;
    }

    private DAOUserInfo adminUserExist(UserInformation userInformation) {
        return userInformationRepository.findByEmail(userInformation.getEmail());
    }

}
