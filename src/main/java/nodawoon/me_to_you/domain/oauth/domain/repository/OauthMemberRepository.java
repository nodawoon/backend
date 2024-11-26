package nodawoon.me_to_you.domain.oauth.domain.repository;

import nodawoon.me_to_you.domain.oauth.domain.OauthMember;
import nodawoon.me_to_you.domain.oauth.domain.OauthServerType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OauthMemberRepository extends JpaRepository<OauthMember, Long> {

    Optional<OauthMember> findByOauthServerTypeAndEmail(OauthServerType oauthServerType, String email);

    void deleteByEmailAndOauthServerType(String email, OauthServerType oauthServerType);
}
