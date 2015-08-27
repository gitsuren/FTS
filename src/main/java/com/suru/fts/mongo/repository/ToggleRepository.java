package com.suru.fts.mongo.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.suru.fts.mongo.domain.FeatureGroup;
import com.suru.fts.mongo.domain.FeatureStatus;
import com.suru.fts.mongo.domain.ToggleSystem;
import com.suru.fts.mongo.domain.strategy.GroupStrategy;


public interface ToggleRepository extends MongoRepository<ToggleSystem, String>{

}
