package com.nutrix.command.application.services;

import com.nutrix.command.domain.Recommendation;
import com.nutrix.command.infra.IRecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class RecommendationCommandService extends Recommendation {

    @Autowired
    private IRecommendationRepository recommendationRepository;

    @Override
    @Transactional
    public Recommendation save(Recommendation recommendation) throws Exception {
        return recommendationRepository.save(recommendation);
    }

    @Override
    @Transactional
    public void delete(Integer id) throws Exception {
        recommendationRepository.deleteById(id);
    }
}