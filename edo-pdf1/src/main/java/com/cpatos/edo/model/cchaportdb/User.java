package com.cpatos.edo.model.cchaportdb;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hibernate_sequence_generator")
    @SequenceGenerator(name = "hibernate_sequence_generator",
            sequenceName = "HIBERNATE_SEQUENCE",
            allocationSize = 1)
    private Long id;

    @Column(name = "u_name")
    private String uName;

    @Column(name = "org_id")
    private Long orgId;

    @Column(name = "login_id")
    private String loginId;

    @Column(name = "org_Type_id")
    private Integer orgTypeId;
    @ManyToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "org_id",
            insertable = false, updatable = false,
            referencedColumnName = "id"
    )
    private OrganizationProfile organizationProfile;
}
