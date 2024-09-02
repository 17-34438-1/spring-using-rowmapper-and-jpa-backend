package com.cpatos.edo.model.cchaportdb;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "shed_mlo_do_info")
public class ShedMloDoInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "igm_detail_id")
    private Long igmDetailId;

    @Column(name = "edo_id")
    private  int edoId;

    @Column(name = "be_no")
    private String beNo;

    @Column(name = "imp_rot")
    private String impRot;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "be_date")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss" , timezone = "UTC")
    private Date beDate;

    @Column(name = "bl_no")
    private String blNo;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "upload_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss" , timezone = "UTC")
    private Date uploadTime;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "valid_upto_dt")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss" , timezone = "UTC")
    private Date validUpDt;

    @Column(name = "check_st")
    private int checkSt;

    @Column(name = "cpa_view_st")
    private Integer cpaViewSt;

    @Column(name = "cpa_viewed_by")
    private String cpaViewedBy;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "cpa_check_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss" , timezone = "UTC")
    private Date cpaCheckTime;

    @Column(name = "edo_mlo")
    private  String edoMlo;

    @Column(name = "edo_sl")
    private  int edoSl;

    @Column(name = "edo_year")
    private  int edoYear;

    @Column(name = "office_code")
    private String officeCode;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "do_no")
    private Long doNo;

    @Column(name = "do_date")
    private Date doDate;

    @Column(name = "do_image_loc")
    private String doImageLoc;

    @Column(name = "cnf_lic_no")
    private String cnfLicNo;

    @Column(name = "bl_type")
    private String blType;

    @Column(name = "gross_quantity")
    private Double grossQuantity;

    @Column(name = "delv_quantity")
    private Double delvQuantity;

    @Column(name = "line_no")
    private String lineNo;

    @Column(name = "receipt_no")
    private String receiptNo;

    @Column(name = "receipt_date")
    private Date receiptDate;

    @Column(name = "measurement")
    private String measurement;

    @Column(name = "r_no")
    private String rNo;

    @Column(name = "r_no_date")
    private Date rNoDate;

    @Column(name = "net_weight")
    private String netWeight;

    @Column(name = "cpa_checked_by")
    private String cpaCheckedBy;

    @Column(name = "cpa_check_ip")
    private String cpaCheckIp;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "ip_addr")
    private String ipAddr;

    @Column(name = "nts_send_st")
    private Long ntsSendSt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "nts_send_at")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss" , timezone = "UTC")
    private Date ntsSendAt;
}
