package com.cpatos.edo.model.cchaportdb;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "igm_masters")
public class IgmMasters {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hibernate_sequence_generator")
    @SequenceGenerator(name = "hibernate_sequence_generator",
            sequenceName = "HIBERNATE_SEQUENCE",
            allocationSize = 1)
    private Long id;

    @Column(name = "Import_Rotation_No")
    private String importRotationNo;

    @Column(name = "Vessel_Name")
    private String vesselName;

    @Column(name = "Voy_No")
    private String voyNo;

    @Column(name = "Net_Tonnage")
    private String netTonnage;

    @Column(name = "Name_of_Master")
    private String nameOfMaster;

    @Column(name = "Port_Ship_ID")
    private String portShipID;

    @Column(name = "Port_of_Shipment")
    private String portOfShipment;

    @Column(name = "Port_of_Destination")
    private String portOfDestination;

    @Column(name = "custom_approved")
    private String customApproved;

    @Column(name = "file_clearence_date")
    private Date fileClearanceDate;

    @Column(name = "Submitee_Org_Id")
    private Long submiteeOrgId;

    @Column(name = "Submitee_Org_Type")
    private String submiteeOrgType;

    @Column(name = "S_Org_License_Number")
    private String sOrgLicenseNumber;

    @Column(name = "Submission_Date")
    private Date submissionDate;

    @Column(name = "flag")
    private String flag;

    @Column(name = "imo")
    private String imo;
}
