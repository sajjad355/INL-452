package com.internal.service.somruinternal.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;


@Entity
@Table(name = "itemtype")
@EntityListeners(AuditingEntityListener.class)
public class Itemtype {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long dbid;

	@Column(nullable = true)
	private String itemtype;

	public Itemtype(
			Long dbid,
			String itemtype){
			super();
			this.dbid = dbid;
			this.itemtype = itemtype;
		}

	public Itemtype(){
		super();
	}

	public long getDbid() {
		return dbid;
	}

	public void setDbid(long dbid) {
		this.dbid = dbid;
	}

	public String getItemtype() {
		return itemtype;
	}

	public void setItemtype(String itemtype) {
		this.itemtype = itemtype;
	}
}
