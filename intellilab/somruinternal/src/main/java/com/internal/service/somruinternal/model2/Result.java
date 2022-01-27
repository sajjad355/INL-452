package com.internal.service.somruinternal.model2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "ws_instance_result")
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dbid;

    @ManyToOne
    @JoinColumn(name = "fk_worksheet_instance_id")
    @JsonIgnore
    private WorksheetInstance worksheet;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    @JsonProperty("data")
    private String data;

    @Column
    @JsonProperty("name")
    private String name;

    @Column
    @JsonProperty("type")
    private String type;

    public Long getDbid() {
        return dbid;
    }

    public void setDbid(Long dbid) {
        //Following if block is an work-around of an unresolved issue. 
        //Exception of the issue was as below:

        //org.springframework.orm.jpa.JpaObjectRetrievalFailureException: 
        //Unable to find com.internal.service.somruinternal.model2.Result with id -1; 
        //nested exception is javax.persistence.EntityNotFoundException: 
        //Unable to find com.internal.service.somruinternal.model2.Result with id -1
        //How to reproduce (when following if block is not there): 
        //while updating ws instance, add file(s) with qc/calibrator preparation step with files dbid:-1 in JSON
        if (dbid != null && dbid > 0) {
            this.dbid = dbid;
        }
    }

    public WorksheetInstance getWorksheet() {
        return worksheet;
    }

    public void setWorksheet(WorksheetInstance worksheet) {
        this.worksheet = worksheet;
    }

    
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
