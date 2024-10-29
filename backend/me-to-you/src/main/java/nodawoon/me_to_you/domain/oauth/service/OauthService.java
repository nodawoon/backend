package nodawoon.me_to_you.domain.oauth.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nodawoon.me_to_you.domain.oauth.authcode.AuthCodeRequestUrlProviderComposite;
import nodawoon.me_to_you.domain.oauth.client.OauthMemberClientComposite;
import nodawoon.me_to_you.domain.oauth.domain.OauthMember;
import nodawoon.me_to_you.domain.oauth.domain.OauthServerType;
import nodawoon.me_to_you.domain.oauth.domain.repository.OauthMemberRepository;
import nodawoon.me_to_you.domain.oauth.presentation.dto.response.OauthLoginResponse;
import nodawoon.me_to_you.domain.user.domain.RefreshToken;
import nodawoon.me_to_you.domain.user.domain.User;
import nodawoon.me_to_you.domain.user.domain.repository.RefreshTokenRepository;
import nodawoon.me_to_you.domain.user.domain.repository.UserRepository;
import nodawoon.me_to_you.global.exception.InvalidTokenException;
import nodawoon.me_to_you.global.exception.RefreshTokenExpiredException;
import nodawoon.me_to_you.global.security.JwtTokenProvider;
import nodawoon.me_to_you.global.utils.user.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OauthService implements OauthServiceUtils {

    private final AuthCodeRequestUrlProviderComposite authCodeRequestUrlProviderComposite;
    private final UserRepository userRepository;
    private final OauthMemberClientComposite oauthMemberClientComposite;
    private final OauthMemberRepository oauthMemberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserUtils userUtils;

    public String getAuthCodeRequestUrl(OauthServerType oauthServerType) {
        return authCodeRequestUrlProviderComposite.provide(oauthServerType);
    }

    public OauthLoginResponse signIn(OauthServerType oauthServerType, String authCode, HttpServletResponse response) {
        OauthMember oauthMember = oauthMemberClientComposite.fetch(oauthServerType, authCode);
        OauthMember saved = oauthMemberRepository.findByOauthServerTypeAndEmail(oauthMember.getOauthServerType(), oauthMember.getEmail())
                .orElseGet(() -> oauthMemberRepository.save(oauthMember));
        Optional<User> savedUser = userRepository.findByOauthServerTypeAndEmail(saved.getOauthServerType(), saved.getEmail());

        if (!savedUser.isPresent()) {
            return new OauthLoginResponse(saved, true);
        }

        String accessToken = jwtTokenProvider.generateAccessToken(savedUser.get().getId());
        String refreshToken = jwtTokenProvider.generateRefreshToken(savedUser.get().getId());

        refreshTokenRepository.findByUserId(savedUser.get().getId())
                .ifPresent(refreshTokenRepository::delete);

        refreshTokenRepository.save(new RefreshToken(refreshToken, savedUser.get().getId()));

        jwtTokenProvider.setHeaderAccessToken(response, accessToken);
        jwtTokenProvider.setHeaderRefreshToken(response, refreshToken);

        return new OauthLoginResponse(saved, false);
    }

    @Transactional
    public void tokenRefresh(HttpServletResponse response, String requestRefreshToken) {
        RefreshToken getRefreshToken = refreshTokenRepository.findByRefreshToken(requestRefreshToken).orElseThrow(() -> RefreshTokenExpiredException.EXCEPTION);

        Long userId = jwtTokenProvider.parseRefreshToken(requestRefreshToken);

        if (!userId.equals(getRefreshToken.getUserId())) {
            throw InvalidTokenException.EXCEPTION;
        }

        User user = userUtils.getUserById(userId);

        String accessToken = jwtTokenProvider.generateAccessToken(user.getId());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getId());

        refreshTokenRepository.deleteByRefreshToken(requestRefreshToken);
        refreshTokenRepository.save(new RefreshToken(refreshToken, userId));

        jwtTokenProvider.setHeaderAccessToken(response, accessToken);
        jwtTokenProvider.setHeaderRefreshToken(response, refreshToken);

    }

    @Override
    @Transactional
    public void deleteByUser(User user) {
        oauthMemberRepository.deleteByEmailAndOauthServerType(user.getEmail(), user.getOauthServerType());
    }
}
