package com.cpatos.edo.Implementation.cchaportdb;

import com.cpatos.edo.payload.DischargeListForMlo;
import com.cpatos.edo.repository.sparcsn4.DischargeListForMloRepository;
import com.cpatos.edo.service.sparcsn4.DischargeListForMloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Service
public class DischargeListForMloServicesImpl implements DischargeListForMloService {
    @Autowired
    DischargeListForMloRepository dischargeListForMloRepository;

    @Override
    public List getDischargeListForMlo(String from_date,String to_date){
        ArrayList<DischargeListForMlo> arrayList = new ArrayList<>();
        List resltDischargeList[]=dischargeListForMloRepository.getDischargeListForMlo(from_date,to_date);

        System.out.println("length:"+resltDischargeList.length);

        for (int i = 0; i < resltDischargeList.length; i++) {
            DischargeListForMlo dischargeListForMlo = new DischargeListForMlo();

            if (resltDischargeList[i].get(0) != null) {
                dischargeListForMlo.setId(resltDischargeList[i].get(0).toString());
            }

            if (resltDischargeList[i].get(2) != null) {
                dischargeListForMlo.setCont_size((String) resltDischargeList[i].get(2));
            }

            if (resltDischargeList[i].get(3) != null) {
                dischargeListForMlo.setHeight((BigDecimal) resltDischargeList[i].get(3));
            }

            if (resltDischargeList[i].get(4) != null) {
                dischargeListForMlo.setFreight_kind((String) resltDischargeList[i].get(4));
            }

            if (resltDischargeList[i].get(5) != null) {
                dischargeListForMlo.setTime_in((String) resltDischargeList[i].get(5));
            }

            if (resltDischargeList[i].get(6) != null) {
                dischargeListForMlo.setTime_out((String) resltDischargeList[i].get(6));
            }
         arrayList.add(dischargeListForMlo);
        }
        return arrayList;

    }
}
