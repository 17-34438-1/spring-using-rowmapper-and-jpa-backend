package com.cpatos.edo.service.sparcsn4;

import java.util.List;

public interface ImportDischargeAndBalanceService {
    public List getContainerReportBalance(String rotation);
    public List getContainerReportDischarge(String rotation);
    public List getDischargeReport(String rotation);
    public List getBalanceReport(String rotation);
}
