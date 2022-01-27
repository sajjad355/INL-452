package com.internal.service.somruinternal.model2;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "il_catalog_sequence")
public class CatalogSequenceV2 {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long catalogNumberId;
    
    @Column(nullable = false,length=50)
    private String catalogNumberType;
    
    @Column(nullable = false)
    private int catalogNumber;
    
    public CatalogSequenceV2() {
        
    } 

    /**
     * @return the catalogNumberId
     */
    public long getCatalogNumberId() {
        return catalogNumberId;
    }

    /**
     * @param catalogNumberId the catalogNumberId to set
     */
    public void setCatalogNumberId(long catalogNumberId) {
        this.catalogNumberId = catalogNumberId;
    }

    /**
     * @return the catalogNumberType
     */
    public String getCatalogNumberType() {
        return catalogNumberType;
    }

    /**
     * @param catalogNumberType the catalogNumberType to set
     */
    public void setCatalogNumberType(String catalogNumberType) {
        this.catalogNumberType = catalogNumberType;
    }

    /**
     * @return the catalogNumber
     */
    public int getCatalogNumber() {
        return catalogNumber;
    }

    /**
     * @param catalogNumber the catalogNumber to set
     */
    public void setCatalogNumber(int catalogNumber) {
        this.catalogNumber = catalogNumber;
    }
    
    /**
     *
     * @return String representation of object
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append( "catalogNumberId=");
        sb.append( catalogNumberId );
        sb.append( ",catalogNumberType=");
        sb.append( catalogNumberType );
        sb.append( ",catalogNumber=");
        sb.append( catalogNumber );
        sb.append( "\n");
        return sb.toString();
    }
    
}
