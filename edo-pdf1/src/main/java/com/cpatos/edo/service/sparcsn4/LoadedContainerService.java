package com.cpatos.edo.service.sparcsn4;

import java.util.List;

public interface LoadedContainerService {
    public List getLoadedContainer(String rotation,String from_date,String to_date);
    public List getDischargeReport(String rotation);
    public List getBalanceReport(String rotation);

}
