//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.06.19 at 12:24:22 AM CEST 
//


package com.example.user.xmlmodel.user.user_privilege;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="user_privilege_id" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "userPrivilegeId"
})
@XmlRootElement(name = "getUserPrivilegeById")
public class GetUserPrivilegeById {

    @XmlElement(name = "user_privilege_id")
    protected long userPrivilegeId;

    /**
     * Gets the value of the userPrivilegeId property.
     * 
     */
    public long getUserPrivilegeId() {
        return userPrivilegeId;
    }

    /**
     * Sets the value of the userPrivilegeId property.
     * 
     */
    public void setUserPrivilegeId(long value) {
        this.userPrivilegeId = value;
    }

}
