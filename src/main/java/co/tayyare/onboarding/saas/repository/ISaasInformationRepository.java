package co.tayyare.onboarding.saas.repository;

import co.tayyare.onboarding.saas.dao.DAOSaasInfo;
import co.tayyare.onboarding.user.dao.DAOUserInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ISaasInformationRepository extends CrudRepository<DAOSaasInfo, Long> {
    DAOSaasInfo findBySaasToken(String token);

    List<DAOSaasInfo> findAllByNameAndOwnerInfo(String name, DAOUserInfo userInfo);
}
