package com.cpatos.edo.payload;

import com.cpatos.edo.model.cchaportdb.IgmDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EdoPdf {

    private Long edoId;
    private Long uploadId;
    private String rotation;
    private String bl;
    private String blType;
    private String igmType;
    private LocalDateTime edoAppliedTime;
    private String edoForwardingTime;
    private String submittedBy;
    private Long dtlId;
    private String igmPackNumber;
    private String packDescription;
    private String packMarksNumber;
    private Double weight;
    private String billOfEntryNo;
    private String weightUnit;
    private String consigneeName;
    private String consigneeAddress;
    private String descriptionOfGoods;
    private String notifyName;
    private String notifyAddress;
    private String portOfOrigin;
    private String exporterName;
    private String exporterAddress;
    private String vesselName;
    private String voyNo;
    private String netTonnage;
    private String nameOfMaster;
    private String portShipID;
    private String portOfShipment;
    private String portOfDestination;
    private String customApproved;
    private Date fileClearanceDate;
    private String submiteeOrgType;
    private String sOrgLicenseNumber;
    private Date submissionDate;
    private String flag;
    private String imo;
    private String organizationName;
    private Date etaDate;
    private String reg_no;
    private String dec_code;
    private Date uploadTime;
    private String measurement;
    private Date validUptoDt;
    private String beNo;
    private Date billOfEntryDt;
    private String officeCode;
    private String edoMlo;
    private String edoSl;
    private int edoYear;
    private String remarks;
    private String lineNo;
    private String receiptNo;
    private Date receiptDate;
    private String rNo;
    private Date rNoDate;
    private String cnfLicNo;
    private String edoNumber;
    private String mloCode;
    private String cnfName;
    private String cnfLicenseNo;
    private Long orgProfileInfo;
    private Long organizationId;
    private String logo;
    private String orgName;
    private String address1;
    private String address2;
    private String licenseNo;
    private String ainNoNew;
    private String cellNo1;
    private String telephoneNoLand;
    private String orgTypeid;
    private List<IgmDetails> igmDetails;
    private List<ContainerList> contList;

}
