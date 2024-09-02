package com.cpatos.edo.Implementation.cchaportdb;

import com.cpatos.edo.payload.AllAssignment;
import com.cpatos.edo.payload.BillEntry;
import com.cpatos.edo.repository.cchaportdb.AllAssignmentReportRepo;
import com.cpatos.edo.service.cchaportdb.AllAssignmentReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AllAssignmentReportImpl implements AllAssignmentReportService {
    @Autowired
    AllAssignmentReportRepo  allAssignmentReportRepo;

    @Override
    public List AllAssignmentReport(String from_date){
        List<Object>[] chkAssignment = allAssignmentReportRepo.getAllAssignment(from_date);
        ArrayList<AllAssignment> arrayList = new ArrayList<>();

            for (int i = 0; i < chkAssignment.length; i++) {
                AllAssignment allAssignment=new AllAssignment();

                if (chkAssignment[i].get(0) != null) {
                    allAssignment.setCont_no(chkAssignment[i].get(0).toString());
                }
                if (chkAssignment[i].get(1) != null) {
                    allAssignment.setRot_no(chkAssignment[i].get(1).toString());
                }
                if (chkAssignment[i].get(2) != null) {
                    allAssignment.setIso_code(chkAssignment[i].get(2).toString());
                }
                if (chkAssignment[i].get(3) != null) {
                    allAssignment.setMlo(chkAssignment[i].get(3).toString());
                }
                if (chkAssignment[i].get(4) != null) {
                    allAssignment.setCont_status(chkAssignment[i].get(4).toString());
                }
                if (chkAssignment[i].get(5) != null) {
                    allAssignment.setMfdch_desc(chkAssignment[i].get(5).toString());
                }
                if (chkAssignment[i].get(6) != null) {
                    allAssignment.setWeight(chkAssignment[i].get(6).toString());
                }
                if (chkAssignment[i].get(7) != null) {
                    allAssignment.setAssignmentdate(chkAssignment[i].get(7).toString());
                }
                if (chkAssignment[i].get(8) != null) {
                    allAssignment.setTime_out(chkAssignment[i].get(8).toString());
                }
                arrayList.add(allAssignment);

            }
        return arrayList;
    }

}
