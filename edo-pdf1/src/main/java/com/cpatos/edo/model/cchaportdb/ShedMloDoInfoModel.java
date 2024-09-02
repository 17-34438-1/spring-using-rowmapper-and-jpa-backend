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
public class ShedMloDoInfoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "igm_detail_id")
    private Long igmDetailId;

//    @Column(name = "edo_id")
//    private  int edoId;

    @Column(name = "be_no")
    private String beNo;

    @Column(name = "imp_rot")
    private String impRot;

//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    @Column(name = "be_date")
//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss" , timezone = "UTC")
//    private Date beDate;

    @Column(name = "bl_no")
    private String blNo;

    @Column(name = "do_image_loc")
    private String doImageLoc;
}
