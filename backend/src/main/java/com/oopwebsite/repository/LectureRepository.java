package com.oopwebsite.repository;

import com.oopwebsite.entity.Lecture;
import org.springframework.data.repository.CrudRepository;

public interface LectureRepository extends CrudRepository<Lecture,Integer> {
}
