package com.api.btlwsandroid.dao.repo;

import com.api.btlwsandroid.dao.entity.Schedule;
import com.api.btlwsandroid.dao.entity.SubjectGroup;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends PagingAndSortingRepository<Schedule, Integer> {

    Optional<Schedule> findBySubjectGroupIdAndPracticeGroupId(Integer subjectGroup_id, Integer practiceGroup_id);

    @Query(value = "SELECT * FROM `schedule` sh, subject_group su, student_course st WHERE st.id_student = :studentId AND st.id_subject_group = su.id AND sh.id_subject_group = su.id", nativeQuery = true)
    List<Schedule> fillAllSubjectGroupScheduleOfStudent(@Param("studentId") String studentId);
}
