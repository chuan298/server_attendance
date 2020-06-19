package com.api.btlwsandroid.dao.repo;

import com.api.btlwsandroid.dao.entity.PracticeGroup;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PracticeGroupRepository extends PagingAndSortingRepository<PracticeGroup, Integer> {
    List<PracticeGroup> findAllBySubjectGroupId(Integer subjectGroup_id);
}
