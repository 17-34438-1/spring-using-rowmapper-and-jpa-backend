package com.cpatos.edo.Implementation.sparcsn4;

import com.cpatos.edo.payload.ExportContainerGateIn;
import com.cpatos.edo.payload.MloWisePreAdvisedContainer;
import com.cpatos.edo.payload.ResponseMessage;
import com.cpatos.edo.repository.sparcsn4.ExportContainerGateInRepository;
import com.cpatos.edo.repository.sparcsn4.MloWisePreAdvisedContainerRepository;
import com.cpatos.edo.service.sparcsn4.ExportContainerGateInService;
import com.cpatos.edo.service.sparcsn4.MloWisePreAdvisedContainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExportContainerGateInServiceImpl implements ExportContainerGateInService {
    ResponseMessage responseMessage;

    @Autowired
    ExportContainerGateInRepository exportContainerGateInRepository;
    @Override
    public List getExportContainerGateIn(String rotation) {
        ArrayList<ExportContainerGateIn> arrayList = new ArrayList<>();
        String rot = rotation.replace("_", "/");
        System.out.println("rotation :" + rotation);


        List rtnChkList[] = exportContainerGateInRepository.chkListQuery(rot);
        System.out.println("rtnChkList............:"+rtnChkList.length);
        if(rtnChkList.length>0){
            List rsltExportContainerGateIn[] = exportContainerGateInRepository.getExportContainerGateIn(rot);
            for (int i = 0; i < rsltExportContainerGateIn.length; i++) {
                ExportContainerGateIn exportContainerGateIn = new ExportContainerGateIn();

                if (rsltExportContainerGateIn[i].get(0) != null) {
                    exportContainerGateIn.setId(rsltExportContainerGateIn[i].get(0).toString());
                }
                if (rsltExportContainerGateIn[i].get(1) != null) {
                    exportContainerGateIn.setCont_size(rsltExportContainerGateIn[i].get(1).toString());
                }
                if (rsltExportContainerGateIn[i].get(2) != null) {
                    exportContainerGateIn.setHeight((BigDecimal) rsltExportContainerGateIn[i].get(2));
                }
                if (rsltExportContainerGateIn[i].get(3) != null) {
                    exportContainerGateIn.setSeal_nbr1(rsltExportContainerGateIn[i].get(3).toString());
                }
                if (rsltExportContainerGateIn[i].get(4) != null) {
                    exportContainerGateIn.setName(rsltExportContainerGateIn[i].get(4).toString());
                }
                if (rsltExportContainerGateIn[i].get(5) != null) {
                    exportContainerGateIn.setMLO(rsltExportContainerGateIn[i].get(5).toString());
                }

                if (rsltExportContainerGateIn[i].get(7) != null) {
                    exportContainerGateIn.setFreight_kind(rsltExportContainerGateIn[i].get(7).toString());
                }
                if (rsltExportContainerGateIn[i].get(10) != null) {
                    exportContainerGateIn.setTime_in(rsltExportContainerGateIn[i].get(10).toString());
                }


                arrayList.add(exportContainerGateIn);
            }
        }
        return arrayList;
    }
}
