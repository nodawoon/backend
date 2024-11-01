package nodawoon.me_to_you.domain.user.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nodawoon.me_to_you.domain.oauth.service.OauthServiceUtils;
import nodawoon.me_to_you.domain.user.domain.RefreshToken;
import nodawoon.me_to_you.domain.user.domain.User;
import nodawoon.me_to_you.domain.user.domain.repository.RefreshTokenRepository;
import nodawoon.me_to_you.domain.user.domain.repository.UserRepository;
import nodawoon.me_to_you.domain.user.exception.NicknameDuplicationException;
import nodawoon.me_to_you.domain.user.exception.UserDuplicationException;
import nodawoon.me_to_you.domain.user.presentation.dto.request.CheckNicknameRequest;
import nodawoon.me_to_you.domain.user.presentation.dto.request.SignUpUserRequest;
import nodawoon.me_to_you.domain.user.presentation.dto.request.UpdateUserRequest;
import nodawoon.me_to_you.domain.user.presentation.dto.response.CheckNicknameResponse;
import nodawoon.me_to_you.domain.user.presentation.dto.response.ShareUrlResponse;
import nodawoon.me_to_you.domain.user.presentation.dto.response.UserProfileResponse;
import nodawoon.me_to_you.global.security.JwtTokenProvider;
import nodawoon.me_to_you.global.utils.security.SecurityUtils;
import nodawoon.me_to_you.global.utils.user.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserUtils userUtils;
    private final OauthServiceUtils oauthServiceUtils;
    private static final String SHARE_URL = "http://localhost:3000/survey/responses/";

    // 회원 가입
    @Transactional
    public UserProfileResponse signUp(SignUpUserRequest signUpUserRequest, HttpServletResponse response) {
        User user = User.createUser(
                signUpUserRequest.nickname(),
                signUpUserRequest.email(),
                signUpUserRequest.profileImage(),
                signUpUserRequest.birthday(),
                UUID.randomUUID().toString(),
                signUpUserRequest.mbti(),
                signUpUserRequest.gender(),
                signUpUserRequest.oauthServerType()
        );

        validateSignUpRequest(signUpUserRequest);

        userRepository.save(user);

        String accessToken = jwtTokenProvider.generateAccessToken(user.getId());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getId());

        refreshTokenRepository.save(new RefreshToken(refreshToken, user.getId()));

        jwtTokenProvider.setHeaderRefreshToken(response, refreshToken);
        jwtTokenProvider.setHeaderAccessToken(response, accessToken);

        return new UserProfileResponse(user);
    }

    // 로그아웃
    @Transactional
    public void logout(HttpServletResponse response) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        refreshTokenRepository.deleteByUserId(currentUserId);

        jwtTokenProvider.setHeaderAccessTokenEmpty(response);
        jwtTokenProvider.setHeaderRefreshTokenEmpty(response);
    }

    // 회원 정보 조회
    public UserProfileResponse findUser() {
        User user = userUtils.getUserFromSecurityContext();

        return new UserProfileResponse(user);
    }

    //회원 정보 수정
    @Transactional
    public UserProfileResponse updateUser(UpdateUserRequest updateUserRequest) {
        User user = userUtils.getUserFromSecurityContext();

        if (userRepository.existsByNickname(updateUserRequest.nickname())) {
            throw NicknameDuplicationException.EXCEPTION;
        }

        user.updateUser(updateUserRequest.nickname(), updateUserRequest.profileImage(), updateUserRequest.mbti());

        return new UserProfileResponse(user);
    }

    // 회원 탈퇴
    @Transactional
    public void withdraw(HttpServletResponse response) {
        User user = userUtils.getUserFromSecurityContext();

        userRepository.delete(user);

        oauthServiceUtils.deleteByUser(user);

        refreshTokenRepository.deleteByUserId(user.getId());

        jwtTokenProvider.setHeaderAccessTokenEmpty(response);
        jwtTokenProvider.setHeaderRefreshTokenEmpty(response);
    }

    // 설문 공유 url
    public ShareUrlResponse getShareUrl() {
        User user = userUtils.getUserFromSecurityContext();
        String shareUrl = SHARE_URL + user.getShareUrl();

        return new ShareUrlResponse(shareUrl);
    }

    // 닉네임 중복 확인
    public CheckNicknameResponse checkNickname(CheckNicknameRequest checkNicknameRequest) {
        return new CheckNicknameResponse(userRepository.existsByNickname(checkNicknameRequest.nickname()));
    }

    // 회원가입 시 검증
    private void validateSignUpRequest(SignUpUserRequest signUpUserRequest) {
        if (userRepository.existsByEmailAndOauthServerType(signUpUserRequest.email(), signUpUserRequest.oauthServerType())) {
            throw UserDuplicationException.EXCEPTION;
        }

        if (userRepository.existsByNickname(signUpUserRequest.nickname())) {
            throw NicknameDuplicationException.EXCEPTION;
        }
    }
}
