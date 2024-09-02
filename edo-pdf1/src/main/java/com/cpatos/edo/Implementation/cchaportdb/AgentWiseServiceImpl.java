package com.cpatos.edo.Implementation.cchaportdb;

import com.cpatos.edo.payload.AgentWiseVessel;
import com.cpatos.edo.payload.ResponseMessage;
import com.cpatos.edo.service.sparcsn4.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AgentWiseServiceImpl implements AgentService {
    ResponseMessage responseMessage;

    @Autowired
    com.cpatos.edo.repository.sparcsn4.AgentWiseVessel agentWiseVessel;

    @Override
    public List getAgentList(){
        List rsltAgentList[] = agentWiseVessel.getAgentWiseVessel();
        ArrayList<AgentWiseVessel> arrayList = new ArrayList<>();

        for(int i=0;i<rsltAgentList.length;i++){
            AgentWiseVessel  agentWiseVessel = new AgentWiseVessel();

            if(rsltAgentList[i].get(0) != null){
                agentWiseVessel.setName( rsltAgentList[i].get(0).toString());
            }
            if(rsltAgentList[i].get(1) != null){
                agentWiseVessel.setPhase(rsltAgentList[i].get(1).toString());
            }
            if(rsltAgentList[i].get(2) != null){
                agentWiseVessel.setAta(rsltAgentList[i].get(2).toString());
            }
            if(rsltAgentList[i].get(3) != null){
                agentWiseVessel.setAtd(rsltAgentList[i].get(3).toString());
            }
            if(rsltAgentList[i].get(4) != null){
                agentWiseVessel.setIb_vyg(rsltAgentList[i].get(4).toString());
            }
            arrayList.add(agentWiseVessel);
        }
        return arrayList;
    }
    public List getAgentWiseVessel(String rotation){
        String rot = rotation.replace("_", "/");
        System.out.println("rotation :" + rot);
        List rsltAgentList[] = agentWiseVessel.getAgentWiseVessel(rot);
        ArrayList<AgentWiseVessel> arrayList = new ArrayList<>();

        for(int i=0;i<rsltAgentList.length;i++){
            AgentWiseVessel  agentWiseVessel = new AgentWiseVessel();

            if(rsltAgentList[i].get(0) != null){
                agentWiseVessel.setCont_id( rsltAgentList[i].get(0).toString());
            }
            if(rsltAgentList[i].get(1) != null){
                agentWiseVessel.setVesselName(rsltAgentList[i].get(1).toString());
            }
            if(rsltAgentList[i].get(2) != null){
                agentWiseVessel.setRotation(rsltAgentList[i].get(2).toString());
            }
            if(rsltAgentList[i].get(3) != null){
                agentWiseVessel.setCont_size(rsltAgentList[i].get(3).toString());
            }
            if(rsltAgentList[i].get(4) != null){
                agentWiseVessel.setCont_height(rsltAgentList[i].get(4).toString());
            }
            if(rsltAgentList[i].get(5) != null){
                agentWiseVessel.setCont_mlo(rsltAgentList[i].get(5).toString());
            }
            if(rsltAgentList[i].get(6) != null){
                agentWiseVessel.setCont_status(rsltAgentList[i].get(6).toString());
            }
            if(rsltAgentList[i].get(7) != null){
                agentWiseVessel.setTransit_state(rsltAgentList[i].get(7).toString());
            }
            if(rsltAgentList[i].get(8) != null){
                agentWiseVessel.setCategory(rsltAgentList[i].get(8).toString());
            }

            arrayList.add(agentWiseVessel);
        }
        return arrayList;
    }

}
