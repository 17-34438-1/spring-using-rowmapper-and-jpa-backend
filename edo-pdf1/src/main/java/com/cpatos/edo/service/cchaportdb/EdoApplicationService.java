package com.cpatos.edo.service.cchaportdb;

import com.cpatos.edo.model.sparcsn4.RefBizunitScoped;
import com.cpatos.edo.payload.*;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface EdoApplicationService {
    public ResponseEntity<ResponseMessage> edoApplication(String rot, String bl, String loginId, String ipAddress);
    public ResponseEntity<ResponseMessage> edoApplicationWithoutCNF(String rot, String bl, String ain_no, String loginId, String ipAddress);


    public ResponseEntity<ResponseMessage> deleteEDO(Long edoId,String loginId,String ipAddress);
    public ResponseEntity<List<ContainerList>> contListForHblValidityExtension(String impRot,String blNo);
    public ResponseEntity<List<ContainerList>> contListForMblValidityExtension(String impRot,String blNo);
    public ResponseEntity<ResponseMessage> validityExtendHBL(List<Long> contId, Long uploadId, Long edoId, String validityDate, String loginId);
    public ResponseEntity<ResponseMessage> validityExtendMBL(List<Long> contId, Long uploadId, Long edoId, String validityDate, String loginId);
    public ResponseEntity<EdoPdf> edoPdf(Long uploadId,String rotNo,String bl,String blType,String submittedBy,Integer organizationTypeId, String loginId);
    //Raifq
    public List<EdoIgmData> edoIgmData(String rot, String bl);
    //Minhaz
    public  List<EdoListDTO> edoList(Long orgId, String loginId, int cpaSearch, String flag, String searchBy, String searchInput, Date searchedBeDt);
    public ResponseEntity<ResponseMessage>rejectEdoApplication(String orgId, String loginId, String rejectionRemarks, Long eId, String type);

    public ResponseEntity<ShedDEOInfoData> shedDeOInfoData(String rotNo, String blNo, Long edoId, String typeOfBl, String igmType, String sumittedBy, Long uploadId, String flag);

    ResponseEntity<RefBizunitScoped> getCnf(String cnfLic);

    public void shedDOUpload(ShedDEOInfoData shedDEOInfoData,String loginId,Long orgId,String orgTypeId);

    public ResponseEntity<ResponseMessage> updateStatforEDO(Long edoId,String loginId,String orgId,String orgTypeId,Date validUptoDate,String ipAddress);

    public List edoContainerReport(String from_date,String to_date);


}
