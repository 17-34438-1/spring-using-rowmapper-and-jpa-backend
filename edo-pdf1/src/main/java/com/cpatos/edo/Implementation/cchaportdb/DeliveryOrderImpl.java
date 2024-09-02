package com.cpatos.edo.Implementation.cchaportdb;

import com.cpatos.edo.payload.DeliveryOrder;
import com.cpatos.edo.repository.cchaportdb.DeliveryOrderRepository;
import com.cpatos.edo.service.cchaportdb.DeliveryOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class DeliveryOrderImpl implements DeliveryOrderService {

    @Autowired
    DeliveryOrderRepository deliveryOrderRepository;

    @Override
    public List getDeliveryOrder(String bill_entry){
        List rslt_bill[] = deliveryOrderRepository.getVesselBill(bill_entry);

        List rslt_vessel_info[] = deliveryOrderRepository.getDeliveryOrderVesselInfo(bill_entry);

        ArrayList<DeliveryOrder> arrayList = new ArrayList<>();



        for(int i=0;i<rslt_bill.length;i++) {
            DeliveryOrder deliveryOrder = new DeliveryOrder();

            if (rslt_bill[i].get(0) != null) {
                deliveryOrder.setBL_NO(rslt_bill[i].get(0).toString());
            }
            if (rslt_bill[i].get(1) != null) {
                deliveryOrder.setPack_Number(rslt_bill[i].get(1).toString());
            }
            if (rslt_bill[i].get(2) != null) {
                deliveryOrder.setPack_Marks_Number(rslt_bill[i].get(2).toString());
            }

            if (rslt_bill[i].get(3) != null) {
                deliveryOrder.setWeight(rslt_bill[i].get(3).toString());
            }


            if (rslt_bill[i].get(4) != null) {
                deliveryOrder.setWeight_unit(rslt_bill[i].get(4).toString());
            }


            if (rslt_bill[i].get(5) != null) {
                deliveryOrder.setPack_Description(rslt_bill[i].get(5).toString());
            }

            if (rslt_bill[i].get(6) != null) {
                deliveryOrder.setDescription_of_Goods(rslt_bill[i].get(6).toString());
            }

            if (rslt_bill[i].get(7) != null) {
                deliveryOrder.setCont_number(rslt_bill[i].get(7).toString());
            }
            if (rslt_bill[i].get(8) != null) {
                deliveryOrder.setCont_size(rslt_bill[i].get(8).toString());
            }
            if (rslt_bill[i].get(9) != null) {
                deliveryOrder.setCont_status(rslt_bill[i].get(9).toString());
            }

            for(int j=0;j<rslt_vessel_info.length;j++) {

//            if (rslt_vessel_info[i].get(0) != null) {
//                deliveryOrder.setBL_NO(rslt_vessel_info[i].get(0).toString());
//            }

                if (rslt_vessel_info[j].get(1) != null) {
                    deliveryOrder.setNotify_name(rslt_vessel_info[j].get(1).toString());
                }
                if (rslt_vessel_info[j].get(2) != null) {
                    deliveryOrder.setLine_No(rslt_vessel_info[j].get(2).toString());
                }
                if (rslt_vessel_info[j].get(3) != null) {
                    deliveryOrder.setBill_of_entry_date(rslt_vessel_info[j].get(3).toString());
                }
                if (rslt_vessel_info[j].get(4) != null) {
                    deliveryOrder.setImport_Rotation_No(rslt_vessel_info[j].get(4).toString());
                }
                if (rslt_vessel_info[j].get(5) != null) {
                    deliveryOrder.setVessel_Name(rslt_vessel_info[j].get(5).toString());
                }
                if (rslt_vessel_info[j].get(6) != null) {
                    deliveryOrder.setVoy_No(rslt_vessel_info[j].get(6).toString());
                }

                //arrayList.add(deliveryOrder);
            }


            arrayList.add(deliveryOrder);

        }

        return arrayList;
    }
}
