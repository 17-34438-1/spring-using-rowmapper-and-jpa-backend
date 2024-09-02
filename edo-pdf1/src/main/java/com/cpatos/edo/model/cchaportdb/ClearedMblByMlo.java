package com.cpatos.edo.model.cchaportdb;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cleared_mbl_by_mlo")
public class ClearedMblByMlo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hibernate_sequence_generator")
    @SequenceGenerator(name = "hibernate_sequence_generator",
            sequenceName = "HIBERNATE_SEQUENCE",
            allocationSize = 1)
    private Long id;

    @Column(name = "master_bl")
    private String masterBl;

    @Column(name = "clearance_st")
    private Integer clearanceSt;

    @Column(name = "valid_upto_dt")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date validUptoDt;

    @Column(name = "entry_org_id")
    private Integer entryOrgId;

    @Column(name = "entered_by")
    private String enteredBy;

    @Column(name = "entry_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mi:ss")
    private Date entryTime;

    @Column(name = "entry_ip_address")
    private String entryIpAddress;

    @Column(name = "cleared_by")
    private String clearedBy;

    @Column(name = "clearance_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mi:ss")
    private Date clearanceTime;

    @Column(name = "clearance_ip")
    private String clearanceIp;

    @Column(name = "cleared_by_org_id")
    private Integer clearedByOrgId;
}
