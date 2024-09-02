package com.cpatos.edo.model.cchaportdb;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sad_info")
public class SadInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hibernate_sequence_generator")
    @SequenceGenerator(name = "hibernate_sequence_generator",
            sequenceName = "HIBERNATE_SEQUENCE",
            allocationSize = 1)
    private Long id;
	
	@Column(name = "manif_num")
    String manifNum;
	
	@Column(name = "place_dec")
    String placeDec;

    @Column(name = "reg_no")
    private String regNo;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "reg_date")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss" , timezone = "UTC")
    private Date regDate;

    @Column(name = "dec_code")
    private String decCode;
	
	@OneToMany(mappedBy = "sadInfo")
    private List<SadItem> sadItems;
}
