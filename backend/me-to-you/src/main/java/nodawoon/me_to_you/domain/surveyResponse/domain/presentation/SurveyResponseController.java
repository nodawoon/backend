package nodawoon.me_to_you.domain.surveyResponse.domain.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nodawoon.me_to_you.domain.surveyResponse.domain.presentation.dto.request.SurveyResponseRequest;
import nodawoon.me_to_you.domain.surveyResponse.service.SurveyResponseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "설문 답변", description = "설문 답변 API")
@RestController
@RequestMapping("/api/surveys-responses")
@RequiredArgsConstructor
public class SurveyResponseController {

    private final SurveyResponseService surveyResponseService;

    @SecurityRequirements
    @Operation(summary = "기본 질문 답변 저장")
    @PostMapping
    public ResponseEntity<Void> createSurveyResponse(
            @RequestBody @Valid SurveyResponseRequest surveyResponseRequest) {
        surveyResponseService.createSurveyResponse(surveyResponseRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
