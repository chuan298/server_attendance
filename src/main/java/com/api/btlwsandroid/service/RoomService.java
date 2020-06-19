package com.api.btlwsandroid.service;

import com.api.btlwsandroid.dao.entity.Room;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface RoomService {
    List<Room> findAll();
}