package com.internal.service.somruinternal.model2;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.apache.commons.lang3.builder.DiffBuilder;
import org.apache.commons.lang3.builder.DiffResult;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
@Table(name = "il_folder_location")
public class FolderLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dbid;

    private String title;

    @Column(nullable = false)
    @UpdateTimestamp
    private Date modifiedOn;

    @ManyToOne
    private UserV2 employee;

    @ManyToOne
    private UserV2 projectManager;

    @OneToMany(mappedBy = "location")
    @JsonIgnore
    private List<Folder> folderList = new ArrayList<>();

    private boolean isActive = true;

    private final String barcode = "L" + (new Random().nextInt(89999999) + 10000000);

    public Long getDbid() {
        return dbid;
    }

    public void setDbid(Long dbid) {
        this.dbid = dbid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(Date modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public UserV2 getEmployee() {
        return employee;
    }

    public void setEmployee(UserV2 employee) {
        this.employee = employee;
    }

    public UserV2 getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(UserV2 projectManager) {
        this.projectManager = projectManager;
    }

    public List<Folder> getFolderList() {
        return folderList;
    }

    public void setFolderList(List<Folder> folderList) {
        this.folderList = folderList;
    }
    
	private boolean hasFolder(Folder folderToCheck) {
		boolean found = false;
		if (folderToCheck != null) {
			for (Folder folder : folderList) {
				if (folder.getDbid() == folderToCheck.getDbid()) {
					found = true;
				}
			}
		}
		return found;
	}

	/**
	 * @param addressId the addressId of the address to return
	 * @return CompanyShippingAddressV2 if the collection of shipping addresses has
	 *         a record with this id. If not found, return null
	 */
	private Folder getFolder(long id) {
		Folder aFolder = null;
		for (Folder sa : folderList) {
			if (sa.getDbid() == id) {
				aFolder = sa;
			}
		}
		return aFolder;
	}

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getBarcode() {
        return barcode;
    }
    
    @Override
    public String toString() {        
        return ReflectionToStringBuilder.toString(this,  ToStringStyle.SHORT_PREFIX_STYLE);
    }

	public String diffCompare(FolderLocation obj) {
		StringBuilder diffStringBuilder = new StringBuilder();
		StringBuilder folderListStringBuilder = new StringBuilder();

		DiffBuilder db = new DiffBuilder(this, obj, ToStringStyle.SHORT_PREFIX_STYLE, true)
				.append("title", this.getTitle(), obj.getTitle())
				.append("modifiedOn", this.getModifiedOn(), obj.getModifiedOn())
				.append("isActive", this.isActive(), obj.isActive())
				.append("barcode", this.getBarcode(), obj.getBarcode());

		DiffResult projectManagerDiffs = this.projectManager.diff(obj.getProjectManager());
		db.append("projectManager", projectManagerDiffs);
		
		DiffResult employeeDiffs = this.employee.diff(obj.getEmployee());
		db.append("employee", employeeDiffs);

		// iterate over this object contacts and check contact collection from both
		// objects to see what has been added or updated
		// note that no delete check is necessary as we do not delete contacts, simply
		// set them to inactive
		int folderListIncrementer = 1;
		for (Folder folder : folderList) {
			if (obj.hasFolder(folder)) {
				// check update diffs on same object
				Folder otherFolder = obj.getFolder(folder.getDbid());
				DiffResult contactDiffs = folder.diff(otherFolder);
				db.append(String.format("folder #%d (name:%s)", folderListIncrementer, folder.getCreatedBy()),
						contactDiffs);
			} else {
				// collect delta of new Contacts and append directly to diffSB
				folderListStringBuilder.append(System.lineSeparator());
				folderListStringBuilder.append(String.format("folderList #%d is new - details : %s",
						folderListIncrementer, folder.toString()));
			}
			folderListIncrementer++;
		}



		// join all the differences together
		diffStringBuilder.append(db.build().toString());
		if (folderListStringBuilder.length() > 0) {
			diffStringBuilder.append(folderListStringBuilder.toString());
		}

		return diffStringBuilder.toString();
	}



}
