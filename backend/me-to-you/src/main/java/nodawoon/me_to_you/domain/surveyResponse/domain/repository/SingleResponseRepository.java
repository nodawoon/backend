package nodawoon.me_to_you.domain.surveyResponse.domain.repository;

import nodawoon.me_to_you.domain.surveyResponse.domain.SingleResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SingleResponseRepository extends JpaRepository<SingleResponse, Long> {
    @Query("SELECT sr.responseDetail, COUNT(sr) AS responseDetailCount " +
            "FROM SingleResponse sr " +
            "JOIN sr.surveyResponse s " +              // SurveyResponse와 조인
            "JOIN s.respondent r " +                   // Respondent와 조인
            "JOIN r.user u " +                         // User와 조인
            "WHERE u.id = :userId " +                   // User의 id에 접근
            "GROUP BY sr.responseDetail " +
            "ORDER BY responseDetailCount DESC")
    List<Object[]> findTop3ResponseDetailsByUser(@Param("userId") Long userId);
}
