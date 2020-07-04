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

    @Query(value = "SELECT * FROM schedule sch, subject_group sg, student_course sc, practice_group pg WHERE sc.id_student = :studentId AND sc.id_subject_group = sg.id AND sg.id = pg.id_subject_group AND sch.id_practice_group = pg.id", nativeQuery = true)
    List<Schedule> findAllSubjectGroupScheduleOfStudent(@Param("studentId") String studentId);

    @Query(value = "SELECT * FROM schedule sch, subject_group sg, student_course sc, practice_group pg " +
            "WHERE sc.id_student = :studentId AND sc.id_subject_group = sg.id AND sg.id = pg.id_subject_group AND sch.id_practice_group = pg.id " +
            "AND pg.day_practice = :schoolDay AND pg.week_practice LIKE %:schoolWeek% AND pg.id_practice_shift = :schoolShiftId", nativeQuery = true)
    List<Schedule> findAllCurrentSchedulePractice(@Param("studentId") String studentId, @Param("schoolDay") String schoolDay, @Param("schoolWeek") String schoolWeek, @Param("schoolShiftId") String schoolShiftId);

    @Query(value = "SELECT * FROM schedule sch, subject_group sg, student_course sc, practice_group pg " +
            "WHERE sc.id_student = :studentId AND sc.id_subject_group = sg.id AND sg.id = pg.id_subject_group AND sch.id_practice_group = pg.id " +
            "AND sg.day = :schoolDay AND sg.week_1 LIKE %:schoolWeek% AND sg.id_shift_1 = :schoolShiftId", nativeQuery = true)
    List<Schedule> findAllCurrentScheduleShift1(@Param("studentId") String studentId, @Param("schoolDay") String schoolDay, @Param("schoolWeek") String schoolWeek, @Param("schoolShiftId") String schoolShiftId);

    @Query(value = "SELECT * FROM schedule sch, subject_group sg, student_course sc, practice_group pg " +
            "WHERE sc.id_student = :studentId AND sc.id_subject_group = sg.id AND sg.id = pg.id_subject_group AND sch.id_practice_group = pg.id " +
            "AND sg.day = :schoolDay AND sg.week_2 LIKE %:schoolWeek% AND sg.id_shift_2 = :schoolShiftId", nativeQuery = true)
    List<Schedule> findAllCurrentScheduleShift2(@Param("studentId") String studentId, @Param("schoolDay") String schoolDay, @Param("schoolWeek") String schoolWeek, @Param("schoolShiftId") String schoolShiftId);
}
