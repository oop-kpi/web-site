package com.oopwebsite.repository;

import com.oopwebsite.entity.LaboratoryWork;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface LaboratoryWorkRepository extends CrudRepository<LaboratoryWork,Integer> {
    Optional<LaboratoryWork> findByName(String name);
}
