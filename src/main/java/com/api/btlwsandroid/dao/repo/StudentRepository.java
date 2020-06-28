package com.api.btlwsandroid.dao.repo;

import com.api.btlwsandroid.dao.entity.Attendance;
import com.api.btlwsandroid.dao.entity.Student;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface StudentRepository extends PagingAndSortingRepository<Student, Integer> {
    Optional<Student> findByUsernameAndPassword(String username, String password);

    Optional<Student> findByUsername(String username);


//    @Modifying(clearAutomatically = true)
//    @Query( value = "UPDATE `student` SET `password`= :password WHERE `id`= :studentId", nativeQuery = true)
//    Integer changePassword(@Param("studentId") Integer studentId, @Param("password") String password);
}
