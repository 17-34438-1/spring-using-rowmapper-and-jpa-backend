package com.cpatos.edo.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgentWiseVessel {
    public String name;

    public String phase;
    public String ata;
    public String atd;
    public String ib_vyg;

    String cont_id;
    String VesselName;
    String rotation;
    String cont_size;
    String cont_height;
    String cont_mlo;
    String cont_status;
    String transit_state;
    String category;



}
