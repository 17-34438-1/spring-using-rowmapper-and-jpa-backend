package com.cpatos.edo.payload;

import com.cpatos.edo.model.cchaportdb.EdoApplication;
import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EdoListDTO {
    private EdoApplication edoApplication;
    String exitNo;
    private Long uploadId;
    private Date uploadedAt;
    private Date validityDt;
    private int checkSt;
    private Date approveAt;
    private String beNo;
    private Date beDate;
    private String edoMlo;

    private String edoSl;
    private int edoYear;
    private String edoNumber;

    String applicantName;
    String applicantLic;
    String applicantAIN;
    String mloName;
    String mloAIN;
    String ffName;
    String ffAIN;
    String shippingAgentName;
    String shippingAgentAIN;

}