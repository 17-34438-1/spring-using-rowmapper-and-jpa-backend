package com.cpatos.edo.model.cchaportdb;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "edo_application_by_cf")
public class EdoApplication {

//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hibernate_sequence_generator")
//    @SequenceGenerator(name = "hibernate_sequence_generator",
//            sequenceName = "HIBERNATE_SEQUENCE",
//            allocationSize = 1)
//    private Long id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rotation;
    private String bl;

    @Column(name = "bl_type")
    private String blType;

    @Column(name = "igm_type")
    private String igmType;

    @Column(name = "mlo")
    private String mlo;

    @Column(name = "ff_stat")
    private Integer ffStat;

    @Column(name = "ff_clearance_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date ffClearanceTime;

    @Column(name = "forwarded_by")
    private String forwardedBy;

    @Column(name = "forwarded_org_id")
    private Integer forwardedOrgId;

    @Column(name = "cnf_vldty_appr_st")
    private Integer cnfVldtyApprSt;

    @Column(name = "vldty_appr_by_mlo_st")
    private Integer vldtyApprByMloSt;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "validity_approved_by_mlo_at")
    private Date validityApprovedByMloAt;

    @Column(name = "cpa_approve_st")
    private Integer cpaApproveSt;

    @Column(name = "ff_assoc_st")
    private Integer ffAssocSt;

    @Column(name = "ff_org_id")
    private String ffOrgId;

    @Column(name = "sh_agent_org_id")
    private String shAgentOrgId;

    @Column(name = "valid_upto_dt_by_mlo")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date validUptoDtByMlo;

    @Column(name = "rejection_st")
    private Integer rejectionSt;

    @Column(name = "rejection_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date rejectionTime;

    @Column(name = "rejection_remarks")
    private String rejectionRemarks;

    @Column(name = "rejected_by_org")
    private String rejectedByOrg;

    @Column(name = "rejected_by_user")
    private String rejectedByUser;

    @Column(name = "rejection_withdrawn_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date rejectionWithdrawnTime;

    @Column(name = "rejection_withdrawn_remarks")
    private String rejectionWithdrawnRemarks;

    @Column(name = "withdrawn_by_org")
    private String withdrawnByOrg;

    @Column(name = "withdrawn_by_user")
    private String withdrawnByUser;

    @Column(name = "applied_valid_dt")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date appliedValidDt;

    @Column(name = "applied_validity_extension_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date appliedValidityExtensionAt;

    @Column(name = "do_upload_st")
    private Integer doUploadSt;

    @Column(name = "cont_status")
    private String contStatus;

    @Column(name = "mbl_of_hbl")
    private String mblOfHbl;

    @Column(name = "entry_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime entryTime;

    @Column(name = "sumitted_by")
    private String submittedBy;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "edo_applied_by")
    private String edoAppliedBy;

    @Column(name = "be_no")
    private String beNo;

    @Column(name = "be_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beDate;

    @Column(name = "ofc_code")
    private String ofcCode;

    private String users;
}
