package com.cpatos.edo.Implementation.cchaportdb;

import com.cpatos.edo.model.cchaportdb.CommudityDetail;
import com.cpatos.edo.payload.ResponseMessage;
import com.cpatos.edo.repository.cchaportdb.CommudityDetailRepository;
import com.cpatos.edo.service.cchaportdb.CommudityDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommydityDetailServiceImpl implements CommudityDetailService {
    ResponseMessage responseMessage;

    @Autowired
    CommudityDetailRepository commudityDetailRepository;

    @Override
    public List<CommudityDetail> getCommydityList(){

        return commudityDetailRepository.findAll();
    }
}
