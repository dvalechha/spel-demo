package com.example.spel.service;

import com.example.spel.model.PartnerConfig;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class PartnerConfigService {
    private final MongoTemplate mongoTemplate;

    public PartnerConfigService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public PartnerConfig getPartnerConfig(String partnerName) {
        return mongoTemplate.findOne(
                Query.query(Criteria.where("partnerName").is(partnerName)),
                PartnerConfig.class
        );
    }

}
