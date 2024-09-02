package com.cpatos.edo.model.cchaportdb;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "igm_detail_container")
public class IgmDetailContainer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hibernate_sequence_generator")
    @SequenceGenerator(name = "hibernate_sequence_generator",
            sequenceName = "HIBERNATE_SEQUENCE",
            allocationSize = 1)
    private Long id;

    @Column(name = "cont_status")
    private String contStatus;

    @Column(name = "cont_number")
    private String contNumber;

    @Column(name = "cont_location_code")
    private String contLocationCode;

    @Column(name = "cont_seal_number")
    private String contSealNumber;

    @Column(name = "cont_size")
    private String contSize;

    @Column(name = "cont_type")
    private String contType;

    @Column(name = "cont_height")
    private String contHeight;

    @Column(name = "Cont_gross_weight")
    private String contGrossWeight;

    @Column(name = "cont_weight")
    private String contWeight;

    @Column(name = "cont_number_packaages")
    private String contNumberPackaages;

    @ManyToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "igm_detail_id",
            insertable = false, updatable = false,
            referencedColumnName = "id"
    )
    private IgmDetails igmDetails;

}
