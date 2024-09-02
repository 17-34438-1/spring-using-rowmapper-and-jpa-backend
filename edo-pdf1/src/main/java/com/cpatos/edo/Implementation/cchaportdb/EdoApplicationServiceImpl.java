package com.cpatos.edo.Implementation.cchaportdb;


import com.cpatos.edo.model.cchaportdb.*;
import com.cpatos.edo.model.sparcsn4.RefBizunitScoped;
import com.cpatos.edo.payload.*;
import com.cpatos.edo.repository.cchaportdb.*;
import com.cpatos.edo.repository.sparcsn4.RefBizunitScopedRepo;
import com.cpatos.edo.service.cchaportdb.EdoApplicationService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class EdoApplicationServiceImpl implements EdoApplicationService {

    ResponseMessage responseMessage;

    @Autowired
    EdoApplicationRepository edoAppRepo;

    @Autowired
    IgmDetailsRepository igmDetailsRepo;

    @Autowired
    IgmSupplimentaryDetailsRepository igmSuppDetailsRepo;

    @Autowired
    IgmSupDetailContainerRepository igmSupDtlContRepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    ClearedMblByMloRepository clearedMblByMloRepo;

    @Autowired
    IgmDtlContRepo igmDtlContRepo;

    @Autowired
    EdoAppDltLogRepository dltLogRepository;

    @Autowired
    EdoAppliedValidityDateRepository validityDateRepo;

    @Autowired
    OrganizationProfileRepository organizationProfileRepository;

    @Autowired
    DoUploadWiseContRepository doUploadWiseContRepo;

    @Autowired
    IgmMastersRepository igmMastersRepo;

    @Autowired
    OrganizationProfileRepository orgProfileRepo;

    @Autowired
    VesselsBerthDetailRepository vslBerthDtlRepo;

    @Autowired
    SadItemRepository sadItemRepo;

    @Autowired
    SadInfoRepository sadInfoRepo;

    @Autowired
    ShedMloDoInfoRepository shedMloDoInfoRepo;

    @Autowired
    OrganizationLogoRepository orgLogoRepo;

    @Autowired
    RefBizunitScopedRepo refBizunitScopedRepo;

    @Autowired
    EdoContainerReportRepository  edoContainerReportRepository;



    @Override
    public ResponseEntity<ResponseMessage> edoApplication(String rot, String bl, String loginId, String ipAddress){
        String cnfLoginId = "";
        rot = rot.replace("_","/");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        System.out.println(formatter.format(date));
        System.out.println(java.time.LocalDateTime.now().minusHours(6));

        Integer cntExisting = edoAppRepo.countByRotationAndBl(rot,bl);
        if(cntExisting > 0){
            List<EdoApplication> edoApplicationListByRotAndBl = edoAppRepo.findByRotationAndBl(rot,bl);
            for (int i=0;i<edoApplicationListByRotAndBl.size();i++){
                cnfLoginId = edoApplicationListByRotAndBl.get(i).getEdoAppliedBy();
            }
        }

        Integer cntApplication = edoAppRepo.countByRotationAndBlAndRejectionSt(rot,bl,0);

        if((cntApplication==0) && (!cnfLoginId.equals(loginId)))
        {
            String typeOfIgm = "";
            String blTypeBB = "";

            Integer cntResult = igmDetailsRepo.countByImportRotationNoAndBlNo(rot,bl);
            if(cntResult == 0)
            {
                Integer cntSupResult = igmSuppDetailsRepo.countByImportRotationNoAndBlNo(rot,bl);
                if(cntSupResult == 0)
                {
                    responseMessage = new ResponseMessage( "Wrong Combination of Rotation and BL");
                    return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
                }
                else
                {
                    List<IgmSupplimentaryDetail> igmSupplimentaryDetails = igmSuppDetailsRepo.findByImportRotationNoAndBlNo(rot,bl);
                    for (int i=0; i<igmSupplimentaryDetails.size();i++){
                        typeOfIgm = igmSupplimentaryDetails.get(i).getTypeOfIgm();
                    }
                    blTypeBB = "HB";
                }
            }
            else
            {
                List<IgmDetails> igmDetails = igmDetailsRepo.findByImportRotationNoAndBlNo(rot,bl);
                for (int i=0; i<igmDetails.size();i++){
                    typeOfIgm = igmDetails.get(i).getTypeOfIgm();
                }
                blTypeBB = "MB";
            }

            if(typeOfIgm != "")
            {
                if(typeOfIgm.equals("BB"))
                {
                    String submiteeOrgId = "";
                    List<IgmSupplimentaryDetail> igmSuppDtls = igmSuppDetailsRepo.findByImportRotationNoAndBlNo(rot,bl);
                    List<IgmDetails> igmDtls = igmDetailsRepo.findByImportRotationNoAndBlNo(rot,bl);

                    if(igmSuppDtls.size() == 0)
                    {
                        for (int i=0; i<igmDtls.size();i++){
                            submiteeOrgId = igmDtls.get(i).getSubmiteeOrgId();
                        }
                    }
                    else
                    {
                        for (int i=0; i<igmSuppDtls.size();i++){
                            submiteeOrgId = igmSuppDtls.get(i).getSubmiteeOrgId();
                        }
                    }



                    EdoApplication edoApplication = new EdoApplication();
                    edoApplication.setRotation(rot);
                    edoApplication.setBl(bl);
                    edoApplication.setBlType(blTypeBB);
                    edoApplication.setIgmType(typeOfIgm);
                    edoApplication.setShAgentOrgId(submiteeOrgId);
                    edoApplication.setEntryTime(java.time.LocalDateTime.now().minusHours(6));
                    edoApplication.setSubmittedBy(loginId);
                    edoApplication.setIpAddress(ipAddress);
                    edoApplication.setEdoAppliedBy(loginId);

                    edoAppRepo.save(edoApplication);
                    responseMessage = new ResponseMessage( "Application Successful");
                    return new ResponseEntity<>(responseMessage, HttpStatus.OK);
                }
                else
                {
                    System.out.println("........... GM ...............");
                    String blType = "";
                    List<IgmSupplimentaryDetail> igmData = igmSuppDetailsRepo.findByImportRotationNoAndBlNo(rot,bl);
                    if(igmData.size() > 0)
                    {
                        blType = "HB";

                        String supOrg = "";
                        String masterOrg = "";
                        String masterBl = "";
                        String contStatus = "";
                        Integer ffStat = 0;
                        Date ffClearanceTime = null;
                        Integer entryOrgId = 0;
                        String enteredBy = "";
                        Date entryTime = null;
                        String entryIpAddress = "";
                        String clearedBy = "";
                        Date clearanceTime = null;
                        String clearanceIp = "";
                        Integer clearedByOrgId = 0;
                        Integer clearanceSt = 0;
                        Date mblValidUptoDt = null;
                        Integer fclStatus = 0;

                        for (int i=0; i<igmData.size();i++){
                            supOrg = igmData.get(i).getSubmiteeOrgId();
                            masterOrg = igmData.get(i).getIgmDetails().getSubmiteeOrgId();
                        }

                        List<IgmSupDetailContainer> contDtls = igmSupDtlContRepo.findByIgmSupplimentaryDetailImportRotationNoAndIgmSupplimentaryDetailBlNo(rot,bl);
                        for (int i=0;i<contDtls.size();i++ )
                        {
                            contStatus = contDtls.get(i).getContStatus();
                            masterBl = contDtls.get(i).getIgmSupplimentaryDetail().getMasterBLNo();

                            if(contStatus.equals("FCL") || contStatus.equals("FCL/PART") || contStatus.equals("ETY"))
                            {
                                fclStatus = fclStatus+1;
                            }
                        }

                        if(fclStatus > 0)
                        {
                            contStatus = "FCL";
                        }
                        else
                        {
                            contStatus = contStatus;
                        }

                        Integer providedOrgTypeId = 0;
                        List<User> users = userRepo.findByLoginId(loginId);
                        for (int i=0; i<users.size();i++)
                        {
                            providedOrgTypeId = users.get(i).getOrgTypeId();
                        }

                        if(contStatus.equals("") || masterBl.equals("") ){
                            responseMessage = new ResponseMessage( "Sorry! Could not apply EDO");
                            return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
                        } else if(providedOrgTypeId != 2){
                            responseMessage = new ResponseMessage( "Sorry! Invalid C&F.");
                            return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
                        } else {
                            if(contStatus.equals("LCL"))
                            {
                                Integer mblClearanceStatus = clearedMblByMloRepo.countByMasterBl(masterBl);
                                if(mblClearanceStatus == 0)
                                {
                                    ffStat = 0;
                                    EdoApplication edoApplication = new EdoApplication();
                                    edoApplication.setRotation(rot);
                                    edoApplication.setBl(bl);
                                    edoApplication.setBlType(blType);
                                    edoApplication.setIgmType(typeOfIgm);
                                    edoApplication.setMlo(masterOrg);
                                    edoApplication.setFfOrgId(supOrg);
                                    edoApplication.setFfStat(ffStat);
                                    edoApplication.setFfClearanceTime(ffClearanceTime);
                                    edoApplication.setContStatus(contStatus);
                                    edoApplication.setMblOfHbl(masterBl);
                                    edoApplication.setEntryTime(java.time.LocalDateTime.now().minusHours(6));
                                    edoApplication.setSubmittedBy(loginId);
                                    edoApplication.setIpAddress(ipAddress);
                                    edoApplication.setEdoAppliedBy(loginId);
                                    edoAppRepo.save(edoApplication);
                                    responseMessage = new ResponseMessage( "Application Successful");
                                    return new ResponseEntity<>(responseMessage, HttpStatus.OK);
                                }
                                else
                                {
                                    List<ClearedMblByMlo> clearedData = clearedMblByMloRepo.findByMasterBl(masterBl);
                                    for (int i=0;i<clearedData.size();i++)
                                    {
                                        ffClearanceTime = clearedData.get(i).getEntryTime();
                                        clearanceSt = clearedData.get(i).getClearanceSt();
                                        entryOrgId = clearedData.get(i).getEntryOrgId();
                                        enteredBy = clearedData.get(i).getEnteredBy();
                                        entryTime = clearedData.get(i).getEntryTime();
                                        entryIpAddress = clearedData.get(i).getEntryIpAddress();
                                        clearedBy = clearedData.get(i).getClearedBy();
                                        clearanceTime = clearedData.get(i).getClearanceTime();
                                        clearanceIp = clearedData.get(i).getClearanceIp();
                                        clearedByOrgId = clearedData.get(i).getClearedByOrgId();
                                        mblValidUptoDt = clearedData.get(i).getValidUptoDt();
                                    }
                                    ffStat = 1;
                                    EdoApplication edoApplication = new EdoApplication();
                                    edoApplication.setRotation(rot);
                                    edoApplication.setBl(bl);
                                    edoApplication.setBlType(blType);
                                    edoApplication.setIgmType(typeOfIgm);
                                    edoApplication.setMlo(masterOrg);
                                    edoApplication.setFfOrgId(supOrg);
                                    edoApplication.setFfStat(ffStat);
                                    edoApplication.setFfClearanceTime(ffClearanceTime);
                                    edoApplication.setForwardedBy(clearedBy);
                                    edoApplication.setForwardedOrgId(clearedByOrgId);
                                    edoApplication.setContStatus(contStatus);
                                    edoApplication.setMblOfHbl(masterBl);
                                    edoApplication.setEntryTime(java.time.LocalDateTime.now().minusHours(6));
                                    edoApplication.setSubmittedBy(loginId);
                                    edoApplication.setIpAddress(ipAddress);
                                    edoApplication.setEdoAppliedBy(loginId);
                                    edoAppRepo.save(edoApplication);
                                    responseMessage = new ResponseMessage( "Application Successful");
                                    return new ResponseEntity<>(responseMessage, HttpStatus.OK);
                                }
                            }
                            else if(contStatus.equals("FCL") || contStatus.equals("FCL/PART") || contStatus.equals("ETY"))
                            {
                                Integer mblClearanceStatus = clearedMblByMloRepo.countByMasterBl(masterBl);
                                if(mblClearanceStatus == 0)
                                {
                                    ffStat = 0;
                                    EdoApplication edoApplication = new EdoApplication();
                                    edoApplication.setRotation(rot);
                                    edoApplication.setBl(bl);
                                    edoApplication.setBlType(blType);
                                    edoApplication.setIgmType(typeOfIgm);
                                    edoApplication.setMlo(masterOrg);
                                    edoApplication.setFfOrgId(supOrg);
                                    edoApplication.setFfStat(ffStat);
                                    edoApplication.setFfClearanceTime(ffClearanceTime);
                                    edoApplication.setContStatus(contStatus);
                                    edoApplication.setMblOfHbl(masterBl);
                                    edoApplication.setEntryTime(java.time.LocalDateTime.now().minusHours(6));
                                    edoApplication.setSubmittedBy(loginId);
                                    edoApplication.setIpAddress(ipAddress);
                                    edoApplication.setEdoAppliedBy(loginId);
                                    edoAppRepo.save(edoApplication);
                                    responseMessage = new ResponseMessage( "Application Successful");
                                    return new ResponseEntity<>(responseMessage, HttpStatus.OK);
                                }
                                else
                                {
                                    List<ClearedMblByMlo> clearedData = clearedMblByMloRepo.findByMasterBl(masterBl);
                                    for (int i=0;i<clearedData.size();i++)
                                    {
                                        ffClearanceTime = clearedData.get(i).getEntryTime();
                                        clearanceSt = clearedData.get(i).getClearanceSt();
                                        entryOrgId = clearedData.get(i).getEntryOrgId();
                                        enteredBy = clearedData.get(i).getEnteredBy();
                                        entryTime = clearedData.get(i).getEntryTime();
                                        entryIpAddress = clearedData.get(i).getEntryIpAddress();
                                        clearedBy = clearedData.get(i).getClearedBy();
                                        clearanceTime = clearedData.get(i).getClearanceTime();
                                        clearanceIp = clearedData.get(i).getClearanceIp();
                                        clearedByOrgId = clearedData.get(i).getClearedByOrgId();
                                        mblValidUptoDt = clearedData.get(i).getValidUptoDt();
                                    }
                                    ffStat = 1;
                                    EdoApplication edoApplication = new EdoApplication();
                                    edoApplication.setRotation(rot);
                                    edoApplication.setBl(bl);
                                    edoApplication.setBlType(blType);
                                    edoApplication.setIgmType(typeOfIgm);
                                    edoApplication.setMlo(masterOrg);
                                    edoApplication.setFfOrgId(supOrg);
                                    edoApplication.setFfStat(ffStat);
                                    edoApplication.setFfClearanceTime(ffClearanceTime);
                                    edoApplication.setForwardedBy(clearedBy);
                                    edoApplication.setForwardedOrgId(clearedByOrgId);
                                    edoApplication.setValidUptoDtByMlo(mblValidUptoDt);
                                    edoApplication.setContStatus(contStatus);
                                    edoApplication.setMblOfHbl(masterBl);
                                    edoApplication.setEntryTime(java.time.LocalDateTime.now().minusHours(6));
                                    edoApplication.setSubmittedBy(loginId);
                                    edoApplication.setIpAddress(ipAddress);
                                    edoApplication.setEdoAppliedBy(loginId);
                                    edoAppRepo.save(edoApplication);
                                    responseMessage = new ResponseMessage( "Application Successful");
                                    return new ResponseEntity<>(responseMessage, HttpStatus.OK);
                                }

                            }
                            else
                            {
                                responseMessage = new ResponseMessage( "Sorry! Could not apply EDO");
                                return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
                            }
                        }

                    }
                    else
                    {
                        blType = "MB";
                        List<IgmDetailContainer> contDtls = igmDtlContRepo.findByIgmDetailsImportRotationNoAndIgmDetailsBlNo(rot,bl);
                        String containerStatus = "";
                        Integer fclStatus = 0;
                        String masterOrg = "";

                        for(int i=0;i<contDtls.size();i++)
                        {
                            containerStatus = contDtls.get(i).getContStatus();

                            if(containerStatus.equals("FCL") || containerStatus.equals("FCL/PART") || containerStatus.equals("ETY"))
                            {
                                fclStatus = fclStatus+1;
                            }
                        }
                        if(fclStatus > 0)
                        {
                            containerStatus = "FCL";
                        }
                        else
                        {
                            containerStatus = containerStatus;
                        }

                        List<IgmDetails> igmDtls = igmDetailsRepo.findByImportRotationNoAndBlNo(rot,bl);
                        for (int i=0; i<igmDtls.size();i++){
                            masterOrg = igmDtls.get(i).getSubmiteeOrgId();
                        }

                        if(masterOrg.equals("")){
                            responseMessage = new ResponseMessage( "Sorry! Could not apply EDO");
                            return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
                        } else {
                            EdoApplication edoApplication = new EdoApplication();
                            edoApplication.setRotation(rot);
                            edoApplication.setBl(bl);
                            edoApplication.setBlType(blType);
                            edoApplication.setIgmType(typeOfIgm);
                            edoApplication.setMlo(masterOrg);
                            edoApplication.setContStatus(containerStatus);
                            edoApplication.setEntryTime(java.time.LocalDateTime.now().minusHours(6));
                            edoApplication.setSubmittedBy(loginId);
                            edoApplication.setIpAddress(ipAddress);
                            edoApplication.setEdoAppliedBy(loginId);
                            edoAppRepo.save(edoApplication);
                            responseMessage = new ResponseMessage( "Application Successful");
                            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
                        }
                    }
                }
            }
            else
            {
                responseMessage = new ResponseMessage( "Sorry! Could not apply EDO");
                return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
            }
        }
        else
        {
            responseMessage = new ResponseMessage( "Sorry ! Already applied for Rotation : " + rot + " and BL : " + bl);
            return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
        }
    }





    @Override
    public ResponseEntity<ResponseMessage> edoApplicationWithoutCNF(String rot, String bl, String ain_no , String loginId, String ipAddress){

        String cnfLoginId = "";
        rot = rot.replace("_","/");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        System.out.println(formatter.format(date));
        System.out.println(java.time.LocalDateTime.now().minusHours(6));

        Integer cntExisting = edoAppRepo.countByRotationAndBl(rot,bl);
        if(cntExisting > 0){
            List<EdoApplication> edoApplicationListByRotAndBl = edoAppRepo.findByRotationAndBl(rot,bl);
            for (int i=0;i<edoApplicationListByRotAndBl.size();i++){
                cnfLoginId = edoApplicationListByRotAndBl.get(i).getEdoAppliedBy();
            }
        }

        Integer cntApplication = edoAppRepo.countByRotationAndBlAndRejectionSt(rot,bl,0);
        if(cntApplication==0) {

            String typeOfIgm = "";
            String blTypeBB = "";

            Integer cntResult = igmDetailsRepo.countByImportRotationNoAndBlNo(rot, bl);
            if (cntResult == 0) {
                Integer cntSupResult = igmSuppDetailsRepo.countByImportRotationNoAndBlNo(rot, bl);
                if (cntSupResult == 0) {
                    responseMessage = new ResponseMessage("Wrong Combination of Rotation and BL");
                    return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
                } else {
                    List<IgmSupplimentaryDetail> igmSupplimentaryDetails = igmSuppDetailsRepo.findByImportRotationNoAndBlNo(rot, bl);
                    for (int i = 0; i < igmSupplimentaryDetails.size(); i++) {
                        typeOfIgm = igmSupplimentaryDetails.get(i).getTypeOfIgm();
                    }
                    blTypeBB = "HB";
                }
            } else {
                List<IgmDetails> igmDetails = igmDetailsRepo.findByImportRotationNoAndBlNo(rot, bl);
                for (int i = 0; i < igmDetails.size(); i++) {
                    typeOfIgm = igmDetails.get(i).getTypeOfIgm();
                }
                blTypeBB = "MB";
            }
            if(typeOfIgm != "")
            {
                if(typeOfIgm.equals("BB"))
                {
                    String submiteeOrgId = "";
                    List<IgmSupplimentaryDetail> igmSuppDtls = igmSuppDetailsRepo.findByImportRotationNoAndBlNo(rot,bl);
                    List<IgmDetails> igmDtls = igmDetailsRepo.findByImportRotationNoAndBlNo(rot,bl);

                    if(igmSuppDtls.size() == 0)
                    {
                        for (int i=0; i<igmDtls.size();i++){
                            submiteeOrgId = igmDtls.get(i).getSubmiteeOrgId();
                        }
                    }
                    else
                    {
                        for (int i=0; i<igmSuppDtls.size();i++){
                            submiteeOrgId = igmSuppDtls.get(i).getSubmiteeOrgId();
                        }
                    }



                    EdoApplication edoApplication = new EdoApplication();
                    edoApplication.setRotation(rot);
                    edoApplication.setBl(bl);
                    edoApplication.setBlType(blTypeBB);
                    edoApplication.setIgmType(typeOfIgm);
                    edoApplication.setShAgentOrgId(submiteeOrgId);
                    edoApplication.setEntryTime(java.time.LocalDateTime.now().minusHours(6));
                    edoApplication.setSubmittedBy(loginId);
                    edoApplication.setIpAddress(ipAddress);
                    edoApplication.setEdoAppliedBy(loginId);

                    edoAppRepo.save(edoApplication);
                    responseMessage = new ResponseMessage( "Application Successful");
                    return new ResponseEntity<>(responseMessage, HttpStatus.OK);
                }
                else
                {
                    System.out.println("........... GM ...............");
                    String blType = "";
                    List<IgmSupplimentaryDetail> igmData = igmSuppDetailsRepo.findByImportRotationNoAndBlNo(rot,bl);
                    if(igmData.size() > 0)
                    {
                        blType = "HB";

                        String supOrg = "";
                        String masterOrg = "";
                        String masterBl = "";
                        String contStatus = "";
                        Integer ffStat = 0;
                        Date ffClearanceTime = null;
                        Integer entryOrgId = 0;
                        String enteredBy = "";
                        Date entryTime = null;
                        String entryIpAddress = "";
                        String clearedBy = "";
                        Date clearanceTime = null;
                        String clearanceIp = "";
                        Integer clearedByOrgId = 0;
                        Integer clearanceSt = 0;
                        Date mblValidUptoDt = null;
                        Integer fclStatus = 0;

                        for (int i=0; i<igmData.size();i++){
                            supOrg = igmData.get(i).getSubmiteeOrgId();
                            masterOrg = igmData.get(i).getIgmDetails().getSubmiteeOrgId();
                        }

                        List<IgmSupDetailContainer> contDtls = igmSupDtlContRepo.findByIgmSupplimentaryDetailImportRotationNoAndIgmSupplimentaryDetailBlNo(rot,bl);
                        for (int i=0;i<contDtls.size();i++ )
                        {
                            contStatus = contDtls.get(i).getContStatus();
                            masterBl = contDtls.get(i).getIgmSupplimentaryDetail().getMasterBLNo();

                            if(contStatus.equals("FCL") || contStatus.equals("FCL/PART") || contStatus.equals("ETY"))
                            {
                                fclStatus = fclStatus+1;
                            }
                        }

                        if(fclStatus > 0)
                        {
                            contStatus = "FCL";
                        }
                        else
                        {
                            contStatus = contStatus;
                        }

                        Integer providedOrgTypeId = 0;
                        List<User> users = userRepo.findByLoginId(loginId);
                        for (int i=0; i<users.size();i++)
                        {
                            providedOrgTypeId = users.get(i).getOrgTypeId();
                        }

                        if(contStatus.equals("") || masterBl.equals("") ){
                            responseMessage = new ResponseMessage( "Sorry! Could not apply EDO");
                            return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
                        } else if(providedOrgTypeId != 2){
                            responseMessage = new ResponseMessage( "Sorry! Invalid C&F.");
                            return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
                        } else {
                            if(contStatus.equals("LCL"))
                            {
                                Integer mblClearanceStatus = clearedMblByMloRepo.countByMasterBl(masterBl);
                                if(mblClearanceStatus == 0)
                                {
                                    ffStat = 0;
                                    EdoApplication edoApplication = new EdoApplication();
                                    edoApplication.setRotation(rot);
                                    edoApplication.setBl(bl);
                                    edoApplication.setBlType(blType);
                                    edoApplication.setIgmType(typeOfIgm);
                                    edoApplication.setMlo(masterOrg);
                                    edoApplication.setFfOrgId(supOrg);
                                    edoApplication.setFfStat(ffStat);
                                    edoApplication.setFfClearanceTime(ffClearanceTime);
                                    edoApplication.setContStatus(contStatus);
                                    edoApplication.setMblOfHbl(masterBl);
                                    edoApplication.setEntryTime(java.time.LocalDateTime.now().minusHours(6));
                                    edoApplication.setSubmittedBy(loginId);
                                    edoApplication.setIpAddress(ipAddress);
                                    edoApplication.setEdoAppliedBy(loginId);
                                    edoAppRepo.save(edoApplication);
                                    responseMessage = new ResponseMessage( "Application Successful");
                                    return new ResponseEntity<>(responseMessage, HttpStatus.OK);
                                }
                                else
                                {
                                    List<ClearedMblByMlo> clearedData = clearedMblByMloRepo.findByMasterBl(masterBl);
                                    for (int i=0;i<clearedData.size();i++)
                                    {
                                        ffClearanceTime = clearedData.get(i).getEntryTime();
                                        clearanceSt = clearedData.get(i).getClearanceSt();
                                        entryOrgId = clearedData.get(i).getEntryOrgId();
                                        enteredBy = clearedData.get(i).getEnteredBy();
                                        entryTime = clearedData.get(i).getEntryTime();
                                        entryIpAddress = clearedData.get(i).getEntryIpAddress();
                                        clearedBy = clearedData.get(i).getClearedBy();
                                        clearanceTime = clearedData.get(i).getClearanceTime();
                                        clearanceIp = clearedData.get(i).getClearanceIp();
                                        clearedByOrgId = clearedData.get(i).getClearedByOrgId();
                                        mblValidUptoDt = clearedData.get(i).getValidUptoDt();
                                    }
                                    ffStat = 1;
                                    EdoApplication edoApplication = new EdoApplication();
                                    edoApplication.setRotation(rot);
                                    edoApplication.setBl(bl);
                                    edoApplication.setBlType(blType);
                                    edoApplication.setIgmType(typeOfIgm);
                                    edoApplication.setMlo(masterOrg);
                                    edoApplication.setFfOrgId(supOrg);
                                    edoApplication.setFfStat(ffStat);
                                    edoApplication.setFfClearanceTime(ffClearanceTime);
                                    edoApplication.setForwardedBy(clearedBy);
                                    edoApplication.setForwardedOrgId(clearedByOrgId);
                                    edoApplication.setContStatus(contStatus);
                                    edoApplication.setMblOfHbl(masterBl);
                                    edoApplication.setEntryTime(java.time.LocalDateTime.now().minusHours(6));
                                    edoApplication.setSubmittedBy(loginId);
                                    edoApplication.setIpAddress(ipAddress);
                                    edoApplication.setEdoAppliedBy(loginId);
                                    edoAppRepo.save(edoApplication);
                                    responseMessage = new ResponseMessage( "Application Successful");
                                    return new ResponseEntity<>(responseMessage, HttpStatus.OK);
                                }
                            }
                            else if(contStatus.equals("FCL") || contStatus.equals("FCL/PART") || contStatus.equals("ETY"))
                            {
                                Integer mblClearanceStatus = clearedMblByMloRepo.countByMasterBl(masterBl);
                                if(mblClearanceStatus == 0)
                                {
                                    ffStat = 0;
                                    EdoApplication edoApplication = new EdoApplication();
                                    edoApplication.setRotation(rot);
                                    edoApplication.setBl(bl);
                                    edoApplication.setBlType(blType);
                                    edoApplication.setIgmType(typeOfIgm);
                                    edoApplication.setMlo(masterOrg);
                                    edoApplication.setFfOrgId(supOrg);
                                    edoApplication.setFfStat(ffStat);
                                    edoApplication.setFfClearanceTime(ffClearanceTime);
                                    edoApplication.setContStatus(contStatus);
                                    edoApplication.setMblOfHbl(masterBl);
                                    edoApplication.setEntryTime(java.time.LocalDateTime.now().minusHours(6));
                                    edoApplication.setSubmittedBy(loginId);
                                    edoApplication.setIpAddress(ipAddress);
                                    edoApplication.setEdoAppliedBy(loginId);
                                    edoAppRepo.save(edoApplication);
                                    responseMessage = new ResponseMessage( "Application Successful");
                                    return new ResponseEntity<>(responseMessage, HttpStatus.OK);
                                }
                                else
                                {
                                    List<ClearedMblByMlo> clearedData = clearedMblByMloRepo.findByMasterBl(masterBl);
                                    for (int i=0;i<clearedData.size();i++)
                                    {
                                        ffClearanceTime = clearedData.get(i).getEntryTime();
                                        clearanceSt = clearedData.get(i).getClearanceSt();
                                        entryOrgId = clearedData.get(i).getEntryOrgId();
                                        enteredBy = clearedData.get(i).getEnteredBy();
                                        entryTime = clearedData.get(i).getEntryTime();
                                        entryIpAddress = clearedData.get(i).getEntryIpAddress();
                                        clearedBy = clearedData.get(i).getClearedBy();
                                        clearanceTime = clearedData.get(i).getClearanceTime();
                                        clearanceIp = clearedData.get(i).getClearanceIp();
                                        clearedByOrgId = clearedData.get(i).getClearedByOrgId();
                                        mblValidUptoDt = clearedData.get(i).getValidUptoDt();
                                    }
                                    ffStat = 1;
                                    EdoApplication edoApplication = new EdoApplication();
                                    edoApplication.setRotation(rot);
                                    edoApplication.setBl(bl);
                                    edoApplication.setBlType(blType);
                                    edoApplication.setIgmType(typeOfIgm);
                                    edoApplication.setMlo(masterOrg);
                                    edoApplication.setFfOrgId(supOrg);
                                    edoApplication.setFfStat(ffStat);
                                    edoApplication.setFfClearanceTime(ffClearanceTime);
                                    edoApplication.setForwardedBy(clearedBy);
                                    edoApplication.setForwardedOrgId(clearedByOrgId);
                                    edoApplication.setValidUptoDtByMlo(mblValidUptoDt);
                                    edoApplication.setContStatus(contStatus);
                                    edoApplication.setMblOfHbl(masterBl);
                                    edoApplication.setEntryTime(java.time.LocalDateTime.now().minusHours(6));
                                    edoApplication.setSubmittedBy(loginId);
                                    edoApplication.setIpAddress(ipAddress);
                                    edoApplication.setEdoAppliedBy(loginId);
                                    edoAppRepo.save(edoApplication);
                                    responseMessage = new ResponseMessage( "Application Successful");
                                    return new ResponseEntity<>(responseMessage, HttpStatus.OK);
                                }

                            }
                            else
                            {
                                responseMessage = new ResponseMessage( "Sorry! Could not apply EDO");
                                return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
                            }
                        }

                    }
                    else
                    {
                        blType = "MB";
                        List<IgmDetailContainer> contDtls = igmDtlContRepo.findByIgmDetailsImportRotationNoAndIgmDetailsBlNo(rot,bl);
                        String containerStatus = "";
                        Integer fclStatus = 0;
                        String masterOrg = "";

                        for(int i=0;i<contDtls.size();i++)
                        {
                            containerStatus = contDtls.get(i).getContStatus();

                            if(containerStatus.equals("FCL") || containerStatus.equals("FCL/PART") || containerStatus.equals("ETY"))
                            {
                                fclStatus = fclStatus+1;
                            }
                        }
                        if(fclStatus > 0)
                        {
                            containerStatus = "FCL";
                        }
                        else
                        {
                            containerStatus = containerStatus;
                        }

                        List<IgmDetails> igmDtls = igmDetailsRepo.findByImportRotationNoAndBlNo(rot,bl);
                        for (int i=0; i<igmDtls.size();i++){
                            masterOrg = igmDtls.get(i).getSubmiteeOrgId();
                        }

                        if(masterOrg.equals("")){
                            responseMessage = new ResponseMessage( "Sorry! Could not apply EDO");
                            return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
                        } else {
                            EdoApplication edoApplication = new EdoApplication();
                            edoApplication.setRotation(rot);
                            edoApplication.setBl(bl);
                            edoApplication.setBlType(blType);
                            edoApplication.setIgmType(typeOfIgm);
                            edoApplication.setMlo(masterOrg);
                            edoApplication.setContStatus(containerStatus);
                            edoApplication.setEntryTime(java.time.LocalDateTime.now().minusHours(6));
                            edoApplication.setSubmittedBy(loginId);
                            edoApplication.setIpAddress(ipAddress);
                            edoApplication.setEdoAppliedBy(loginId);
                            edoAppRepo.save(edoApplication);
                            responseMessage = new ResponseMessage( "Application Successful");
                            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
                        }
                    }
                }
            }
            else
            {
                responseMessage = new ResponseMessage( "Sorry! Could not apply EDO");
                return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
            }

            }
        else
        {
            responseMessage = new ResponseMessage( "Sorry ! Already applied for Rotation : " + rot + " and BL : " + bl);
            return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
        }
    }



    @Override
    public ResponseEntity<ResponseMessage> deleteEDO(Long edoId,String loginId,String ipAddress){

        EdoApplicationDeleteLog deleteLog = new EdoApplicationDeleteLog();
        deleteLog.setEdoId(edoId);
        deleteLog.setDeletedBy(loginId);
        deleteLog.setDeletedAt(java.time.LocalDateTime.now().plusHours(6));
        deleteLog.setDeletedByIp(ipAddress);

        dltLogRepository.save(deleteLog);
        edoAppRepo.deleteById(edoId);

        responseMessage = new ResponseMessage("Deleted Successfully.");
        return new ResponseEntity<>(responseMessage,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<ContainerList>> contListForHblValidityExtension(String impRot, String blNo){
        ArrayList<ContainerList> containerList = new ArrayList<ContainerList>();

        List<IgmSupDetailContainer> containers = igmSupDtlContRepo.findByIgmSupplimentaryDetailImportRotationNoAndIgmSupplimentaryDetailBlNo(impRot,blNo);
        for (int i=0;i<containers.size();i++)
        {
            ContainerList container = new ContainerList();

            container.setCId(containers.get(i).getId());
            container.setContNumber(containers.get(i).getContNumber());
            container.setContStatus(containers.get(i).getContStatus());
            container.setContLocationCode(containers.get(i).getContLocationCode());
            container.setContSealNumber(containers.get(i).getContSealNumber());
            container.setContSize(containers.get(i).getContSize());
            container.setContType(containers.get(i).getContType());
            container.setContHeight(containers.get(i).getContHeight());
            container.setContGrossWeight(containers.get(i).getContGrossWeight());
            container.setContWeight(containers.get(i).getContWeight());
            container.setContNumberPackaages(containers.get(i).getContNumberPackaages());

            containerList.add(container);
        }

        return new ResponseEntity<>(containerList,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseMessage> validityExtendHBL(List<Long> contId, Long uploadId, Long edoId, String validityDate, String loginId){
        try {

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            formatter.setTimeZone(TimeZone.getTimeZone("Bangladesh/Dhaka"));

            Date date = formatter.parse(validityDate);

            edoAppRepo.updateCnfValidityApprovalSt(edoId,date);

            validityDateRepo.deleteByEdoId(edoId);

            for (int i=0;i<contId.size();i++){
                EdoAppliedValidityDate appliedValidityDate = new EdoAppliedValidityDate();

                appliedValidityDate.setEdoId(edoId);
                appliedValidityDate.setShedMloDoInfoId(uploadId);
                appliedValidityDate.setContIgmId(contId.get(i));
                appliedValidityDate.setAppliedValidityDate(date);
                appliedValidityDate.setEnteredBy(loginId);
                appliedValidityDate.setEnteredAt(java.time.LocalDateTime.now().plusHours(6));

                validityDateRepo.save(appliedValidityDate);
            }



        } catch (ParseException p){
            responseMessage = new ResponseMessage(p.getMessage());
            return new ResponseEntity<>(responseMessage,HttpStatus.BAD_REQUEST);
        }


        responseMessage = new ResponseMessage("Applied Successfully.");
        return new ResponseEntity<>(responseMessage,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<ContainerList>> contListForMblValidityExtension(String impRot, String blNo){
        ArrayList<ContainerList> containerList = new ArrayList<ContainerList>();

        List<IgmDetailContainer> containers = igmDtlContRepo.findByIgmDetailsImportRotationNoAndIgmDetailsBlNo(impRot,blNo);
        for (int i=0;i<containers.size();i++)
        {
            ContainerList container = new ContainerList();

            container.setCId(containers.get(i).getId());
            container.setContNumber(containers.get(i).getContNumber());
            container.setContStatus(containers.get(i).getContStatus());
            container.setContLocationCode(containers.get(i).getContLocationCode());
            container.setContSealNumber(containers.get(i).getContSealNumber());
            container.setContSize(containers.get(i).getContSize());
            container.setContType(containers.get(i).getContType());
            container.setContHeight(containers.get(i).getContHeight());
            container.setContGrossWeight(containers.get(i).getContGrossWeight());
            container.setContWeight(containers.get(i).getContWeight());
            container.setContNumberPackaages(containers.get(i).getContNumberPackaages());

            containerList.add(container);
        }

        return new ResponseEntity<>(containerList,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseMessage> validityExtendMBL(List<Long> contId, Long uploadId, Long edoId, String validityDate, String loginId){
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            formatter.setTimeZone(TimeZone.getTimeZone("Bangladesh/Dhaka"));

            Date date = formatter.parse(validityDate);

            edoAppRepo.updateCnfValidityApprovalSt(edoId,date);

            validityDateRepo.deleteByEdoId(edoId);

            for (int i=0;i<contId.size();i++){
                EdoAppliedValidityDate appliedValidityDate = new EdoAppliedValidityDate();

                appliedValidityDate.setEdoId(edoId);
                appliedValidityDate.setShedMloDoInfoId(uploadId);
                appliedValidityDate.setContIgmId(contId.get(i));
                appliedValidityDate.setAppliedValidityDate(date);
                appliedValidityDate.setEnteredBy(loginId);
                appliedValidityDate.setEnteredAt(java.time.LocalDateTime.now().plusHours(6));

                validityDateRepo.save(appliedValidityDate);
            }
        } catch (ParseException p){
            responseMessage = new ResponseMessage(p.getMessage());
            return new ResponseEntity<>(responseMessage,HttpStatus.BAD_REQUEST);
        }

        responseMessage = new ResponseMessage("Applied Successfully.");
        return new ResponseEntity<>(responseMessage,HttpStatus.OK);
    }

    @Override
    public List<EdoIgmData> edoIgmData(String rot, String bl) {
        String cnfLoginId = "";
        rot = rot.replace("_","/");
        List<EdoIgmData>  edoApplicationOnblurLists=new ArrayList<>();

        EdoIgmData edoApplication=new EdoIgmData();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        System.out.println(formatter.format(date));
        System.out.println(java.time.LocalDateTime.now().minusHours(6));

        String type_of_igm = "";
        String blType_BB = "";
        Integer msgFlag = 1;

        String typeOfIgm = "";
        String blTypeBB = "";
        String shippingAgentName="";

        Integer cntResult = igmDetailsRepo.countByImportRotationNoAndBlNo(rot,bl);
        if(cntResult == 0){
            Integer cntSupResult = igmSuppDetailsRepo.countByImportRotationNoAndBlNo(rot,bl);

            if(cntSupResult==0){
                msgFlag=0;
            }
            else
            {
                List<IgmSupplimentaryDetail> igmSupplimentaryDetails = igmSuppDetailsRepo.findByImportRotationNoAndBlNo(rot,bl);
                for (int i=0; i<igmSupplimentaryDetails.size();i++){
                    typeOfIgm = igmSupplimentaryDetails.get(i).getTypeOfIgm();
                }
                blTypeBB = "HB";
            }

        }

        else
        {
            List<IgmDetails> igmDetails = igmDetailsRepo.findByImportRotationNoAndBlNo(rot,bl);
            for (int i=0; i<igmDetails.size();i++){

                typeOfIgm = igmDetails.get(i).getTypeOfIgm();
            }
            blTypeBB = "MB";
        }

        edoApplication.setBlType_BB(blTypeBB);
        if(typeOfIgm != "")
        {
            if(typeOfIgm.equals("BB")) {
                String submiteeOrgId = "";
                List<IgmSupplimentaryDetail> igmSuppDtls = igmSuppDetailsRepo.findByImportRotationNoAndBlNo(rot, bl);
                List<IgmDetails> igmDtls = igmDetailsRepo.findByImportRotationNoAndBlNo(rot, bl);

                if (igmSuppDtls.size() == 0) {
                    for (int i = 0; i < igmDtls.size(); i++) {
                        submiteeOrgId = igmDtls.get(i).getSubmiteeOrgId();
                    }
                } else {
                    for (int i = 0; i < igmSuppDtls.size(); i++) {
                        submiteeOrgId = igmSuppDtls.get(i).getSubmiteeOrgId();
                    }
                }

                if(!submiteeOrgId.equals("0"))
                {

                    OrganizationProfile saDtls = organizationProfileRepository.findById(Long.parseLong(submiteeOrgId)).get();
                    edoApplication.setShippingAgent(saDtls.getOrganizationName()+"-"+"("+saDtls.getAinNo()+")");

                }

            }
            else {
                System.out.println("........... GM ...............");
                String blType = "";
                List<IgmSupplimentaryDetail> rsltStr = igmSuppDetailsRepo.findByImportRotationNoAndBlNo(rot,bl);
                if(rsltStr.size() > 0) {
                    blType = "HB";

                    String supOrg= "";
                    String masterOrg= "" ;
                    String mloId = "";
                    String masterBl = "";
                    String contStatus = "";
                    String ffName="";
                    String mloName="";


                    for (int i = 0; i < rsltStr.size(); i++) {
                        supOrg = rsltStr.get(i).getSubmiteeOrgId();
                        System.out.println("supOrg:"+supOrg);
                        mloId = rsltStr.get(i).getSubmiteeId();

                        masterOrg = rsltStr.get(i).getIgmDetails().getSubmiteeOrgId();
                    }
                    if(!supOrg.equals("0"))
                    {
                        OrganizationProfile saDtls =  organizationProfileRepository.findById(Long.parseLong(supOrg)).get();
                        edoApplication.setFfName(saDtls.getOrganizationName()+"-"+"("+saDtls.getAinNo()+")");
                    }

                    if(!masterOrg.equals("0"))
                    {

                        OrganizationProfile saDtls =  organizationProfileRepository.findById(Long.parseLong(masterOrg)).get();

//                        for(int i = 0; i < saDtls.hashCode(); i++)
//                        {
//                            mloName = saDtls.getOrganizationName();
//                        }
//                        edoApplication.setMloName(saDtls.getOrganizationName()+"-"+(saDtls.getAinNo()));

                        edoApplication.setMloName(saDtls.getOrganizationName()+"-"+"("+saDtls.getAinNo()+")");
                    }

                    List<IgmSupDetailContainer> contDtls = igmSupDtlContRepo.findByIgmSupplimentaryDetailImportRotationNoAndIgmSupplimentaryDetailBlNo(rot, bl);
                    for (int i = 0; i < contDtls.size(); i++) {
                        contStatus = contDtls.get(i).getContStatus();
                        masterBl = contDtls.get(i).getIgmSupplimentaryDetail().getMasterBLNo();
                    }
                    edoApplication.setMasterBl(masterBl);
                    edoApplication.setBlType_BB(blType);

                    edoApplication.setContStatus(contStatus);
                    edoApplication.setMloId(mloId);

                }

                else{
                    List<IgmDetails> igmDetail=igmDetailsRepo.findByImportRotationNoAndBlNo(rot, bl);


                    String bl_type = "MB";
                    String master_org= "";
                    String MloName="";
                    for (int i = 0; i < igmDetail.size(); i++) {
                        master_org = igmDetail.get(i).getSubmiteeOrgId();
                    }

                    if(!master_org.equals("0"))
                    {
                        OrganizationProfile saDtls =organizationProfileRepository.findById(Long.parseLong(master_org)).get();

//                        for(int i = 0; i < saDtls.hashCode(); i++)
//                        {
//                            MloName = saDtls.getOrganizationName();
//                        }

                        edoApplication.setMloName(saDtls.getOrganizationName()+"-"+"("+saDtls.getAinNo()+")");
                    }
                    edoApplication.setBlType_BB(bl_type);
                }
            }
            edoApplication.setType_of_igm(typeOfIgm);
        }
        edoApplicationOnblurLists.add(edoApplication);

        return  edoApplicationOnblurLists;
    }

    @Override
    public ResponseEntity<EdoPdf> edoPdf(Long uploadId, String rotNo, String bl, String blType, String submittedBy, Integer organizationTypeId, String loginId){
        EdoPdf edoPdf = new EdoPdf();
        edoPdf.setUploadId(uploadId);
        edoPdf.setRotation(rotNo);
        edoPdf.setBl(bl);
        edoPdf.setBlType(blType);
        edoPdf.setSubmittedBy(submittedBy);

        if (organizationTypeId==5 || organizationTypeId==62 || organizationTypeId==80 ||
                organizationTypeId==28 || organizationTypeId==91 || organizationTypeId==84)
        {
            shedMloDoInfoRepo.updateCpaViewSt(loginId,uploadId);
        }

        String edoIgmType = edoAppRepo.getEdoIgmType(rotNo,bl);
        LocalDateTime edoAppliedTime = edoAppRepo.getEntryTime(rotNo,bl);
        Date edoForwardingTime = edoAppRepo.getFfClearanceTime(rotNo,bl);

        edoPdf.setIgmType(edoIgmType);
        edoPdf.setEdoAppliedTime(edoAppliedTime.minusHours(6));
        if(edoForwardingTime != null){
            edoPdf.setEdoForwardingTime(edoForwardingTime.toInstant().minusMillis(6).toString());
        }


        String resltBE = igmDetailsRepo.getBeNo(rotNo,bl);

        List<Long> contIdList = doUploadWiseContRepo.getContIdList(uploadId);
        ArrayList contList = new ArrayList();

        if(blType.equals("MB"))
        {
            for (int i=0; i<contIdList.size();i++)
            {
                ContainerList cont = new ContainerList();
                Long contId = contIdList.get(i);

                Date validity = doUploadWiseContRepo.getValidUptoDt(contId,uploadId);

                IgmDetailContainer igmDetailContainer = igmDtlContRepo.findById(contId).get();
                cont.setContNumber(igmDetailContainer.getContNumber());
                cont.setContSealNumber(igmDetailContainer.getContSealNumber());
                cont.setContSize(igmDetailContainer.getContSize());
                cont.setContType(igmDetailContainer.getContType());
                cont.setContHeight(igmDetailContainer.getContHeight());
                cont.setContStatus(igmDetailContainer.getContStatus());
                cont.setContLocationCode(igmDetailContainer.getContLocationCode());
                cont.setContNumberPackaages(igmDetailContainer.getContNumberPackaages());
                cont.setValidUptoDate(validity);
                contList.add(cont);
            }
            edoPdf.setContList(contList);
        }
        else if(blType.equals("HB"))
        {

            for (int i=0; i<contIdList.size();i++)
            {
                ContainerList cont = new ContainerList();
                Long contId = contIdList.get(i);

                Date validity = doUploadWiseContRepo.getValidUptoDt(contId,uploadId);

                IgmSupDetailContainer igmSupDetailContainer = igmSupDtlContRepo.findById(contId).get();
                cont.setContNumber(igmSupDetailContainer.getContNumber());
                cont.setContSealNumber(igmSupDetailContainer.getContSealNumber());
                cont.setContSize(igmSupDetailContainer.getContSize());
                cont.setContType(igmSupDetailContainer.getContType());
                cont.setContHeight(igmSupDetailContainer.getContHeight());
                cont.setContStatus(igmSupDetailContainer.getContStatus());
                cont.setContLocationCode(igmSupDetailContainer.getContLocationCode());
                cont.setContNumberPackaages(igmSupDetailContainer.getContNumberPackaages());

                cont.setValidUptoDate(validity);

                contList.add(cont);
            }

            edoPdf.setContList(contList);
        }

        Long dtlId = 0L;
        Long igmMastersId = 0L;
        String igmPackNumber = "";
        String packDescription = "";
        String packMarksNumber = "";
        Double weight = 0.0;
        String billOfEntryNo = "";
        String weightUnit = "";
        String consigneeName = "";
        String consigneeAddress = "";
        String descriptionOfGoods = "";
        String notifyName = "";
        String notifyAddress = "";
        String portOfOrigin = "";
        String exporterName = "";
        String exporterAddress = "";

        List<IgmDetails> igmDtls = igmDetailsRepo.findByImportRotationNoAndBlNo(rotNo,bl);
        List<IgmSupplimentaryDetail> igmSupDtls = igmSuppDetailsRepo.findByImportRotationNoAndBlNo(rotNo,bl);

        if(igmSupDtls.size() > 0)
        {
            for (int i=0;i<igmSupDtls.size();i++){
                dtlId = igmSupDtls.get(i).getIgmDetails().getId();
                igmPackNumber = igmSupDtls.get(i).getPackNumber();
                packDescription = igmSupDtls.get(i).getPackDescription();
                packMarksNumber = igmSupDtls.get(i).getPackMarksNumber();
                weight = igmSupDtls.get(i).getWeight();
                billOfEntryNo = igmSupDtls.get(i).getBillOfEntryNo();
                weightUnit = igmSupDtls.get(i).getWeightUnit();
                consigneeName = igmSupDtls.get(i).getConsigneeName();
                consigneeAddress = igmSupDtls.get(i).getConsigneeAddress();
                descriptionOfGoods = igmSupDtls.get(i).getDescriptionOfGoods();
                notifyName = igmSupDtls.get(i).getNotifyName();
                notifyAddress = igmSupDtls.get(i).getNotifyAddress();
                portOfOrigin = igmSupDtls.get(i).getPortOfOrigin();
            }
        }
        else
        {
            for (int i=0;i<igmDtls.size();i++){
                dtlId = igmDtls.get(i).getId();
                igmPackNumber = igmDtls.get(i).getPackNumber();
                packDescription = igmDtls.get(i).getPackDescription();
                packMarksNumber = igmDtls.get(i).getPackMarksNumber();
                weight = igmDtls.get(i).getWeight();
                billOfEntryNo = igmDtls.get(i).getBillOfEntryNo();
                weightUnit = igmDtls.get(i).getWeightUnit();
                consigneeName = igmDtls.get(i).getConsigneeName();
                consigneeAddress = igmDtls.get(i).getConsigneeAddress();
                descriptionOfGoods = igmDtls.get(i).getDescriptionOfGoods();
                notifyName = igmDtls.get(i).getNotifyName();
                notifyAddress = igmDtls.get(i).getNotifyAddress();
                portOfOrigin = igmDtls.get(i).getPortOfOrigin();
            }
        }

        edoPdf.setDtlId(dtlId);
        edoPdf.setExporterName(exporterName);
        edoPdf.setExporterAddress(exporterAddress);
        edoPdf.setIgmPackNumber(igmPackNumber);
        edoPdf.setPackDescription(packDescription);
        edoPdf.setPackMarksNumber(packMarksNumber);
        edoPdf.setWeight(weight);
        edoPdf.setBillOfEntryNo(billOfEntryNo);
        edoPdf.setWeightUnit(weightUnit);
        edoPdf.setConsigneeName(consigneeName);
        edoPdf.setConsigneeAddress(consigneeAddress);
        edoPdf.setDescriptionOfGoods(descriptionOfGoods);
        edoPdf.setNotifyName(notifyName);
        edoPdf.setNotifyAddress(notifyAddress);
        edoPdf.setPortOfOrigin(portOfOrigin);

        Long masterId = 0L;
        String vesselName = "";
        String voyNo = "";
        String netTonnage = "";
        String nameOfMaster = "";
        String portShipID = "";
        String portOfShipment = "";
        String portOfDestination = "";
        String customApproved = "";
        Date fileClearanceDate = null;
        String submiteeOrgType = "";
        String sOrgLicenseNumber = "";
        Date submissionDate = null;
        String flag = "";
        String imo = "";
        Long submiteeOrgId = 0L;

        IgmDetails igmDetails = igmDetailsRepo.findById(dtlId).get();
        edoPdf.setExporterName(igmDetails.getExporterName());
        edoPdf.setExporterAddress(igmDetails.getExporterAddress());
        igmMastersId = igmDetails.getIgmId();
        String igmDtlBlNo = igmDetails.getBlNo();

        Optional<IgmMasters> igmMasters = igmMastersRepo.findById(igmMastersId);
        if(igmMasters.isPresent()){
            IgmMasters master = igmMasters.get();
            masterId = master.getId();
            vesselName = master.getVesselName();
            voyNo = master.getVoyNo();
            netTonnage = master.getNetTonnage();
            nameOfMaster = master.getNameOfMaster();
            portShipID = master.getPortShipID();
            portOfShipment = master.getPortOfShipment();
            portOfDestination = master.getPortOfDestination();
            customApproved = master.getCustomApproved();
            fileClearanceDate = master.getFileClearanceDate();
            submiteeOrgType = master.getSubmiteeOrgType();
            sOrgLicenseNumber = master.getSOrgLicenseNumber();
            submissionDate = master.getSubmissionDate();
            flag = master.getFlag();
            imo = master.getImo();
            submiteeOrgId = master.getSubmiteeOrgId();
        }

        edoPdf.setVesselName(vesselName);
        edoPdf.setVoyNo(voyNo);
        edoPdf.setNetTonnage(netTonnage);
        edoPdf.setNameOfMaster(nameOfMaster);
        edoPdf.setPortShipID(portShipID);
        edoPdf.setPortOfShipment(portOfShipment);
        edoPdf.setPortOfDestination(portOfDestination);
        edoPdf.setCustomApproved(customApproved);
        edoPdf.setFileClearanceDate(fileClearanceDate);
        edoPdf.setSubmiteeOrgType(submiteeOrgType);
        edoPdf.setSOrgLicenseNumber(sOrgLicenseNumber);
        edoPdf.setSubmissionDate(submissionDate);
        edoPdf.setFlag(flag);
        edoPdf.setImo(imo);

        String organizationName = orgProfileRepo.getOrgNameById(submiteeOrgId);
        edoPdf.setOrganizationName(organizationName);

        Date etaDate = vslBerthDtlRepo.getEtaDateByIgmId(igmMastersId);
        edoPdf.setEtaDate(etaDate);

        String reg_no = "";
        String dec_code = "";

//        Long sadId = sadItemRepo.getSadIdBySumDeclare(igmDtlBlNo);
//        if(sadId != null){
//            SadInfo sadInfo = sadInfoRepo.findById(sadId).get();
//            edoPdf.setReg_no(sadInfo.getRegNo());
//            edoPdf.setDec_code(sadInfo.getDecCode());
//        }

        Date edoUploadingTime = null;
        String measurement = "";
        Date validUptoDt = null;
        String beNo = "";
        Date billOfEntryDt = null;
        String officeCode = "";
        String edoMlo = "";
        int edoSl = 0;
        String edoSerial = "";
        int edoYear = 0;
        String remarks = "";
        String lineNo = "";
        String receiptNo = "";
        Date receiptDate = null;
        String rNo = "";
        Date rNoDate = null;
        String cnfLicNo = "";
        String edoNumber = "";

        Optional<ShedMloDoInfo> shedMloDoInfo = shedMloDoInfoRepo.findById(uploadId);
        if(shedMloDoInfo.isPresent()){
            ShedMloDoInfo doInfo = shedMloDoInfo.get();
            edoUploadingTime = doInfo.getUploadTime();
            measurement = doInfo.getMeasurement();
            validUptoDt = doInfo.getValidUpDt();
            beNo = doInfo.getBeNo();
            billOfEntryDt = doInfo.getBeDate();
            officeCode = doInfo.getOfficeCode();
            edoMlo = doInfo.getEdoMlo();
            edoSl = doInfo.getEdoSl();
            edoSerial = String.format("%06d", edoSl);
            edoYear = doInfo.getEdoYear();
            remarks = doInfo.getRemarks();
            lineNo = doInfo.getLineNo();
            receiptNo = doInfo.getReceiptNo();
            receiptDate = doInfo.getReceiptDate();
            rNo = doInfo.getRNo();
            rNoDate = doInfo.getRNoDate();
            cnfLicNo = doInfo.getCnfLicNo();
        }

        edoPdf.setUploadTime(edoUploadingTime);
        edoPdf.setMeasurement(measurement);
        edoPdf.setValidUptoDt(validUptoDt);
        edoPdf.setBeNo(beNo);
        edoPdf.setBillOfEntryDt(billOfEntryDt);
        edoPdf.setOfficeCode(officeCode);
        edoPdf.setEdoMlo(edoMlo);
        edoPdf.setEdoSl(edoSerial);
        edoPdf.setEdoYear(edoYear);
        edoPdf.setRemarks(remarks);
        edoPdf.setLineNo(lineNo);
        edoPdf.setReceiptNo(receiptNo);
        edoPdf.setReceiptDate(receiptDate);
        edoPdf.setRNo(rNo);
        edoPdf.setRNoDate(rNoDate);
        edoPdf.setCnfLicNo(cnfLicNo);

        Date comparingDate = new Date(2021-12-02);

        if(edoUploadingTime.before(comparingDate)){
            edoNumber = uploadId.toString();
        } else {
            edoNumber = edoMlo + edoSerial + edoYear;
        }

        edoPdf.setEdoNumber(edoNumber);

        String mloCode = "";
        for (int i=0;i<igmDtls.size();i++) {
            mloCode = igmDtls.get(i).getMlocode();
        }

        edoPdf.setMloCode(mloCode);

        String cnfName = "";
        Long orgId = 0L;

        List<User> userList = userRepo.findByLoginId(submittedBy);
        for(int i=0;i<userList.size();i++){
            cnfName = userList.get(i).getUName();
            orgId = userList.get(i).getOrgId();
        }

        String cnfLicenseNo = orgProfileRepo.getLicNoById(orgId);

        edoPdf.setCnfName(cnfName);
        edoPdf.setCnfLicenseNo(cnfLicenseNo);

        String uploaderOrgId = "";
        if(blType.equals("HB") && edoIgmType.equals("GM")){
            uploaderOrgId = edoAppRepo.getFfOrgid(rotNo,bl);
        } else if(blType.equals("MB") && edoIgmType.equals("GM")){
            uploaderOrgId = edoAppRepo.getMloOrgid(rotNo,bl);
        } else if(edoIgmType.equals("BB")){
            uploaderOrgId = edoAppRepo.getShippingAgentOrgid(rotNo,bl);
        }

        Long uploader = Long.parseLong(uploaderOrgId);

        Long organizationId = 0L;
        String logo = "";
        String orgName = "";
        String address1 = "";
        String address2 = "";
        String licenseNo = "";
        String ainNoNew = "";
        String cellNo1 = "";
        String telephoneNoLand = "";
        String orgTypeid = "";

        Optional<OrganizationProfile> orgProfileInfo = orgProfileRepo.findById(uploader);
        if(orgProfileInfo.isPresent()) {
            OrganizationProfile orgProfile = orgProfileInfo.get();
            organizationId = orgProfile.getId();
            logo = orgProfile.getLogo();
            orgName = orgProfile.getOrganizationName();
            address1 = orgProfile.getAddress1();
            address2 = orgProfile.getAddress2();
            licenseNo = orgProfile.getLicenseNo();
            ainNoNew = orgProfile.getAinNoNew();
            cellNo1 = orgProfile.getCellNo1();
            telephoneNoLand = orgProfile.getTelephoneNoLand();
            orgTypeid = orgProfile.getOrgTypeid();
        }

        edoPdf.setOrganizationId(organizationId);
        edoPdf.setOrgName(orgName);
        edoPdf.setAddress1(address1);
        edoPdf.setAddress2(address2);
        edoPdf.setLicenseNo(licenseNo);
        edoPdf.setAinNoNew(ainNoNew);
        edoPdf.setCellNo1(cellNo1);
        edoPdf.setTelephoneNoLand(telephoneNoLand);
        edoPdf.setOrgTypeid(orgTypeid);

        String mloLogo = orgLogoRepo.getLogoPathByOrgIdAndMloCode(organizationId,mloCode);

        if(blType.equals("MB") && edoIgmType.equals("GM")){
            logo = mloLogo;
        } else {
            logo = logo;
        }

        edoPdf.setLogo(logo);

        return new ResponseEntity<EdoPdf>(edoPdf, HttpStatus.OK);
    }

    @Override
    public List<EdoListDTO> edoList(Long orgId, String loginId, int cpaSearch, String flag, String searchBy, String searchInput, Date searchedBeDt) {
        cpaSearch = cpaSearch;
        flag = flag;
        searchBy=searchBy;
        searchInput=searchInput.trim();
        searchedBeDt=searchedBeDt;


        List<EdoListDTO> edoListDTOS = new ArrayList<>();
        List<User> users = userRepo.findByLoginId(loginId);

        List<Long> submiteeOrgIdList = new ArrayList<>();

        for (User user : users) {
            submiteeOrgIdList.add(user.getOrgId());
        }
        String submiteeOrgId="";
        if (!submiteeOrgIdList.isEmpty()) {
            submiteeOrgId = submiteeOrgIdList.get(0).toString();
        }

        String orgTypeId="";

        List<EdoApplication> allApplications = null;
        OrganizationProfile organizationProfile = organizationProfileRepository.findById(orgId).get();
        if (organizationProfile != null) {
            orgTypeId = organizationProfile.getOrgTypeid();
        }

        System.out.println("org type id : "+orgTypeId);

        if (orgTypeId.equals("73")) //freight forwarder association
        {
            allApplications = edoAppRepo.findTop50ByBlTypeAndIgmTypeAndFfStatOrderByIdDesc("HB", "GM", 1);
        }
        else if (orgTypeId.equals("57") || orgTypeId.equals("93") || orgTypeId.equals("10")) //shipping_agent
        {
            allApplications = edoAppRepo.findTop50ByIgmTypeAndShAgentOrgIdOrderByIdDesc("BB", submiteeOrgId);
        }
        else if (orgTypeId.equals("4")) //freight forwarder
        {
            allApplications = edoAppRepo.findTop50ByIgmTypeAndFfOrgIdAndDoUploadStAndVldtyApprByMloStOrderByIdDesc("GM",submiteeOrgId,1,0);
        }
        else if (orgTypeId.equals("1")) //MLO
        {
            allApplications = edoAppRepo.findTop100ByIgmTypeAndMloAndDoUploadStAndCnfVldtyApprStOrderByIdDesc("GM",submiteeOrgId,1,0);
        }
        else if (orgTypeId.equals("91"))  //BB CPA
        {
            allApplications = edoAppRepo.findTop100ByIgmTypeOrderByIdDesc("BB");
        }
        else if (orgTypeId.equals("5") || orgTypeId.equals("62") || orgTypeId.equals("80") || orgTypeId.equals("28") || orgTypeId.equals("84")) //CPA or ONESTOP
        {

            if (cpaSearch == 0) {
                allApplications = edoAppRepo.findByBlTypeAndIgmTypeAndFfAssocStOrDoUploadSt("HB","GM",1,1);
            }
            else {
                if (searchBy.equals("be_no")) {
                    ShedMloDoInfo  shedMloDoInfo = shedMloDoInfoRepo.findFirstByBeNoOrderByIdDesc(searchInput);
                    String impRot = "";
                    String blNo = "";

                    if (shedMloDoInfo != null) {
                        impRot = shedMloDoInfo.getImpRot();
                        blNo = shedMloDoInfo.getBlNo();
                    }

                    allApplications = new ArrayList<>();
                    List<EdoApplication> applicationList = edoAppRepo.findByRotationAndBl(impRot, blNo);
                    for (EdoApplication application : applicationList) {
                        if ("HB".equals(application.getBlType()) && "GM".equals(application.getIgmType())) {
                            if (application.getFfAssocSt() == 1) {
                                allApplications.add(application);
                            }
                        } else {
                            if (application.getDoUploadSt() == 1) {
                                allApplications.add(application);
                            }
                        }
                    }


                }
                else if (searchBy.equals("exit_no")){

                }
                else {

                }
            }


        }


        else  //cnf=2
        {
            if (flag.equals("search")){


                if (searchBy.equals("bl")) {
                    allApplications = edoAppRepo.findTop50ByBlAndSubmittedByOrderByIdDesc(searchInput,loginId);
                }
                else if (searchBy.equals("rotation")) {
                    allApplications = edoAppRepo.findTop50ByRotationAndSubmittedByOrderByIdDesc(searchInput,loginId);
                }
                else if (searchBy.equals("be_no")) {
                    ShedMloDoInfo shedMloDoInfo = null;
                    if (searchedBeDt == null) {
                        shedMloDoInfo = shedMloDoInfoRepo.findFirstByBeNoOrderByIdDesc(searchInput);
                    }
                    else {
                        try {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
                            String dateString = dateFormat.format(searchedBeDt);
                            dateFormat.setTimeZone(TimeZone.getTimeZone("Bangladesh/Dhaka"));
                            Date date = dateFormat.parse(dateString);
                            shedMloDoInfo = shedMloDoInfoRepo.findFirstByBeNoAndBeDateOrderByIdDesc(searchInput,date);

                        } catch (java.text.ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    String impRot = "";
                    String blNo = "";

                    if (shedMloDoInfo != null) {
                        impRot = shedMloDoInfo.getImpRot();
                        blNo = shedMloDoInfo.getBlNo();
                    }

                    //allApplications =  edoAppRepo.findApplicationsByConditions(impRot,blNo);

                    allApplications = new ArrayList<>();
                    List<EdoApplication> applicationList = edoAppRepo.findByRotationAndBl(impRot, blNo);
                    for (EdoApplication application : applicationList) {
                        if ("HB".equals(application.getBlType()) && "GM".equals(application.getIgmType())) {
                            if (application.getFfAssocSt() == 1) {
                                allApplications.add(application);
                            }
                        }

                        else {
                            if (application.getDoUploadSt() == 1) {
                                allApplications.add(application);
                            }
                        }
                    }



                }
            }
            else{
                allApplications = edoAppRepo.findTop50BySubmittedByOrderByIdDesc(loginId);
            }


        }




        //start edoapplication result
        for (EdoApplication application : allApplications) {

            EdoListDTO edoListDTO = new EdoListDTO();

            String exitNo="";
            String impRot=application.getRotation();
            String bl =application.getBl();
            Date ffClearanceTime = application.getFfClearanceTime();
            int edoId = Integer.parseInt(String.valueOf(application.getId()));
            String contStatus = application.getContStatus();
            String blType = application.getBlType();
            //System.out.println("impRot "+impRot);

            //System.out.println("bl "+bl);
            List<SadInfo> resultExitNo;
            List<SadItem> sadItems;


            String tempImpRot = impRot.replaceAll("/", "");
            //System.out.println("manIfNum "+tempImpRot);


            sadItems  =   sadItemRepo.findBySumDeclare(bl);
            for (SadItem sadItem : sadItems) {
                String manifNum = sadItem.getSadInfo().getManifNum().replaceAll("\\s", "");
                if (manifNum.contains(tempImpRot)) {
                    exitNo = sadItem.getSadInfo().getPlaceDec();
                }
            }


            // check for Bill of Entry - start

            String[] arr = impRot.split("/");

            String manIfNum2 = "";

            if (arr.length > 1) {
                String arr1 = arr[1];
                int arr1Length = arr1.length();

                if (arr1Length == 1) {
                    manIfNum2 = arr[0] + "000" + arr1;
                } else if (arr1Length == 2) {
                    manIfNum2 = arr[0] + "00" + arr1;
                } else if (arr1Length == 3) {
                    manIfNum2 = arr[0] + "0" + arr1;
                } else if (arr1Length == 4) {
                    manIfNum2 = arr[0] + arr1;
                }
            }
            String manIfNum = impRot.replace("/", "");

            if (exitNo=="")
            {
                resultExitNo =   sadInfoRepo.findBySadItemsSumDeclare(bl);

                for (SadInfo sadInfo : resultExitNo) {
                    String getManifNum = sadInfo.getManifNum().replaceAll("\\s", "");
                    if (getManifNum.contains(manIfNum) || getManifNum.contains(manIfNum2)) {
                        exitNo = sadInfo.getPlaceDec();
                        //System.out.println("placeDec : "+exitNo);
                    }
                }
            }

            long chkBE=0;
            List<SadItem> sqlChkBE =   sadItemRepo.findBySumDeclare(bl);

            for (SadItem sadItem : sqlChkBE) {
                String getManifNum = sadItem.getSadInfo().getManifNum().replaceAll("\\s", "");
                if (getManifNum.contains(manIfNum) || getManifNum.contains(manIfNum2)) {
                    chkBE++;
                }
            }

            //System.out.println("chkBE : "+ chkBE);


            if(chkBE==0)
            {
                //System.out.println("edoId : "+ edoId);
                List<ShedMloDoInfo>  BeNoDt = shedMloDoInfoRepo.findByEdoId(edoId);

                for (ShedMloDoInfo shedMloDoInfo : BeNoDt) {
                    String beNoChk = shedMloDoInfo.getBeNo();
                    Date date = shedMloDoInfo.getBeDate();

                    //System.out.println("Be Date : "+date);

                    try {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
                        String dateString = dateFormat.format(date);
                        dateFormat.setTimeZone(TimeZone.getTimeZone("Bangladesh/Dhaka"));
                        Date beDtChk = dateFormat.parse(dateString);

                        //System.out.println("beDtChk  : "+beDtChk); // This date will have 00:00:00 time

                        chkBE = sadInfoRepo.countByRegNoAndRegDate(beNoChk,beDtChk);
                        //System.out.println(" chkBE : "+chkBE);

                    } catch (java.text.ParseException e) {
                        e.printStackTrace();
                    }


                }

            }
            //System.out.println("blType : "+blType);
            //System.out.println("contStatus : "+contStatus);

            // check for Bill of Entry - end


            if (contStatus == null)
            {
                //System.out.println("blType : "+blType);
                if (blType == "HB") {
                    List<IgmDetailContainer> igmDetailContainers =  igmDtlContRepo.findByIgmDetailsImportRotationNoAndIgmDetailsBlNo(impRot,bl);
                    for (IgmDetailContainer igmDetailContainer : igmDetailContainers) {
                        contStatus = igmDetailContainer.getContStatus();
                        //System.out.println("contStatus : "+contStatus);
                    }
                }

                else if(blType=="MB")
                {
                    List<IgmSupDetailContainer> igmSupDetailContainers =  igmSupDtlContRepo.findByIgmSupplimentaryDetailImportRotationNoAndIgmSupplimentaryDetailBlNo(impRot,bl);
                    for (IgmSupDetailContainer igmSupDetailContainer  : igmSupDetailContainers) {
                        contStatus = igmSupDetailContainer.getContStatus();
                        //System.out.println("contStatus : "+contStatus);
                    }
                }
                else {
                    contStatus = contStatus;
                }
            }


            String placeOfUnloading = "";

            Integer numberOfRows  = igmSuppDetailsRepo.countByBlNo(bl);

            //System.out.println("numberOfRows : "+numberOfRows);

            if(numberOfRows>0)
            {
                List<IgmSupplimentaryDetail> igmSupplimentaryDetails = igmSuppDetailsRepo.findByBlNo(bl);

                for (IgmSupplimentaryDetail igmSupplimentaryDetail : igmSupplimentaryDetails) {
                    placeOfUnloading =igmSupplimentaryDetail.getIgmDetails().getPlaceOfUnloading();
                    //System.out.println("contStatus : "+placeOfUnloading);
                }

            }
            else{
                List<IgmDetails> igmDetailsList = igmDetailsRepo.findByBlNo(bl);
                for (IgmDetails igmDetails : igmDetailsList) {
                    placeOfUnloading =igmDetails.getPlaceOfUnloading();
                    //System.out.println("contStatus : "+placeOfUnloading);
                }
            }

            //System.out.println("place of unloading : "+placeOfUnloading);

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date specificDate = null;
            try {
                specificDate = format.parse("0000-00-00 00:00:00");
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (ffClearanceTime != null && ffClearanceTime.equals(specificDate)) {
                ffClearanceTime = null;
            }

            //System.out.println(" ffClearanceTime : "+ffClearanceTime);

            ShedMloDoInfo shedMloDoInfo = shedMloDoInfoRepo.findFirstByImpRotAndBlNoOrderByIdDesc(impRot,bl);


            Long uploadId=null;
            Date uploadedAt=null;
            Date validityDt=null;
            int checkSt=0;
            Date approveAt=null;
            String beNo=null;
            Date beDate=null;
            String edoMlo=null;
            int edoSl=0;
            String formattedEdoSl=null;
            int edoYear=0;
            String edoNumber="";


            if (shedMloDoInfo!=null){
                uploadId = shedMloDoInfo.getId();
                uploadedAt = shedMloDoInfo.getUploadTime();
                validityDt = shedMloDoInfo.getValidUpDt();
                checkSt = shedMloDoInfo.getCheckSt();
                approveAt = shedMloDoInfo.getCpaCheckTime();
                beNo = shedMloDoInfo.getBeNo();
                beDate = shedMloDoInfo.getBeDate();
                edoMlo = shedMloDoInfo.getEdoMlo();
                edoSl = shedMloDoInfo.getEdoSl();
                formattedEdoSl = String.format("%06d", edoSl);
                edoYear = shedMloDoInfo.getEdoYear();

                //System.out.println("edo sl : "+formattedEdoSl);


                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String edoUploadedAt = dateFormat.format(uploadedAt);
                    Date comparisonDate = dateFormat.parse("2021-12-02");



                    if (comparisonDate.after(uploadedAt)) {
                        edoNumber = uploadId.toString();
                    } else {
                        edoNumber = edoMlo + formattedEdoSl + edoYear;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            // System.out.println("Edo Number : "+edoNumber);



            String applicantName = "";
            String applicantLic = "";
            String applicantAIN = "";
            String mloName = "";
            String mloAIN = "";
            String ffName = "";
            String ffAIN = "";
            String shippingAgentName = "";
            String shippingAgentAIN = "";
            //System.out.println("application.getFfOrgId() "+application.getFfOrgId());
            if (!orgTypeId.equals("2")){
                OrganizationProfile organization = organizationProfileRepository.findById(orgId).get();
                applicantName = organization.getOrganizationName();
                applicantLic = organization.getLicenseNo();
                String ain = (organization.getAinNo() != null) ? organization.getAinNo() : organization.getAinNoNew();
                applicantAIN = " (" + ain + ")";
            }

            if (orgTypeId.equals("1") || orgTypeId.equals("4") || orgTypeId.equals("2") || orgTypeId.equals("5")|| orgTypeId.equals("73") || orgTypeId.equals("62") || orgTypeId.equals("80")){
                if ( application.getMlo()!=null){
                    OrganizationProfile organization = organizationProfileRepository.findById(Long.parseLong(application.getMlo())).get();
                    mloName = organization.getOrganizationName();
                    String ain = (organization.getAinNo() != null) ? organization.getAinNo() : organization.getAinNoNew();
                    mloAIN = " (" + ain + ")";
                }

                if ( application.getFfOrgId()!=null){
                    //System.out.println("application.getFfOrgId()!=null "+application.getFfOrgId());
                    OrganizationProfile organization = organizationProfileRepository.findById(Long.parseLong(application.getFfOrgId())).get();
                    ffName = organization.getOrganizationName();
                    // System.out.println("ffName : "+ffName);
                    String ain = (organization.getAinNo() != null) ? organization.getAinNo() : organization.getAinNoNew();
                    ffAIN = " (" + ain + ")";
                }

            }

            if (orgTypeId.equals("57") || orgTypeId.equals("93") || orgTypeId.equals("10") || orgTypeId.equals("2") || orgTypeId.equals("5") || orgTypeId.equals("62") || orgTypeId.equals("80") || orgTypeId.equals("28") || orgTypeId.equals("91") || orgTypeId.equals("84"))
            {
                if (application.getShAgentOrgId()!=null){
                    OrganizationProfile organization = organizationProfileRepository.findById(Long.parseLong(application.getShAgentOrgId())).get();
                    shippingAgentName = organization.getOrganizationName();
                    String ain = (organization.getAinNo() != null) ? organization.getAinNo() : organization.getAinNoNew();
                    shippingAgentAIN = " (" + ain + ")";
                }
            }





            edoListDTO.setEdoApplication(application);
            edoListDTO.setExitNo(exitNo);
            edoListDTO.setUploadId(uploadId);
            edoListDTO.setUploadedAt(uploadedAt);
            edoListDTO.setValidityDt(validityDt);
            edoListDTO.setCheckSt(checkSt);
            edoListDTO.setApproveAt(approveAt);
            edoListDTO.setBeNo(beNo);
            edoListDTO.setBeDate(beDate);
            edoListDTO.setEdoMlo(edoMlo);
            edoListDTO.setEdoSl(formattedEdoSl);
            edoListDTO.setEdoYear(edoYear);
            edoListDTO.setEdoNumber(edoNumber);
            edoListDTO.setApplicantName(applicantName);
            edoListDTO.setApplicantLic(applicantLic);
            edoListDTO.setApplicantAIN(applicantAIN);
            edoListDTO.setMloName(mloName);
            edoListDTO.setMloAIN(mloAIN);
            edoListDTO.setFfName(ffName);
            edoListDTO.setFfAIN(ffAIN);
            edoListDTO.setShippingAgentName(shippingAgentName);
            edoListDTO.setShippingAgentAIN(shippingAgentAIN);
            edoListDTOS.add(edoListDTO);

        }
        //end of edoApllication loop

        return edoListDTOS;

    }


    @Override
    public ResponseEntity<ResponseMessage> rejectEdoApplication(String orgId, String loginId, String rejectionRemarks, Long eId, String type) {

        try {
            // Update the rejection status using the repository method

            if (type.equals("reject")) {
                edoAppRepo.updateRejectionStByEid(eId, rejectionRemarks, orgId, loginId);
                return ResponseEntity.ok().body(new ResponseMessage("Rejected Successfully."));
            } else {
                edoAppRepo.updateWithdrawStByEid(eId, rejectionRemarks, orgId, loginId);
                return ResponseEntity.ok().body(new ResponseMessage("Withdraw Rejection Successfully."));
            }
        } catch (NumberFormatException ex) {
            System.out.println("Invalid eId parameter.");
            // Handle the case where eId is not a valid Long value
            return ResponseEntity.badRequest().body(new ResponseMessage("Invalid eId parameter."));
        } catch (Exception ex) {
            System.out.println("Failed to update.");
            // Handle any other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage("Failed to update."));
        }
    }



    @Override
    public ResponseEntity<ShedDEOInfoData> shedDeOInfoData(String rotNo, String blNo, Long edoId, String typeOfBl, String igmType, String sumittedBy, Long uploadId, String flag) {

        System.out.println("rotNo : " + rotNo);
        System.out.println("blNo : " + blNo);
        System.out.println("edoId : " + edoId);
        System.out.println("typeOfBl : " + typeOfBl);
        System.out.println("igmType : " + igmType);
        System.out.println("sumittedBy : " + sumittedBy);
        System.out.println("uploadId : " + uploadId);
        System.out.println("flag : " + flag);


        ShedDEOInfoData shedDEOInfoData = new ShedDEOInfoData();


        if (flag.equals("edit") || flag.equals("validity_extend")) {
            ShedMloDoInfo shedMloDoInfo = shedMloDoInfoRepo.findById(uploadId).get();
            shedDEOInfoData.setMeasurementVal(shedMloDoInfo.getMeasurement());
            shedDEOInfoData.setValidUptodtVal(shedMloDoInfo.getValidUpDt());
            shedDEOInfoData.setBillOfEntryNoVal(shedMloDoInfo.getBeNo());
            shedDEOInfoData.setBeDtVal(shedMloDoInfo.getBeDate());
            shedDEOInfoData.setOfficeCodeVal(shedMloDoInfo.getOfficeCode());
            shedDEOInfoData.setLineNo(shedMloDoInfo.getLineNo());
            shedDEOInfoData.setReceiptNo(shedMloDoInfo.getReceiptNo());
            shedDEOInfoData.setReceiptDate(shedMloDoInfo.getReceiptDate());
            shedDEOInfoData.setRemarks(shedMloDoInfo.getRemarks());
            shedDEOInfoData.setCnfLicNo(shedMloDoInfo.getCnfLicNo());
            shedDEOInfoData.setRNo(shedMloDoInfo.getRNo());
            shedDEOInfoData.setRNoDate(shedMloDoInfo.getRNoDate());
        }

        EdoApplication edoApplication = edoAppRepo.findById(edoId).get();
        shedDEOInfoData.setEdoApplication(edoApplication);

        System.out.println("edoApplication : " + edoApplication);

        List<Object[]> orgInfo = null;

        if (typeOfBl.equals("HB") && igmType.equals("GM")) {
            orgInfo = edoAppRepo.findOrganizationDetailsByFfOrgId(edoId);
        } else if (typeOfBl.equals("MB") && igmType.equals("GM")) {
            orgInfo = edoAppRepo.findOrganizationDetailsByMlo(edoId);
        } else if (igmType.equals("BB")) {
            orgInfo = edoAppRepo.findOrganizationDetailsByShAgentOrgId(edoId);
        }
        shedDEOInfoData.setLogoPic(String.valueOf(orgInfo.get(0)[6]));


        List<IgmDetails> igmDetailsList;

        if (blNo.equals("all")) {
            igmDetailsList = igmDetailsRepo.findByImportRotationNo(rotNo);
        } else {
            igmDetailsList = igmDetailsRepo.findByImportRotationNoAndBlNo(rotNo, blNo);
        }

        System.out.println("igmDetailsList : " + igmDetailsList);

        String resultBE = "";

        if (igmDetailsList.isEmpty()) {
            List<IgmSupplimentaryDetail> igmSupplimentaryDetailList = igmSuppDetailsRepo.findByImportRotationNoAndBlNo(rotNo, blNo);
            if (!igmSupplimentaryDetailList.isEmpty()) {
                resultBE = igmSupplimentaryDetailList.get(0).getBillOfEntryNo();
            }
        } else {
            resultBE = igmDetailsList.get(0).getBillOfEntryNo();
        }

        System.out.println("resultBE : " + resultBE);

        List<ContainerList> containerLists = null;



//        if (typeOfBl.equals("MB")) {
//            // Fetch the list of IgmDetailContainer objects
//            List<IgmDetailContainer> igmDetailContainerList = igmDtlContRepo.findByIgmDetailsImportRotationNoAndIgmDetailsBlNo(rotNo, blNo);
//
//            // Map each IgmDetailContainer object to a ContainerList object
//            containerLists = igmDetailContainerList.stream()
//                    .map(igmDetailContainer -> {
//                        ContainerList containerList = modelMapper.map(igmDetailContainer, ContainerList.class);
//                        containerList.setCId(igmDetailContainer.getId());
//                        return containerList;
//                    })
//                    .collect(Collectors.toList());
//
//            // Set the list of ContainerList objects in shedDEOInfoData
//            shedDEOInfoData.setContList(containerLists);
//        }
//        else if (typeOfBl.equals("HB")) {
//            // Fetch the list of IgmSupDetailContainer objects
//            List<IgmSupDetailContainer> igmSupDetailContainerList = igmSupDtlContRepo.findByIgmSupplimentaryDetailImportRotationNoAndIgmSupplimentaryDetailBlNo(rotNo, blNo);
//
//            // Map each IgmSupDetailContainer object to a ContainerList object
//            containerLists = igmSupDetailContainerList.stream()
//                    .map(igmSupDetailContainer -> {
//                        ContainerList containerList = modelMapper.map(igmSupDetailContainer, ContainerList.class);
//                        containerList.setCId(igmSupDetailContainer.getId());
//                        return containerList;
//                    })
//                    .collect(Collectors.toList());
//
//            // Set the list of ContainerList objects in shedDEOInfoData
//            shedDEOInfoData.setContList(containerLists);
//        }

        System.out.println("containerLists : " + containerLists);


        for (ContainerList containerList : containerLists) {
            System.out.println("containerList : " + containerList);
            if (flag.equals("edit")) {
                int count = doUploadWiseContRepo.countByShedMloDoInfoIdAndContIgmId(uploadId, containerList.getCId());
                containerList.setCnt(count);
            } else if ((flag.equals("extend_validity") ||
                    (typeOfBl.equals("MB") && igmType.equals("GM") && edoApplication.getCnfVldtyApprSt() == 1) ||
                    (typeOfBl.equals("HB") && igmType.equals("GM")))) {
                int count = validityDateRepo.countByEdoIdAndContIgmId(edoId, containerList.getCId());
                containerList.setCnt(count);
            }
        }


        List<Object[]> igmMasterDetails;

        igmMasterDetails = igmMastersRepo.findIgmDetailsByRotNoAndBlNO(rotNo, blNo);
        if (igmMasterDetails.size() == 0) {
            igmMasterDetails = igmMastersRepo.findIgmSupplimentaryDetailByRotNoAndBlNO(rotNo, blNo);
        }

        System.out.println("igmMasterDetails.get(0)[26] : " + igmMasterDetails.get(0)[26].getClass().getName());

        shedDEOInfoData.setDtlId(igmMasterDetails.get(0)[0].toString());
        shedDEOInfoData.setNotifyName(igmMasterDetails.get(0)[25].toString());
        shedDEOInfoData.setNotifyAddress(new String((byte[]) igmMasterDetails.get(0)[26], StandardCharsets.UTF_8));
        shedDEOInfoData.setVesselName(igmMasterDetails.get(0)[22].toString());
        shedDEOInfoData.setVoyNo(igmMasterDetails.get(0)[23].toString());
        shedDEOInfoData.setImportRotationNo(igmMasterDetails.get(0)[20].toString());
        shedDEOInfoData.setBillOfEntryNo(igmMasterDetails.get(0)[6] != null ? igmMasterDetails.get(0)[6].toString() : "");
        shedDEOInfoData.setBillOfEntryDate((Date) igmMasterDetails.get(0)[7]);
        shedDEOInfoData.setSubmissionDate((Date) igmMasterDetails.get(0)[38]);
        shedDEOInfoData.setPortOfOrigin(igmMasterDetails.get(0)[27].toString());
        shedDEOInfoData.setPortOfShipment(igmMasterDetails.get(0)[31].toString());
        shedDEOInfoData.setPortOfDestination(igmMasterDetails.get(0)[32].toString());
        shedDEOInfoData.setConsigneeName(igmMasterDetails.get(0)[15].toString());
        shedDEOInfoData.setConsigneeAddress(new String((byte[]) igmMasterDetails.get(0)[16], StandardCharsets.UTF_8));
        shedDEOInfoData.setDescriptionOfGoods(new String((byte[]) igmMasterDetails.get(0)[17], StandardCharsets.UTF_8));
        shedDEOInfoData.setPackDescription(igmMasterDetails.get(0)[3].toString());
        shedDEOInfoData.setPackMarksNumber(new String((byte[]) igmMasterDetails.get(0)[4], StandardCharsets.UTF_8));
        shedDEOInfoData.setWeight(igmMasterDetails.get(0)[5].toString());
        shedDEOInfoData.setWeightUnit(igmMasterDetails.get(0)[13].toString());
        shedDEOInfoData.setOfficeCode(igmMasterDetails.get(0)[8] != null ? igmMasterDetails.get(0)[8].toString() : "");
        shedDEOInfoData.setVolumeInCubicMeters(igmMasterDetails.get(0)[18].toString());
        shedDEOInfoData.setIgmPackNumber(igmMasterDetails.get(0)[2].toString());
        shedDEOInfoData.setExporterName(igmMasterDetails.get(0)[43].toString());
        shedDEOInfoData.setExporterAddress(new String((byte[]) igmMasterDetails.get(0)[44], StandardCharsets.UTF_8));





        List<User> users = userRepo.findByLoginId(sumittedBy);
        shedDEOInfoData.setCnfName(users.get(0).getUName());
        shedDEOInfoData.setCnfLicenseNo(users.get(0).getOrganizationProfile().getLicenseNo());
        System.out.println("users : " + users.get(0));

        String decCode = !igmMasterDetails.isEmpty() && igmMasterDetails.get(0).length > 42 ? (String) igmMasterDetails.get(0)[42] : null;
        System.out.println("decCode : " + decCode);

        String cnfLic = "";
        if (decCode != null) {
            String cnfCode2 = decCode.substring(5, 9);
            String cnfCode1 = decCode.substring(3, 5);
            cnfLic = cnfCode2 + "/" + cnfCode1;

            System.out.println("cnfLic : " + cnfLic);
        }


//        RefBizunitScoped refBizunitScoped;
//
//        if (flag.equals("edit")) {
//            refBizunitScoped = refBizunitScopedRepo.findByRoleAndLifeCycleStateAndId("SHIPPER", "ACT", users.get(0).getOrganizationProfile().getLicenseNo());
//        } else {
//            refBizunitScoped = refBizunitScopedRepo.findByRoleAndLifeCycleStateAndId("SHIPPER", "ACT", cnfLic);
//        }
//
//        shedDEOInfoData.setCnfName(refBizunitScoped.getName());

        Object RemainingQty = shedMloDoInfoRepo.totalDeliveredAndRemaining(rotNo, blNo);
        // Cast RemainingQty to an array of Object[]
        Object[] remainingQtyArray = (Object[]) RemainingQty;

        System.out.println(" remainingQtyArray : " + remainingQtyArray[0]);
        System.out.println(" remainingQtyArray : " + remainingQtyArray[1]);
        System.out.println(" remainingQtyArray : " + remainingQtyArray[2]);

        return new ResponseEntity<ShedDEOInfoData>(shedDEOInfoData, HttpStatus.OK);

    }


    @Override
    public ResponseEntity<RefBizunitScoped> getCnf(String cnfLic) {
        System.out.println("cnfLic : " + cnfLic);
        RefBizunitScoped refBizunitScoped = refBizunitScopedRepo.findByRoleAndLifeCycleStateAndId("SHIPPER", "ACT", cnfLic.trim());
        return new ResponseEntity<RefBizunitScoped>(refBizunitScoped, HttpStatus.OK);
    }


    @Override
    public void shedDOUpload(ShedDEOInfoData shedDEOInfoData,String loginId,Long orgId,String orgTypeId) {
        System.out.println("loginId : "+loginId);
        System.out.println("orgTypeId : "+orgTypeId);
        System.out.println("Shed DEO Info Data"+shedDEOInfoData);
        if(orgTypeId.equals("1"))
        {
            OrganizationProfile organizationProfile = organizationProfileRepository.findById(orgId).get();
            System.out.println("organizationProfile : "+organizationProfile);
        }
    }

    @Override
    public ResponseEntity<ResponseMessage> updateStatforEDO(Long edoId, String loginId, String orgId, String orgTypeId, Date validUptoDate, String ipAddress) {

        String msg = "";
        LocalDateTime localDateTime = LocalDateTime.now();
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

        EdoApplication edoDtls = edoAppRepo.findById(edoId).get();

        System.out.println("edoDtls : " + edoDtls);

        if (edoDtls.getContStatus().equals("LCL")) {
            int updateSt = edoAppRepo.updateFFStat(loginId, edoDtls.getMlo(), edoId);
            System.out.println("updateSt : "+updateSt);
            if (updateSt == 1) {

                System.out.println(" before clearPendingApplications : ");
                edoAppRepo.clearPendingApplications(loginId, edoDtls.getMlo(),edoDtls.getMblOfHbl());

                System.out.println(" after clearPendingApplications : ");

                ClearedMblByMlo entity = new ClearedMblByMlo();
                entity.setMasterBl(edoDtls.getMblOfHbl());
                entity.setClearanceSt(1);
                entity.setEntryTime(date);
                entity.setEntryIpAddress(ipAddress);
                entity.setEnteredBy(loginId);
                entity.setClearedByOrgId(Integer.valueOf(orgId));
                entity.setClearedBy(loginId);
                entity.setClearanceTime(date);
                entity.setClearanceIp(ipAddress);

                System.out.println("before clearedMblByMloRepo save");

                System.out.println("Entity : "+entity);

                ClearedMblByMlo resInsert = clearedMblByMloRepo.save(entity);

                System.out.println("after clearedMblByMloRepo save");


                if (resInsert != null) {
                    msg = "Forwarded Successfully.";
                } else {
                    msg = "Forwarding Failed.";
                }
            } else {
                msg = "Forwarding Failed.";
            }


        }

        else {

            int updateSt = edoAppRepo.updateFFStatAndAppliedValidDt(edoId, validUptoDate, edoDtls.getMlo(), loginId);
            if (updateSt == 1) {
                edoAppRepo.clearPendingApplicationsAndAppliedValidDt(validUptoDate, edoDtls.getMblOfHbl(), edoDtls.getMlo(), loginId);

                ClearedMblByMlo entity = new ClearedMblByMlo();
                entity.setMasterBl(edoDtls.getMblOfHbl());
                entity.setClearanceSt(1);
                entity.setValidUptoDt(validUptoDate);
                entity.setEntryTime(date);
                entity.setEntryIpAddress(ipAddress);
                entity.setEnteredBy(loginId);
                entity.setClearedByOrgId(Integer.valueOf(orgId));
                entity.setClearedBy(loginId);
                entity.setClearanceTime(date);
                entity.setClearanceIp(ipAddress);
                ClearedMblByMlo resInsert = clearedMblByMloRepo.save(entity);
                if (resInsert != null) {
                    msg = "Forwarded Successfully.";
                } else {
                    msg = "Forwarding Failed.";
                }
            } else {
                msg = "Forwarding Failed.";
            }

        }

//        EdoNotification edoNotifyFF = new EdoNotification();
//
//        edoNotifyFF.setApplicationId(Long.valueOf(edoId).intValue());
//        edoNotifyFF.setOrgNotified(edoDtls.getFfOrgId());
//        edoNotifyFF.setNotificationSt(2);
//        edoNotifyFF.setSeenSt(0);
//        edoNotifyFF.setLifeSt(0);
//        edoNotifyFF.setOrgNotifyBy(loginId);
//        edoNotifyFF.setGenerateTime(date);
//        System.out.println("edoNotifyFF value : "+edoNotifyFF);
//        edoNotificationRepository.save(edoNotifyFF);
//        System.out.println("edoNotifyFF");
//
//        User user = userRepo.findByLoginId(edoDtls.getSubmittedBy()).get(0);
//
//
//        EdoNotification edoNotifyCF = new EdoNotification();
//
//        edoNotifyCF.setApplicationId(Long.valueOf(edoId).intValue());
//        edoNotifyCF.setOrgNotified(String.valueOf(user.getOrganizationProfile().getId()));
//        edoNotifyCF.setNotificationSt(2);
//        edoNotifyCF.setSeenSt(0);
//        edoNotifyCF.setOrgNotifyBy(loginId);
//        edoNotifyCF.setGenerateTime(date);
//        edoNotificationRepository.save(edoNotifyCF);
//
//        System.out.println("edoNotifyCF");
//
//        edoNotificationRepository.updateFfLifeStat(orgId, edoId);
//
//        System.out.println("updateFfLifeStat");

        return ResponseEntity.ok().body(new ResponseMessage(msg));

    }

    @Override
    public List edoContainerReport(String from_date,String to_date) {
        List<Object>[] chkEdoContainer = edoContainerReportRepository.getEdoContainerInfo(from_date, to_date);
        ArrayList<EdoContainerReport> arrayList = new ArrayList<>();
        System.out.println("length:"+chkEdoContainer.length);
        for (int i = 0; i < chkEdoContainer.length; i++) {
            EdoContainerReport edoContainerReport = new EdoContainerReport();

            if (chkEdoContainer[i].get(0) != null) {
                edoContainerReport.setImp_rot(chkEdoContainer[i].get(0).toString());
            }
            if (chkEdoContainer[i].get(1) != null) {
                edoContainerReport.setBl_no(chkEdoContainer[i].get(1).toString());
            }
            if (chkEdoContainer[i].get(2) != null) {
                edoContainerReport.setCont_number(chkEdoContainer[i].get(2).toString());
            }
            if (chkEdoContainer[i].get(3) != null) {
                edoContainerReport.setCont_size(chkEdoContainer[i].get(3).toString());
            }

            if (chkEdoContainer[i].get(4) != null) {
                edoContainerReport.setCont_height(chkEdoContainer[i].get(4).toString());
            }

            if (chkEdoContainer[i].get(5) != null) {
                edoContainerReport.setCont_iso_type(chkEdoContainer[i].get(5).toString());
            }

            if (chkEdoContainer[i].get(6) != null) {
                edoContainerReport.setVessel_Name(chkEdoContainer[i].get(6).toString());
            }

            if (chkEdoContainer[i].get(7) != null) {
                edoContainerReport.setUpload_date(chkEdoContainer[i].get(7).toString());
            }
            if (chkEdoContainer[i].get(8) != null) {
                edoContainerReport.setRemarks(chkEdoContainer[i].get(8).toString());
            }
            if (chkEdoContainer[i].get(9) != null) {
                edoContainerReport.setValid_upto_dt(chkEdoContainer[i].get(9).toString());
            }

            if (chkEdoContainer[i].get(12) != null) {
                edoContainerReport.setCF(chkEdoContainer[i].get(12).toString());
            }
            arrayList.add(edoContainerReport);

        }
        return arrayList;

    }

}
