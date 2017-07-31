package com.sample.soap.xml.dm;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "MathsDbService", targetNamespace = "http://dm.xml.soap.sample.com/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface MathsDbService {


    /**
     * 
     * @param arg0
     * @return
     *     returns java.util.List<com.sample.soap.xml.dm.EmpDetails>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "selectAllEmpAllData", targetNamespace = "http://dm.xml.soap.sample.com/", className = "com.sample.soap.xml.dm.SelectAllEmpAllData")
    @ResponseWrapper(localName = "selectAllEmpAllDataResponse", targetNamespace = "http://dm.xml.soap.sample.com/", className = "com.sample.soap.xml.dm.SelectAllEmpAllDataResponse")
    public List<EmpDetails> selectAllEmpAllData(
        @WebParam(name = "arg0", targetNamespace = "")
        EmpAddress arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns com.sample.soap.xml.dm.EmpDetails
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "queryEmpConfigs", targetNamespace = "http://dm.xml.soap.sample.com/", className = "com.sample.soap.xml.dm.QueryEmpConfigs")
    @ResponseWrapper(localName = "queryEmpConfigsResponse", targetNamespace = "http://dm.xml.soap.sample.com/", className = "com.sample.soap.xml.dm.QueryEmpConfigsResponse")
    public EmpDetails queryEmpConfigs(
        @WebParam(name = "arg0", targetNamespace = "")
        EmpConfigIdentifier arg0);

}
