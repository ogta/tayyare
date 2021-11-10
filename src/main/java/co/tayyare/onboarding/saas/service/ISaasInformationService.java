package co.tayyare.onboarding.saas.service;

import co.tayyare.onboarding.saas.dto.SaasInformation;

public interface ISaasInformationService {

    SaasInformation createSaasProjectInformation(SaasInformation saasInformation);

    SaasInformation getSaasProjectInformation(String saasToken);
}
