package com.cpatos.edo.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgentWiseContainer {
    public String cont_id;
    public String rotation;
    public String cont_status;
    public String cont_mlo;
    public String cont_size;
    public String cont_height;
    public String agent;
    public String transOp;

    private String Vessel_name;
    private String category;
    private String transit_Way;
    String vvd_gkey;


}
