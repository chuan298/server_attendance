package com.api.btlwsandroid.dao.repo;

import com.api.btlwsandroid.dao.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends PagingAndSortingRepository<Room, Integer> {
}
