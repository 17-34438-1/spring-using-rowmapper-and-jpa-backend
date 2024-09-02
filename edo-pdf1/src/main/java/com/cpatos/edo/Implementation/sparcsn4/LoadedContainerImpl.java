package com.cpatos.edo.Implementation.sparcsn4;

import com.cpatos.edo.payload.LoadedContainer;
import com.cpatos.edo.repository.sparcsn4.LoadedContainerRepository;
import com.cpatos.edo.service.sparcsn4.LoadedContainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class LoadedContainerImpl implements LoadedContainerService {

    @Autowired
    LoadedContainerRepository loadedContainerRepository;
    @Override
    public List getLoadedContainer(String rotation,String from_date,String to_date) {
        ArrayList<LoadedContainer> arrayList = new ArrayList<>();
        String rot = rotation.replace("_", "/");
        System.out.println("rotation :" + rotation);


        List rsltLoadedContainer[] = loadedContainerRepository.LoadedContainer(rot,from_date,to_date);
        for (int i = 0; i < rsltLoadedContainer.length; i++) {
            LoadedContainer loadedContainer = new LoadedContainer();

            if (rsltLoadedContainer[i].get(1) != null) {
                loadedContainer.setCONT_ID(rsltLoadedContainer[i].get(1).toString());
            }
            if (rsltLoadedContainer[i].get(3) != null) {
                loadedContainer.setMl(rsltLoadedContainer[i].get(3).toString());
            }
            if (rsltLoadedContainer[i].get(5) != null) {
                loadedContainer.setCONT_SIZE(rsltLoadedContainer[i].get(5).toString());
            }
            if (rsltLoadedContainer[i].get(7) != null) {
                loadedContainer.setISO(rsltLoadedContainer[i].get(7).toString());
            }
            if (rsltLoadedContainer[i].get(8) != null) {
                loadedContainer.setFREIGHT_KIND(rsltLoadedContainer[i].get(8).toString());
            }
            if (rsltLoadedContainer[i].get(9) != null) {
                loadedContainer.setGOODS_AND_CTR_WT_KG(rsltLoadedContainer[i].get(9).toString());
            }
            if (rsltLoadedContainer[i].get(10) != null) {
                loadedContainer.setPOD(rsltLoadedContainer[i].get(10).toString());
            }


            if (rsltLoadedContainer[i].get(12) != null) {
                loadedContainer.setSEAL_NO(rsltLoadedContainer[i].get(12).toString());
            }
            if (rsltLoadedContainer[i].get(13) != null) {
                loadedContainer.setIB_VYG(rsltLoadedContainer[i].get(13).toString());
            }
            if (rsltLoadedContainer[i].get(14) != null) {
                loadedContainer.setNAME(rsltLoadedContainer[i].get(14).toString());
            }
            if (rsltLoadedContainer[i].get(15) != null) {
                loadedContainer.setCOMING_FROM(rsltLoadedContainer[i].get(15).toString());
            }
            if (rsltLoadedContainer[i].get(16) != null) {
                loadedContainer.setPRE_ADVISED_DT(rsltLoadedContainer[i].get(16).toString());
            }
            if (rsltLoadedContainer[i].get(17) != null) {
                loadedContainer.setStowage_pos(rsltLoadedContainer[i].get(17).toString());
            }
            if (rsltLoadedContainer[i].get(18) != null) {
                loadedContainer.setRemark(rsltLoadedContainer[i].get(18).toString());
            }
            if (rsltLoadedContainer[i].get(19) != null) {
                loadedContainer.setSEAL_NO(rsltLoadedContainer[i].get(19).toString());
            }


            arrayList.add(loadedContainer);
        }
        return arrayList;
    }


    @Override
    public List getDischargeReport(String rotation) {
        ArrayList<LoadedContainer> arrayList = new ArrayList<>();
        String rot = rotation.replace("_", "/");
        System.out.println("rotation :" + rotation);



        List rsltLoadedContainer[] = loadedContainerRepository.getDischargeReport(rot);
        for (int i = 0; i < rsltLoadedContainer.length; i++) {
            LoadedContainer loadedContainer = new LoadedContainer();

            if (rsltLoadedContainer[i].get(0) != null) {
                loadedContainer.setOnboard_LD_20(rsltLoadedContainer[i].get(0).toString());
            }
            if (rsltLoadedContainer[i].get(1) != null) {
                loadedContainer.setOnboard_LD_40(rsltLoadedContainer[i].get(1).toString());
            }

            if (rsltLoadedContainer[i].get(2) != null) {
                loadedContainer.setOnboard_MT_20(rsltLoadedContainer[i].get(2).toString());
            }
            if (rsltLoadedContainer[i].get(3) != null) {
                loadedContainer.setOnboard_MT_40(rsltLoadedContainer[i].get(3).toString());
            }
            if (rsltLoadedContainer[i].get(4) != null) {
                loadedContainer.setOnboard_LD_tues(rsltLoadedContainer[i].get(4).toString());
            }
            if (rsltLoadedContainer[i].get(5) != null) {
                loadedContainer.setOnboard_MT_tues(rsltLoadedContainer[i].get(5).toString());
            }



            arrayList.add(loadedContainer);
        }
        return arrayList;
    }


    @Override
    public List getBalanceReport(String rotation) {
        ArrayList<LoadedContainer> arrayList = new ArrayList<>();
        String rot = rotation.replace("_", "/");
        System.out.println("rotation :" + rotation);


        List rsltLoadedContainer[] = loadedContainerRepository.getBalanceReport(rot);
        for (int i = 0; i < rsltLoadedContainer.length; i++) {
            LoadedContainer loadedContainer = new LoadedContainer();

            if (rsltLoadedContainer[i].get(0) != null) {
                loadedContainer.setBalance_LD_20(rsltLoadedContainer[i].get(0).toString());
            }
            if (rsltLoadedContainer[i].get(1) != null) {
                loadedContainer.setBalance_LD_40(rsltLoadedContainer[i].get(1).toString());
            }
            if (rsltLoadedContainer[i].get(2) != null) {
                loadedContainer.setBalance_MT_20(rsltLoadedContainer[i].get(2).toString());
            }
            if (rsltLoadedContainer[i].get(3) != null) {
                loadedContainer.setBalance_MT_40(rsltLoadedContainer[i].get(3).toString());
            }
            if (rsltLoadedContainer[i].get(4) != null) {
                loadedContainer.setBalance_LD_tues(rsltLoadedContainer[i].get(4).toString());
            }
            if (rsltLoadedContainer[i].get(5) != null) {
                loadedContainer.setBalance_MT_tues(rsltLoadedContainer[i].get(5).toString());
            }



            arrayList.add(loadedContainer);
        }
        return arrayList;
    }






}
