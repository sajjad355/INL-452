package com.internal.service.somruinternal.repository2;

import org.springframework.data.jpa.repository.JpaRepository;

import com.internal.service.somruinternal.model2.FolderCustodianTransfer;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FolderCustodianTransferRepository extends JpaRepository<FolderCustodianTransfer, Long> {

    FolderCustodianTransfer findByDbid(Long dbid);
    
    @Query(value = "SELECT transfer FROM FolderCustodianTransfer transfer where " +
                   "  transfer.requestStatus = 'accepted' AND transfer.sender.userId = :userId")  
    public List<FolderCustodianTransfer> getMyAcceptedFolderTransfersPageable( 
            @Param("userId") long userId,
            Pageable pageable);
    
    @Query(value = "SELECT transfer FROM FolderCustodianTransfer transfer where " +
                   "  transfer.requestStatus = 'accepted' AND transfer.sender.userId = :userId")  
    public List<FolderCustodianTransfer> getMyAcceptedFolderTransfersAll( 
            @Param("userId") long userId);
    
    @Query(value = "SELECT transfer FROM FolderCustodianTransfer transfer where " +
                   " transfer.requestStatus = 'requested' AND transfer.receiver.userId = :userId") 
    public List<FolderCustodianTransfer> getMyRequestedFolderTransfersPageable( 
            @Param("userId") long userId,
            Pageable pageable);
    
    @Query(value = "SELECT transfer FROM FolderCustodianTransfer transfer where " +
                   " transfer.requestStatus = 'requested' AND transfer.receiver.userId = :userId") 
    public List<FolderCustodianTransfer> getMyRequestedFolderTransfersAll( 
            @Param("userId") long userId);
    
    
    @Query(value = "SELECT transfer FROM FolderCustodianTransfer transfer where " +
                   " transfer.sender.userId = :userId")
    public List<FolderCustodianTransfer> getFolderTransfersBySender(@Param("userId") long userId);
    
}
