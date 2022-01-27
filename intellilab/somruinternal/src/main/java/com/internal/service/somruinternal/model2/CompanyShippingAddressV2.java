package com.internal.service.somruinternal.model2;

import java.util.Date;
import javax.persistence.*;
import org.apache.commons.lang3.builder.*;


@Entity
@Table(name = "il_company_shipping_address")
public class CompanyShippingAddressV2 implements Diffable<CompanyShippingAddressV2> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long addressId;
    
    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false,length=100)
    private String locationName;

    @Column(nullable = false,length=200)
    private String addressLine1;

    @Column(nullable = true,length=200)
    private String addressLine2;

    @Column(nullable = false,length=50)
    private String city;

    @Column(nullable = true,length=50)
    private String province;

    @Column(nullable = false,length=50)
    private String country;

    @Column(nullable = true,length=50)
    private String postalCode;

    @Column(nullable = false,length=50)
    private String editedBy;

    @Column(nullable = false)
    private Date modifiedOn;

    


    public CompanyShippingAddressV2() {
        super();
    }

  

  /**
   * @return the dbid
     */
    public long getAddressId() {
        return addressId;
    }

    /**
     * @param addressId the dbid to set
     */
    public void setAddessId(long addressId) {
        this.addressId = addressId;
    }

    /**
     * @return the addressLine1
     */
    public String getAddressLine1() {
        return addressLine1;
    }

    /**
     * @param addressLine1 the addressLine1 to set
     */
    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    /**
     * @return the addressLine2
     */
    public String getAddressLine2() {
        return addressLine2;
    }

    /**
     * @param addressLine2 the addressLine2 to set
     */
    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the province
     */
    public String getProvince() {
        return province;
    }

    /**
     * @param province the province to set
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return the postalCode
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * @param postalCode the postalCode to set
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * @return the editedBy
     */
    public String getEditedBy() {
        return editedBy;
    }

    /**
     * @param editedBy the editedBy to set
     */
    public void setEditedBy(String editedBy) {
        this.editedBy = editedBy;
    }

    /**
     * @return the modifiedOn
     */
    public Date getModifiedOn() {
        return modifiedOn;
    }

    /**
     * @param modifiedBy the modifiedBy to set
     */
    public void setModifiedOn(Date modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    /**
     * @return the locationName
     */
    public String getLocationName() {
        return locationName;
    }

    /**
     * @param locationName the locationName to set
     */
    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
    
    /**
     * @return the active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }


	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	@Override
	public DiffResult diff(CompanyShippingAddressV2 obj) {
		// No need for null check, as NullPointerException correct if obj is null
		return new DiffBuilder(this, obj, ToStringStyle.SHORT_PREFIX_STYLE, true)
				.append("locationName", this.getLocationName(), obj.getLocationName())
				.append("active", this.isActive(), obj.isActive())
				.append("addressLine1", this.getAddressLine1(), obj.getAddressLine1())
				.append("addressLine2", this.getAddressLine2(), obj.getAddressLine2())
				.append("city", this.getCity(), obj.getCity()).append("province", this.getProvince(), obj.getProvince())
				.append("country", this.getCountry(), obj.getCountry())
				.append("postalCode", this.getPostalCode(), obj.getPostalCode())
				.append("modifiedOn", this.getModifiedOn(), obj.getModifiedOn())
				.append("editedBy", this.getEditedBy(), obj.getEditedBy()).build();

	}

     

    

  
}
