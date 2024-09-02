package com.cpatos.edo.controller;

import com.cpatos.edo.model.sparcsn4.ActiveMqLock;
import com.cpatos.edo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private TestService testService;

    @GetMapping("/list")
    public List<ActiveMqLock> test(){
        return testService.list();
    }

}
