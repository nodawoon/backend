package nodawoon.me_to_you.domain.oauth.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import nodawoon.me_to_you.domain.oauth.domain.OauthServerType;
import nodawoon.me_to_you.domain.oauth.presentation.dto.request.TokenRefreshRequest;
import nodawoon.me_to_you.domain.oauth.presentation.dto.response.OauthLoginResponse;
import nodawoon.me_to_you.domain.oauth.service.OauthService;
import org.springframework.web.bind.annotation.*;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.PATH;

@Tag(name = "인증", description = "인증 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/oauth")
public class OauthController {

    private final OauthService oauthService;

    @SecurityRequirements
    @Operation(summary = "소셜 로그인")
    @GetMapping("/login/{oauthServerType}")
    public OauthLoginResponse login(
            @Parameter(description = "소셜 로그인 Type", in = PATH)
            @PathVariable OauthServerType oauthServerType,
            @RequestParam(value = "code") String code,
            HttpServletResponse response
    ) {
        return oauthService.signIn(oauthServerType, code, response);
    }

    @SecurityRequirements
    @Operation(summary = "refreshToken 재발급")
    @PostMapping("/refresh")
    public void refreshingToken(@RequestBody TokenRefreshRequest tokenRefreshRequest, HttpServletResponse response) {
        oauthService.tokenRefresh(response, tokenRefreshRequest.refreshToken());
    }
}
