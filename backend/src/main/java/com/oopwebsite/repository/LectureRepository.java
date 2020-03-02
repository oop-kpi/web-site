package com.oopwebsite.repository;

import com.oopwebsite.entity.Lecture;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureRepository extends MongoRepository<Lecture,String> {
}
