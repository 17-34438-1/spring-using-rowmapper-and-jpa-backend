package com.cpatos.edo.service.sparcsn4;

import org.springframework.stereotype.Service;

import java.util.List;
public interface DischargeListForMloService {
    public List getDischargeListForMlo(String from_date,String to_date);
}
