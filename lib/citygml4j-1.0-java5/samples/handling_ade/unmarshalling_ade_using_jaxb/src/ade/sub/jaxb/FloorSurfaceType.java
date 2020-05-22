
package ade.sub.jaxb;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FloorSurfaceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FloorSurfaceType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.citygml.org/ade/sub/0.9.0}AbstractBoundarySurfaceType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.citygml.org/ade/sub/0.9.0}_GenericApplicationPropertyOfFloorSurface" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FloorSurfaceType", propOrder = {
    "genericApplicationPropertyOfFloorSurface"
})
public class FloorSurfaceType
    extends AbstractBoundarySurfaceType
{

    @XmlElement(name = "_GenericApplicationPropertyOfFloorSurface")
    protected List<Object> genericApplicationPropertyOfFloorSurface;

    /**
     * Gets the value of the genericApplicationPropertyOfFloorSurface property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the genericApplicationPropertyOfFloorSurface property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    get_GenericApplicationPropertyOfFloorSurface().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Object }
     * 
     * 
     */
    public List<Object> get_GenericApplicationPropertyOfFloorSurface() {
        if (genericApplicationPropertyOfFloorSurface == null) {
            genericApplicationPropertyOfFloorSurface = new ArrayList<Object>();
        }
        return this.genericApplicationPropertyOfFloorSurface;
    }

    public boolean isSet_GenericApplicationPropertyOfFloorSurface() {
        return ((this.genericApplicationPropertyOfFloorSurface!= null)&&(!this.genericApplicationPropertyOfFloorSurface.isEmpty()));
    }

    public void unset_GenericApplicationPropertyOfFloorSurface() {
        this.genericApplicationPropertyOfFloorSurface = null;
    }

}
