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
@Table(name = "do_upload_wise_container")
public class DoUploadWiseContainer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cont_igm_id")
    private Long contIgmId;

    @Column(name = "shed_mlo_do_info_id")
    private Long shedMloDoInfoId;

    @Column(name = "valid_upto_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date validUptoDate;

//    @ManyToOne(
//            cascade = CascadeType.ALL,
//            fetch = FetchType.LAZY
//    )
//    @JoinColumn(
//            name = "cont_igm_id",
//            insertable = false, updatable = false,
//            referencedColumnName = "id"
//    )
//    private IgmSupDetailContainer igmSupDetailContainer;
}
