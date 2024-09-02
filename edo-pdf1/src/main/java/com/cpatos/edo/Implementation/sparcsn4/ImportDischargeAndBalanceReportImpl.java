package com.cpatos.edo.Implementation.sparcsn4;

import com.cpatos.edo.payload.ImportDischargeAndBalanceReport;
import com.cpatos.edo.repository.sparcsn4.ImportDischargeAndBalanceReportRepository;
import com.cpatos.edo.service.sparcsn4.ImportDischargeAndBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class ImportDischargeAndBalanceReportImpl implements ImportDischargeAndBalanceService {
    @Autowired
    ImportDischargeAndBalanceReportRepository importDischargeAndBalanceReportRepository;
    @Override
    public List getContainerReportBalance(String rotation) {
        ArrayList<ImportDischargeAndBalanceReport> arrayList = new ArrayList<>();
        String rot = rotation.replace("_", "/");
        System.out.println("rotation :" + rotation);


        List rsltLoadedContainer[] = importDischargeAndBalanceReportRepository.getImportReportBalance(rot);
        for (int i = 0; i < rsltLoadedContainer.length; i++) {
            ImportDischargeAndBalanceReport importDischargeAndBalanceReport = new ImportDischargeAndBalanceReport();

            if (rsltLoadedContainer[i].get(0) != null) {
                importDischargeAndBalanceReport.setID(rsltLoadedContainer[i].get(0).toString());
            }
            if (rsltLoadedContainer[i].get(1) != null) {
                importDischargeAndBalanceReport.setFCY_TIME_IN(rsltLoadedContainer[i].get(1).toString());
            }
            if (rsltLoadedContainer[i].get(2) != null) {
                importDischargeAndBalanceReport.setLOCATION(rsltLoadedContainer[i].get(2).toString());
            }

            if (rsltLoadedContainer[i].get(3) != null) {
                importDischargeAndBalanceReport.setSEALNO(rsltLoadedContainer[i].get(3).toString());
            }
            if (rsltLoadedContainer[i].get(4) != null) {
                importDischargeAndBalanceReport.setISO(rsltLoadedContainer[i].get(4).toString());
            }
            if (rsltLoadedContainer[i].get(5) != null) {
                importDischargeAndBalanceReport.setMLO(rsltLoadedContainer[i].get(5).toString());
            }
            if (rsltLoadedContainer[i].get(6) != null) {
                importDischargeAndBalanceReport.setFREIGHT_KIND(rsltLoadedContainer[i].get(6).toString());
            }
            if (rsltLoadedContainer[i].get(7) != null) {
                importDischargeAndBalanceReport.setWEIGHT(rsltLoadedContainer[i].get(7).toString());
            }
            if (rsltLoadedContainer[i].get(8) != null) {
                importDischargeAndBalanceReport.setSHORT_NAME(rsltLoadedContainer[i].get(8).toString());
            }
            if (rsltLoadedContainer[i].get(10) != null) {
                importDischargeAndBalanceReport.setCl_date(rsltLoadedContainer[i].get(10).toString());
            }
            arrayList.add(importDischargeAndBalanceReport);
        }
        return arrayList;

    }



    @Override
    public List getContainerReportDischarge(String rotation) {
        ArrayList<ImportDischargeAndBalanceReport> arrayList = new ArrayList<>();
        String rot = rotation.replace("_", "/");
        System.out.println("rotation :" + rotation);



        List rsltLoadedContainer[] = importDischargeAndBalanceReportRepository.getImportReportDischarge(rot);
        for (int i = 0; i < rsltLoadedContainer.length; i++) {
            ImportDischargeAndBalanceReport importDischargeAndBalanceReport = new ImportDischargeAndBalanceReport();

            if (rsltLoadedContainer[i].get(0) != null) {
                importDischargeAndBalanceReport.setID(rsltLoadedContainer[i].get(0).toString());
            }
            if (rsltLoadedContainer[i].get(1) != null) {
                importDischargeAndBalanceReport.setFCY_TIME_IN(rsltLoadedContainer[i].get(1).toString());
            }
            if (rsltLoadedContainer[i].get(2) != null) {
                importDischargeAndBalanceReport.setLOCATION(rsltLoadedContainer[i].get(2).toString());
            }

            if (rsltLoadedContainer[i].get(3) != null) {
                importDischargeAndBalanceReport.setSEALNO(rsltLoadedContainer[i].get(3).toString());
            }
            if (rsltLoadedContainer[i].get(4) != null) {
                importDischargeAndBalanceReport.setISO(rsltLoadedContainer[i].get(4).toString());
            }
            if (rsltLoadedContainer[i].get(5) != null) {
                importDischargeAndBalanceReport.setMLO(rsltLoadedContainer[i].get(5).toString());
            }
            if (rsltLoadedContainer[i].get(6) != null) {
                importDischargeAndBalanceReport.setFREIGHT_KIND(rsltLoadedContainer[i].get(6).toString());
            }
            if (rsltLoadedContainer[i].get(7) != null) {
                importDischargeAndBalanceReport.setWEIGHT(rsltLoadedContainer[i].get(7).toString());
            }
            if (rsltLoadedContainer[i].get(8) != null) {
                importDischargeAndBalanceReport.setSHORT_NAME(rsltLoadedContainer[i].get(8).toString());
            }
            if (rsltLoadedContainer[i].get(9) != null) {
                importDischargeAndBalanceReport.setRemark(rsltLoadedContainer[i].get(9).toString());
            }
            if (rsltLoadedContainer[i].get(10) != null) {
                importDischargeAndBalanceReport.setCl_date(rsltLoadedContainer[i].get(10).toString());
            }
            arrayList.add(importDischargeAndBalanceReport);
        }
        return arrayList;

    }


    @Override
    public List getDischargeReport(String rotation) {
        ArrayList<ImportDischargeAndBalanceReport> arrayList = new ArrayList<>();
        String rot = rotation.replace("_", "/");
        System.out.println("rotation :" + rotation);


        List rsltLoadedContainer[] = importDischargeAndBalanceReportRepository.getDischargeReport(rot);
        for (int i = 0; i < rsltLoadedContainer.length; i++) {
            ImportDischargeAndBalanceReport importDischargeAndBalanceReport = new ImportDischargeAndBalanceReport();

            if (rsltLoadedContainer[i].get(0) != null) {
                importDischargeAndBalanceReport.setOnboard_LD_20(rsltLoadedContainer[i].get(0).toString());
            }
            if (rsltLoadedContainer[i].get(1) != null) {
                importDischargeAndBalanceReport.setOnboard_LD_40(rsltLoadedContainer[i].get(1).toString());
            }

            if (rsltLoadedContainer[i].get(2) != null) {
                importDischargeAndBalanceReport.setOnboard_MT_20(rsltLoadedContainer[i].get(2).toString());
            }
            if (rsltLoadedContainer[i].get(3) != null) {
                importDischargeAndBalanceReport.setOnboard_MT_40(rsltLoadedContainer[i].get(3).toString());
            }
            if (rsltLoadedContainer[i].get(4) != null) {
                importDischargeAndBalanceReport.setOnboard_LD_tues(rsltLoadedContainer[i].get(4).toString());
            }
            if (rsltLoadedContainer[i].get(5) != null) {
                importDischargeAndBalanceReport.setOnboard_MT_tues(rsltLoadedContainer[i].get(5).toString());
            }
            arrayList.add(importDischargeAndBalanceReport);
        }
        return arrayList;
    }


    @Override
    public List getBalanceReport(String rotation) {
        ArrayList<ImportDischargeAndBalanceReport> arrayList = new ArrayList<>();
        String rot = rotation.replace("_", "/");
        System.out.println("rotation :" + rotation);


        List rsltLoadedContainer[] = importDischargeAndBalanceReportRepository.getBalanceReport(rot);
        for (int i = 0; i < rsltLoadedContainer.length; i++) {
            ImportDischargeAndBalanceReport importDischargeAndBalanceReport = new ImportDischargeAndBalanceReport();

            if (rsltLoadedContainer[i].get(0) != null) {
                importDischargeAndBalanceReport.setBalance_LD_20(rsltLoadedContainer[i].get(0).toString());
            }
            if (rsltLoadedContainer[i].get(1) != null) {
                importDischargeAndBalanceReport.setBalance_LD_40(rsltLoadedContainer[i].get(1).toString());
            }
            if (rsltLoadedContainer[i].get(2) != null) {
                importDischargeAndBalanceReport.setBalance_MT_20(rsltLoadedContainer[i].get(2).toString());
            }
            if (rsltLoadedContainer[i].get(3) != null) {
                importDischargeAndBalanceReport.setBalance_MT_40(rsltLoadedContainer[i].get(3).toString());
            }
            if (rsltLoadedContainer[i].get(4) != null) {
                importDischargeAndBalanceReport.setBalance_LD_tues(rsltLoadedContainer[i].get(4).toString());
            }
            if (rsltLoadedContainer[i].get(5) != null) {
                importDischargeAndBalanceReport.setBalance_MT_tues(rsltLoadedContainer[i].get(5).toString());
            }

            arrayList.add(importDischargeAndBalanceReport);
        }
        return arrayList;
    }

}
