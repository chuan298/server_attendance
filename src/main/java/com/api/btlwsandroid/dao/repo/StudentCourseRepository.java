package com.api.btlwsandroid.dao.repo;


import com.api.btlwsandroid.dao.entity.StudentCourse;
import jdk.nashorn.internal.runtime.options.Option;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentCourseRepository extends PagingAndSortingRepository<StudentCourse, Integer> {
    List<StudentCourse> findAllByStudentId(Integer id);
    Optional<StudentCourse> findByStudentUsernameAndSubjectGroupId(String student_username, Integer subjectGroup_id);
}
