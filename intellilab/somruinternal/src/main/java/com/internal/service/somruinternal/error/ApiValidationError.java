package com.internal.service.somruinternal.error;


import org.apache.commons.lang3.builder.*;

public class ApiValidationError extends ApiSubError {
    private String object;
    private String field;
    private Object rejectedValue;
    private String message;

    ApiValidationError(String object, String message) {
        this.object = object;
        this.message = message;
    }

    ApiValidationError(String object, String field, Object rejectedValue, String message) {
        this.object = object;
        this.message = message;
        this.rejectedValue = rejectedValue;
        this.field = field;
    }
    
    @Override
    public String toString() {        
        return ReflectionToStringBuilder.toString(this,  ToStringStyle.SHORT_PREFIX_STYLE);
    }
    
    @Override
    public int hashCode() {        
         return HashCodeBuilder.reflectionHashCode(this);
    }
    
    @Override
    public boolean equals( Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

}
