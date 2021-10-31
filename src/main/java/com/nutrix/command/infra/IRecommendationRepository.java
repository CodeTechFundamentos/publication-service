package com.nutrix.command.infra;

import com.nutrix.command.domain.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface IRecommendationRepository extends JpaRepository<Recommendation, Integer> {

    public List<Recommendation> findByName(String name);

    @Query("Select b from Recommendation b where b.createdAt BETWEEN :date1 AND :date2")
    public List<Recommendation> findBetweenDates(@Param("date1") Date date1,
                                                 @Param("date2") Date date2);

    @Query("Select b from Recommendation b where b.nutritionistId = :nutritionist_id")
    public List<Recommendation> findByNutritionist(@Param("nutritionist_id") Integer nutritionist_id);
}


