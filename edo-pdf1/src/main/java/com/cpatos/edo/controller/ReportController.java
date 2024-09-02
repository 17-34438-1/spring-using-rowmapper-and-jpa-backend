package com.cpatos.edo.controller;

import com.cpatos.edo.model.cchaportdb.CommudityDetail;
import com.cpatos.edo.model.cchaportdb.OffDoc;
import com.cpatos.edo.service.cchaportdb.*;
import com.cpatos.edo.service.sparcsn4.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/report")
public class ReportController {
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
    DeliveryOrderService deliveryOrderService;

    @Autowired
    VesselBillService vesselBillService;

    @Autowired
    MloWisePreAdvisedContainerService mloWisePreAdvisedContainerService;

    @Autowired
    LoadedContainerService loadedContainerService;

    @Autowired
    ImportDischargeAndBalanceService importDischargeAndBalanceService;


    //Rafiq
    @RequestMapping(value = "/OffDocInformation", method = RequestMethod.GET)
    public @ResponseBody
    List<OffDoc> OffDocInfo(){
        return offDocInformationService.getOffDocList();
    }


    //Rafiq
    @RequestMapping(value = "/CommudityInformation", method = RequestMethod.GET)
    public @ResponseBody
    List<CommudityDetail> CommudityInfo(){
        return commudityDetailService.getCommydityList();
    }

    // Rafiq
    @RequestMapping(value = "/PortCodeInfo", method = RequestMethod.GET)
    public @ResponseBody
    List PortCode(){
        return portCodeService.getPortCodeList();
    }

    //Rafiq
    @RequestMapping(value = "/AgentWiseVessel", method = RequestMethod.GET)
    public @ResponseBody
    List AgentList(){
        return agentService.getAgentList();
    }

    //Rafiq
    @RequestMapping(value = "/AgentWiseVesselList/{rotation}", method = RequestMethod.GET)
    public @ResponseBody
    List AgentWiseVessel(@PathVariable String rotation){
        return agentService.getAgentWiseVessel(rotation);
    }


    //Rafiq
    @RequestMapping(value = "/OffDocWiseContainer/{rotation}", method = RequestMethod.GET)
    public @ResponseBody
    List OffDocWiseContainerList( @PathVariable String rotation){
        return offDocWiseContainerService.getOffDocWiseContainer(rotation);
    }

    //Rafiq
    @RequestMapping(value = "/AgentWiseContainer/{serch_by}/{serch_value}", method = RequestMethod.GET)
    public @ResponseBody
    List AgentWiseContainerList( @PathVariable String serch_by ,@PathVariable String serch_value){
        return agentWiseContainerService.getAgentWiseContainer(serch_by,serch_value);
    }

    //Rafiq
    @RequestMapping(value = "/VesselTransitWay/{rotation}", method = RequestMethod.GET)
    public @ResponseBody
    List VesselTransitWay( @PathVariable String rotation){
        return vesselTransitWayService.getVesselTransitWay(rotation);
    }


    //Rafiq
    @RequestMapping(value = "/deliveryOrder/{bill_entry}", method = RequestMethod.GET)
    public @ResponseBody
    List DeliveryOrder(@PathVariable String bill_entry){
        return deliveryOrderService.getDeliveryOrder(bill_entry);
    }



    // Rafiq
    @RequestMapping(value = "/VesselBill", method = RequestMethod.GET)
    public @ResponseBody
    List VesselBill(){
        return vesselBillService.getVesselList();
    }

    //Rafiq
    @RequestMapping(value = "/mloWisePreAdvisedContainer/{rot}",method = RequestMethod.GET)
    public @ResponseBody
    List mloWisePreAdvised(@PathVariable String rot){
        return mloWisePreAdvisedContainerService.getMloWisePreAdvisedContainer(rot);
    }

    @RequestMapping(value = "/PreAdvisedContainer/{rot}/{mlo}",method = RequestMethod.GET)
    public @ResponseBody
    List PreAdvisedContainer(@PathVariable String rot,@PathVariable String mlo){
        return mloWisePreAdvisedContainerService.getPreAdvisedContainer(rot,mlo);
    }

    @RequestMapping(value = "/ExportSummary/{rot}/{mlo}",method = RequestMethod.GET)
    public @ResponseBody
    List mloWisePreAdvised(@PathVariable String rot,@PathVariable String mlo){
        return mloWisePreAdvisedContainerService.getExportSummary(rot,mlo);
    }

    @RequestMapping(value = "/LoadedContainer/{rot}/{from_date}/{to_date}",method = RequestMethod.GET)
    public @ResponseBody
    List LoadedContainer(@PathVariable String rot,@PathVariable String from_date,@PathVariable String to_date){
        return loadedContainerService.getLoadedContainer(rot,from_date,to_date);
    }

    @RequestMapping(value = "/LoadedContainerDischargeList/{rot}",method = RequestMethod.GET)
    public @ResponseBody
    List LoadedContainerDischarge(@PathVariable String rot){
        return loadedContainerService.getDischargeReport(rot);
    }

    @RequestMapping(value = "/LoadedContainerBalanceList/{rot}",method = RequestMethod.GET)
    public @ResponseBody
    List LoadedContainerBalance(@PathVariable String rot){
        return loadedContainerService.getBalanceReport(rot);
    }

    @RequestMapping(value = "/ImportContainerBalance/{rot}",method = RequestMethod.GET)
    public @ResponseBody
    List ImportContainerBalance(@PathVariable String rot){
        return importDischargeAndBalanceService.getContainerReportBalance(rot);
    }

    @RequestMapping(value = "/ImportContainerDischarge/{rot}",method = RequestMethod.GET)
    public @ResponseBody
    List ImportContainerDischarge(@PathVariable String rot){
        return importDischargeAndBalanceService.getContainerReportDischarge(rot);
    }

    @RequestMapping(value = "/ImportContainerDischargeList/{rot}",method = RequestMethod.GET)
    public @ResponseBody
    List ImportContainerDischargeList(@PathVariable String rot){
        return importDischargeAndBalanceService.getDischargeReport(rot);
    }


    @RequestMapping(value = "/ImportContainerBalanceList/{rot}",method = RequestMethod.GET)
    public @ResponseBody
    List ImportContainerBalanceList(@PathVariable String rot){
        return importDischargeAndBalanceService.getBalanceReport(rot);
    }

}
