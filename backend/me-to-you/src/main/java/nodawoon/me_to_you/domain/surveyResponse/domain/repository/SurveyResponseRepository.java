package nodawoon.me_to_you.domain.surveyResponse.domain.repository;

import nodawoon.me_to_you.domain.result.domain.Respondent;
import nodawoon.me_to_you.domain.surveyResponse.domain.SurveyResponse;
import nodawoon.me_to_you.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SurveyResponseRepository extends JpaRepository<SurveyResponse, Long> {
    @Query("SELECT sr FROM SurveyResponse sr JOIN sr.respondent r WHERE r.user = :user AND sr.surveyQuestionId = :surveyQuestionId ORDER BY sr.id")
    List<SurveyResponse> findByUserAndSurveyQuestionIdOrderById(@Param("user") User user, @Param("surveyQuestionId") Long surveyQuestionId);

    List<SurveyResponse> findByRespondentOrderBySurveyQuestionIdAsc(Respondent respondent);

    @Query(value = "SELECT sr1.survey_question_id, sr1.response, (COUNT(*) * 100.0 / " +
            "(SELECT COUNT(*) " +
            " FROM survey_responses sr2 " +
            " JOIN respondents r ON sr2.respondent_id = r.respondent_id " +
            " JOIN user u ON r.user_id = u.user_id " +
            " WHERE sr2.survey_question_id = 1 AND u.user_id = :userId)) AS percentage " +
            "FROM survey_responses sr1 " +
            "JOIN respondents r ON sr1.respondent_id = r.respondent_id " +
            "JOIN user u ON r.user_id = u.user_id " +
            "WHERE sr1.survey_question_id = 1 AND u.user_id = :userId " +
            "GROUP BY sr1.response ",
            nativeQuery = true)
    List<Object[]> percentResponsesForQuestions1(@Param("userId") Long userId);

    @Query(value = "SELECT sr5.survey_question_id, sr5.response, (COUNT(*) * 100.0 / " +
            "(SELECT COUNT(*) " +
            " FROM survey_responses sr6 " +
            " JOIN respondents r ON sr6.respondent_id = r.respondent_id " +
            " JOIN user u ON r.user_id = u.user_id " +
            " WHERE sr6.survey_question_id = 5 AND u.user_id = :userId)) AS percentage " +
            "FROM survey_responses sr5 " +
            "JOIN respondents r ON sr5.respondent_id = r.respondent_id " +
            "JOIN user u ON r.user_id = u.user_id " +
            "WHERE sr5.survey_question_id = 5 AND u.user_id = :userId " +
            "GROUP BY sr5.response",
            nativeQuery = true)
    List<Object[]> percentResponsesForQuestions5(@Param("userId") Long userId);
}
