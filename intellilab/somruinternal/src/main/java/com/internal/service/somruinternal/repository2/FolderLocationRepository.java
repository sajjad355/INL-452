package com.internal.service.somruinternal.repository2;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.internal.service.somruinternal.model2.FolderLocation;

public interface FolderLocationRepository extends JpaRepository<FolderLocation, Long>, JpaSpecificationExecutor<FolderLocation>  {

	List<FolderLocation> findByTitleContaining(String searchKey);

	FolderLocation findByDbid(Long dbid);

}
