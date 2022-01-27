package com.internal.service.somruinternal.repository2;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.internal.service.somruinternal.model2.PlateMap;
import com.internal.service.somruinternal.model2.WorksheetDesign;

@Repository
public interface PlateMapRepository extends JpaRepository<PlateMap, Long> {

	List<PlateMap> findByWorksheet(WorksheetDesign worksheet);

}
