package nodawoon.me_to_you.domain.surveyResponse.domain.repository;

import nodawoon.me_to_you.domain.result.domain.Respondent;
import nodawoon.me_to_you.domain.surveyResponse.domain.SurveyResponse;
import nodawoon.me_to_you.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SurveyResponseRepository extends JpaRepository<SurveyResponse, Long> {
    @Query("SELECT sr FROM SurveyResponse sr JOIN sr.respondent r WHERE r.user = :user AND sr.surveyQuestionId = :surveyQuestionId")
    List<SurveyResponse> findByUserAndSurveyQuestionIdOrderById(@Param("user") User user, @Param("surveyQuestionId") Long surveyQuestionId);

    List<SurveyResponse> findByRespondentOrderBySurveyQuestionIdAsc(Respondent respondent);

    @Query(value = "SELECT 'question1', sr1.response, " +
            "(COUNT(sr1) * 100.0 / (SELECT COUNT(sr2) FROM survey_responses sr2 WHERE sr2.survey_question_id = 1 AND sr2.respondent.user = :user)) AS percentage " +
            "FROM survey_responses sr1 " +
            "WHERE sr1.survey_question_id = 1 AND sr1.respondent.user = :user " +
            "GROUP BY sr1.response " +
            "UNION ALL " +
            "SELECT 'question5', sr5.response, " +
            "(COUNT(sr5) * 100.0 / (SELECT COUNT(sr6) FROM survey_responses sr6 WHERE sr6.survey_question_id = 5 AND sr6.respondent.user = :user)) AS percentage " +
            "FROM survey_responses sr5 " +
            "WHERE sr5.survey_question_id = 5 AND sr5.respondent.user = :user " +
            "GROUP BY sr5.response",
            nativeQuery = true)
    List<Object[]> percentResponsesForQuestions(@Param("user") User user);

    // 수정 전
//    @Query("SELECT sr FROM SurveyResponse sr JOIN sr.respondent r WHERE r.user = :user")
//    List<SurveyResponse> findByUser(@Param("user") User user);
//    List<SurveyResponse> findBySurveyQuestionIdAndUserOrderById(Long surveyQuestionId, User user);
}
