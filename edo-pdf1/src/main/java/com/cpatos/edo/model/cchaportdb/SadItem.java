package com.cpatos.edo.model.cchaportdb;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sad_item")
public class SadItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hibernate_sequence_generator")
    @SequenceGenerator(name = "hibernate_sequence_generator",
            sequenceName = "HIBERNATE_SEQUENCE",
            allocationSize = 1)
    private Long id;

//    @Column(name = "sad_id")
//    private Long sadId;

    @Column(name = "sum_declare")
    private String sumDeclare;
	
	@ManyToOne
    @JoinColumn(name = "sad_id")
    private SadInfo sadInfo;
}
