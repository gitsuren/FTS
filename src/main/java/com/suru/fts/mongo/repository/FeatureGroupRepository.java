package com.suru.fts.mongo.repository;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.suru.fts.mongo.domain.FeatureGroup;

public interface FeatureGroupRepository extends
		MongoRepository<FeatureGroup, String> {

}
