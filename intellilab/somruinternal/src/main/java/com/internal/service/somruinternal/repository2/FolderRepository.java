package com.internal.service.somruinternal.repository2;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.internal.service.somruinternal.model2.Folder;
import com.internal.service.somruinternal.model2.FolderLocation;
import com.internal.service.somruinternal.model2.UserV2;

public interface FolderRepository extends JpaRepository<Folder, Long>, JpaSpecificationExecutor<Folder> {

	List<Folder> findByCustodianIn(List<UserV2> users);

	List<Folder> findByProjectManagerIn(List<UserV2> users);

	List<Folder> findByLocationIn(List<FolderLocation> folderLocationList);

	List<Folder> findByTitleContaining(String searchKey);

	Folder findByDbid(Long dbid);

}
