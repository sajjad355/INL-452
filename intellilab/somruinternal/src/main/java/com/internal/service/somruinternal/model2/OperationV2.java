package com.internal.service.somruinternal.model2;



import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "il_operation")
public class OperationV2 {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long operationId;

  @Column(nullable = false,length=100)
  private String operationName;



  public OperationV2() { super(); }

  /**
   * @return the operationId
   */
  public long getOperationId() { return operationId; }

  /**
   * @param operationId the operationId to set
   */
  public void setOperationId(long operationId) { this.operationId = operationId; }



  /**
   * @return the operation's name
   */
  public String getOperationName() {
    return operationName;
  }

  /**
   * @param operationName the operation name to set
   */
  public void setOperationName(String operationName) {
    this.operationName = operationName;
  }
}
