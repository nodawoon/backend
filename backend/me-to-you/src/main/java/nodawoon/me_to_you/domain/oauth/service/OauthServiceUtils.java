package nodawoon.me_to_you.domain.oauth.service;

import nodawoon.me_to_you.domain.user.domain.User;

public interface OauthServiceUtils {
    void deleteByUser(User user);
}
