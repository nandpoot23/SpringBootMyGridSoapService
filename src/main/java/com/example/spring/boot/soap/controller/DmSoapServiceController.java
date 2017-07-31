package com.example.spring.boot.soap.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.example.spring.boot.soap.service.DmServiceInterface;
import com.example.spring.boot.soap.types.EmpAddress;
import com.example.spring.boot.soap.types.EmpConfigIdentifier;
import com.example.spring.boot.soap.types.EmpDetails;
import com.sample.soap.xml.dm.QueryEmpConfigs;
import com.sample.soap.xml.dm.QueryEmpConfigsResponse;
import com.sample.soap.xml.dm.SelectAllEmpAllData;
import com.sample.soap.xml.dm.SelectAllEmpAllDataResponse;

/**
 * @author mlahariya
 * @version 1.0, Dec 2016
 */

@Component
@Endpoint("DmSoapServiceController")
public class DmSoapServiceController {

    private static final String NAMESPACE_URI = "http://dm.xml.soap.sample.com/";

    private static final Logger logger = LoggerFactory.getLogger(DmSoapServiceController.class);

    @Autowired
    private DmServiceInterface dmService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "queryEmpConfigs")
    @ResponsePayload
    public QueryEmpConfigsResponse queryEmpConfigs(@RequestPayload QueryEmpConfigs queryCustomer,
            MessageContext messageContext) throws Exception {

        logger.info("SOAP Request Received Of queryCustomer " + queryCustomer);

        com.sample.soap.xml.dm.EmpConfigIdentifier soapEmpConfigIdentifier = queryCustomer.getArg0();

        EmpConfigIdentifier restEmpConfigIdentifier = new EmpConfigIdentifier();
        restEmpConfigIdentifier.setId(soapEmpConfigIdentifier.getId());

        com.example.spring.boot.soap.types.EmpDetails restImplResponse = dmService
                .queryEmpConfigs(restEmpConfigIdentifier);

        QueryEmpConfigsResponse response = new QueryEmpConfigsResponse();
        com.sample.soap.xml.dm.EmpDetails soapXmlEmpDetails = new com.sample.soap.xml.dm.EmpDetails();
        soapXmlEmpDetails.setId(restImplResponse.getId());
        soapXmlEmpDetails.setFirstName(restImplResponse.getFirstName());
        soapXmlEmpDetails.setLastName(restImplResponse.getLastName());
        soapXmlEmpDetails.setAddress(restImplResponse.getAddress());
        soapXmlEmpDetails.setCity(restImplResponse.getCity());
        soapXmlEmpDetails.setEmail(restImplResponse.getEmail());
        soapXmlEmpDetails.setMobile(restImplResponse.getMobile());
        response.setReturn(soapXmlEmpDetails);

        if (logger.isDebugEnabled())
            logger.debug("Returning Response for queryEmpConfigs");

        logger.info("SOAP Response Sent Of queryEmpConfigs  : " + response.toString());
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "selectAllEmpAllData")
    @ResponsePayload
    public SelectAllEmpAllDataResponse selectAllEmpAllData(@RequestPayload SelectAllEmpAllData selectAllEmpAllData,
            MessageContext messageContext) throws Exception {

        logger.info("SOAP Request Received Of selectAllEmpAddrData " + selectAllEmpAllData);

        com.sample.soap.xml.dm.EmpAddress soapEmpAddress = selectAllEmpAllData.getArg0();

        EmpAddress restEmpAddr = new EmpAddress();
        restEmpAddr.setAddress(soapEmpAddress.getAddress());

        List<EmpDetails> restImplResponse = dmService.selectAllEmpAllData(restEmpAddr);

        SelectAllEmpAllDataResponse response = new SelectAllEmpAllDataResponse();

        for (EmpDetails restEmpDetails : restImplResponse) {

            com.sample.soap.xml.dm.EmpDetails soapEmpDetails = new com.sample.soap.xml.dm.EmpDetails();
            soapEmpDetails.setId(restEmpDetails.getId());
            soapEmpDetails.setFirstName(restEmpDetails.getFirstName());
            soapEmpDetails.setLastName(restEmpDetails.getLastName());
            soapEmpDetails.setAddress(restEmpDetails.getAddress());
            soapEmpDetails.setCity(restEmpDetails.getCity());
            soapEmpDetails.setEmail(restEmpDetails.getEmail());
            soapEmpDetails.setMobile(restEmpDetails.getMobile());
            response.getReturn().add(soapEmpDetails);

        }

        logger.info("SOAP Response Sent Of selectAllEmpAddrData  : " + response.toString());
        return response;

    }

}
