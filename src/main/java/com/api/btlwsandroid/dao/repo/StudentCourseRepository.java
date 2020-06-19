package com.api.btlwsandroid.dao.repo;


import com.api.btlwsandroid.dao.entity.StudentCourse;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface StudentCourseRepository extends PagingAndSortingRepository<StudentCourse, Integer> {
    List<StudentCourse> findAllByStudentId(Integer id);
}
