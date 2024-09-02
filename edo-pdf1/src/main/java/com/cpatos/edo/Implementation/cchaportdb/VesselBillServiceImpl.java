package com.cpatos.edo.Implementation.cchaportdb;

import com.cpatos.edo.payload.VesselBill;
import com.cpatos.edo.repository.cchaportdb.VesselBillRepository;
import com.cpatos.edo.service.cchaportdb.VesselBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class VesselBillServiceImpl implements VesselBillService {
    @Autowired
    VesselBillRepository vesselBillRepository;



    @Override
    public List getVesselList() {
        ArrayList<VesselBill> arrayList = new ArrayList<>();
        List rsltVesselBill[] = vesselBillRepository.vesselBill();

        for (int i = 0; i < rsltVesselBill.length; i++) {
            VesselBill vesselBill = new VesselBill();



            if (rsltVesselBill[i].get(1) != null) {
               vesselBill.setFinalNumber(rsltVesselBill[i].get(1).toString());
            }
            if (rsltVesselBill[i].get(2) != null) {
                vesselBill.setRotation(rsltVesselBill[i].get(2).toString());
            }
            if (rsltVesselBill[i].get(3) != null) {
                vesselBill.setVsl_name(rsltVesselBill[i].get(3).toString());
            }
            if (rsltVesselBill[i].get(4) != null) {
                vesselBill.setBill_name(rsltVesselBill[i].get(4).toString());
            }
            if (rsltVesselBill[i].get(5) != null) {
                vesselBill.setAta(rsltVesselBill[i].get(5).toString());
            }
            if (rsltVesselBill[i].get(6) != null) {
                vesselBill.setAtd(rsltVesselBill[i].get(6).toString());
            }
            if (rsltVesselBill[i].get(7) != null) {
                vesselBill.setBerth(rsltVesselBill[i].get(7).toString());
            }
            if (rsltVesselBill[i].get(8) != null) {
                vesselBill.setAgent_code(rsltVesselBill[i].get(8).toString());
            }
            if (rsltVesselBill[i].get(10) != null) {
                vesselBill.setFlag(rsltVesselBill[i].get(10).toString());
            }
            arrayList.add(vesselBill);
        }
        return  arrayList;
    }
}
