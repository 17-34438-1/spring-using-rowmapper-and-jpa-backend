package com.cpatos.edo.controller;


import com.cpatos.edo.model.sparcsn4.RefBizunitScoped;
import com.cpatos.edo.payload.*;
import com.cpatos.edo.service.cchaportdb.*;
import com.cpatos.edo.service.sparcsn4.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/edo")
public class EdoController {

    ResponseMessage responseMessage;

    @Autowired
    EdoApplicationService edoAppService;

    @Autowired
    OffDocInformationService offDocInformationService;

    @Autowired
    CommudityDetailService commudityDetailService;

    @Autowired
    PortCodeService portCodeService;

    @Autowired
    AgentService agentService;


    @Autowired
    AgentWiseContainerService agentWiseContainerService;


    @Autowired
    OffDocWiseContainerService offDocWiseContainerService;

    @Autowired
    VesselTransitWayService vesselTransitWayService;

    @Autowired
    BillEntryService billEntryService;


    @Autowired
    DischargeListForMloService dischargeListForMloService;

    @Autowired
    ExportContainerGateInService exportContainerGateInService;

    @Autowired
    AllAssignmentReportService allAssignmentReportService;


    //Rakib
    @PostMapping("/application/{rot}/{bl}/{loginId}/{ipAddress}")
    public ResponseEntity<ResponseMessage> applyEdo(@PathVariable String rot,
                                                    @PathVariable String bl,
                                                    @PathVariable String loginId,
                                                    @PathVariable String ipAddress){
        return edoAppService.edoApplication(rot,bl,loginId,ipAddress);
    }




    @PostMapping("/delete/{edoId}/{loginId}/{ipAddress}")
    public ResponseEntity<ResponseMessage> deleteEDO(@PathVariable Long edoId,
                                                     @PathVariable String loginId,
                                                     @PathVariable String ipAddress){

    return edoAppService.deleteEDO(edoId,loginId,ipAddress);

    }

    @GetMapping("/contList/{impRot}/{blNo}")
    public ResponseEntity<List<ContainerList>> contListForHblValidityExtension( @PathVariable String impRot,@PathVariable String blNo){

        impRot = impRot.replace("_","/");
        return edoAppService.contListForHblValidityExtension(impRot,blNo);
    }

    @PostMapping("/hbl/validity/extend/{uploadId}/{edoId}/{validityDate}/{loginId}")
    public ResponseEntity<ResponseMessage> validityExtendHBL(
                                    @RequestParam(value="contId") List<Long> contId,
                                    @PathVariable Long uploadId,
                                    @PathVariable Long edoId,
                                    @PathVariable String validityDate,
                                    @PathVariable String loginId
                                            ){
        return edoAppService.validityExtendHBL(contId,uploadId,edoId,validityDate,loginId);
    }

    @GetMapping("/contList/mbl/{impRot}/{blNo}")
    public ResponseEntity<List<ContainerList>> contListForMblValidityExtension( @PathVariable String impRot,@PathVariable String blNo){

        impRot = impRot.replace("_","/");
        return edoAppService.contListForMblValidityExtension(impRot,blNo);
    }

    @PostMapping("/mbl/validity/extend/{uploadId}/{edoId}/{validityDate}/{loginId}")
    public ResponseEntity<ResponseMessage> validityExtendMBL(
            @RequestParam(value="contId") List<Long> contId,
            @PathVariable Long uploadId,
            @PathVariable Long edoId,
            @PathVariable String validityDate,
            @PathVariable String loginId
    ){
        return edoAppService.validityExtendMBL(contId,uploadId,edoId,validityDate,loginId);
    }

    @GetMapping("/pdf/{uploadId}/{rotNo}/{bl}/{blType}/{submittedBy}/{organizationTypeId}/{loginId}")
    public ResponseEntity<EdoPdf> edoPdf(
            @PathVariable Long uploadId,@PathVariable String rotNo,
            @PathVariable String bl,@PathVariable String blType,
            @PathVariable String submittedBy,
            @PathVariable Integer organizationTypeId,
            @PathVariable String loginId
    ){
        rotNo = rotNo.replace("_","/");
        return edoAppService.edoPdf(uploadId,rotNo,bl,blType,submittedBy,organizationTypeId,loginId);
    }

    //Rafiq
    @RequestMapping(value = "igm/data/{rot}/{bl}", method = RequestMethod.GET)
    public @ResponseBody
    List<EdoIgmData> igmData(@PathVariable String rot,
                          @PathVariable String bl){
        return edoAppService.edoIgmData(rot,bl);
    }

    //Minhaz
    @GetMapping("/list")
    public ResponseEntity<List<EdoListDTO>> edoList(@RequestParam Long orgId,
                                                    @RequestParam String loginId,
                                                    @RequestParam(defaultValue = "0",required = false) int cpaSearch,
                                                    @RequestParam(defaultValue = " ",required = false) String flag,
                                                    @RequestParam(defaultValue = " ",required = false) String searchBy,
                                                    @RequestParam(defaultValue = " ", required = false) String searchInput,
                                                    @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date searchedBeDt) {

        List<EdoListDTO> edoList = edoAppService.edoList(orgId, loginId, cpaSearch, flag, searchBy, searchInput, searchedBeDt);
        return new ResponseEntity<List<EdoListDTO>>(edoList, HttpStatus.OK);
    }


    @GetMapping("/rejection")
    public ResponseEntity<ResponseMessage> rejectEdoApplication(@RequestParam String orgId,
                                                                @RequestParam String loginId,
                                                                @RequestParam String rejectionRemarks,
                                                                @RequestParam Long eId,
                                                                @RequestParam String type) {
        return edoAppService.rejectEdoApplication(orgId,loginId,rejectionRemarks,eId,type);
    }



    @GetMapping("/shedDeOInfoData")
    public ResponseEntity<ShedDEOInfoData> shedDEOInfoData(@RequestParam String rotNo,
                                                           @RequestParam String blNo,
                                                           @RequestParam Long edoId,
                                                           @RequestParam String typeOfBl,
                                                           @RequestParam String igmType,
                                                           @RequestParam String sumittedBy,
                                                           @RequestParam ( required = false) Long uploadId,
                                                           @RequestParam( required = false) String flag){

        System.out.println("uploadId : "+uploadId);
        System.out.println("flag : "+flag);

        ResponseEntity<ShedDEOInfoData> shedDEOInfoDataResponse = edoAppService.shedDeOInfoData(rotNo, blNo, edoId, typeOfBl, igmType, sumittedBy, uploadId, flag);
        return shedDEOInfoDataResponse;
    }

    @GetMapping("/get-cnf")
    public ResponseEntity<RefBizunitScoped> getCnf(@RequestParam String cnfLic)
    {
        System.out.println("cnfLic"+cnfLic);
        ResponseEntity<RefBizunitScoped> refBizunitScopedResponse = edoAppService.getCnf(cnfLic);
        return refBizunitScopedResponse;
    }


    @PostMapping("/update/status")
    public ResponseEntity<ResponseMessage> updateStatforEDO(
            @RequestParam Long edoId,
            @RequestParam String loginId,
            @RequestParam( required = false) String orgId,
            @RequestParam( required = false) String orgTypeId,
            @RequestParam( required = false) Date validUptoDate,
            HttpServletRequest request)
    {
        System.out.println("loginId : "+loginId);
        System.out.println("edoId : "+edoId);
        System.out.println("orgId : "+orgId);
        System.out.println("orgTypeId : "+orgTypeId);
        System.out.println("validUptoDate : "+validUptoDate);
        String ipAddress = request.getRemoteAddr();

        return edoAppService.updateStatforEDO(edoId,loginId,orgId,orgTypeId,validUptoDate,ipAddress);

    }

    @PostMapping("/shed/do/upload")
    public void shedDOUpload(@RequestBody  ShedDEOInfoData shedDEOInfoData,
                             @RequestParam  String loginId,
                             @RequestParam  Long orgId,
                             @RequestParam  String orgTypeId )
    {
        edoAppService.shedDOUpload(shedDEOInfoData,loginId,orgId,orgTypeId);
    }



    //Rafiq
    @RequestMapping(value = "/shedUpdateForm/{rot}/{container}",method = RequestMethod.GET)
    public @ResponseBody
    List billOfEntry(@PathVariable String rot,@PathVariable String container){
        return billEntryService.UpdateBillOfEntry(rot,container);
    }



    //Rafiq
    @RequestMapping(value = "/shedUpdateForm/{rot}/{container}/{dtlID}/{beNo}/{beDate}/{offCode}/{tblName}",method = RequestMethod.GET)
    public @ResponseBody
    List billOfEntry(@PathVariable String rot,@PathVariable String container,@PathVariable Long dtlID,@PathVariable String beNo,@PathVariable String beDate,@PathVariable String offCode,@PathVariable String tblName){
        return billEntryService.UpdatedBillOfEntry(rot,container,dtlID,beNo,beDate,offCode,tblName);
    }

    //Rafiq
    @RequestMapping(value = "/dischargeListForMlo/{from_date}/{to_date}",method = RequestMethod.GET)
    public @ResponseBody
    List mloWisePreAdvised(@PathVariable String from_date,@PathVariable String to_date){
        return dischargeListForMloService.getDischargeListForMlo(from_date,to_date);
    }

    //Rafiq
    @RequestMapping(value = "/ExportContainerGateIN/{rotation}",method = RequestMethod.GET)
    public @ResponseBody
    List gateIn(@PathVariable String rotation){
        return exportContainerGateInService.getExportContainerGateIn(rotation);
    }

    //Rafiq
    @RequestMapping(value = "/ShedDeliveryOrderInfo", method = RequestMethod.GET)
    public @ResponseBody
    List ShedDeliveryOrderInfo(){
        return billEntryService.ShedDeliveryOrderList();
    }


}
