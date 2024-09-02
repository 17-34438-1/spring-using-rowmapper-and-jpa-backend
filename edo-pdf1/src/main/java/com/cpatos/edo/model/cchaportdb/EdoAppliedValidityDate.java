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
@Table(name = "edo_applied_validity_date")
public class EdoAppliedValidityDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "edo_id")
    private Long edoId;

    @Column(name = "shed_mlo_do_info_id")
    private Long shedMloDoInfoId;

    @Column(name = "cont_igm_id")
    private Long contIgmId;

    @Column(name = "applied_validity_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date appliedValidityDate;

    @Column(name = "entered_by")
    private String enteredBy;

    @Column(name = "entered_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime enteredAt;
}
