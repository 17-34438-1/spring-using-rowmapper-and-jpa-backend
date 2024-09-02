package com.cpatos.edo.service.cchaportdb;



import java.util.List;

public interface BillEntryService{
    public List UpdateBillOfEntry(String rotation, String container);
    public List UpdatedBillOfEntry(String rotation,String container,Long dtlID,String beNo,String beDate,String offCode,String tblName);
    public List ShedDeliveryOrderList();
}
