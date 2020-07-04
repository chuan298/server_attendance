package com.api.btlwsandroid.dao.repo;

import com.api.btlwsandroid.dao.entity.Attendance;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends PagingAndSortingRepository<Attendance, Integer> {
    @Query( value = "select * from attendance att " +
            "join student_course sc " +
            "   on att.id_student_course = sc.id " +
            "join subject_group sg " +
            "   on sc.id_subject_group = sg.id " +
            "where sg.id_subject = :subjectId and sc.id_student = :studentId", nativeQuery = true)
    List<Attendance> findAllByStudentIdAndSubjectId(@Param("subjectId") String subjectId, @Param("studentId") String studentId);

    @Query( value = "select * from attendance att " +
            "join student_course sc " +
            "   on att.id_student_course = sc.id " +
            "where sc.id_student = :studentId", nativeQuery = true)
    List<Attendance> findAllByStudentId(@Param("studentId")String studentId);

    @Query( value = "SELECT  * FROM attendance att, student_course sc, schedule sch WHERE sc.id_student = :studentId AND sc.id = att.id_student_course AND sch.id = att.id_schedule", nativeQuery = true)
    List<Attendance> findAllAttendanceOfScheduleOfStudent(@Param("studentId")String studentId);
}
