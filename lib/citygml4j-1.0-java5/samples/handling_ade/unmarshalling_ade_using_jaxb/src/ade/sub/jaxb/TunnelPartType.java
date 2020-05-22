
package ade.sub.jaxb;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TunnelPartType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TunnelPartType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.citygml.org/ade/sub/0.9.0}AbstractTunnelType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.citygml.org/ade/sub/0.9.0}_GenericApplicationPropertyOfTunnelPart" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TunnelPartType", propOrder = {
    "genericApplicationPropertyOfTunnelPart"
})
public class TunnelPartType
    extends AbstractTunnelType
{

    @XmlElement(name = "_GenericApplicationPropertyOfTunnelPart")
    protected List<Object> genericApplicationPropertyOfTunnelPart;

    /**
     * Gets the value of the genericApplicationPropertyOfTunnelPart property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the genericApplicationPropertyOfTunnelPart property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    get_GenericApplicationPropertyOfTunnelPart().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Object }
     * 
     * 
     */
    public List<Object> get_GenericApplicationPropertyOfTunnelPart() {
        if (genericApplicationPropertyOfTunnelPart == null) {
            genericApplicationPropertyOfTunnelPart = new ArrayList<Object>();
        }
        return this.genericApplicationPropertyOfTunnelPart;
    }

    public boolean isSet_GenericApplicationPropertyOfTunnelPart() {
        return ((this.genericApplicationPropertyOfTunnelPart!= null)&&(!this.genericApplicationPropertyOfTunnelPart.isEmpty()));
    }

    public void unset_GenericApplicationPropertyOfTunnelPart() {
        this.genericApplicationPropertyOfTunnelPart = null;
    }

}
