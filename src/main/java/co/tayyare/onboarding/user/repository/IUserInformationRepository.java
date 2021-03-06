package co.tayyare.onboarding.user.repository;

import co.tayyare.onboarding.saas.dao.DAOSaasInfo;
import co.tayyare.onboarding.user.dao.DAOUserInfo;
import org.springframework.data.repository.CrudRepository;

public interface IUserInformationRepository extends CrudRepository<DAOUserInfo, Long> {

    DAOUserInfo findByUserId(Long id);

    DAOUserInfo findByUsernameAndSaasInfo(String username, DAOSaasInfo saasInfo);

    DAOUserInfo findByEmailAndSaasInfo(String email, DAOSaasInfo saasInfo);

    DAOUserInfo findByEmail(String email);

    DAOUserInfo findByPhoneAndSaasInfo(String phone, DAOSaasInfo saasInfo);

}
