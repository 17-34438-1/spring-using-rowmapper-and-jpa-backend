package com.cpatos.edo.Implementation.cchaportdb;
import com.cpatos.edo.payload.AgentWiseContainer;
import com.cpatos.edo.payload.ResponseMessage;
import com.cpatos.edo.repository.cchaportdb.AgentWiseContainerRepository;
import com.cpatos.edo.repository.sparcsn4.VesselAndTransitRepository;
import com.cpatos.edo.service.cchaportdb.AgentWiseContainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AgentWiseContainerImpl implements AgentWiseContainerService {
    ResponseMessage responseMessage;

    @Autowired
    AgentWiseContainerRepository agentWiseContainerInfo;

    @Autowired
    VesselAndTransitRepository vesselAndTransitRepository;

    @Override
    public List getAgentWiseContainer(String serch_by, String serch_value) {
        ArrayList<AgentWiseContainer> arrayList = new ArrayList<>();

        System.out.println("serch_by :" + serch_by);
        System.out.println("serch_value :" + serch_value);
        String transit_way="";
        String category="";
        String cont_id="";
        String vvd_gkey="";
        String vname="";
        if (serch_by.equals("rot")) {

            String rotation = serch_value.replace("_", "/");
            System.out.println("rotation :" + rotation);
            List rsltAgentList[] = agentWiseContainerInfo.getAgentContainerByRotation(rotation);

            for (int i = 0; i < rsltAgentList.length; i++) {
                AgentWiseContainer agentWiseContainer = new AgentWiseContainer();

                if (rsltAgentList[i].get(0) != null) {
                    cont_id= rsltAgentList[i].get(0).toString();
                    agentWiseContainer.setCont_id(cont_id);

                    List rsltVesselList[] = vesselAndTransitRepository.getTransitInfo(cont_id);
                    System.out.println("length:"+rsltVesselList.length);

                    for (int j = 0; j < rsltVesselList.length; j++) {
                        transit_way=rsltVesselList[j].get(0).toString();
                        category=rsltVesselList[j].get(1).toString();

                    }
                    agentWiseContainer.setTransit_Way(transit_way);
                    agentWiseContainer.setCategory(category);


                }


                if (rsltAgentList[i].get(1) != null) {
                    agentWiseContainer.setRotation(rsltAgentList[i].get(1).toString());
                }
                if (rsltAgentList[i].get(2) != null) {
                    agentWiseContainer.setCont_status(rsltAgentList[i].get(2).toString());
                }
                if (rsltAgentList[i].get(3) != null) {
                    agentWiseContainer.setCont_mlo(rsltAgentList[i].get(3).toString());
                }
                if (rsltAgentList[i].get(4) != null) {
                    agentWiseContainer.setCont_size(rsltAgentList[i].get(4).toString());
                }
                if (rsltAgentList[i].get(5) != null) {
                    agentWiseContainer.setCont_height(rsltAgentList[i].get(5).toString());
                }
                if (rsltAgentList[i].get(6) != null) {
                    agentWiseContainer.setAgent(rsltAgentList[i].get(6).toString());
                }
                if (rsltAgentList[i].get(7) != null) {
                    agentWiseContainer.setTransOp(rsltAgentList[i].get(7).toString());
                }

                if (rsltAgentList[i].get(8) != null) {

                    vvd_gkey=rsltAgentList[i].get(8).toString();
                    System.out.println("vvd_gkey:"+vvd_gkey);
                    agentWiseContainer.setVvd_gkey(vvd_gkey);
                    List rsltVesselList[] = vesselAndTransitRepository.getVesselList(vvd_gkey);
                    System.out.println("length:"+rsltVesselList.length);

                    for (int j = 0; j < rsltVesselList.length; j++) {
                        vname=rsltVesselList[j].get(0).toString();
                        System.out.println("vname:"+vname);
                    }
                    agentWiseContainer.setVessel_name(vname);

                    System.out.println("vname:"+vname);
                }

                arrayList.add(agentWiseContainer);
            }
          }

        else if (serch_by.equals("cont")) {


            List rsltAgentList[] = agentWiseContainerInfo.getAgentContainerByContid(serch_value);

            for (int i = 0; i < rsltAgentList.length; i++) {
                AgentWiseContainer agentWiseContainer = new AgentWiseContainer();

                if (rsltAgentList[i].get(0) != null) {
                    cont_id= rsltAgentList[i].get(0).toString();
                    agentWiseContainer.setCont_id(cont_id);

                    List rsltVesselList[] = vesselAndTransitRepository.getTransitInfo(cont_id);
                    System.out.println("length:"+rsltVesselList.length);

                    for (int j = 0; j < rsltVesselList.length; j++) {
                        transit_way=rsltVesselList[j].get(0).toString();
                        category=rsltVesselList[j].get(1).toString();

                    }
                    agentWiseContainer.setTransit_Way(transit_way);
                    agentWiseContainer.setCategory(category);


                }

                if (rsltAgentList[i].get(1) != null) {
                    agentWiseContainer.setRotation(rsltAgentList[i].get(1).toString());
                }
                if (rsltAgentList[i].get(2) != null) {
                    agentWiseContainer.setCont_status(rsltAgentList[i].get(2).toString());
                }
                if (rsltAgentList[i].get(3) != null) {
                    agentWiseContainer.setCont_mlo(rsltAgentList[i].get(3).toString());
                }
                if (rsltAgentList[i].get(4) != null) {
                    agentWiseContainer.setCont_size(rsltAgentList[i].get(4).toString());
                }
                if (rsltAgentList[i].get(5) != null) {
                    agentWiseContainer.setCont_height(rsltAgentList[i].get(5).toString());
                }
                if (rsltAgentList[i].get(6) != null) {
                    agentWiseContainer.setAgent(rsltAgentList[i].get(6).toString());
                }
                if (rsltAgentList[i].get(7) != null) {
                    agentWiseContainer.setTransOp(rsltAgentList[i].get(7).toString());
                }
                if (rsltAgentList[i].get(8) != null) {

                    vvd_gkey=rsltAgentList[i].get(8).toString();
                    System.out.println("vvd_gkey:"+vvd_gkey);
                    agentWiseContainer.setVvd_gkey(vvd_gkey);
                    List rsltVesselList[] = vesselAndTransitRepository.getVesselList(vvd_gkey);
                    System.out.println("length:"+rsltVesselList.length);

                    for (int j = 0; j < rsltVesselList.length; j++) {
                        vname=rsltVesselList[j].get(0).toString();
                        System.out.println("vname:"+vname);
                    }
                    agentWiseContainer.setVessel_name(vname);

                    System.out.println("vname:"+vname);
                }
                arrayList.add(agentWiseContainer);
            }
        }


        else if (serch_by.equals("mlo")) {
            List rsltAgentList[] = agentWiseContainerInfo.getAgentContainerByContMlo(serch_value);

            for (int i = 0; i < rsltAgentList.length; i++) {
                AgentWiseContainer agentWiseContainer = new AgentWiseContainer();

                if (rsltAgentList[i].get(0) != null) {
                    cont_id= rsltAgentList[i].get(0).toString();
                    agentWiseContainer.setCont_id(cont_id);

                    List rsltVesselList[] = vesselAndTransitRepository.getTransitInfo(cont_id);
                    System.out.println("length:"+rsltVesselList.length);

                    for (int j = 0; j < rsltVesselList.length; j++) {
                        transit_way=rsltVesselList[j].get(0).toString();
                        category=rsltVesselList[j].get(1).toString();

                    }
                    agentWiseContainer.setTransit_Way(transit_way);
                    agentWiseContainer.setCategory(category);


                }

                if (rsltAgentList[i].get(1) != null) {
                    agentWiseContainer.setRotation(rsltAgentList[i].get(1).toString());
                }
                if (rsltAgentList[i].get(2) != null) {
                    agentWiseContainer.setCont_status(rsltAgentList[i].get(2).toString());
                }
                if (rsltAgentList[i].get(3) != null) {
                    agentWiseContainer.setCont_mlo(rsltAgentList[i].get(3).toString());
                }
                if (rsltAgentList[i].get(4) != null) {
                    agentWiseContainer.setCont_size(rsltAgentList[i].get(4).toString());
                }
                if (rsltAgentList[i].get(5) != null) {
                    agentWiseContainer.setCont_height(rsltAgentList[i].get(5).toString());
                }
                if (rsltAgentList[i].get(6) != null) {
                    agentWiseContainer.setAgent(rsltAgentList[i].get(6).toString());
                }
                if (rsltAgentList[i].get(7) != null) {
                    agentWiseContainer.setTransOp(rsltAgentList[i].get(7).toString());
                }
                if (rsltAgentList[i].get(8) != null) {

                    vvd_gkey=rsltAgentList[i].get(8).toString();
                    System.out.println("vvd_gkey:"+vvd_gkey);
                    agentWiseContainer.setVvd_gkey(vvd_gkey);
                    List rsltVesselList[] = vesselAndTransitRepository.getVesselList(vvd_gkey);
                    System.out.println("length:"+rsltVesselList.length);

                    for (int j = 0; j < rsltVesselList.length; j++) {
                        vname=rsltVesselList[j].get(0).toString();
                        System.out.println("vname:"+vname);
                    }
                    agentWiseContainer.setVessel_name(vname);

                    System.out.println("vname:"+vname);
                }
                arrayList.add(agentWiseContainer);
            }
        }

        else if (serch_by.equals("pod")) {

            List rsltAgentList[] = agentWiseContainerInfo.getAgentContainerByPod(serch_value);

            for (int i = 0; i < rsltAgentList.length; i++) {
                AgentWiseContainer agentWiseContainer = new AgentWiseContainer();

                if (rsltAgentList[i].get(0) != null) {
                    cont_id= rsltAgentList[i].get(0).toString();
                    agentWiseContainer.setCont_id(cont_id);

                    List rsltVesselList[] = vesselAndTransitRepository.getTransitInfo(cont_id);
                    System.out.println("length:"+rsltVesselList.length);

                    for (int j = 0; j < rsltVesselList.length; j++) {
                        transit_way=rsltVesselList[j].get(0).toString();
                        category=rsltVesselList[j].get(1).toString();

                    }
                    agentWiseContainer.setTransit_Way(transit_way);
                    agentWiseContainer.setCategory(category);


                }

                if (rsltAgentList[i].get(1) != null) {
                    agentWiseContainer.setRotation(rsltAgentList[i].get(1).toString());
                }
                if (rsltAgentList[i].get(2) != null) {
                    agentWiseContainer.setCont_status(rsltAgentList[i].get(2).toString());
                }
                if (rsltAgentList[i].get(3) != null) {
                    agentWiseContainer.setCont_mlo(rsltAgentList[i].get(3).toString());
                }
                if (rsltAgentList[i].get(4) != null) {
                    agentWiseContainer.setCont_size(rsltAgentList[i].get(4).toString());
                }
                if (rsltAgentList[i].get(5) != null) {
                    agentWiseContainer.setCont_height(rsltAgentList[i].get(5).toString());
                }
                if (rsltAgentList[i].get(6) != null) {
                    agentWiseContainer.setAgent(rsltAgentList[i].get(6).toString());
                }
                if (rsltAgentList[i].get(7) != null) {
                    agentWiseContainer.setTransOp(rsltAgentList[i].get(7).toString());
                }
                if (rsltAgentList[i].get(8) != null) {

                    vvd_gkey=rsltAgentList[i].get(8).toString();
                    System.out.println("vvd_gkey:"+vvd_gkey);
                    agentWiseContainer.setVvd_gkey(vvd_gkey);
                    List rsltVesselList[] = vesselAndTransitRepository.getVesselList(vvd_gkey);
                    System.out.println("length:"+rsltVesselList.length);

                    for (int j = 0; j < rsltVesselList.length; j++) {
                        vname=rsltVesselList[j].get(0).toString();
                        System.out.println("vname:"+vname);
                    }
                    agentWiseContainer.setVessel_name(vname);

                    System.out.println("vname:"+vname);
                }
                arrayList.add(agentWiseContainer);
            }
        }
        else{

            List rsltAgentList[] = agentWiseContainerInfo.getAgentContainerByTransOp(serch_value);

            for (int i = 0; i < rsltAgentList.length; i++) {
                AgentWiseContainer agentWiseContainer = new AgentWiseContainer();

                if (rsltAgentList[i].get(0) != null) {
                    cont_id= rsltAgentList[i].get(0).toString();
                    agentWiseContainer.setCont_id(cont_id);

                    List rsltVesselList[] = vesselAndTransitRepository.getTransitInfo(cont_id);
                    System.out.println("length:"+rsltVesselList.length);

                    for (int j = 0; j < rsltVesselList.length; j++) {
                        transit_way=rsltVesselList[j].get(0).toString();
                        category=rsltVesselList[j].get(1).toString();

                    }
                    agentWiseContainer.setTransit_Way(transit_way);
                    agentWiseContainer.setCategory(category);


                }

                if (rsltAgentList[i].get(1) != null) {
                    agentWiseContainer.setRotation(rsltAgentList[i].get(1).toString());
                }
                if (rsltAgentList[i].get(2) != null) {
                    agentWiseContainer.setCont_status(rsltAgentList[i].get(2).toString());
                }
                if (rsltAgentList[i].get(3) != null) {
                    agentWiseContainer.setCont_mlo(rsltAgentList[i].get(3).toString());
                }
                if (rsltAgentList[i].get(4) != null) {
                    agentWiseContainer.setCont_size(rsltAgentList[i].get(4).toString());
                }
                if (rsltAgentList[i].get(5) != null) {
                    agentWiseContainer.setCont_height(rsltAgentList[i].get(5).toString());
                }
                if (rsltAgentList[i].get(6) != null) {
                    agentWiseContainer.setAgent(rsltAgentList[i].get(6).toString());
                }
                if (rsltAgentList[i].get(7) != null) {
                    agentWiseContainer.setTransOp(rsltAgentList[i].get(7).toString());
                }
                if (rsltAgentList[i].get(8) != null) {

                    vvd_gkey=rsltAgentList[i].get(8).toString();
                    System.out.println("vvd_gkey:"+vvd_gkey);
                    agentWiseContainer.setVvd_gkey(vvd_gkey);
                    List rsltVesselList[] = vesselAndTransitRepository.getVesselList(vvd_gkey);
                    System.out.println("length:"+rsltVesselList.length);

                    for (int j = 0; j < rsltVesselList.length; j++) {
                        vname=rsltVesselList[j].get(0).toString();
                        System.out.println("vname:"+vname);
                    }
                    agentWiseContainer.setVessel_name(vname);

                    System.out.println("vname:"+vname);
                }
                arrayList.add(agentWiseContainer);
            }
        }
        return arrayList;

    }


}
