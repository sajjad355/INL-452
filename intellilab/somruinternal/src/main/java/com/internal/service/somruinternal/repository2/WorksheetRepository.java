package com.internal.service.somruinternal.repository2;

import com.internal.service.somruinternal.model2.Worksheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorksheetRepository extends JpaRepository<Worksheet, Long> {

  @Query(value="Select DISTINCT worksheet From Worksheet worksheet Where worksheet.status = 'In-Progress'")
  public List<Worksheet> getAllWorksheetByInProgressStatus();

  @Query(value="Select DISTINCT worksheet From Worksheet worksheet Where worksheet.status = 'Completed'")
  public List<Worksheet> getAllWorksheetByCompleteStatus();

  @Query(value="Select DISTINCT worksheet From Worksheet worksheet Where worksheet.status = 'Passed Review'")
  public List<Worksheet> getAllWorksheetByPassedReviewStatus();

  @Query(value="Select DISTINCT worksheet From Worksheet worksheet Where worksheet.status ='Declined IN Review' or worksheet.status='Declined IN QC'")
  public List<Worksheet> getAllWorksheetByDeclinedStatus();

public Worksheet findByWorksheetID(long worksheetID);
}
