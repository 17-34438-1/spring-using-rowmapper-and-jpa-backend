package com.cpatos.edo.Implementation.sparcsn4;

import com.cpatos.edo.payload.MloWisePreAdvisedContainer;
import com.cpatos.edo.payload.ResponseMessage;
import com.cpatos.edo.repository.sparcsn4.MloWisePreAdvisedContainerRepository;
import com.cpatos.edo.service.sparcsn4.MloWisePreAdvisedContainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class MloWisePreAdvisedContainerServiceImpl implements MloWisePreAdvisedContainerService {
    ResponseMessage responseMessage;

    @Autowired
    MloWisePreAdvisedContainerRepository mloWisePreAdvisedContainerRepository;
    @Override
    public List getMloWisePreAdvisedContainer(String rotation) {
        ArrayList<MloWisePreAdvisedContainer> arrayList = new ArrayList<>();
        String rot = rotation.replace("_", "/");
        System.out.println("rotation :" + rotation);


        List rsltPreAdvisedContainer[] = mloWisePreAdvisedContainerRepository.getMloWisePreAdvisedContainer(rot);
        for (int i = 0; i < rsltPreAdvisedContainer.length; i++) {
            MloWisePreAdvisedContainer mloWisePreAdvisedContainer = new MloWisePreAdvisedContainer();

            if (rsltPreAdvisedContainer[i].get(0) != null) {
                mloWisePreAdvisedContainer.setMLO(rsltPreAdvisedContainer[i].get(0).toString());
            }
            if (rsltPreAdvisedContainer[i].get(1) != null) {
                mloWisePreAdvisedContainer.setAGENT(rsltPreAdvisedContainer[i].get(1).toString());
            }
            if (rsltPreAdvisedContainer[i].get(2) != null) {
                mloWisePreAdvisedContainer.setLOADED_20(rsltPreAdvisedContainer[i].get(2).toString());
            }
            if (rsltPreAdvisedContainer[i].get(3) != null) {
                mloWisePreAdvisedContainer.setLOADED_40(rsltPreAdvisedContainer[i].get(3).toString());
            }
            if (rsltPreAdvisedContainer[i].get(4) != null) {
                mloWisePreAdvisedContainer.setEMTY_20(rsltPreAdvisedContainer[i].get(4).toString());
            }
            if (rsltPreAdvisedContainer[i].get(5) != null) {
                mloWisePreAdvisedContainer.setEMTY_40(rsltPreAdvisedContainer[i].get(5).toString());
            }
            if (rsltPreAdvisedContainer[i].get(6) != null) {
                mloWisePreAdvisedContainer.setREEFER_20(rsltPreAdvisedContainer[i].get(6).toString());
            }
            if (rsltPreAdvisedContainer[i].get(7) != null) {
                mloWisePreAdvisedContainer.setREEFER_40(rsltPreAdvisedContainer[i].get(7).toString());
            }
            if (rsltPreAdvisedContainer[i].get(8) != null) {
                mloWisePreAdvisedContainer.setIMDG_20(rsltPreAdvisedContainer[i].get(8).toString());
            }
            if (rsltPreAdvisedContainer[i].get(9) != null) {
                mloWisePreAdvisedContainer.setIMDG_40(rsltPreAdvisedContainer[i].get(9).toString());
            }
            if (rsltPreAdvisedContainer[i].get(10) != null) {
                mloWisePreAdvisedContainer.setTRSHP_20(rsltPreAdvisedContainer[i].get(10).toString());
            }
            if (rsltPreAdvisedContainer[i].get(11) != null) {
                mloWisePreAdvisedContainer.setTRSHP_40(rsltPreAdvisedContainer[i].get(11).toString());
            }
            if (rsltPreAdvisedContainer[i].get(12) != null) {
                mloWisePreAdvisedContainer.setICD_20(rsltPreAdvisedContainer[i].get(12).toString());
            }
            if (rsltPreAdvisedContainer[i].get(13) != null) {
                mloWisePreAdvisedContainer.setICD_40(rsltPreAdvisedContainer[i].get(13).toString());
            }
            if (rsltPreAdvisedContainer[i].get(14) != null) {
                mloWisePreAdvisedContainer.setLD_20(rsltPreAdvisedContainer[i].get(14).toString());
            }
            if (rsltPreAdvisedContainer[i].get(15) != null) {
                mloWisePreAdvisedContainer.setLD_40(rsltPreAdvisedContainer[i].get(15).toString());
            }
            if (rsltPreAdvisedContainer[i].get(16) != null) {
                mloWisePreAdvisedContainer.setGRAND_TOT(rsltPreAdvisedContainer[i].get(16).toString());
            }
            arrayList.add(mloWisePreAdvisedContainer);
        }
        return arrayList;
    }

    @Override
    public List getPreAdvisedContainer(String rotation,String mlo) {
        ArrayList<MloWisePreAdvisedContainer> arrayList = new ArrayList<>();
        String rot = rotation.replace("_", "/");
        System.out.println("rotation :" + rotation);


        List rsltPreAdvisedContainer[] = mloWisePreAdvisedContainerRepository.getPreAdvisedContainer(rot,mlo);
        for (int i = 0; i < rsltPreAdvisedContainer.length; i++) {
            MloWisePreAdvisedContainer mloWisePreAdvisedContainer = new MloWisePreAdvisedContainer();

            if (rsltPreAdvisedContainer[i].get(1) != null) {
                mloWisePreAdvisedContainer.setCONT_ID(rsltPreAdvisedContainer[i].get(1).toString());
            }
            if (rsltPreAdvisedContainer[i].get(3) != null) {
                mloWisePreAdvisedContainer.setMl(rsltPreAdvisedContainer[i].get(3).toString());
            }
            if (rsltPreAdvisedContainer[i].get(5) != null) {
                mloWisePreAdvisedContainer.setCONT_SIZE(rsltPreAdvisedContainer[i].get(5).toString());
            }
            if (rsltPreAdvisedContainer[i].get(7) != null) {
                mloWisePreAdvisedContainer.setISO(rsltPreAdvisedContainer[i].get(7).toString());
            }
            if (rsltPreAdvisedContainer[i].get(8) != null) {
                mloWisePreAdvisedContainer.setFREIGHT_KIND(rsltPreAdvisedContainer[i].get(8).toString());
            }
            if (rsltPreAdvisedContainer[i].get(9) != null) {
                mloWisePreAdvisedContainer.setGOODS_AND_CTR_WT_KG(rsltPreAdvisedContainer[i].get(9).toString());
            }
            if (rsltPreAdvisedContainer[i].get(10) != null) {
                mloWisePreAdvisedContainer.setPOD(rsltPreAdvisedContainer[i].get(10).toString());
            }


            if (rsltPreAdvisedContainer[i].get(12) != null) {
                mloWisePreAdvisedContainer.setSEAL_NO(rsltPreAdvisedContainer[i].get(12).toString());
            }
            if (rsltPreAdvisedContainer[i].get(13) != null) {
                mloWisePreAdvisedContainer.setIB_VYG(rsltPreAdvisedContainer[i].get(13).toString());
            }
            if (rsltPreAdvisedContainer[i].get(14) != null) {
                mloWisePreAdvisedContainer.setNAME(rsltPreAdvisedContainer[i].get(14).toString());
            }
            if (rsltPreAdvisedContainer[i].get(15) != null) {
                mloWisePreAdvisedContainer.setCOMING_FROM(rsltPreAdvisedContainer[i].get(15).toString());
            }
            if (rsltPreAdvisedContainer[i].get(16) != null) {
                mloWisePreAdvisedContainer.setPRE_ADVISED_DT(rsltPreAdvisedContainer[i].get(16).toString());
            }


            arrayList.add(mloWisePreAdvisedContainer);
        }
        return arrayList;
    }


    @Override
    public List getExportSummary(String rotation,String mlo) {
        ArrayList<MloWisePreAdvisedContainer> arrayList = new ArrayList<>();
        String rot = rotation.replace("_", "/");
        System.out.println("rotation :" + rotation);

        List rsltPreAdvisedContainer[] = mloWisePreAdvisedContainerRepository.getMloWiseSummary(rot,mlo);
        for (int i = 0; i < rsltPreAdvisedContainer.length; i++) {
            MloWisePreAdvisedContainer mloWisePreAdvisedContainer = new MloWisePreAdvisedContainer();

            if (rsltPreAdvisedContainer[i].get(0) != null) {
                mloWisePreAdvisedContainer.setMLO(rsltPreAdvisedContainer[i].get(0).toString());
            }
            if (rsltPreAdvisedContainer[i].get(1) != null) {
                mloWisePreAdvisedContainer.setAGENT(rsltPreAdvisedContainer[i].get(1).toString());
            }

            if (rsltPreAdvisedContainer[i].get(2) != null) {
                mloWisePreAdvisedContainer.setF_20(rsltPreAdvisedContainer[i].get(2).toString());
            }
            if (rsltPreAdvisedContainer[i].get(3) != null) {
                mloWisePreAdvisedContainer.setL_20(rsltPreAdvisedContainer[i].get(3).toString());
            }
            if (rsltPreAdvisedContainer[i].get(4) != null) {
                mloWisePreAdvisedContainer.setM_20(rsltPreAdvisedContainer[i].get(4).toString());
            }
            if (rsltPreAdvisedContainer[i].get(5) != null) {
                mloWisePreAdvisedContainer.setI_20(rsltPreAdvisedContainer[i].get(5).toString());
            }
            if (rsltPreAdvisedContainer[i].get(6) != null) {
                mloWisePreAdvisedContainer.setT_20(rsltPreAdvisedContainer[i].get(6).toString());
            }
            if (rsltPreAdvisedContainer[i].get(7) != null) {
                mloWisePreAdvisedContainer.setR_20(rsltPreAdvisedContainer[i].get(7).toString());
            }
            if (rsltPreAdvisedContainer[i].get(8) != null) {
                mloWisePreAdvisedContainer.setIMDG_20(rsltPreAdvisedContainer[i].get(8).toString());
            }



            if (rsltPreAdvisedContainer[i].get(9) != null) {
                mloWisePreAdvisedContainer.setF_40(rsltPreAdvisedContainer[i].get(9).toString());
            }
            if (rsltPreAdvisedContainer[i].get(10) != null) {
                mloWisePreAdvisedContainer.setL_40(rsltPreAdvisedContainer[i].get(10).toString());
            }
            if (rsltPreAdvisedContainer[i].get(11) != null) {
                mloWisePreAdvisedContainer.setM_40(rsltPreAdvisedContainer[i].get(11).toString());
            }
            if (rsltPreAdvisedContainer[i].get(12) != null) {
                mloWisePreAdvisedContainer.setI_40(rsltPreAdvisedContainer[i].get(12).toString());
            }
            if (rsltPreAdvisedContainer[i].get(13) != null) {
                mloWisePreAdvisedContainer.setT_40(rsltPreAdvisedContainer[i].get(13).toString());
            }
            if (rsltPreAdvisedContainer[i].get(14) != null) {
                mloWisePreAdvisedContainer.setR_40(rsltPreAdvisedContainer[i].get(14).toString());
            }
            if (rsltPreAdvisedContainer[i].get(15) != null) {
                mloWisePreAdvisedContainer.setIMDG_40(rsltPreAdvisedContainer[i].get(15).toString());
            }



            if (rsltPreAdvisedContainer[i].get(16) != null) {
                mloWisePreAdvisedContainer.setFW_20(rsltPreAdvisedContainer[i].get(16).toString());
            }
            if (rsltPreAdvisedContainer[i].get(17) != null) {
                mloWisePreAdvisedContainer.setLW_20(rsltPreAdvisedContainer[i].get(17).toString());
            }
            if (rsltPreAdvisedContainer[i].get(18) != null) {
                mloWisePreAdvisedContainer.setMW_20(rsltPreAdvisedContainer[i].get(18).toString());
            }
            if (rsltPreAdvisedContainer[i].get(19) != null) {
                mloWisePreAdvisedContainer.setIW_20(rsltPreAdvisedContainer[i].get(19).toString());
            }

            if (rsltPreAdvisedContainer[i].get(20) != null) {
                mloWisePreAdvisedContainer.setTW_20(rsltPreAdvisedContainer[i].get(20).toString());
            }
            if (rsltPreAdvisedContainer[i].get(21) != null) {
                mloWisePreAdvisedContainer.setRW_20(rsltPreAdvisedContainer[i].get(21).toString());
            }
            if (rsltPreAdvisedContainer[i].get(22) != null) {
                mloWisePreAdvisedContainer.setIMDGW_20(rsltPreAdvisedContainer[i].get(22).toString());
            }






            if (rsltPreAdvisedContainer[i].get(23) != null) {
                mloWisePreAdvisedContainer.setFW_40(rsltPreAdvisedContainer[i].get(23).toString());
            }
            if (rsltPreAdvisedContainer[i].get(24) != null) {
                mloWisePreAdvisedContainer.setLW_40(rsltPreAdvisedContainer[i].get(24).toString());
            }
            if (rsltPreAdvisedContainer[i].get(25) != null) {
                mloWisePreAdvisedContainer.setMW_40(rsltPreAdvisedContainer[i].get(25).toString());
            }
            if (rsltPreAdvisedContainer[i].get(26) != null) {
                mloWisePreAdvisedContainer.setIW_40(rsltPreAdvisedContainer[i].get(26).toString());
            }
           if (rsltPreAdvisedContainer[i].get(27) != null) {
                mloWisePreAdvisedContainer.setTW_40(rsltPreAdvisedContainer[i].get(27).toString());
            }
            if (rsltPreAdvisedContainer[i].get(28) != null) {
                mloWisePreAdvisedContainer.setRW_40(rsltPreAdvisedContainer[i].get(28).toString());
            }
            if (rsltPreAdvisedContainer[i].get(29) != null) {
                mloWisePreAdvisedContainer.setIMDGW_40(rsltPreAdvisedContainer[i].get(29).toString());
            }


            arrayList.add(mloWisePreAdvisedContainer);
        }
        return arrayList;
    }


//    public List getPreAdvisedContainer(String rotation,String mlo);
//    public List getExportSummary(String rotation,String mlo);


}
