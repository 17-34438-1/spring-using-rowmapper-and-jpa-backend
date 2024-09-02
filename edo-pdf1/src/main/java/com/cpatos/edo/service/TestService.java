package com.cpatos.edo.service;

import com.cpatos.edo.model.sparcsn4.ActiveMqLock;
import com.cpatos.edo.repository.sparcsn4.ActiveMqLockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {
    @Autowired
    private ActiveMqLockRepository activeMqLockRepository;

    public List<ActiveMqLock> list(){
        return activeMqLockRepository.findAll();
    }
}
