package com.internal.service.somruinternal.repository;
import com.internal.service.somruinternal.model.Barcode;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BarcodeRepository extends JpaRepository<Barcode, Long>{

	Barcode findByDbid(Long drugId);


}
