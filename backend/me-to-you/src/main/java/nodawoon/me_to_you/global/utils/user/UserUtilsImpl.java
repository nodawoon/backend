package nodawoon.me_to_you.global.utils.user;

import lombok.RequiredArgsConstructor;
import nodawoon.me_to_you.domain.user.domain.User;
import nodawoon.me_to_you.domain.user.domain.repository.UserRepository;
import nodawoon.me_to_you.global.exception.UserNotFoundException;
import nodawoon.me_to_you.global.utils.security.SecurityUtils;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserUtilsImpl implements UserUtils{

    private final UserRepository userRepository;

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> UserNotFoundException.EXCEPTION);
    }

    @Override
    public User getUserFromSecurityContext() {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        return getUserById(currentUserId);
    }
}
