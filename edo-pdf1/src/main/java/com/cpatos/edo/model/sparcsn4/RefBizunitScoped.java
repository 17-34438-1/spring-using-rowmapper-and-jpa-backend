package com.cpatos.edo.model.sparcsn4;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ref_bizunit_scoped")
public class RefBizunitScoped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GKEY")
    private Long gkey;

    @Column(name = "id")
    private String id;


    @Column(name = "NAME")
    private String name;

    @Column(name = "ROLE")
    private String role;


    @Column(name = "ADDRESS_LINE1")
    private String addressLine1;


    @Column(name = "ADDRESS_LINE2")
    private String addressLine2;

    @Column(name = "LIFE_CYCLE_STATE")
    private String lifeCycleState;



}
