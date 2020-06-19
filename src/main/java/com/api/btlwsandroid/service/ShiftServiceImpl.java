package com.api.btlwsandroid.service;

import com.api.btlwsandroid.dao.entity.Shift;
import com.api.btlwsandroid.dao.repo.ShiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ShiftServiceImpl implements ShiftService {

    @Autowired
    private ShiftRepository shiftRepository;

    @Override
    public List<Shift> findAll() {
        return (List<Shift>) shiftRepository.findAll();
    }
}
