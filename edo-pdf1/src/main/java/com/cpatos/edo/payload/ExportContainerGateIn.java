package com.cpatos.edo.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExportContainerGateIn {

      public String id;
            public String cont_size;
    public BigDecimal height;
            public String seal_nbr1;
            public String name;
    public String MLO;
            public String category;
    public String freight_kind;
          public String  rotation;
    public String ob_vyg;
           public String time_in;
}
