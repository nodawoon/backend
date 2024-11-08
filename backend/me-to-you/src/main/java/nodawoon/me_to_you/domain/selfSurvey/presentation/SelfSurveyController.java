package nodawoon.me_to_you.domain.selfSurvey.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import nodawoon.me_to_you.domain.selfSurvey.presentation.dto.request.CreateSelfSurveyRequest;
import nodawoon.me_to_you.domain.selfSurvey.presentation.dto.request.UpdateSelfSurveyRequest;
import nodawoon.me_to_you.domain.selfSurvey.presentation.dto.response.SelfSurveyResponse;
import nodawoon.me_to_you.domain.selfSurvey.presentation.dto.response.UserSelfSurveyStatusResponse;
import nodawoon.me_to_you.domain.selfSurvey.service.SelfSurveyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "30문 30답", description = "30문 30답 관련 API")
@RestController
@RequestMapping("/api/self-survey")
@RequiredArgsConstructor
public class SelfSurveyController {

    private final SelfSurveyService selfSurveyService;

    @Operation(summary = "질문 저장하기")
    @PostMapping
    public List<SelfSurveyResponse> createSelfSurvey(@RequestBody List<CreateSelfSurveyRequest> createCommentRequest) {
        return selfSurveyService.createSelfSurvey(createCommentRequest);
    }

    @Operation(summary = "질문 리스트 조회하기")
    @GetMapping("/list")
    public List<SelfSurveyResponse> getSelfSurveys() {
        return selfSurveyService.getSelfSurveys();
    }

    @Operation(summary = "해당 질문 수정하기")
    @PatchMapping("/{selfSurveyId}")
    public SelfSurveyResponse updateSelfSurvey(@PathVariable Long selfSurveyId, @RequestBody UpdateSelfSurveyRequest updateCommentRequest) {
        return selfSurveyService.updateSelfSurvey(selfSurveyId, updateCommentRequest);
    }

    @Operation(summary = "30문 30답 유무 조회")
    @GetMapping("/exists")
    public UserSelfSurveyStatusResponse existsSelfSurveyStatus() {
        return selfSurveyService.checkUserSelfSurveyStatus();
    }
}
