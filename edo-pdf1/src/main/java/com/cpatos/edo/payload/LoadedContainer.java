package com.cpatos.edo.payload;

import lombok.Data;

@Data
public class LoadedContainer {
    String CONT_ID;
    String CONT_SIZE;
    String GOODS_AND_CTR_WT_KG;
    String SEAL_NO;

    String ml;
    String ISO;
    String COMING_FROM;
    String NAME;
    String IB_VYG;
    String PRE_ADVISED_DT;
    String FREIGHT_KIND;
    String POD;
    String stowage_pos;
    String remark;

    private String onboard_LD_20;
    private String onboard_LD_40;
    private String onboard_MT_20;
    private String onboard_MT_40;
    private String onboard_LD_tues;
    private String onboard_MT_tues;
    private String balance_LD_20;
    private String balance_LD_40;
    private String balance_MT_20;
    private String balance_MT_40;
    private String balance_LD_tues;
    private String balance_MT_tues;

}
