package com.cpatos.edo.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillEntry {

        Integer  id;
        String Line_No;
        String Import_Rotation_No;
        String BL_No;
        String cont_number;
        String Pack_Number;
        String tblName;
        String msg;
}
