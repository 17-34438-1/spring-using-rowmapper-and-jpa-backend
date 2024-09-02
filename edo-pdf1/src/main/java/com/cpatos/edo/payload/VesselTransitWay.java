package com.cpatos.edo.payload;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class VesselTransitWay {

    public String vsl;
    public String ib_vyg;
    public String ata;
    public String etd;
    public String berth;
    public String ship_agent;
    public String berth_op;
    public String phase;
    public String transit_way;
}
