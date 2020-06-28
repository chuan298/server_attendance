package com.api.btlwsandroid.dao.repo;

import com.api.btlwsandroid.dao.entity.Schedule;
import com.api.btlwsandroid.dao.entity.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface ShiftRepository extends PagingAndSortingRepository<Shift, Integer> {

    List<Shift> findAllByStartLessonAndEndLesson(Integer startLesson, Integer endLesson);
}
