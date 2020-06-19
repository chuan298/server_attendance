package com.api.btlwsandroid.dao.repo;

import com.api.btlwsandroid.dao.entity.Student;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface StudentRepository extends PagingAndSortingRepository<Student, Integer> {
    Optional<Student> findByUsernameAndPassword(String username, String password);
}
