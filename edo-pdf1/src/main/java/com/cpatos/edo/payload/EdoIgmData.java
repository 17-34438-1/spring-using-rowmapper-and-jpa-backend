package com.cpatos.edo.payload;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class EdoIgmData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String ShippingAgent;
    private String BlType_BB;
    private String Type_of_igm;
    private String MloId;
    private String FfName;
    private String MloName;
    private String ContStatus;
    private String MasterBl;

    public String getMasterBl() {
        return MasterBl;
    }

    public void setMasterBl(String masterBl) {
        MasterBl = masterBl;
    }

    public String getMloId() {
        return MloId;
    }

    public void setMloId(String mloId) {
        MloId = mloId;
    }

    public String getContStatus() {
        return ContStatus;
    }

    public void setContStatus(String contStatus) {
        ContStatus = contStatus;
    }

    public String getMloName() {
        return MloName;
    }

    public void setMloName(String mloName) {
        MloName = mloName;
    }

    public String getFfName() {
        return FfName;
    }

    public void setFfName(String ffName) {
        FfName = ffName;
    }



    public String getType_of_igm() {
        return Type_of_igm;
    }

    public void setType_of_igm(String type_of_igm) {
        Type_of_igm = type_of_igm;
    }

    public String getBlType_BB() {
        return BlType_BB;
    }

    public void setBlType_BB(String blType_BB) {
        BlType_BB = blType_BB;
    }

    public String getShippingAgent() {
        return ShippingAgent;
    }

    public void setShippingAgent(String shippingAgent) {
        ShippingAgent = shippingAgent;
    }
}
