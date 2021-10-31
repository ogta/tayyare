package co.tayyare.onboarding.saas.service;

import co.tayyare.onboarding.saas.dao.DAOSaasInfo;
import co.tayyare.onboarding.user.dao.DAOUserInfo;
import co.tayyare.onboarding.saas.dto.SaasInformation;
import co.tayyare.onboarding.saas.repository.ISaasInformationRepository;
import co.tayyare.onboarding.user.repository.IUserInformationRepository;
import co.tayyare.onboarding.saas.util.constant.SaasServiceResponse;
import co.tayyare.onboarding.saas.util.constant.SaasStatus;
import co.tayyare.onboarding.user.util.constant.UserServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SaasInformationService implements ISaasInformationService {

    @Autowired
    private ISaasInformationRepository saasInformationRepository;

    @Autowired
    private IUserInformationRepository userInformationRepository;

    @Override
    public SaasInformation createSaasProjectInformation(SaasInformation saasInformation) {

        DAOUserInfo owner = userInformationRepository.findByUserId(saasInformation.getUserId());

        if (owner == null) {
            SaasInformation response = new SaasInformation();
            response.setResponseCode(UserServiceResponse.USER_NOT_FOUND.getResponseCode());
            response.setResponseDescription(UserServiceResponse.USER_NOT_FOUND.getResponseDescription());
            return response;
        }

        DAOSaasInfo saasInfoDAO = setSaasInformationDAO(saasInformation, owner);
        saasInfoDAO = saasInformationRepository.save(saasInfoDAO);
        return getSaasInformation(saasInfoDAO);
    }

    @Override
    public SaasInformation getSaasProjectInformation(long id) {
        DAOSaasInfo saasInfoDAO = saasInformationRepository.findBySaasID(id);

        if (saasInfoDAO == null) {
            SaasInformation response = new SaasInformation();
            response.setResponseCode(SaasServiceResponse.RECORD_NOT_FOUND.getResponseCode());
            response.setResponseDescription(SaasServiceResponse.RECORD_NOT_FOUND.getResponseDescription());
            return response;
        }

        return getSaasInformation(saasInfoDAO);
    }

    private SaasInformation getSaasInformation(DAOSaasInfo saasInfoDAO) {
        SaasInformation response = new SaasInformation();
        response.setName(saasInfoDAO.getName());
        response.setPack(saasInfoDAO.getPack());
        response.setSaasToken(saasInfoDAO.getSaasToken());
        response.setStatus(saasInfoDAO.getStatus());
        response.setSaasId(saasInfoDAO.getSaasID());
        response.setUserId(saasInfoDAO.getOwnerInfo().getUserId());
        response.setResponseCode(SaasServiceResponse.SUCCESS.getResponseCode());
        response.setResponseDescription(SaasServiceResponse.SUCCESS.getResponseDescription());
        return response;
    }

    private DAOSaasInfo setSaasInformationDAO(SaasInformation saasInformation, DAOUserInfo owner) {
        DAOSaasInfo saasInfoDAO = new DAOSaasInfo();
        UUID saasToken = UUID.randomUUID();
        saasInfoDAO.setName(saasInformation.getName());
        saasInfoDAO.setPack(saasInformation.getPack());
        saasInfoDAO.setStatus(SaasStatus.ACTIVE.value);
        saasInfoDAO.setSaasToken(saasToken.toString());
        saasInfoDAO.setUniqueValue(saasInformation.getUniqueValue());
        saasInfoDAO.setOwnerInfo(owner);
        return saasInfoDAO;
    }

}
