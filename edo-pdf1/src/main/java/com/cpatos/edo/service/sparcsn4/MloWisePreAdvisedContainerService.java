package com.cpatos.edo.service.sparcsn4;


import java.util.List;

public interface MloWisePreAdvisedContainerService {
    public List getMloWisePreAdvisedContainer(String rotation);
    public List getPreAdvisedContainer(String rotation,String mlo);
    public List getExportSummary(String rotation,String mlo);

    }
