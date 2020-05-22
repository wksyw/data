
package ade.sub.jaxb;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.citygml4j.jaxb.citygml.core._1.AbstractCityObjectType;
import org.citygml4j.jaxb.gml._3_1_1.CodeType;
import org.citygml4j.jaxb.gml._3_1_1.GeometryPropertyType;


/**
 * An TunnelInstallation is an external part of a Tunnel which has a specific function or semantical meaning. As subclass of _CityObject, a
 * 				TunnelInstallation inherits all attributes and relations, in particular an id, names, external references, generic attributes and generalization relations.
 * 			
 * 
 * <p>Java class for TunnelInstallationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TunnelInstallationType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.opengis.net/citygml/1.0}AbstractCityObjectType">
 *       &lt;sequence>
 *         &lt;element name="class" type="{http://www.opengis.net/gml}CodeType" minOccurs="0"/>
 *         &lt;element name="function" type="{http://www.opengis.net/gml}CodeType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="usage" type="{http://www.opengis.net/gml}CodeType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="lod4Geometry" type="{http://www.opengis.net/gml}GeometryPropertyType" minOccurs="0"/>
 *         &lt;element ref="{http://www.citygml.org/ade/sub/0.9.0}_GenericApplicationPropertyOfTunnelInstallation" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TunnelInstallationType", propOrder = {
    "clazz",
    "function",
    "usage",
    "lod4Geometry",
    "genericApplicationPropertyOfTunnelInstallation"
})
public class TunnelInstallationType
    extends AbstractCityObjectType
{

    @XmlElement(name = "class")
    protected CodeType clazz;
    protected List<CodeType> function;
    protected List<CodeType> usage;
    protected GeometryPropertyType lod4Geometry;
    @XmlElement(name = "_GenericApplicationPropertyOfTunnelInstallation")
    protected List<Object> genericApplicationPropertyOfTunnelInstallation;

    /**
     * Gets the value of the clazz property.
     * 
     * @return
     *     possible object is
     *     {@link CodeType }
     *     
     */
    public CodeType getClazz() {
        return clazz;
    }

    /**
     * Sets the value of the clazz property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeType }
     *     
     */
    public void setClazz(CodeType value) {
        this.clazz = value;
    }

    public boolean isSetClazz() {
        return (this.clazz!= null);
    }

    /**
     * Gets the value of the function property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the function property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFunction().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CodeType }
     * 
     * 
     */
    public List<CodeType> getFunction() {
        if (function == null) {
            function = new ArrayList<CodeType>();
        }
        return this.function;
    }

    public boolean isSetFunction() {
        return ((this.function!= null)&&(!this.function.isEmpty()));
    }

    public void unsetFunction() {
        this.function = null;
    }

    /**
     * Gets the value of the usage property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the usage property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUsage().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CodeType }
     * 
     * 
     */
    public List<CodeType> getUsage() {
        if (usage == null) {
            usage = new ArrayList<CodeType>();
        }
        return this.usage;
    }

    public boolean isSetUsage() {
        return ((this.usage!= null)&&(!this.usage.isEmpty()));
    }

    public void unsetUsage() {
        this.usage = null;
    }

    /**
     * Gets the value of the lod4Geometry property.
     * 
     * @return
     *     possible object is
     *     {@link GeometryPropertyType }
     *     
     */
    public GeometryPropertyType getLod4Geometry() {
        return lod4Geometry;
    }

    /**
     * Sets the value of the lod4Geometry property.
     * 
     * @param value
     *     allowed object is
     *     {@link GeometryPropertyType }
     *     
     */
    public void setLod4Geometry(GeometryPropertyType value) {
        this.lod4Geometry = value;
    }

    public boolean isSetLod4Geometry() {
        return (this.lod4Geometry!= null);
    }

    /**
     * Gets the value of the genericApplicationPropertyOfTunnelInstallation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the genericApplicationPropertyOfTunnelInstallation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    get_GenericApplicationPropertyOfTunnelInstallation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Object }
     * 
     * 
     */
    public List<Object> get_GenericApplicationPropertyOfTunnelInstallation() {
        if (genericApplicationPropertyOfTunnelInstallation == null) {
            genericApplicationPropertyOfTunnelInstallation = new ArrayList<Object>();
        }
        return this.genericApplicationPropertyOfTunnelInstallation;
    }

    public boolean isSet_GenericApplicationPropertyOfTunnelInstallation() {
        return ((this.genericApplicationPropertyOfTunnelInstallation!= null)&&(!this.genericApplicationPropertyOfTunnelInstallation.isEmpty()));
    }

    public void unset_GenericApplicationPropertyOfTunnelInstallation() {
        this.genericApplicationPropertyOfTunnelInstallation = null;
    }

}
