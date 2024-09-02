package com.cpatos.edo.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContainerList {
    private Long cId;
    private int cnt;
    private String contNumber;
    private String contStatus;
    private String contLocationCode;
    private String contSealNumber;
    private String contSize;
    private String contType;
    private String contHeight;
    private String contGrossWeight;
    private String contWeight;
    private String contNumberPackaages;
    private Date validUptoDate;
}

