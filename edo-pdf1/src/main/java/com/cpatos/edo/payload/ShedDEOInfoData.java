package com.cpatos.edo.payload;


import com.cpatos.edo.model.cchaportdb.EdoApplication;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShedDEOInfoData {

    private EdoApplication edoApplication;
    private String measurementVal;
    private Date validUptodtVal;
    private String billOfEntryNoVal;
    private Date beDtVal;
    private String officeCodeVal;
    private String lineNo;
    private String receiptNo;
    private Date receiptDate;
    private String remarks;
    private String cnfLicNo;
    private String rNo;
    private Date rNoDate;


    private String logoPic;


    List<ContainerList> contList;

    private String dtlId;
    private String notifyName;
    private String notifyAddress;
    private String vesselName;
    private String voyNo;
    private String importRotationNo;
    private String billOfEntryNo;
    private Date billOfEntryDate;
    private Date submissionDate;
    private String portOfOrigin;
    private String PortOfShipment;
    private String PortOfDestination;
    private String consigneeName;
    private String consigneeAddress;
    private String descriptionOfGoods;
    private String packDescription;
    private String PackMarksNumber;
    private String weight;
    private String weightUnit;
    private String officeCode;
    private String volumeInCubicMeters;
    private String igmPackNumber;
    private String exporterName;
    private String exporterAddress;
    private String cnfName;
    private String cnfLicenseNo;


    private String blNo;
    private String blType;
    private String igmType;
    private String deliveredWeight;
    private String grossQty;
    private String measurement;


}
