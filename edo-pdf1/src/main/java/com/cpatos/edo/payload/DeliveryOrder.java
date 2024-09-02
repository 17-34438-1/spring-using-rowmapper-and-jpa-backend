package com.cpatos.edo.payload;

import lombok.Data;

@Data
public class DeliveryOrder {


    String BL_NO;
    String Pack_Marks_Number;
    String Pack_Number;
    String Pack_Description;
    String Description_of_Goods;
    String weight;
    String weight_unit;
    String cont_number;
    String cont_size;
    String cont_status;

    String Notify_name;
            String Line_No;
    String bill_of_entry_date;
            String Import_Rotation_No;
    String Vessel_Name;
            String Voy_No;



}
