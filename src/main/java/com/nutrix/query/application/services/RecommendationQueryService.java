package com.nutrix.query.application.services;

import com.nutrix.command.domain.Recommendation;
import com.nutrix.command.infra.IRecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class RecommendationQueryService extends Recommendation {

    @Autowired
    private IRecommendationRepository recommendationRepository;

    @Override
    public List<Recommendation> getAll() throws Exception {
        return recommendationRepository.findAll();
    }

    @Override
    public Optional<Recommendation> getById(Integer id) throws Exception {
        return recommendationRepository.findById(id);
    }

    @Override
    public List<Recommendation> findByName(String name) throws Exception
    {
        return recommendationRepository.findByName(name);
    }

    @Override
    public List<Recommendation> findBetweenDates(Date date1, Date date2) throws Exception {
        return recommendationRepository.findBetweenDates(date1, date2);
    }

    @Override
    public List<Recommendation> findByNutritionist(Integer nutritionist_id) throws Exception
    {
        return recommendationRepository.findByNutritionist(nutritionist_id);
    }
}