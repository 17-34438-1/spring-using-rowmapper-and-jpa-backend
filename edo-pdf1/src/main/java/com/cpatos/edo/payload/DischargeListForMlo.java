package com.cpatos.edo.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DischargeListForMlo {
   String id;
   String cont_size;
   BigDecimal height;
   String freight_kind;
   String time_in;
   String time_out;
}
