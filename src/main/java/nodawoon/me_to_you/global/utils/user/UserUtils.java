package nodawoon.me_to_you.global.utils.user;

import nodawoon.me_to_you.domain.user.domain.User;

public interface UserUtils {
    User getUserById(Long id);
    User getUserFromSecurityContext();
}
