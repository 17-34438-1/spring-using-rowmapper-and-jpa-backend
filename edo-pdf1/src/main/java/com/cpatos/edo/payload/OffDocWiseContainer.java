package com.cpatos.edo.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OffDocWiseContainer {
    public String cont_id;
    public String rotation;
    public String cont_status;
    public String cont_mlo;
    public String cont_size;
    public String cont_height;
    public String agent;
    public String transOp;
    public String vvd_gkey;

    public String vessel_name;
    public String transit_way;
    public String category;
}
