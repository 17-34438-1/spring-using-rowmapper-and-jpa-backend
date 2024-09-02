package com.cpatos.edo.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllAssignment {
    private String cont_no;
    private String rot_no;
    private String  iso_code;
    private String mlo;
    private String cont_status;
    private String mfdch_desc;
    private String  weight;
    private String assignmentdate;
    private String time_out;

}
