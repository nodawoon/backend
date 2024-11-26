package nodawoon.me_to_you.global.security.auth;

import lombok.RequiredArgsConstructor;
import nodawoon.me_to_you.domain.user.domain.User;
import nodawoon.me_to_you.domain.user.domain.repository.UserRepository;
import nodawoon.me_to_you.global.exception.UserNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        User user =
                userRepository
                        .findById(Long.valueOf(id))
                        .orElseThrow(() -> UserNotFoundException.EXCEPTION);
        return new AuthDetails(user.getId().toString());
    }
}
