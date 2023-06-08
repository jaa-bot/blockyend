package com.blocky.blockyend.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blocky.blockyend.entity.Log;
import com.blocky.blockyend.repository.LogRepository;

@Service
@Transactional
public class LogService {

    @Autowired
    LogRepository logRepository;

    public void save(Log log) {
        logRepository.save(log);
    }

    public List<Log> list() {
        return logRepository.findAll();
    }

    public Optional<Log> getOne(int id) {
        return logRepository.findById(id);
    }

    public void delete(int id) {
        logRepository.deleteById(id);
    }

    public boolean existsById(int id) {
        return logRepository.existsById(id);
    }

}
