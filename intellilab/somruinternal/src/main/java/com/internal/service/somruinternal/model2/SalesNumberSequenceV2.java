package com.internal.service.somruinternal.model2;

import javax.persistence.*;

@Entity
@Table(name = "il_sales_number_sequence")
public class SalesNumberSequenceV2 {
    
    public static long STARTING_VALUE = 7000;
    
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long salesNumberSequenceId;
    
    @Column(nullable = false,length=50)
    private String salesNumberSequenceType;
    
    @Column(nullable = false)
    private long salesNumberSequenceValue;
    
    public SalesNumberSequenceV2() {}

    /**
     * @return the salesNumberSequenceId
     */
    public long getSalesNumberSequenceId() {
        return salesNumberSequenceId;
    }

    /**
     * @param salesNumberSequenceId the salesNumberSequenceId to set
     */
    public void setSalesNumberSequenceId(long salesNumberSequenceId) {
        this.salesNumberSequenceId = salesNumberSequenceId;
    }

    /**
     * @return the salesNumberSequenceType
     */
    public String getSalesNumberSequenceType() {
        return salesNumberSequenceType;
    }

    /**
     * @param salesNumberSequenceType the salesNumberSequenceType to set
     */
    public void setSalesNumberSequenceType(String salesNumberSequenceType) {
        this.salesNumberSequenceType = salesNumberSequenceType;
    }

    /**
     * @return the salesNumberSequenceValue
     */
    public long getSalesNumberSequenceValue() {
        return salesNumberSequenceValue;
    }

    /**
     * @param salesNumberSequenceValue the salesNumberSequenceValue to set
     */
    public void setSalesNumberSequenceValue(long salesNumberSequenceValue) {
        this.salesNumberSequenceValue = salesNumberSequenceValue;
    }
    
    
}
