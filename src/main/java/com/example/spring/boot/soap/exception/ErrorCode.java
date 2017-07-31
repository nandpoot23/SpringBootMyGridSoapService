package com.example.spring.boot.soap.exception;

/**
 * @author mlahariya
 * @version 1.0, Nov 2016
 */

public enum ErrorCode implements java.io.Serializable {

    CS_1001("CUSTOMER-1001"), 
    CS_1002("CUSTOMER-1002"), 
    CS_1003("CUSTOMER-1003"), 
    CS_1004("CUSTOMER-1004"), 
    CS_1005("CUSTOMER-1005"), 
    CS_1006("CUSTOMER-1006"), 
    CS_1007("CUSTOMER-1007"), 
    CS_1008("CUSTOMER-1008"), 
    CS_1009("CUSTOMER-1009"),
    CS_1010("CUSTOMER-1010"),
    CS_1011("CUSTOMER-1011");
            
    private String property = null;

    /**
     * Enum constructor
     */
    
    private ErrorCode(String property) {
        this.property = property;
    }

    public static ErrorCode fromValue(String value) {
        return valueOf(value);
    }

    public String getValue() {
        return property;
    }

}
