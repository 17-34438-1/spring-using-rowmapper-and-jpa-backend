package com.cpatos.edo.Implementation.cchaportdb;

import com.cpatos.edo.payload.OffDocWiseContainer;
import com.cpatos.edo.payload.ResponseMessage;
import com.cpatos.edo.repository.cchaportdb.OffDocWiseContainerRepository;
import com.cpatos.edo.repository.sparcsn4.VesselAndTransitRepository;
import com.cpatos.edo.service.cchaportdb.OffDocWiseContainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OffDocWiseContainerServiceImpl implements OffDocWiseContainerService {

    ResponseMessage responseMessage;

    @Autowired
    OffDocWiseContainerRepository offDocWiseContainerRepository;
    @Autowired
    VesselAndTransitRepository vesselAndTransitRepository;
    @Override
    public List getOffDocWiseContainer(String rot) {
            ArrayList<OffDocWiseContainer> arrayList = new ArrayList<>();
            String rotation = rot.replace("_", "/");
            System.out.println("rotation :" + rotation);
            List rsltAgentList[] = offDocWiseContainerRepository.getOffDocWiseContainer(rotation);
            String vvd_gkey="";
            String vname="";
            String cont_id="";
            String transit_way="";
            String category="";
            for (int i = 0; i < rsltAgentList.length; i++) {
                OffDocWiseContainer offDocWiseContainer = new OffDocWiseContainer();
                if (rsltAgentList[i].get(0) != null) {
                    cont_id= rsltAgentList[i].get(0).toString();
                    offDocWiseContainer.setCont_id(cont_id);
                    List rsltVesselList[] = vesselAndTransitRepository.getTransitInfo(cont_id);
                    System.out.println("length:"+rsltVesselList.length);

                    for (int j = 0; j < rsltVesselList.length; j++) {
                        transit_way=rsltVesselList[j].get(0).toString();
                        category=rsltVesselList[j].get(1).toString();

                    }
                    offDocWiseContainer.setTransit_way(transit_way);
                    offDocWiseContainer.setCategory(category);

                    System.out.println("category:"+category);
                    System.out.println("transit_way:"+transit_way);


                }
                if (rsltAgentList[i].get(1) != null) {
                    offDocWiseContainer.setRotation(rsltAgentList[i].get(1).toString());
                }
                if (rsltAgentList[i].get(2) != null) {
                    offDocWiseContainer.setCont_status(rsltAgentList[i].get(2).toString());
                }
                if (rsltAgentList[i].get(3) != null) {
                    offDocWiseContainer.setCont_mlo(rsltAgentList[i].get(3).toString());
                }
                if (rsltAgentList[i].get(4) != null) {
                    offDocWiseContainer.setCont_size(rsltAgentList[i].get(4).toString());
                }
                if (rsltAgentList[i].get(5) != null) {
                    offDocWiseContainer.setCont_height(rsltAgentList[i].get(5).toString());
                }
                if (rsltAgentList[i].get(6) != null) {
                    offDocWiseContainer.setAgent(rsltAgentList[i].get(6).toString());
                }
                if (rsltAgentList[i].get(7) != null) {
                    offDocWiseContainer.setTransOp(rsltAgentList[i].get(7).toString());
                }
                if (rsltAgentList[i].get(8) != null) {

                    vvd_gkey=rsltAgentList[i].get(8).toString();
                    System.out.println("vvd_gkey:"+vvd_gkey);
                    offDocWiseContainer.setVvd_gkey(vvd_gkey);
                    List rsltVesselList[] = vesselAndTransitRepository.getVesselList(vvd_gkey);
                    System.out.println("length:"+rsltVesselList.length);

                    for (int j = 0; j < rsltVesselList.length; j++) {
                        vname=rsltVesselList[j].get(0).toString();
                        System.out.println("vname:"+vname);
                    }
                    offDocWiseContainer.setVessel_name(vname);

                    System.out.println("vname:"+vname);
                }


                arrayList.add(offDocWiseContainer);


            }
            return arrayList;

    }
    }

