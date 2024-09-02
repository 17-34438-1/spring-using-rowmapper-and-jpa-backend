package com.cpatos.edo.model.cchaportdb;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "offdoc")
public class OffDoc {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hibernate_sequence_generator")
    @SequenceGenerator(name = "hibernate_sequence_generator",
            sequenceName = "HIBERNATE_SEQUENCE",
            allocationSize = 1)
    private Long id;

    @Column(name = "code")
    String code;



    @Column(name = "code_ctms")
    String code_ctms;

    @Column(name = "name")
    String name;

    @Column(name = "phone")
    String phone;

    @Column(name = "fax")
    String fax;


}
