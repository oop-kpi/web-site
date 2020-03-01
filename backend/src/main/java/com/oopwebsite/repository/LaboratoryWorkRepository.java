package com.oopwebsite.repository;

import com.oopwebsite.entity.LaboratoryWork;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LaboratoryWorkRepository extends MongoRepository<LaboratoryWork,String> {
    Optional<LaboratoryWork> findByName(String name);
    Optional<LaboratoryWork> findById(String id);
}
