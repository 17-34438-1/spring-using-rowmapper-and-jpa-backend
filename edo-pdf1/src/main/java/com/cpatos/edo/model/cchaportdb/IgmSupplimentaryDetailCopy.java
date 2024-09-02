package com.cpatos.edo.model.cchaportdb;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "igm_supplimentary_detail_copy")
public class IgmSupplimentaryDetailCopy {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hibernate_sequence_generator")
    @SequenceGenerator(name = "hibernate_sequence_generator",
            sequenceName = "HIBERNATE_SEQUENCE",
            allocationSize = 1)
    private Long id;

    @Column(name = "Import_Rotation_No")
    private String importRotationNo;

    @Column(name = "BL_No")
    private String blNo;

    @Column(name = "type_of_igm")
    private String typeOfIgm;

    @Column(name = "Submitee_Org_Id")
    private String submiteeOrgId;

    @Column(name = "Submitee_Id")
    private String submiteeId;

    @Column(name = "master_BL_No")
    private String masterBLNo;

    @Column(name = "Pack_Number")
    private String packNumber;

    @Column(name = "Pack_Description")
    private String packDescription;

    @Column(name = "Pack_Marks_Number")
    private String packMarksNumber;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "Bill_of_Entry_No")
    private String billOfEntryNo;

    @Column(name="Bill_of_Entry_Date")
    private String billofEntryDate;

    @Column(name="office_code")
    private String officeCode;



    @Column(name = "weight_unit")
    private String weightUnit;

    @Column(name = "Consignee_name")
    private String consigneeName;

    @Column(name = "Consignee_address")
    private String consigneeAddress;

    @Column(name = "Description_of_Goods")
    private String descriptionOfGoods;

    @Column(name = "Notify_name")
    private String notifyName;

    @Column(name = "Notify_address")
    private String notifyAddress;

    @Column(name = "port_of_origin")
    private String portOfOrigin;
//    @ManyToOne
//    @JoinColumn(name = "igm_detail_id") //setting the name of the joining column
//    private IgmDetails igmDetails;

    @ManyToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "igm_detail_id",
            insertable = false, updatable = false,
            referencedColumnName = "id"
    )
    private IgmDetails igmDetails;

}
