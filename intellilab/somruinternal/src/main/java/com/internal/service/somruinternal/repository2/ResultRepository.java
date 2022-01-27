package com.internal.service.somruinternal.repository2;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.internal.service.somruinternal.model2.Result;
import com.internal.service.somruinternal.model2.WorksheetInstance;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {

	List<Result> findByWorksheet(WorksheetInstance worksheet);

	Result findByDbid(Long dbid);

}
