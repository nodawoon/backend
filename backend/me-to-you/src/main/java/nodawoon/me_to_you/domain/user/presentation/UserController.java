package nodawoon.me_to_you.domain.user.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nodawoon.me_to_you.domain.user.presentation.dto.request.CheckNicknameRequest;
import nodawoon.me_to_you.domain.user.presentation.dto.request.SignUpUserRequest;
import nodawoon.me_to_you.domain.user.presentation.dto.request.UpdateUserRequest;
import nodawoon.me_to_you.domain.user.presentation.dto.response.*;
import nodawoon.me_to_you.domain.user.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "유저", description = "유저 관련 API")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @SecurityRequirements
    @Operation(summary = "회원가입")
    @PostMapping("/signup")
    public UserProfileResponse signUpUser(
            @RequestBody @Valid SignUpUserRequest signUpUserRequest,
            HttpServletResponse response) {
        return userService.signUp(signUpUserRequest, response);
    }

    @Operation(summary = "로그아웃")
    @PostMapping("/logout")
    public void logout(HttpServletResponse response) {
        userService.logout(response);
    }

    @Operation(summary = "회원 정보 조회")
    @GetMapping
    public UserProfileResponse findUser() {
        return userService.findUser();
    }

    @Operation(summary = "회원 정보 수정")
    @PatchMapping
    public UserProfileResponse updateUser(@RequestBody @Valid UpdateUserRequest updateUserRequest) {
        return userService.updateUser(updateUserRequest);
    }

    @Operation(summary = "회원 탈퇴")
    @DeleteMapping
    public void withdraw(HttpServletResponse response) {
        userService.withdraw(response);
    }

    @Operation(summary = "닉네임 중복 체크")
    @PostMapping("/check-nickname")
    public CheckNicknameResponse checkNickname(@RequestBody CheckNicknameRequest nicknameCheckRequest) {
        return userService.checkNickname(nicknameCheckRequest);
    }

    @Operation(summary = "사용자 설문 URL 조회")
    @GetMapping("/share-url")
    public ShareUrlResponse shareUrl() {
        return userService.getShareUrl();
    }

    @Operation(summary = "UUID 값에 따른 주인장 닉네임 반환")
    @GetMapping("/return-nickname/{shareurl}")
    public ReturnNicknameByUUIDResponse returnNickname(@PathVariable String shareurl) {
        return userService.returnNicknameByUUID(shareurl);
    }

    @Operation(summary = "챗봇 페이지에서 닉네임 검색")
    @GetMapping("/search-nickname/{keyword}")
    public List<SearchNicknameResponse> searchNickname(@PathVariable String keyword) {
        return userService.returnNicknameByKeyword(keyword);
    }

    // 검색결과 후 해당 페이지로 이동하는 api
}
