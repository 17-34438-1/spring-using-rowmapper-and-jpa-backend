package com.cpatos.edo.Implementation.cchaportdb;

import com.cpatos.edo.payload.RefUnlocCode;
import com.cpatos.edo.payload.ResponseMessage;
import com.cpatos.edo.repository.sparcsn4.PortCodeRepostiory;
import com.cpatos.edo.service.sparcsn4.PortCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class PortCodeServiceImpl implements PortCodeService {
    ResponseMessage responseMessage;

    @Autowired
    PortCodeRepostiory portCodeRepostiory;

    @Override
    public List getPortCodeList(){
        List rsltPostCode[] = portCodeRepostiory.getPortCodeInfo();
        ArrayList<RefUnlocCode> arrayList = new ArrayList<>();

        for(int i=0;i<rsltPostCode.length;i++){
            RefUnlocCode  refUnlocCode = new RefUnlocCode();

            if(rsltPostCode[i].get(0) != null){
            refUnlocCode.setId( rsltPostCode[i].get(0).toString());
            }
            if(rsltPostCode[i].get(1) != null){
                refUnlocCode.setName(rsltPostCode[i].get(1).toString());
            }
            if(rsltPostCode[i].get(2) != null){
                refUnlocCode.setCountry_Code(rsltPostCode[i].get(2).toString());
            }
            if(rsltPostCode[i].get(3) != null){
                refUnlocCode.setPort_Code(rsltPostCode[i].get(3).toString());
            }
            arrayList.add(refUnlocCode);
        }
         return arrayList;
    }
}
