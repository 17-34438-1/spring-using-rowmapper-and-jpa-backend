package com.cpatos.edo.Implementation.cchaportdb;

import com.cpatos.edo.model.cchaportdb.OffDoc;
import com.cpatos.edo.payload.ResponseMessage;
import com.cpatos.edo.repository.cchaportdb.OffDocRepository;
import com.cpatos.edo.service.cchaportdb.OffDocInformationService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OffDocInformationServiceImpl implements OffDocInformationService {
    ResponseMessage responseMessage;

    @Autowired
    OffDocRepository offDocRepository;

    @Override
    public List<OffDoc> getOffDocList(){

       return offDocRepository.findAll();
    }
}
