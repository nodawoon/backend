package nodawoon.me_to_you.domain.user.domain.repository;

import nodawoon.me_to_you.domain.oauth.domain.OauthServerType;
import nodawoon.me_to_you.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByOauthServerTypeAndEmail(OauthServerType oauthServerType, String email);

    boolean existsByNickname(String nickname);

    boolean existsByEmailAndOauthServerType(String email, OauthServerType oauthServerType);

    Optional<User> findByShareUrl(String shareUrl);

    @Query(value = "SELECT u.nickname, u.profile_image_url FROM user u WHERE MATCH(u.nickname) AGAINST(:keyword)", nativeQuery = true)
    List<Object[]> searchByNickname(@Param("keyword") String keyword);

    Optional<User> findByNickname(String nickname);
}
