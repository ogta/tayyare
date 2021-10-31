package co.tayyare.onboarding.saas.repository;

import co.tayyare.onboarding.saas.dao.DAOSaasInfo;
import org.springframework.data.repository.CrudRepository;

public interface ISaasInformationRepository extends CrudRepository<DAOSaasInfo, Long> {
    DAOSaasInfo findBySaasID(long id);
}
