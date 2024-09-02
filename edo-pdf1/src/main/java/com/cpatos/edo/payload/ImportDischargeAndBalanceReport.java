package com.cpatos.edo.payload;

import lombok.Data;

@Data
public class ImportDischargeAndBalanceReport {



    	                        String    ID;
									String FCY_TIME_IN;
										String cl_date;
									String LOCATION;
										String SEALNO;
									String ISO;
									String MLO;
									String FREIGHT_KIND;
										String WEIGHT;
									String SHORT_NAME;
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
