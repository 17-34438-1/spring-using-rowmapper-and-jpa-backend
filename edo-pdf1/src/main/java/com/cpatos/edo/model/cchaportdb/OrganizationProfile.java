package com.cpatos.edo.model.cchaportdb;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "organization_profiles")
public class OrganizationProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hibernate_sequence_generator")
    @SequenceGenerator(name = "hibernate_sequence_generator",
            sequenceName = "HIBERNATE_SEQUENCE",
            allocationSize = 1)
    private Long id;

//    @Column(name = "id")
//    private String id;

    @Column(name = "Org_Type_id")
    private String orgTypeid;

    @Column(name = "Organization_Name")
    private String organizationName;

    @Column(name = "AIN_No")
    private String ainNo;

    @Column(name = "AIN_No_New")
    private String ainNoNew;

    @Column(name = "License_No")
    private String licenseNo;

    @Column(name = "logo")
    private String logo;

    @Column(name = "Address_1")
    private String address1;

    @Column(name = "Address_2")
    private String address2;

    @Column(name = "Cell_No_1")
    private String cellNo1;

    @Column(name = "Telephone_No_Land")
    private String telephoneNoLand;
}
