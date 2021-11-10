package co.tayyare.onboarding.token.repository;

import co.tayyare.onboarding.token.dao.DAOToken;
import co.tayyare.onboarding.user.dao.DAOUserInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ITokenRepository extends CrudRepository<DAOToken, Long> {
    List<DAOToken> findAllByUserInfoAndIsValidToken(DAOUserInfo userInfo, int validStatus);

    DAOToken findByTokenAndIsValidToken(String token, int validStatus);
}
