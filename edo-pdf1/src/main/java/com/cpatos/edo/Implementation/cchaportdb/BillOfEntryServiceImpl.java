package com.cpatos.edo.Implementation.cchaportdb;


import com.cpatos.edo.model.cchaportdb.ShedMloDoInfoModel;
import com.cpatos.edo.payload.BillEntry;
import com.cpatos.edo.payload.ResponseMessage;
import com.cpatos.edo.repository.cchaportdb.*;
import com.cpatos.edo.service.cchaportdb.BillEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BillOfEntryServiceImpl implements BillEntryService {
    ResponseMessage responseMessage;

    @Autowired
    BillEntryRepository billEntryRepository;
    @Autowired
    ShedMloDoInfoModelRepository shedMloDoInfoModelRepository;
    @Autowired
    IgmDetailsCopyRepository igmDetailsCopyRepository;

    @Autowired
    IgmSupplimentaryDetailsCopyRepository igmSupplimentaryDetailsCopyRepository;


    @Override
    public List UpdatedBillOfEntry(String rot,String container,Long dtlID,String beNo,String beDate,String offCode,String tblName){
       String rotation = rot.replace("_", "/");


        ArrayList<BillEntry> arrayList = new ArrayList<>();
        BillEntry billEntry=new BillEntry();

        System.out.println("dfdfdfetail:"+tblName);
        int updateSt=0;
        if(tblName.equals("Detail"))
        {
            System.out.println("Detail:"+tblName);

            updateSt = igmDetailsCopyRepository.updateBeNoAndBeDateAndOffCode(dtlID,beNo,beDate,offCode);
        }
        else {
            System.out.println("SubDetail:"+tblName);

            updateSt = igmSupplimentaryDetailsCopyRepository.updateBeNoAndBeDateAndOffCode(dtlID, beNo, beDate, offCode);

        }

        System.out.println("updateSt:" + updateSt);



        if(updateSt==1){
            billEntry.setMsg("Data successfully updated");
        }
        else {
            billEntry.setMsg("Data Not Updated");
            List<Object>[] chkSupTbl = billEntryRepository.getCountBillEntry(rotation,container);

            if(chkSupTbl.length==0){
                List BillDetailTbl[] = billEntryRepository.getBillDetailEntry(rotation,container);
                for(int i=0;i<BillDetailTbl.length;i++) {
                    if(BillDetailTbl[i].get(0)!=null){
                        billEntry.setId(Integer.valueOf(BillDetailTbl[i].get(0).toString()));
                    }
                    if(BillDetailTbl[i].get(1)!=null){
                        billEntry.setLine_No(BillDetailTbl[i].get(1).toString());
                    }
                    if(BillDetailTbl[i].get(3)!=null){
                        billEntry.setBL_No( BillDetailTbl[i].get(3).toString());
                    }
                    if(BillDetailTbl[i].get(5)!=null){
                        billEntry.setPack_Number( BillDetailTbl[i].get(5).toString());
                    }
                }
                billEntry.setTblName("Detail");
            }else {
                List<Object>[] SupDetailTbl = billEntryRepository.getBillSubDetailEntry(rotation,container);
                for(int i=0;i<SupDetailTbl.length;i++) {
                    if(SupDetailTbl[i].get(0)!=null){
                        billEntry.setId(Integer.valueOf(SupDetailTbl[i].get(0).toString()));
                    }
                    if(SupDetailTbl[i].get(1)!=null){
                        billEntry.setLine_No( SupDetailTbl[i].get(1).toString());
                    }
                    if(SupDetailTbl[i].get(3)!=null){
                        billEntry.setBL_No( SupDetailTbl[i].get(3).toString());
                    }
                    if(SupDetailTbl[i].get(5)!=null){
                        billEntry.setPack_Number( SupDetailTbl[i].get(5).toString());
                    }
                }
                billEntry.setTblName("SupDetail");
            }
        }

        arrayList.add(billEntry);


        return arrayList;
    }


    @Override
    public List UpdateBillOfEntry(String rot, String cont){
        String rotation = rot.replace("_", "/");

        List<Object>[] chkSupTbl = billEntryRepository.getCountBillEntry(rotation,cont);
        ArrayList<BillEntry> arrayList = new ArrayList<>();
//        BillEntry billEntry=new BillEntry();
        if(chkSupTbl.length==0){

            List BillDetailTbl[] = billEntryRepository.getBillDetailEntry(rotation,cont);
            for(int i=0;i<BillDetailTbl.length;i++) {
                BillEntry billEntry=new BillEntry();

                if(BillDetailTbl[i].get(0)!=null){
                    billEntry.setId(Integer.valueOf(BillDetailTbl[i].get(0).toString()));
                }
                if(BillDetailTbl[i].get(1)!=null){
                    billEntry.setLine_No(BillDetailTbl[i].get(1).toString());
                }
                if(BillDetailTbl[i].get(3)!=null){
                    billEntry.setBL_No( BillDetailTbl[i].get(3).toString());
                }
                if(BillDetailTbl[i].get(5)!=null){
                    billEntry.setPack_Number( BillDetailTbl[i].get(5).toString());
                }
                billEntry.setTblName("Detail");
                arrayList.add(billEntry);

            }

        }else {


            List<Object>[] SupDetailTbl = billEntryRepository.getBillSubDetailEntry(rotation,cont);
            for(int i=0;i<SupDetailTbl.length;i++) {
                BillEntry billEntry=new BillEntry();

                if(SupDetailTbl[i].get(0)!=null){
                    billEntry.setId(Integer.valueOf(SupDetailTbl[i].get(0).toString()));
                }
                if(SupDetailTbl[i].get(1)!=null){
                    billEntry.setLine_No( SupDetailTbl[i].get(1).toString());
                }
                if(SupDetailTbl[i].get(3)!=null){
                    billEntry.setBL_No( SupDetailTbl[i].get(3).toString());
                }
                if(SupDetailTbl[i].get(5)!=null){
                    billEntry.setPack_Number( SupDetailTbl[i].get(5).toString());
                }
                billEntry.setTblName("SupDetail");
                arrayList.add(billEntry);

            }
        }


        return arrayList;


    }

   @Override
   public List ShedDeliveryOrderList() {
       int limit = 100;
       Pageable pageable = PageRequest.of(0, limit);

       Page<ShedMloDoInfoModel> billDetailTbl = shedMloDoInfoModelRepository.findAll(pageable);
       return billDetailTbl.getContent();
   }




}


