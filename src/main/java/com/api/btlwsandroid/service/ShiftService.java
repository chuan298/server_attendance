package com.api.btlwsandroid.service;

import com.api.btlwsandroid.dao.entity.Shift;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface ShiftService {
    List<Shift> findAll();
}
