package com.payconiq.stockapp.repository;

import com.payconiq.stockapp.entity.StockEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends MongoRepository<StockEntity, Integer> {

}
