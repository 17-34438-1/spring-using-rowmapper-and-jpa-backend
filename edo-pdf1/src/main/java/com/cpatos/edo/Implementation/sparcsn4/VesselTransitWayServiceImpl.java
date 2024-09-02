package com.cpatos.edo.Implementation.sparcsn4;

import com.cpatos.edo.payload.ResponseMessage;
import com.cpatos.edo.payload.VesselTransitWay;
import com.cpatos.edo.repository.sparcsn4.VesselTransitWaysInfoRepository;
import com.cpatos.edo.service.sparcsn4.VesselTransitWayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VesselTransitWayServiceImpl implements VesselTransitWayService {
    ResponseMessage responseMessage;

    @Autowired
    VesselTransitWaysInfoRepository vesselTransitWaysInfoRepository;

    @Override
    public List getVesselTransitWay(String rotation) {

        ArrayList<VesselTransitWay> arrayList = new ArrayList<>();
        String rot = rotation.replace("_", "/");
        System.out.println("rotation :" + rotation);
        List rsltAgentList[] = vesselTransitWaysInfoRepository.getVesselTransitWaysInfo(rot);

        for (int i = 0; i < rsltAgentList.length; i++) {
            VesselTransitWay vesselTransitWay = new VesselTransitWay();
            if (rsltAgentList[i].get(0) != null) {
                vesselTransitWay.setVsl(rsltAgentList[i].get(0).toString());
            }
            if (rsltAgentList[i].get(1) != null) {
                vesselTransitWay.setIb_vyg(rsltAgentList[i].get(1).toString());
            }
            if (rsltAgentList[i].get(2) != null) {
                vesselTransitWay.setAta(rsltAgentList[i].get(2).toString());
            }
            if (rsltAgentList[i].get(3) != null) {
                vesselTransitWay.setEtd(rsltAgentList[i].get(3).toString());
            }
            if (rsltAgentList[i].get(4) != null) {
                vesselTransitWay.setBerth(rsltAgentList[i].get(4).toString());
            }
            if (rsltAgentList[i].get(5) != null) {
                vesselTransitWay.setShip_agent(rsltAgentList[i].get(5).toString());
            }
            if (rsltAgentList[i].get(6) != null) {
                vesselTransitWay.setBerth_op(rsltAgentList[i].get(6).toString());
            }
            if (rsltAgentList[i].get(7) != null) {
                String phase=rsltAgentList[i].get(7).toString();
                String result = phase.substring(2);

                System.out.println(result);  // Output: CLOSED

                vesselTransitWay.setPhase(result);
            }
            if (rsltAgentList[i].get(8) != null) {
                vesselTransitWay.setTransit_way(rsltAgentList[i].get(8).toString());
            }
            arrayList.add(vesselTransitWay);

        }
        return arrayList;
    }
}