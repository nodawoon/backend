package nodawoon.me_to_you.domain.result.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import nodawoon.me_to_you.domain.result.presentation.dto.response.RespondentResponse;
import nodawoon.me_to_you.domain.result.presentation.dto.response.ResultByRIdResponse;
import nodawoon.me_to_you.domain.result.service.ResultService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "설문 결과", description = "설문 결과 API")
@RestController
@RequestMapping("/api/survey-results")
@RequiredArgsConstructor
public class ResultController {

    private final ResultService resultService;

    @SecurityRequirements
    @Operation(summary = "질문 답변자 리스트 반환")
    @GetMapping("/respondent-list")
    public List<RespondentResponse> respondentList() {
        return resultService.getRespondentList();
    }

    @SecurityRequirements
    @Operation(summary = "질문 응답자별 답변 리스트 반환")
    @GetMapping("/{respondentId}")
    public List<ResultByRIdResponse> resultByRIdResponseList(@PathVariable long respondentId) {
        return resultService.getResultByRIDList(respondentId);}
}
