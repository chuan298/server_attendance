package com.api.btlwsandroid.dao.repo;

import com.api.btlwsandroid.dao.entity.SubjectGroup;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SubjectGroupRepository extends PagingAndSortingRepository<SubjectGroup, Integer> {

    @Query(value = "SELECT * FROM subject_group sg JOIN student_course sc ON sg.id = sc.id_subject_group JOIN student s ON sc.id_student = s.id WHERE s.id = :id", nativeQuery = true)
    List<SubjectGroup> fillAllSubjectGroupsOfStudent(@Param("id") String id);

}
