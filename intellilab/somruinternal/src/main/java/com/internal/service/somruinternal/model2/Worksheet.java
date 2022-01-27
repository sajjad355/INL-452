package com.internal.service.somruinternal.model2;

import org.apache.commons.lang3.builder.DiffBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "worksheetList_part_1")
@SecondaryTable(name = "worksheetList_part_2")
@EntityListeners(AuditingEntityListener.class)
public class Worksheet {

	public Worksheet() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long worksheetID;
	@Column(nullable = true, length = 100)
	private int templateCategory;

	@Column(table = "worksheetList_part_2", nullable = true, length = 500)
	private String reviewComment;

	@Column(table = "worksheetList_part_2", nullable = true, length = 500)
	private String qcComment;

	// ============================= Section One ================================
	@Column(nullable = true, length = 100)
	private long assayNumber;
	@Column(nullable = true, length = 100)
	private String scientist;
	@Temporal(TemporalType.DATE)
	@Column(nullable = true, length = 100)
	private Date date;

	@Column(nullable = true, length = 100)
	private String status;

	@Column(nullable = true, length = 100)
	private String step2_CoatingBuffer;
	@Column(nullable = true, length = 100)
	private String step2_Supplier_1;
	@Column(nullable = true, length = 100)
	private String step2_CoatingBufferLotNumber;

	@Column(nullable = true, length = 100)
	private String step2_CoatingMaterial1;
	@Column(nullable = true, length = 100)
	private String step2_Supplier_2;

	@Column(nullable = true, length = 100)
	private String step2_Concentration_1;
	@Column(nullable = true, length = 100)
	private String step2_CoatingMaterial1LotNumber;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step2_ulCoatingMaterial;

	@Column(nullable = true, length = 100)
	private String step2_ulCoatingBuffer;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step2_ulCoatingMaterial_ulCoatingBuffer;
	@Column(nullable = true, length = 100)
	private boolean step2_CheckedBox_1;
	@Column(nullable = true, length = 100)
	private boolean step2_CheckedBox_2;

	@Column(nullable = true, length = 100)
	private String step2_Time;

	@Column(nullable = true, length = 100)
	private String step2_Item_1;
	@Column(nullable = true, length = 100)
	private String step2_LotNum;

	@Column(nullable = true, length = 100)
	private String step2_ConcentrationAmount;

	@Column(nullable = true, length = 100)
	private String step2_Item_2;
	@Column(nullable = true, length = 100)
	private String step2_LotNum_1;

	@Column(nullable = true, length = 100)
	private String step2_Results_0;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step2_ulCoatingMaterial_0;

	@Column(nullable = true, length = 100)
	private String step2_CoatingMaterial_0;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step2_ulCoatingBuffer_1;

	@Column(nullable = true, length = 100)
	private String step2_CoatingMaterial_2;

	@Column(nullable = true, length = 100)
	private String step2_CoatingMaterial_3;

	@Column(nullable = true, length = 100)
	private String step2_CoatingMaterial_4;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step2_ulCoatingMaterial_1;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step2_ulCoatingMaterial_2;

	@Column(nullable = true, length = 100)
	private String step2_CoatingMaterial_5;

	@Column(nullable = true, length = 100)
	private String step2_CoatingMaterial_6;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step2_ulCoatingBuffer_2;

	@Column(nullable = true, length = 100)
	private String step2_ConcentrationAmount_1;
	@Column(nullable = true, length = 100)
	private String step2_ConcentrationLotNum;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step2_ulCoatingMaterial_3;

	@Column(nullable = true, length = 100)
	private String step2_CoatingMaterial_7;

	@Column(nullable = true, length = 100)
	private String step2_Results;

	@Column(nullable = true, length = 100)
	private String step2_Item_3;
	@Column(nullable = true, length = 100)
	private String step2_Supplier_3;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step2_ulCoatingMaterial_4;

	@Column(nullable = true, length = 100)
	private String step2_CoatingMaterial_8;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step2_ulCoatingBuffer_3;

	@Column(nullable = true, length = 100)
	private String step2_Results_2;

	@Column(nullable = true, length = 100)
	private String step2_Item_9;
	@Column(nullable = true, length = 100)
	private String step2_Supplier_9;

	@Column(nullable = true, length = 100)
	private String step2_Concentration_2;
	@Column(nullable = true, length = 100)
	private String step2_LotNum_2;

	@Column(nullable = true, length = 100)
	private String step2_CoatingMaterial_9;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step2_ulCoatingBuffer_4;

	@Column(nullable = true, length = 100)
	private String step2_Results_3;

	@Column(nullable = true, length = 100)
	private String step2_CoatingMaterial_10;

	@Column(nullable = true, length = 100)
	private String step2_CoatingMaterial_11;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step2_ulCoatingBuffer_5;

	@Column(nullable = true, length = 100)
	private String step2_Results_4;

	@Column(nullable = true, length = 100)
	private String step2_Item_4;
	@Column(nullable = true, length = 100)
	private String step2_Supplier_4;

	@Column(nullable = true, length = 100)
	private String step2_Concentration_3;

	@Column(nullable = true, length = 100)
	private String step2_Item_5;
	@Column(nullable = true, length = 100)
	private String step2_Supplier_5;

	@Column(nullable = true, length = 100)
	private String step2_Concentration_7;
	@Column(nullable = true, length = 100)
	private String step2_LotNum_3;

	@Column(nullable = true, length = 100)
	private String step2_CoatingMaterial_25;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step2_ulCoatingBuffer_14;

	@Column(nullable = true, length = 100)
	private String step2_Results_13;

	@Column(nullable = true, length = 100)
	private String step2_CoatingMaterial_26;

	@Column(nullable = true, length = 100)
	private String step2_CoatingMaterial_27;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step2_CoatingBuffer_15;

	@Column(nullable = true, length = 100)
	private String step2_Results_14;
	@Column(nullable = true, length = 100)
	private String step2_CoatingMaterial_12;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step2_ulCoatingBuffer_6;

	@Column(nullable = true, length = 100)
	private String step2_Results_5;

	@Column(nullable = true, length = 100)
	private String step2_CoatingMaterial_13;

	@Column(nullable = true, length = 100)
	private String step2_CoatingMaterial_14;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step2_CoatingBuffer_7;

	@Column(nullable = true, length = 100)
	private String step2_Results_6;

	@Column(nullable = true, length = 100)
	private String step2_Item_6;
	@Column(nullable = true, length = 100)
	private String step2_Supplier_6;

	@Column(nullable = true, length = 100)
	private String step2_Concentration_4;
	@Column(nullable = true, length = 100)
	private String step2_LotNum_4;

	@Column(nullable = true, length = 100)
	private String step2_CoatingMaterial_15;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step2_ulCoatingBuffer_7;

	@Column(nullable = true, length = 100)
	private String step2_Results_7;
	@Column(nullable = true, length = 100)
	private String step2_CoatingMaterial_16;

	@Column(nullable = true, length = 100)
	private String step2_CoatingMaterial_17;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step2_CoatingBuffer_8;

	@Column(nullable = true, length = 100)
	private String step2_Results_8;

	@Column(nullable = true, length = 100)
	private String step2_Item_7;
	@Column(nullable = true, length = 100)
	private String step2_Supplier_7;

	@Column(nullable = true, length = 100)
	private String step2_Concentration_5;
	@Column(nullable = true, length = 100)
	private String step2_LotNum_7;
	@Column(nullable = true, length = 100)
	private String step4_LotNum_5;

	@Column(nullable = true, length = 100)
	private String step2_CoatingMaterial_18;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step2_CoatingBuffer_10;

	@Column(nullable = true, length = 100)
	private String step2_Results_9;

	@Column(nullable = true, length = 100)
	private String step2_CoatingMaterial_19;

	@Column(nullable = true, length = 100)
	private String step2_CoatingMaterial_20;

	@Column(nullable = true, length = 100)
	private String step2_CoatingBuffer_11;

	@Column(nullable = true, length = 100)
	private String step2_Results_10;

	@Column(nullable = true, length = 100)
	private String step2_Item_8;
	@Column(nullable = true, length = 100)
	private String step2_Supplier_8;

	@Column(nullable = true, length = 100)
	private String step2_LotNum_6;

	@Column(nullable = true, length = 100)
	private String step2_CoatingMaterial_21;

	@Column(nullable = true, length = 100)
	private String step2_Results_11;

	@Column(nullable = true, length = 100)
	private String step2_CoatingMaterial_1;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step2_ulCoatingBuffer_0;

	@Column(nullable = true, length = 100)
	private String step2_addingAmount;

	@Column(nullable = true, length = 100)
	private String step2_Results_1;
	@Column(nullable = true, length = 100)
	private String step2_addingAmount_1;

	@Column(nullable = true, length = 100)
	private String step2_CoatingBuffer_2;

	@Column(nullable = true, length = 100)
	private String step2_CoatingBuffer_3;

	@Column(nullable = true, length = 100)
	private String step2_addingAmount_2;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step2_ulCoatingMaterial_8;

	@Column(nullable = true, length = 100)
	private String step2_addingAmount_8;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step2_ulCoatingBuffer_10;
	@Column(nullable = true, length = 100)
	private String step2_LotNum_5;
	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step2_ulCoatingMaterial_5;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step2_ulCoatingMaterial_6;

	@Column(nullable = true, length = 100)
	private String step2_addingAmount_3;
	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step2_ulCoatingMaterial_7;

	@Column(nullable = true, length = 100)
	private String step2_addingAmount_4;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step2_ulCoatingBuffer_8;

//=================================== END =====================================
	// ============================= Section Three =================================
	@Column(nullable = true, length = 100)
	private String step3_Item_1;
	@Column(nullable = true, length = 100)
	private String step3_Supplier_1;
	@Column(nullable = true, length = 100)
	private String step3_LotNum;
	@Column(nullable = true, length = 100)
	private boolean step3_CheckedBox_1;
	@Column(nullable = true, length = 100)
	private boolean step3_CheckedBox_2;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step3_Time;

//=================================== END =====================================
	// ============================= Section Four ================================
	@Column(nullable = true, length = 100)
	private String step4_Item_1;
	@Column(nullable = true, length = 100)
	private String step4_Supplier_1;
	@Column(nullable = true, length = 100)
	private String step4_LotNum;
	@Column(nullable = true, length = 100)
	private boolean step4_CheckedBox_1;
	@Column(nullable = true, length = 100)
	private boolean step4_CheckedBox_2;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step4_Time;

//=================================== END =====================================
	// ============================= Section Five ==================================
	@Column(nullable = true, length = 100)
	private String step5_Item_1;
	@Column(nullable = true, length = 100)
	private String step5_Supplier_1;
	@Column(nullable = true, length = 100)
	private String step5_LotNum;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step5_CloneId_1;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step5_ulClone_1;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step5_ulClone_2;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step5_ulDiluent;
	@Column(nullable = true, length = 100)
	private boolean step5_CheckedBox_1;
	@Column(nullable = true, length = 100)
	private boolean step5_CheckedBox_2;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step5_Time;

//=================================== END ====================================
	// ============================= Section Six ==================================
	@Column(nullable = true, length = 100)
	private String step6_Item_1;
	@Column(nullable = true, length = 100)
	private String step6_Supplier_1;
	@Column(nullable = true, length = 100)
	private String step6_LotNum;
	@Column(nullable = true, length = 100)
	private String step6_Item_2;
	@Column(nullable = true, length = 100)
	private String step6_Supplier_2;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step6_Concentration_1;
	@Column(nullable = true, length = 100)
	private String step6_LotNum_1;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step6_ulAvastin;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step6_ulSuperblock;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step6_Results;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step6_ulAvastin1;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step6_addingAmount;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step6_ulSuperblock2;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step6_Results_2;
	@Column(nullable = true, length = 100)
	private String step6_Item_3;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step6_Concentration_2;
	@Column(nullable = true, length = 100)
	private String step6_Concentration_LotNum;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step6_ulBiosimilar;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step6_ulSuperblock3;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step6_Results_3;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step6_ulBiosimilar2;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step6_addingAmount_2;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step6_Superblock4;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step6_Results_4;
	@Column(nullable = true, length = 100)
	private String step6_Item_4;
	@Column(nullable = true, length = 100)
	private String step6_Supplier_4;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step6_Concentration_3;
	@Column(nullable = true, length = 100)
	private String step6_LotNum_2;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step6_ulStandard;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step6_ulSuperblock5;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step6_Results_5;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step6_ulStandard2;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step6_addingAmount_3;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step6_ulSuperblock6;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step6_Results_6;
	@Column(nullable = true, length = 100)
	private String step6_Supplier_3;
	@Column(nullable = true, length = 100)
	private boolean step6_CheckedBox_1;
	@Column(nullable = true, length = 100)
	private boolean step6_CheckedBox_2;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step6_Time;
	@Column(nullable = true, length = 100)
	private String step6_Concentration;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step6_ulAvastin_1;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step6_ulSuperblock_1;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step6_Results_1;

	@Column(nullable = true, length = 100)
	private String step6_LotNum_3;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step6_ulNistmAb;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step6_ulSuperblock_2;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step6_ulNistmAb_2;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step6_addingAmount_1;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step6_ulSuperblock_3;

	@Column(nullable = true, length = 100)
	private String step6_LotNum_4;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step6_ulSuperblock_4;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step6_ulBeavatas;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step6_ulBeavatas_1;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step6_ulSuperblock_5;

//=================================== END =====================================
	// ============================= Section Seven =================================
	@Column(nullable = true, length = 100)
	private String step7_Item_1;
	@Column(nullable = true, length = 100)
	private String step7_Supplier_1;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step7_CoatingMaterial0;
	@Column(nullable = true, length = 100)
	private boolean step7_CheckedBox_1;
	@Column(nullable = true, length = 100)
	private String step7_LotNum;
	@Column(nullable = true, length = 100)
	private String step7_LotNum_1;
	@Column(nullable = true, length = 100)
	private String step7_Item_2;
	@Column(nullable = true, length = 100)
	private String step7_Supplier_2;
	@Column(nullable = true, length = 100)
	private String step7_LotNum_2;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step7_ulConjugate;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step7_ulDiluent;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step7_ul;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step7_Results;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step7_addingAmount;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step7_ulDiluent_1;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step7_Results_1;
	@Column(nullable = true, length = 100)
	private boolean step7_CheckedBox_2;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step7_Concentration_1;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step7_Time;

//=================================== END =====================================
	// ============================= Section Eight ===============================
	@Column(nullable = true, length = 100)
	private String step8_Item_1;
	@Column(nullable = true, length = 100)
	private String step8_Supplier_1;
	@Column(nullable = true, length = 100)
	private String step8_LotNum;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step8_ulConjugate;
	@Column(nullable = true, length = 100)
	private String step8_Item_2;
	@Column(nullable = true, length = 100)
	private String step8_Supplier_2;
	@Column(nullable = true, length = 100)
	private String step8_LotNum_1;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step8_Results;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step8_ulAmount;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step8_ulDiluent;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step8_ulDiluent_1;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step8_Results_1;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step8_addingAmount;
	@Column(nullable = true, length = 100)
	private boolean step8_CheckedBox_1;
	@Column(nullable = true, length = 100)
	private boolean step8_CheckedBox_2;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step8_Time;
	@Column(nullable = true, length = 100)
	private String step8_Concentration_1;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step8_ulDectection;

//=================================== END =====================================
	// ============================= Section Nine ================================
	@Column(nullable = true, length = 100)
	private String step9_Item_1;
	@Column(nullable = true, length = 100)
	private String step9_Supplier_1;
	@Column(nullable = true, length = 100)
	private String step9_LotNum;
	@Column(nullable = true, length = 100)
	private boolean step9_CheckedBox_1;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step9_mlAmount;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step9_mlAmount_1;
	@Column(nullable = true, length = 100)
	private boolean step9_CheckedBox_2;
	@Column(nullable = true, length = 100)
	private boolean step9_CheckedBox_3;
	@Column(nullable = true, length = 100)
	private boolean step9_CheckedBox_4;

//=================================== END ====================================
	// ============================= Section Ten ==================================
	@Column(nullable = true, length = 100)
	private String step10_Item_1;
	@Column(nullable = true, length = 100)
	private String step10_Item_2;
	@Column(nullable = true, length = 100)
	private String step10_Supplier_1;
	@Column(nullable = true, length = 100)
	private String step10_Supplier_2;
	@Column(nullable = true, length = 100)
	private String step10_LotNum;
	@Column(nullable = true, length = 100)
	private String step10_LotNum_1;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step10_ulDetection;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step10_ulDiluent;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step10_Results;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step10_ulDectection1;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step10_addingAmount;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step10_ulDiluent1;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step10_Results_1;
	@Column(nullable = true, length = 100)
	private boolean step10_CheckedBox_1;
	@Column(nullable = true, length = 100)
	private boolean step10_CheckedBox_2;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step10_Time;

	@Column(nullable = true, length = 100)
	private String step10_ItemName;
	@Column(nullable = true, length = 100)
	private String step10_Supplier;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step10_mlChemicalInput;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step10_mlChemicalInput_2;
	@Column(nullable = true, length = 100)
	private boolean step10_CheckedBox_3;
	@Column(nullable = true, length = 100)
	private boolean step10_CheckedBox_4;

//=================================== END =====================================
	// ============================= Section Eleven ================================
	@Column(nullable = true, length = 100)
	private String step11_Item_1;
	@Column(nullable = true, length = 100)
	private String step11_Supplier_1;
	@Column(nullable = true, length = 100)
	private String step11_LotNum;
	@Column(nullable = true, length = 100)
	private boolean step11_CheckedBox_1;

//=================================== END =====================================
	// ============================= Section Twelve ================================
	@Column(nullable = true, length = 100)
	private String step12_Item_1;
	@Column(nullable = true, length = 100)
	private String step12_Supplier_1;
	@Column(nullable = true, length = 100)
	private String step12_LotNum;

	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step12_Time;
	@Column(nullable = true, length = 100)
	private boolean step12_CheckedBox_1;
	@Column(nullable = true, length = 100)
	private boolean step12_CheckedBox_2;
	@Column(table = "worksheetList_part_2", nullable = true, length = 100)
	private String step12_TimeInput;

//=================================== END =====================================
	// ============================= Section Thirteen ==============================
	@Column(nullable = true, length = 100)
	private String step13_Item_1;
	@Column(nullable = true, length = 100)
	private String step13_Supplier_1;
	@Column(nullable = true, length = 100)
	private String step13_LotNum;
	@Column(nullable = true, length = 100)
	private boolean step13_CheckedBox_1;
	@Column(nullable = true, length = 100)
	private boolean step13_CheckedBox_2;

	// =================================== END =====================================
	// =========================All Getter Method ==================================
	public long getWorksheetID() {
		return worksheetID;
	}

	public void setWorksheetID(long worksheetID) {
		this.worksheetID = worksheetID;
	}

	public int getTemplateCategory() {
		return templateCategory;
	}

	public void setTemplateCategory(int templateCategory) {
		this.templateCategory = templateCategory;
	}

	public long getAssayNumber() {
		return assayNumber;
	}

	public void setAssayNumber(long assayNumber) {
		this.assayNumber = assayNumber;
	}

	public String getScientist() {
		return scientist;
	}

	public void setScientist(String scientist) {
		this.scientist = scientist;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStep2_CoatingBuffer() {
		return step2_CoatingBuffer;
	}

	public void setStep2_CoatingBuffer(String step2_CoatingBuffer) {
		this.step2_CoatingBuffer = step2_CoatingBuffer;
	}

	public String getStep2_Supplier_1() {
		return step2_Supplier_1;
	}

	public void setStep2_Supplier_1(String step2_Supplier_1) {
		this.step2_Supplier_1 = step2_Supplier_1;
	}

	public String getStep2_CoatingBufferLotNumber() {
		return step2_CoatingBufferLotNumber;
	}

	public void setStep2_CoatingBufferLotNumber(String step2_CoatingBufferLotNumber) {
		this.step2_CoatingBufferLotNumber = step2_CoatingBufferLotNumber;
	}

	public String getStep2_CoatingMaterial1() {
		return step2_CoatingMaterial1;
	}

	public void setStep2_CoatingMaterial1(String step2_CoatingMaterial1) {
		this.step2_CoatingMaterial1 = step2_CoatingMaterial1;
	}

	public String getStep2_Supplier_2() {
		return step2_Supplier_2;
	}

	public void setStep2_Supplier_2(String step2_Supplier_2) {
		this.step2_Supplier_2 = step2_Supplier_2;
	}

	public String getStep2_Concentration_1() {
		return step2_Concentration_1;
	}

	public void setStep2_Concentration_1(String step2_Concentration_1) {
		this.step2_Concentration_1 = step2_Concentration_1;
	}

	public String getStep2_CoatingMaterial1LotNumber() {
		return step2_CoatingMaterial1LotNumber;
	}

	public void setStep2_CoatingMaterial1LotNumber(String step2_CoatingMaterial1LotNumber) {
		this.step2_CoatingMaterial1LotNumber = step2_CoatingMaterial1LotNumber;
	}

	public String getStep2_ulCoatingMaterial() {
		return step2_ulCoatingMaterial;
	}

	public void setStep2_ulCoatingMaterial(String step2_ulCoatingMaterial) {
		this.step2_ulCoatingMaterial = step2_ulCoatingMaterial;
	}

	public String getStep2_ulCoatingBuffer() {
		return step2_ulCoatingBuffer;
	}

	public void setStep2_ulCoatingBuffer(String step2_ulCoatingBuffer) {
		this.step2_ulCoatingBuffer = step2_ulCoatingBuffer;
	}

	public String getStep2_ulCoatingMaterial_ulCoatingBuffer() {
		return step2_ulCoatingMaterial_ulCoatingBuffer;
	}

	public void setStep2_ulCoatingMaterial_ulCoatingBuffer(String step2_ulCoatingMaterial_ulCoatingBuffer) {
		this.step2_ulCoatingMaterial_ulCoatingBuffer = step2_ulCoatingMaterial_ulCoatingBuffer;
	}

	public boolean isStep2_CheckedBox_1() {
		return step2_CheckedBox_1;
	}

	public void setStep2_CheckedBox_1(boolean step2_CheckedBox_1) {
		this.step2_CheckedBox_1 = step2_CheckedBox_1;
	}

	public boolean isStep2_CheckedBox_2() {
		return step2_CheckedBox_2;
	}

	public void setStep2_CheckedBox_2(boolean step2_CheckedBox_2) {
		this.step2_CheckedBox_2 = step2_CheckedBox_2;
	}

	public String getStep2_Item_1() {
		return step2_Item_1;
	}

	public void setStep2_Item_1(String step2_Item_1) {
		this.step2_Item_1 = step2_Item_1;
	}

	public String getStep2_LotNum() {
		return step2_LotNum;
	}

	public void setStep2_LotNum(String step2_LotNum) {
		this.step2_LotNum = step2_LotNum;
	}

	public String getStep2_ConcentrationAmount() {
		return step2_ConcentrationAmount;
	}

	public void setStep2_ConcentrationAmount(String step2_ConcentrationAmount) {
		this.step2_ConcentrationAmount = step2_ConcentrationAmount;
	}

	public String getStep2_Item_2() {
		return step2_Item_2;
	}

	public void setStep2_Item_2(String step2_Item_2) {
		this.step2_Item_2 = step2_Item_2;
	}

	public String getStep2_LotNum_1() {
		return step2_LotNum_1;
	}

	public void setStep2_LotNum_1(String step2_LotNum_1) {
		this.step2_LotNum_1 = step2_LotNum_1;
	}

	public String getStep2_Results_0() {
		return step2_Results_0;
	}

	public void setStep2_Results_0(String step2_Results_0) {
		this.step2_Results_0 = step2_Results_0;
	}

	public String getStep2_ulCoatingMaterial_0() {
		return step2_ulCoatingMaterial_0;
	}

	public void setStep2_ulCoatingMaterial_0(String step2_ulCoatingMaterial_0) {
		this.step2_ulCoatingMaterial_0 = step2_ulCoatingMaterial_0;
	}

	public String getStep2_CoatingMaterial_0() {
		return step2_CoatingMaterial_0;
	}

	public void setStep2_CoatingMaterial_0(String step2_CoatingMaterial_0) {
		this.step2_CoatingMaterial_0 = step2_CoatingMaterial_0;
	}

	public String getStep2_ulCoatingBuffer_1() {
		return step2_ulCoatingBuffer_1;
	}

	public void setStep2_ulCoatingBuffer_1(String step2_ulCoatingBuffer_1) {
		this.step2_ulCoatingBuffer_1 = step2_ulCoatingBuffer_1;
	}

	public String getStep2_CoatingMaterial_2() {
		return step2_CoatingMaterial_2;
	}

	public void setStep2_CoatingMaterial_2(String step2_CoatingMaterial_2) {
		this.step2_CoatingMaterial_2 = step2_CoatingMaterial_2;
	}

	public String getStep2_CoatingMaterial_3() {
		return step2_CoatingMaterial_3;
	}

	public void setStep2_CoatingMaterial_3(String step2_CoatingMaterial_3) {
		this.step2_CoatingMaterial_3 = step2_CoatingMaterial_3;
	}

	public String getStep2_CoatingMaterial_4() {
		return step2_CoatingMaterial_4;
	}

	public void setStep2_CoatingMaterial_4(String step2_CoatingMaterial_4) {
		this.step2_CoatingMaterial_4 = step2_CoatingMaterial_4;
	}

	public String getStep2_ulCoatingMaterial_1() {
		return step2_ulCoatingMaterial_1;
	}

	public void setStep2_ulCoatingMaterial_1(String step2_ulCoatingMaterial_1) {
		this.step2_ulCoatingMaterial_1 = step2_ulCoatingMaterial_1;
	}

	public String getStep2_ulCoatingMaterial_2() {
		return step2_ulCoatingMaterial_2;
	}

	public void setStep2_ulCoatingMaterial_2(String step2_ulCoatingMaterial_2) {
		this.step2_ulCoatingMaterial_2 = step2_ulCoatingMaterial_2;
	}

	public String getStep2_CoatingMaterial_5() {
		return step2_CoatingMaterial_5;
	}

	public void setStep2_CoatingMaterial_5(String step2_CoatingMaterial_5) {
		this.step2_CoatingMaterial_5 = step2_CoatingMaterial_5;
	}

	public String getStep2_CoatingMaterial_6() {
		return step2_CoatingMaterial_6;
	}

	public void setStep2_CoatingMaterial_6(String step2_CoatingMaterial_6) {
		this.step2_CoatingMaterial_6 = step2_CoatingMaterial_6;
	}

	public String getStep2_ulCoatingBuffer_2() {
		return step2_ulCoatingBuffer_2;
	}

	public void setStep2_ulCoatingBuffer_2(String step2_ulCoatingBuffer_2) {
		this.step2_ulCoatingBuffer_2 = step2_ulCoatingBuffer_2;
	}

	public String getStep2_ConcentrationAmount_1() {
		return step2_ConcentrationAmount_1;
	}

	public void setStep2_ConcentrationAmount_1(String step2_ConcentrationAmount_1) {
		this.step2_ConcentrationAmount_1 = step2_ConcentrationAmount_1;
	}

	public String getStep2_ConcentrationLotNum() {
		return step2_ConcentrationLotNum;
	}

	public void setStep2_ConcentrationLotNum(String step2_ConcentrationLotNum) {
		this.step2_ConcentrationLotNum = step2_ConcentrationLotNum;
	}

	public String getStep2_ulCoatingMaterial_3() {
		return step2_ulCoatingMaterial_3;
	}

	public void setStep2_ulCoatingMaterial_3(String step2_ulCoatingMaterial_3) {
		this.step2_ulCoatingMaterial_3 = step2_ulCoatingMaterial_3;
	}

	public String getStep2_CoatingMaterial_7() {
		return step2_CoatingMaterial_7;
	}

	public void setStep2_CoatingMaterial_7(String step2_CoatingMaterial_7) {
		this.step2_CoatingMaterial_7 = step2_CoatingMaterial_7;
	}

	public String getStep2_Results() {
		return step2_Results;
	}

	public void setStep2_Results(String step2_Results) {
		this.step2_Results = step2_Results;
	}

	public String getStep2_Item_3() {
		return step2_Item_3;
	}

	public void setStep2_Item_3(String step2_Item_3) {
		this.step2_Item_3 = step2_Item_3;
	}

	public String getStep2_Supplier_3() {
		return step2_Supplier_3;
	}

	public void setStep2_Supplier_3(String step2_Supplier_3) {
		this.step2_Supplier_3 = step2_Supplier_3;
	}

	public String getStep2_ulCoatingMaterial_4() {
		return step2_ulCoatingMaterial_4;
	}

	public void setStep2_ulCoatingMaterial_4(String step2_ulCoatingMaterial_4) {
		this.step2_ulCoatingMaterial_4 = step2_ulCoatingMaterial_4;
	}

	public String getStep2_CoatingMaterial_8() {
		return step2_CoatingMaterial_8;
	}

	public void setStep2_CoatingMaterial_8(String step2_CoatingMaterial_8) {
		this.step2_CoatingMaterial_8 = step2_CoatingMaterial_8;
	}

	public String getStep2_ulCoatingBuffer_3() {
		return step2_ulCoatingBuffer_3;
	}

	public void setStep2_ulCoatingBuffer_3(String step2_ulCoatingBuffer_3) {
		this.step2_ulCoatingBuffer_3 = step2_ulCoatingBuffer_3;
	}

	public String getStep2_Results_2() {
		return step2_Results_2;
	}

	public void setStep2_Results_2(String step2_Results_2) {
		this.step2_Results_2 = step2_Results_2;
	}

	public String getStep2_Item_9() {
		return step2_Item_9;
	}

	public void setStep2_Item_9(String step2_Item_9) {
		this.step2_Item_9 = step2_Item_9;
	}

	public String getStep2_Supplier_9() {
		return step2_Supplier_9;
	}

	public void setStep2_Supplier_9(String step2_Supplier_9) {
		this.step2_Supplier_9 = step2_Supplier_9;
	}

	public String getStep2_Concentration_2() {
		return step2_Concentration_2;
	}

	public void setStep2_Concentration_2(String step2_Concentration_2) {
		this.step2_Concentration_2 = step2_Concentration_2;
	}

	public String getStep2_LotNum_2() {
		return step2_LotNum_2;
	}

	public void setStep2_LotNum_2(String step2_LotNum_2) {
		this.step2_LotNum_2 = step2_LotNum_2;
	}

	public String getStep2_CoatingMaterial_9() {
		return step2_CoatingMaterial_9;
	}

	public void setStep2_CoatingMaterial_9(String step2_CoatingMaterial_9) {
		this.step2_CoatingMaterial_9 = step2_CoatingMaterial_9;
	}

	public String getStep2_ulCoatingBuffer_4() {
		return step2_ulCoatingBuffer_4;
	}

	public void setStep2_ulCoatingBuffer_4(String step2_ulCoatingBuffer_4) {
		this.step2_ulCoatingBuffer_4 = step2_ulCoatingBuffer_4;
	}

	public String getStep2_Results_3() {
		return step2_Results_3;
	}

	public void setStep2_Results_3(String step2_Results_3) {
		this.step2_Results_3 = step2_Results_3;
	}

	public String getStep2_CoatingMaterial_10() {
		return step2_CoatingMaterial_10;
	}

	public void setStep2_CoatingMaterial_10(String step2_CoatingMaterial_10) {
		this.step2_CoatingMaterial_10 = step2_CoatingMaterial_10;
	}

	public String getStep2_CoatingMaterial_11() {
		return step2_CoatingMaterial_11;
	}

	public void setStep2_CoatingMaterial_11(String step2_CoatingMaterial_11) {
		this.step2_CoatingMaterial_11 = step2_CoatingMaterial_11;
	}

	public String getStep2_ulCoatingBuffer_5() {
		return step2_ulCoatingBuffer_5;
	}

	public void setStep2_ulCoatingBuffer_5(String step2_ulCoatingBuffer_5) {
		this.step2_ulCoatingBuffer_5 = step2_ulCoatingBuffer_5;
	}

	public String getStep2_Results_4() {
		return step2_Results_4;
	}

	public void setStep2_Results_4(String step2_Results_4) {
		this.step2_Results_4 = step2_Results_4;
	}

	public String getStep2_Item_4() {
		return step2_Item_4;
	}

	public void setStep2_Item_4(String step2_Item_4) {
		this.step2_Item_4 = step2_Item_4;
	}

	public String getStep2_Supplier_4() {
		return step2_Supplier_4;
	}

	public void setStep2_Supplier_4(String step2_Supplier_4) {
		this.step2_Supplier_4 = step2_Supplier_4;
	}

	public String getStep2_Concentration_3() {
		return step2_Concentration_3;
	}

	public void setStep2_Concentration_3(String step2_Concentration_3) {
		this.step2_Concentration_3 = step2_Concentration_3;
	}

	public String getStep2_Item_5() {
		return step2_Item_5;
	}

	public void setStep2_Item_5(String step2_Item_5) {
		this.step2_Item_5 = step2_Item_5;
	}

	public String getStep2_Supplier_5() {
		return step2_Supplier_5;
	}

	public void setStep2_Supplier_5(String step2_Supplier_5) {
		this.step2_Supplier_5 = step2_Supplier_5;
	}

	public String getStep2_Concentration_7() {
		return step2_Concentration_7;
	}

	public void setStep2_Concentration_7(String step2_Concentration_7) {
		this.step2_Concentration_7 = step2_Concentration_7;
	}

	public String getStep2_LotNum_3() {
		return step2_LotNum_3;
	}

	public void setStep2_LotNum_3(String step2_LotNum_3) {
		this.step2_LotNum_3 = step2_LotNum_3;
	}

	public String getStep2_CoatingMaterial_25() {
		return step2_CoatingMaterial_25;
	}

	public void setStep2_CoatingMaterial_25(String step2_CoatingMaterial_25) {
		this.step2_CoatingMaterial_25 = step2_CoatingMaterial_25;
	}

	public String getStep2_ulCoatingBuffer_14() {
		return step2_ulCoatingBuffer_14;
	}

	public void setStep2_ulCoatingBuffer_14(String step2_ulCoatingBuffer_14) {
		this.step2_ulCoatingBuffer_14 = step2_ulCoatingBuffer_14;
	}

	public String getStep2_Results_13() {
		return step2_Results_13;
	}

	public void setStep2_Results_13(String step2_Results_13) {
		this.step2_Results_13 = step2_Results_13;
	}

	public String getStep2_CoatingMaterial_26() {
		return step2_CoatingMaterial_26;
	}

	public void setStep2_CoatingMaterial_26(String step2_CoatingMaterial_26) {
		this.step2_CoatingMaterial_26 = step2_CoatingMaterial_26;
	}

	public String getStep2_CoatingMaterial_27() {
		return step2_CoatingMaterial_27;
	}

	public void setStep2_CoatingMaterial_27(String step2_CoatingMaterial_27) {
		this.step2_CoatingMaterial_27 = step2_CoatingMaterial_27;
	}

	public String getStep2_CoatingBuffer_15() {
		return step2_CoatingBuffer_15;
	}

	public void setStep2_CoatingBuffer_15(String step2_CoatingBuffer_15) {
		this.step2_CoatingBuffer_15 = step2_CoatingBuffer_15;
	}

	public String getStep2_Results_14() {
		return step2_Results_14;
	}

	public void setStep2_Results_14(String step2_Results_14) {
		this.step2_Results_14 = step2_Results_14;
	}

	public String getStep2_CoatingMaterial_12() {
		return step2_CoatingMaterial_12;
	}

	public void setStep2_CoatingMaterial_12(String step2_CoatingMaterial_12) {
		this.step2_CoatingMaterial_12 = step2_CoatingMaterial_12;
	}

	public String getStep2_ulCoatingBuffer_6() {
		return step2_ulCoatingBuffer_6;
	}

	public void setStep2_ulCoatingBuffer_6(String step2_ulCoatingBuffer_6) {
		this.step2_ulCoatingBuffer_6 = step2_ulCoatingBuffer_6;
	}

	public String getStep2_Results_5() {
		return step2_Results_5;
	}

	public void setStep2_Results_5(String step2_Results_5) {
		this.step2_Results_5 = step2_Results_5;
	}

	public String getStep2_CoatingMaterial_13() {
		return step2_CoatingMaterial_13;
	}

	public void setStep2_CoatingMaterial_13(String step2_CoatingMaterial_13) {
		this.step2_CoatingMaterial_13 = step2_CoatingMaterial_13;
	}

	public String getStep2_CoatingMaterial_14() {
		return step2_CoatingMaterial_14;
	}

	public void setStep2_CoatingMaterial_14(String step2_CoatingMaterial_14) {
		this.step2_CoatingMaterial_14 = step2_CoatingMaterial_14;
	}

	public String getStep2_CoatingBuffer_7() {
		return step2_CoatingBuffer_7;
	}

	public void setStep2_CoatingBuffer_7(String step2_CoatingBuffer_7) {
		this.step2_CoatingBuffer_7 = step2_CoatingBuffer_7;
	}

	public String getStep2_Results_6() {
		return step2_Results_6;
	}

	public void setStep2_Results_6(String step2_Results_6) {
		this.step2_Results_6 = step2_Results_6;
	}

	public String getStep2_Item_6() {
		return step2_Item_6;
	}

	public void setStep2_Item_6(String step2_Item_6) {
		this.step2_Item_6 = step2_Item_6;
	}

	public String getStep2_Supplier_6() {
		return step2_Supplier_6;
	}

	public void setStep2_Supplier_6(String step2_Supplier_6) {
		this.step2_Supplier_6 = step2_Supplier_6;
	}

	public String getStep2_Concentration_4() {
		return step2_Concentration_4;
	}

	public void setStep2_Concentration_4(String step2_Concentration_4) {
		this.step2_Concentration_4 = step2_Concentration_4;
	}

	public String getStep2_LotNum_4() {
		return step2_LotNum_4;
	}

	public void setStep2_LotNum_4(String step2_LotNum_4) {
		this.step2_LotNum_4 = step2_LotNum_4;
	}

	public String getStep2_CoatingMaterial_15() {
		return step2_CoatingMaterial_15;
	}

	public void setStep2_CoatingMaterial_15(String step2_CoatingMaterial_15) {
		this.step2_CoatingMaterial_15 = step2_CoatingMaterial_15;
	}

	public String getStep2_ulCoatingBuffer_7() {
		return step2_ulCoatingBuffer_7;
	}

	public void setStep2_ulCoatingBuffer_7(String step2_ulCoatingBuffer_7) {
		this.step2_ulCoatingBuffer_7 = step2_ulCoatingBuffer_7;
	}

	public String getStep2_Results_7() {
		return step2_Results_7;
	}

	public void setStep2_Results_7(String step2_Results_7) {
		this.step2_Results_7 = step2_Results_7;
	}

	public String getStep2_CoatingMaterial_16() {
		return step2_CoatingMaterial_16;
	}

	public void setStep2_CoatingMaterial_16(String step2_CoatingMaterial_16) {
		this.step2_CoatingMaterial_16 = step2_CoatingMaterial_16;
	}

	public String getStep2_CoatingMaterial_17() {
		return step2_CoatingMaterial_17;
	}

	public void setStep2_CoatingMaterial_17(String step2_CoatingMaterial_17) {
		this.step2_CoatingMaterial_17 = step2_CoatingMaterial_17;
	}

	public String getStep2_CoatingBuffer_8() {
		return step2_CoatingBuffer_8;
	}

	public void setStep2_CoatingBuffer_8(String step2_CoatingBuffer_8) {
		this.step2_CoatingBuffer_8 = step2_CoatingBuffer_8;
	}

	public String getStep2_Results_8() {
		return step2_Results_8;
	}

	public void setStep2_Results_8(String step2_Results_8) {
		this.step2_Results_8 = step2_Results_8;
	}

	public String getStep2_Item_7() {
		return step2_Item_7;
	}

	public void setStep2_Item_7(String step2_Item_7) {
		this.step2_Item_7 = step2_Item_7;
	}

	public String getStep2_Supplier_7() {
		return step2_Supplier_7;
	}

	public void setStep2_Supplier_7(String step2_Supplier_7) {
		this.step2_Supplier_7 = step2_Supplier_7;
	}

	public String getStep2_Concentration_5() {
		return step2_Concentration_5;
	}

	public void setStep2_Concentration_5(String step2_Concentration_5) {
		this.step2_Concentration_5 = step2_Concentration_5;
	}

	public String getStep2_LotNum_7() {
		return step2_LotNum_7;
	}

	public void setStep2_LotNum_7(String step2_LotNum_7) {
		this.step2_LotNum_7 = step2_LotNum_7;
	}

	public String getStep4_LotNum_5() {
		return step4_LotNum_5;
	}

	public void setStep4_LotNum_5(String step4_LotNum_5) {
		this.step4_LotNum_5 = step4_LotNum_5;
	}

	public String getStep2_CoatingMaterial_18() {
		return step2_CoatingMaterial_18;
	}

	public void setStep2_CoatingMaterial_18(String step2_CoatingMaterial_18) {
		this.step2_CoatingMaterial_18 = step2_CoatingMaterial_18;
	}

	public String getStep2_CoatingBuffer_10() {
		return step2_CoatingBuffer_10;
	}

	public void setStep2_CoatingBuffer_10(String step2_CoatingBuffer_10) {
		this.step2_CoatingBuffer_10 = step2_CoatingBuffer_10;
	}

	public String getStep2_Results_9() {
		return step2_Results_9;
	}

	public void setStep2_Results_9(String step2_Results_9) {
		this.step2_Results_9 = step2_Results_9;
	}

	public String getStep2_CoatingMaterial_19() {
		return step2_CoatingMaterial_19;
	}

	public void setStep2_CoatingMaterial_19(String step2_CoatingMaterial_19) {
		this.step2_CoatingMaterial_19 = step2_CoatingMaterial_19;
	}

	public String getStep2_CoatingMaterial_20() {
		return step2_CoatingMaterial_20;
	}

	public void setStep2_CoatingMaterial_20(String step2_CoatingMaterial_20) {
		this.step2_CoatingMaterial_20 = step2_CoatingMaterial_20;
	}

	public String getStep2_CoatingBuffer_11() {
		return step2_CoatingBuffer_11;
	}

	public void setStep2_CoatingBuffer_11(String step2_CoatingBuffer_11) {
		this.step2_CoatingBuffer_11 = step2_CoatingBuffer_11;
	}

	public String getStep2_Results_10() {
		return step2_Results_10;
	}

	public void setStep2_Results_10(String step2_Results_10) {
		this.step2_Results_10 = step2_Results_10;
	}

	public String getStep2_Item_8() {
		return step2_Item_8;
	}

	public void setStep2_Item_8(String step2_Item_8) {
		this.step2_Item_8 = step2_Item_8;
	}

	public String getStep2_Supplier_8() {
		return step2_Supplier_8;
	}

	public void setStep2_Supplier_8(String step2_Supplier_8) {
		this.step2_Supplier_8 = step2_Supplier_8;
	}

	/*
	 * public String getStep2_Concentration_6() { return step2_Concentration_6; }
	 * 
	 * public void setStep2_Concentration_6(String step2_Concentration_6) {
	 * this.step2_Concentration_6 = step2_Concentration_6; }
	 */
	public String getStep2_LotNum_6() {
		return step2_LotNum_6;
	}

	public void setStep2_LotNum_6(String step2_LotNum_6) {
		this.step2_LotNum_6 = step2_LotNum_6;
	}

	public String getStep2_CoatingMaterial_21() {
		return step2_CoatingMaterial_21;
	}

	public void setStep2_CoatingMaterial_21(String step2_CoatingMaterial_21) {
		this.step2_CoatingMaterial_21 = step2_CoatingMaterial_21;
	}

	/*
	 * public String getStep2_CoatingBuffer_12() { return step2_CoatingBuffer_12; }
	 * 
	 * public void setStep2_CoatingBuffer_12(String step2_CoatingBuffer_12) {
	 * this.step2_CoatingBuffer_12 = step2_CoatingBuffer_12; }
	 */
	public String getStep2_Results_11() {
		return step2_Results_11;
	}

	public void setStep2_Results_11(String step2_Results_11) {
		this.step2_Results_11 = step2_Results_11;
	}

	/*
	 * public String getStep2_CoatingMaterial_22() { return
	 * step2_CoatingMaterial_22; }
	 * 
	 * public void setStep2_CoatingMaterial_22(String step2_CoatingMaterial_22) {
	 * this.step2_CoatingMaterial_22 = step2_CoatingMaterial_22; }
	 * 
	 * public String getStep2_CoatingMaterial_23() { return
	 * step2_CoatingMaterial_23; }
	 * 
	 * public void setStep2_CoatingMaterial_23(String step2_CoatingMaterial_23) {
	 * this.step2_CoatingMaterial_23 = step2_CoatingMaterial_23; }
	 * 
	 * public String getStep2_CoatingMaterial_24() { return
	 * step2_CoatingMaterial_24; }
	 * 
	 * public void setStep2_CoatingMaterial_24(String step2_CoatingMaterial_24) {
	 * this.step2_CoatingMaterial_24 = step2_CoatingMaterial_24; }
	 * 
	 * public String getStep2_CoatingBuffer_13() { return step2_CoatingBuffer_13; }
	 * 
	 * public void setStep2_CoatingBuffer_13(String step2_CoatingBuffer_13) {
	 * this.step2_CoatingBuffer_13 = step2_CoatingBuffer_13; }
	 * 
	 * public String getStep2_Results_12() { return step2_Results_12; }
	 * 
	 * public void setStep2_Results_12(String step2_Results_12) {
	 * this.step2_Results_12 = step2_Results_12; }
	 */
	public String getStep2_Time() {
		return step2_Time;
	}

	public void setStep2_Time(String step2_Time) {
		this.step2_Time = step2_Time;
	}

	public String getStep2_CoatingMaterial_1() {
		return step2_CoatingMaterial_1;
	}

	public void setStep2_CoatingMaterial_1(String step2_CoatingMaterial_1) {
		this.step2_CoatingMaterial_1 = step2_CoatingMaterial_1;
	}

	public String getStep2_ulCoatingBuffer_0() {
		return step2_ulCoatingBuffer_0;
	}

	public void setStep2_ulCoatingBuffer_0(String step2_ulCoatingBuffer_0) {
		this.step2_ulCoatingBuffer_0 = step2_ulCoatingBuffer_0;
	}

	public String getStep2_addingAmount() {
		return step2_addingAmount;
	}

	public void setStep2_addingAmount(String step2_addingAmount) {
		this.step2_addingAmount = step2_addingAmount;
	}

	public String getStep2_Results_1() {
		return step2_Results_1;
	}

	public void setStep2_Results_1(String step2_Results_1) {
		this.step2_Results_1 = step2_Results_1;
	}

	public String getStep2_addingAmount_1() {
		return step2_addingAmount_1;
	}

	public void setStep2_addingAmount_1(String step2_addingAmount_1) {
		this.step2_addingAmount_1 = step2_addingAmount_1;
	}

	public String getStep2_CoatingBuffer_2() {
		return step2_CoatingBuffer_2;
	}

	public void setStep2_CoatingBuffer_2(String step2_CoatingBuffer_2) {
		this.step2_CoatingBuffer_2 = step2_CoatingBuffer_2;
	}

	public String getStep2_CoatingBuffer_3() {
		return step2_CoatingBuffer_3;
	}

	public void setStep2_CoatingBuffer_3(String step2_CoatingBuffer_3) {
		this.step2_CoatingBuffer_3 = step2_CoatingBuffer_3;
	}

	public String getStep2_addingAmount_2() {
		return step2_addingAmount_2;
	}

	public void setStep2_addingAmount_2(String step2_addingAmount_2) {
		this.step2_addingAmount_2 = step2_addingAmount_2;
	}

	public String getStep2_ulCoatingMaterial_8() {
		return step2_ulCoatingMaterial_8;
	}

	public void setStep2_ulCoatingMaterial_8(String step2_ulCoatingMaterial_8) {
		this.step2_ulCoatingMaterial_8 = step2_ulCoatingMaterial_8;
	}

	public String getStep2_addingAmount_8() {
		return step2_addingAmount_8;
	}

	public void setStep2_addingAmount_8(String step2_addingAmount_8) {
		this.step2_addingAmount_8 = step2_addingAmount_8;
	}

	public String getStep2_ulCoatingBuffer_10() {
		return step2_ulCoatingBuffer_10;
	}

	public void setStep2_ulCoatingBuffer_10(String step2_ulCoatingBuffer_10) {
		this.step2_ulCoatingBuffer_10 = step2_ulCoatingBuffer_10;
	}

	public String getStep2_LotNum_5() {
		return step2_LotNum_5;
	}

	public void setStep2_LotNum_5(String step2_LotNum_5) {
		this.step2_LotNum_5 = step2_LotNum_5;
	}

	public String getStep2_ulCoatingMaterial_5() {
		return step2_ulCoatingMaterial_5;
	}

	public void setStep2_ulCoatingMaterial_5(String step2_ulCoatingMaterial_5) {
		this.step2_ulCoatingMaterial_5 = step2_ulCoatingMaterial_5;
	}

	public String getStep2_ulCoatingMaterial_6() {
		return step2_ulCoatingMaterial_6;
	}

	public void setStep2_ulCoatingMaterial_6(String step2_ulCoatingMaterial_6) {
		this.step2_ulCoatingMaterial_6 = step2_ulCoatingMaterial_6;
	}

	public String getStep2_addingAmount_3() {
		return step2_addingAmount_3;
	}

	public void setStep2_addingAmount_3(String step2_addingAmount_3) {
		this.step2_addingAmount_3 = step2_addingAmount_3;
	}

	public String getStep2_ulCoatingMaterial_7() {
		return step2_ulCoatingMaterial_7;
	}

	public void setStep2_ulCoatingMaterial_7(String step2_ulCoatingMaterial_7) {
		this.step2_ulCoatingMaterial_7 = step2_ulCoatingMaterial_7;
	}

	public String getStep2_addingAmount_4() {
		return step2_addingAmount_4;
	}

	public void setStep2_addingAmount_4(String step2_addingAmount_4) {
		this.step2_addingAmount_4 = step2_addingAmount_4;
	}

	public String getStep2_ulCoatingBuffer_8() {
		return step2_ulCoatingBuffer_8;
	}

	public void setStep2_ulCoatingBuffer_8(String step2_ulCoatingBuffer_8) {
		this.step2_ulCoatingBuffer_8 = step2_ulCoatingBuffer_8;
	}

	public String getStep3_Item_1() {
		return step3_Item_1;
	}

	public void setStep3_Item_1(String step3_Item_1) {
		this.step3_Item_1 = step3_Item_1;
	}

	public String getStep3_Supplier_1() {
		return step3_Supplier_1;
	}

	public void setStep3_Supplier_1(String step3_Supplier_1) {
		this.step3_Supplier_1 = step3_Supplier_1;
	}

	public String getStep3_LotNum() {
		return step3_LotNum;
	}

	public void setStep3_LotNum(String step3_LotNum) {
		this.step3_LotNum = step3_LotNum;
	}

	public boolean isStep3_CheckedBox_1() {
		return step3_CheckedBox_1;
	}

	public void setStep3_CheckedBox_1(boolean step3_CheckedBox_1) {
		this.step3_CheckedBox_1 = step3_CheckedBox_1;
	}

	public boolean isStep3_CheckedBox_2() {
		return step3_CheckedBox_2;
	}

	public void setStep3_CheckedBox_2(boolean step3_CheckedBox_2) {
		this.step3_CheckedBox_2 = step3_CheckedBox_2;
	}

	public String getStep3_Time() {
		return step3_Time;
	}

	public void setStep3_Time(String step3_Time) {
		this.step3_Time = step3_Time;
	}

	public String getStep4_Item_1() {
		return step4_Item_1;
	}

	public void setStep4_Item_1(String step4_Item_1) {
		this.step4_Item_1 = step4_Item_1;
	}

	public String getStep4_Supplier_1() {
		return step4_Supplier_1;
	}

	public void setStep4_Supplier_1(String step4_Supplier_1) {
		this.step4_Supplier_1 = step4_Supplier_1;
	}

	public String getStep4_LotNum() {
		return step4_LotNum;
	}

	public void setStep4_LotNum(String step4_LotNum) {
		this.step4_LotNum = step4_LotNum;
	}

	public boolean isStep4_CheckedBox_1() {
		return step4_CheckedBox_1;
	}

	public void setStep4_CheckedBox_1(boolean step4_CheckedBox_1) {
		this.step4_CheckedBox_1 = step4_CheckedBox_1;
	}

	public boolean isStep4_CheckedBox_2() {
		return step4_CheckedBox_2;
	}

	public void setStep4_CheckedBox_2(boolean step4_CheckedBox_2) {
		this.step4_CheckedBox_2 = step4_CheckedBox_2;
	}

	public String getStep4_Time() {
		return step4_Time;
	}

	public void setStep4_Time(String step4_Time) {
		this.step4_Time = step4_Time;
	}

	public String getStep5_Item_1() {
		return step5_Item_1;
	}

	public void setStep5_Item_1(String step5_Item_1) {
		this.step5_Item_1 = step5_Item_1;
	}

	public String getStep5_Supplier_1() {
		return step5_Supplier_1;
	}

	public void setStep5_Supplier_1(String step5_Supplier_1) {
		this.step5_Supplier_1 = step5_Supplier_1;
	}

	public String getStep5_LotNum() {
		return step5_LotNum;
	}

	public void setStep5_LotNum(String step5_LotNum) {
		this.step5_LotNum = step5_LotNum;
	}

	public String getStep5_CloneId_1() {
		return step5_CloneId_1;
	}

	public void setStep5_CloneId_1(String step5_CloneId_1) {
		this.step5_CloneId_1 = step5_CloneId_1;
	}

	public String getStep5_ulClone_1() {
		return step5_ulClone_1;
	}

	public void setStep5_ulClone_1(String step5_ulClone_1) {
		this.step5_ulClone_1 = step5_ulClone_1;
	}

	public String getStep5_ulClone_2() {
		return step5_ulClone_2;
	}

	public void setStep5_ulClone_2(String step5_ulClone_2) {
		this.step5_ulClone_2 = step5_ulClone_2;
	}

	public String getStep5_ulDiluent() {
		return step5_ulDiluent;
	}

	public void setStep5_ulDiluent(String step5_ulDiluent) {
		this.step5_ulDiluent = step5_ulDiluent;
	}

	public boolean isStep5_CheckedBox_1() {
		return step5_CheckedBox_1;
	}

	public void setStep5_CheckedBox_1(boolean step5_CheckedBox_1) {
		this.step5_CheckedBox_1 = step5_CheckedBox_1;
	}

	public boolean isStep5_CheckedBox_2() {
		return step5_CheckedBox_2;
	}

	public void setStep5_CheckedBox_2(boolean step5_CheckedBox_2) {
		this.step5_CheckedBox_2 = step5_CheckedBox_2;
	}

	public String getStep5_Time() {
		return step5_Time;
	}

	public void setStep5_Time(String step5_Time) {
		this.step5_Time = step5_Time;
	}

	public String getStep6_Item_1() {
		return step6_Item_1;
	}

	public void setStep6_Item_1(String step6_Item_1) {
		this.step6_Item_1 = step6_Item_1;
	}

	public String getStep6_Supplier_1() {
		return step6_Supplier_1;
	}

	public void setStep6_Supplier_1(String step6_Supplier_1) {
		this.step6_Supplier_1 = step6_Supplier_1;
	}

	public String getStep6_LotNum() {
		return step6_LotNum;
	}

	public void setStep6_LotNum(String step6_LotNum) {
		this.step6_LotNum = step6_LotNum;
	}

	public String getStep6_Item_2() {
		return step6_Item_2;
	}

	public void setStep6_Item_2(String step6_Item_2) {
		this.step6_Item_2 = step6_Item_2;
	}

	public String getStep6_Supplier_2() {
		return step6_Supplier_2;
	}

	public void setStep6_Supplier_2(String step6_Supplier_2) {
		this.step6_Supplier_2 = step6_Supplier_2;
	}

	public String getStep6_Concentration_1() {
		return step6_Concentration_1;
	}

	public void setStep6_Concentration_1(String step6_Concentration_1) {
		this.step6_Concentration_1 = step6_Concentration_1;
	}

	public String getStep6_LotNum_1() {
		return step6_LotNum_1;
	}

	public void setStep6_LotNum_1(String step6_LotNum_1) {
		this.step6_LotNum_1 = step6_LotNum_1;
	}

	public String getStep6_ulAvastin() {
		return step6_ulAvastin;
	}

	public void setStep6_ulAvastin(String step6_ulAvastin) {
		this.step6_ulAvastin = step6_ulAvastin;
	}

	public String getStep6_ulSuperblock() {
		return step6_ulSuperblock;
	}

	public void setStep6_ulSuperblock(String step6_ulSuperblock) {
		this.step6_ulSuperblock = step6_ulSuperblock;
	}

	public String getStep6_Results() {
		return step6_Results;
	}

	public void setStep6_Results(String step6_Results) {
		this.step6_Results = step6_Results;
	}

	public String getStep6_ulAvastin1() {
		return step6_ulAvastin1;
	}

	public void setStep6_ulAvastin1(String step6_ulAvastin1) {
		this.step6_ulAvastin1 = step6_ulAvastin1;
	}

	public String getStep6_addingAmount() {
		return step6_addingAmount;
	}

	public void setStep6_addingAmount(String step6_addingAmount) {
		this.step6_addingAmount = step6_addingAmount;
	}

	public String getStep6_ulSuperblock2() {
		return step6_ulSuperblock2;
	}

	public void setStep6_ulSuperblock2(String step6_ulSuperblock2) {
		this.step6_ulSuperblock2 = step6_ulSuperblock2;
	}

	public String getStep6_Results_2() {
		return step6_Results_2;
	}

	public void setStep6_Results_2(String step6_Results_2) {
		this.step6_Results_2 = step6_Results_2;
	}

	public String getStep6_Item_3() {
		return step6_Item_3;
	}

	public void setStep6_Item_3(String step6_Item_3) {
		this.step6_Item_3 = step6_Item_3;
	}

	public String getStep6_Concentration_2() {
		return step6_Concentration_2;
	}

	public void setStep6_Concentration_2(String step6_Concentration_2) {
		this.step6_Concentration_2 = step6_Concentration_2;
	}

	public String getStep6_Concentration_LotNum() {
		return step6_Concentration_LotNum;
	}

	public void setStep6_Concentration_LotNum(String step6_Concentration_LotNum) {
		this.step6_Concentration_LotNum = step6_Concentration_LotNum;
	}

	public String getStep6_ulBiosimilar() {
		return step6_ulBiosimilar;
	}

	public void setStep6_ulBiosimilar(String step6_ulBiosimilar) {
		this.step6_ulBiosimilar = step6_ulBiosimilar;
	}

	public String getStep6_ulSuperblock3() {
		return step6_ulSuperblock3;
	}

	public void setStep6_ulSuperblock3(String step6_ulSuperblock3) {
		this.step6_ulSuperblock3 = step6_ulSuperblock3;
	}

	public String getStep6_Results_3() {
		return step6_Results_3;
	}

	public void setStep6_Results_3(String step6_Results_3) {
		this.step6_Results_3 = step6_Results_3;
	}

	public String getStep6_ulBiosimilar2() {
		return step6_ulBiosimilar2;
	}

	public void setStep6_ulBiosimilar2(String step6_ulBiosimilar2) {
		this.step6_ulBiosimilar2 = step6_ulBiosimilar2;
	}

	public String getStep6_addingAmount_2() {
		return step6_addingAmount_2;
	}

	public void setStep6_addingAmount_2(String step6_addingAmount_2) {
		this.step6_addingAmount_2 = step6_addingAmount_2;
	}

	public String getStep6_Superblock4() {
		return step6_Superblock4;
	}

	public void setStep6_Superblock4(String step6_Superblock4) {
		this.step6_Superblock4 = step6_Superblock4;
	}

	public String getStep6_Results_4() {
		return step6_Results_4;
	}

	public void setStep6_Results_4(String step6_Results_4) {
		this.step6_Results_4 = step6_Results_4;
	}

	public String getStep6_Item_4() {
		return step6_Item_4;
	}

	public void setStep6_Item_4(String step6_Item_4) {
		this.step6_Item_4 = step6_Item_4;
	}

	public String getStep6_Supplier_4() {
		return step6_Supplier_4;
	}

	public void setStep6_Supplier_4(String step6_Supplier_4) {
		this.step6_Supplier_4 = step6_Supplier_4;
	}

	public String getStep6_Concentration_3() {
		return step6_Concentration_3;
	}

	public void setStep6_Concentration_3(String step6_Concentration_3) {
		this.step6_Concentration_3 = step6_Concentration_3;
	}

	public String getStep6_LotNum_2() {
		return step6_LotNum_2;
	}

	public void setStep6_LotNum_2(String step6_LotNum_2) {
		this.step6_LotNum_2 = step6_LotNum_2;
	}

	public String getStep6_ulStandard() {
		return step6_ulStandard;
	}

	public void setStep6_ulStandard(String step6_ulStandard) {
		this.step6_ulStandard = step6_ulStandard;
	}

	public String getStep6_ulSuperblock5() {
		return step6_ulSuperblock5;
	}

	public void setStep6_ulSuperblock5(String step6_ulSuperblock5) {
		this.step6_ulSuperblock5 = step6_ulSuperblock5;
	}

	public String getStep6_Results_5() {
		return step6_Results_5;
	}

	public void setStep6_Results_5(String step6_Results_5) {
		this.step6_Results_5 = step6_Results_5;
	}

	public String getStep6_ulStandard2() {
		return step6_ulStandard2;
	}

	public void setStep6_ulStandard2(String step6_ulStandard2) {
		this.step6_ulStandard2 = step6_ulStandard2;
	}

	public String getStep6_addingAmount_3() {
		return step6_addingAmount_3;
	}

	public void setStep6_addingAmount_3(String step6_addingAmount_3) {
		this.step6_addingAmount_3 = step6_addingAmount_3;
	}

	public String getStep6_ulSuperblock6() {
		return step6_ulSuperblock6;
	}

	public void setStep6_ulSuperblock6(String step6_ulSuperblock6) {
		this.step6_ulSuperblock6 = step6_ulSuperblock6;
	}

	public String getStep6_Results_6() {
		return step6_Results_6;
	}

	public void setStep6_Results_6(String step6_Results_6) {
		this.step6_Results_6 = step6_Results_6;
	}

	public String getStep6_Supplier_3() {
		return step6_Supplier_3;
	}

	public void setStep6_Supplier_3(String step6_Supplier_3) {
		this.step6_Supplier_3 = step6_Supplier_3;
	}

	public boolean isStep6_CheckedBox_1() {
		return step6_CheckedBox_1;
	}

	public void setStep6_CheckedBox_1(boolean step6_CheckedBox_1) {
		this.step6_CheckedBox_1 = step6_CheckedBox_1;
	}

	public boolean isStep6_CheckedBox_2() {
		return step6_CheckedBox_2;
	}

	public void setStep6_CheckedBox_2(boolean step6_CheckedBox_2) {
		this.step6_CheckedBox_2 = step6_CheckedBox_2;
	}

	public String getStep6_Concentration() {
		return step6_Concentration;
	}

	public void setStep6_Concentration(String step6_Concentration) {
		this.step6_Concentration = step6_Concentration;
	}

	public String getStep6_ulAvastin_1() {
		return step6_ulAvastin_1;
	}

	public void setStep6_ulAvastin_1(String step6_ulAvastin_1) {
		this.step6_ulAvastin_1 = step6_ulAvastin_1;
	}

	public String getStep6_ulSuperblock_1() {
		return step6_ulSuperblock_1;
	}

	public void setStep6_ulSuperblock_1(String step6_ulSuperblock_1) {
		this.step6_ulSuperblock_1 = step6_ulSuperblock_1;
	}

	public String getStep6_Results_1() {
		return step6_Results_1;
	}

	public void setStep6_Results_1(String step6_Results_1) {
		this.step6_Results_1 = step6_Results_1;
	}

	public String getStep6_LotNum_3() {
		return step6_LotNum_3;
	}

	public void setStep6_LotNum_3(String step6_LotNum_3) {
		this.step6_LotNum_3 = step6_LotNum_3;
	}

	public String getStep6_ulNistmAb() {
		return step6_ulNistmAb;
	}

	public void setStep6_ulNistmAb(String step6_ulNistmAb) {
		this.step6_ulNistmAb = step6_ulNistmAb;
	}

	public String getStep6_ulSuperblock_2() {
		return step6_ulSuperblock_2;
	}

	public void setStep6_ulSuperblock_2(String step6_ulSuperblock_2) {
		this.step6_ulSuperblock_2 = step6_ulSuperblock_2;
	}

	public String getStep6_ulNistmAb_2() {
		return step6_ulNistmAb_2;
	}

	public void setStep6_ulNistmAb_2(String step6_ulNistmAb_2) {
		this.step6_ulNistmAb_2 = step6_ulNistmAb_2;
	}

	public String getStep6_addingAmount_1() {
		return step6_addingAmount_1;
	}

	public void setStep6_addingAmount_1(String step6_addingAmount_1) {
		this.step6_addingAmount_1 = step6_addingAmount_1;
	}

	public String getStep6_ulSuperblock_3() {
		return step6_ulSuperblock_3;
	}

	public void setStep6_ulSuperblock_3(String step6_ulSuperblock_3) {
		this.step6_ulSuperblock_3 = step6_ulSuperblock_3;
	}

	public String getStep6_LotNum_4() {
		return step6_LotNum_4;
	}

	public void setStep6_LotNum_4(String step6_LotNum_4) {
		this.step6_LotNum_4 = step6_LotNum_4;
	}

	public String getStep6_ulSuperblock_4() {
		return step6_ulSuperblock_4;
	}

	public void setStep6_ulSuperblock_4(String step6_ulSuperblock_4) {
		this.step6_ulSuperblock_4 = step6_ulSuperblock_4;
	}

	public String getStep6_ulBeavatas() {
		return step6_ulBeavatas;
	}

	public void setStep6_ulBeavatas(String step6_ulBeavatas) {
		this.step6_ulBeavatas = step6_ulBeavatas;
	}

	public String getStep6_ulBeavatas_1() {
		return step6_ulBeavatas_1;
	}

	public void setStep6_ulBeavatas_1(String step6_ulBeavatas_1) {
		this.step6_ulBeavatas_1 = step6_ulBeavatas_1;
	}

	public String getStep6_ulSuperblock_5() {
		return step6_ulSuperblock_5;
	}

	public void setStep6_ulSuperblock_5(String step6_ulSuperblock_5) {
		this.step6_ulSuperblock_5 = step6_ulSuperblock_5;
	}

	public String getStep6_Time() {
		return step6_Time;
	}

	public void setStep6_Time(String step6_Time) {
		this.step6_Time = step6_Time;
	}

	public String getStep7_Item_1() {
		return step7_Item_1;
	}

	public void setStep7_Item_1(String step7_Item_1) {
		this.step7_Item_1 = step7_Item_1;
	}

	public String getStep7_Supplier_1() {
		return step7_Supplier_1;
	}

	public void setStep7_Supplier_1(String step7_Supplier_1) {
		this.step7_Supplier_1 = step7_Supplier_1;
	}

	public String getStep7_CoatingMaterial0() {
		return step7_CoatingMaterial0;
	}

	public void setStep7_CoatingMaterial0(String step7_CoatingMaterial0) {
		this.step7_CoatingMaterial0 = step7_CoatingMaterial0;
	}

	public boolean isStep7_CheckedBox_1() {
		return step7_CheckedBox_1;
	}

	public void setStep7_CheckedBox_1(boolean step7_CheckedBox_1) {
		this.step7_CheckedBox_1 = step7_CheckedBox_1;
	}

	public String getStep7_LotNum() {
		return step7_LotNum;
	}

	public void setStep7_LotNum(String step7_LotNum) {
		this.step7_LotNum = step7_LotNum;
	}

	public String getStep7_LotNum_1() {
		return step7_LotNum_1;
	}

	public void setStep7_LotNum_1(String step7_LotNum_1) {
		this.step7_LotNum_1 = step7_LotNum_1;
	}

	public String getStep7_Item_2() {
		return step7_Item_2;
	}

	public void setStep7_Item_2(String step7_Item_2) {
		this.step7_Item_2 = step7_Item_2;
	}

	public String getStep7_Supplier_2() {
		return step7_Supplier_2;
	}

	public void setStep7_Supplier_2(String step7_Supplier_2) {
		this.step7_Supplier_2 = step7_Supplier_2;
	}

	public String getStep7_LotNum_2() {
		return step7_LotNum_2;
	}

	public void setStep7_LotNum_2(String step7_LotNum_2) {
		this.step7_LotNum_2 = step7_LotNum_2;
	}

	public String getStep7_ulConjugate() {
		return step7_ulConjugate;
	}

	public void setStep7_ulConjugate(String step7_ulConjugate) {
		this.step7_ulConjugate = step7_ulConjugate;
	}

	public String getStep7_ulDiluent() {
		return step7_ulDiluent;
	}

	public void setStep7_ulDiluent(String step7_ulDiluent) {
		this.step7_ulDiluent = step7_ulDiluent;
	}

	public String getStep7_ul() {
		return step7_ul;
	}

	public void setStep7_ul(String step7_ul) {
		this.step7_ul = step7_ul;
	}

	public String getStep7_Results() {
		return step7_Results;
	}

	public void setStep7_Results(String step7_Results) {
		this.step7_Results = step7_Results;
	}

	public String getStep7_addingAmount() {
		return step7_addingAmount;
	}

	public void setStep7_addingAmount(String step7_addingAmount) {
		this.step7_addingAmount = step7_addingAmount;
	}

	public String getStep7_ulDiluent_1() {
		return step7_ulDiluent_1;
	}

	public void setStep7_ulDiluent_1(String step7_ulDiluent_1) {
		this.step7_ulDiluent_1 = step7_ulDiluent_1;
	}

	public String getStep7_Results_1() {
		return step7_Results_1;
	}

	public void setStep7_Results_1(String step7_Results_1) {
		this.step7_Results_1 = step7_Results_1;
	}

	public boolean isStep7_CheckedBox_2() {
		return step7_CheckedBox_2;
	}

	public void setStep7_CheckedBox_2(boolean step7_CheckedBox_2) {
		this.step7_CheckedBox_2 = step7_CheckedBox_2;
	}

	public String getStep7_Concentration_1() {
		return step7_Concentration_1;
	}

	public void setStep7_Concentration_1(String step7_Concentration_1) {
		this.step7_Concentration_1 = step7_Concentration_1;
	}

	public String getStep8_Item_1() {
		return step8_Item_1;
	}

	public void setStep8_Item_1(String step8_Item_1) {
		this.step8_Item_1 = step8_Item_1;
	}

	public String getStep8_Supplier_1() {
		return step8_Supplier_1;
	}

	public void setStep8_Supplier_1(String step8_Supplier_1) {
		this.step8_Supplier_1 = step8_Supplier_1;
	}

	public String getStep8_LotNum() {
		return step8_LotNum;
	}

	public void setStep8_LotNum(String step8_LotNum) {
		this.step8_LotNum = step8_LotNum;
	}

	public String getStep8_ulConjugate() {
		return step8_ulConjugate;
	}

	public void setStep8_ulConjugate(String step8_ulConjugate) {
		this.step8_ulConjugate = step8_ulConjugate;
	}

	public String getStep8_Item_2() {
		return step8_Item_2;
	}

	public void setStep8_Item_2(String step8_Item_2) {
		this.step8_Item_2 = step8_Item_2;
	}

	public String getStep8_Supplier_2() {
		return step8_Supplier_2;
	}

	public void setStep8_Supplier_2(String step8_Supplier_2) {
		this.step8_Supplier_2 = step8_Supplier_2;
	}

	public String getStep8_LotNum_1() {
		return step8_LotNum_1;
	}

	public void setStep8_LotNum_1(String step8_LotNum_1) {
		this.step8_LotNum_1 = step8_LotNum_1;
	}

	public String getStep8_Results() {
		return step8_Results;
	}

	public void setStep8_Results(String step8_Results) {
		this.step8_Results = step8_Results;
	}

	public String getStep8_ulAmount() {
		return step8_ulAmount;
	}

	public void setStep8_ulAmount(String step8_ulAmount) {
		this.step8_ulAmount = step8_ulAmount;
	}

	public String getStep8_ulDiluent() {
		return step8_ulDiluent;
	}

	public void setStep8_ulDiluent(String step8_ulDiluent) {
		this.step8_ulDiluent = step8_ulDiluent;
	}

	public String getStep8_ulDiluent_1() {
		return step8_ulDiluent_1;
	}

	public void setStep8_ulDiluent_1(String step8_ulDiluent_1) {
		this.step8_ulDiluent_1 = step8_ulDiluent_1;
	}

	public String getStep8_Results_1() {
		return step8_Results_1;
	}

	public void setStep8_Results_1(String step8_Results_1) {
		this.step8_Results_1 = step8_Results_1;
	}

	public String getStep8_addingAmount() {
		return step8_addingAmount;
	}

	public void setStep8_addingAmount(String step8_addingAmount) {
		this.step8_addingAmount = step8_addingAmount;
	}

	public boolean isStep8_CheckedBox_1() {
		return step8_CheckedBox_1;
	}

	public void setStep8_CheckedBox_1(boolean step8_CheckedBox_1) {
		this.step8_CheckedBox_1 = step8_CheckedBox_1;
	}

	public boolean isStep8_CheckedBox_2() {
		return step8_CheckedBox_2;
	}

	public void setStep8_CheckedBox_2(boolean step8_CheckedBox_2) {
		this.step8_CheckedBox_2 = step8_CheckedBox_2;
	}

	public String getStep8_Concentration_1() {
		return step8_Concentration_1;
	}

	public void setStep8_Concentration_1(String step8_Concentration_1) {
		this.step8_Concentration_1 = step8_Concentration_1;
	}

	public String getStep8_ulDectection() {
		return step8_ulDectection;
	}

	public void setStep8_ulDectection(String step8_ulDectection) {
		this.step8_ulDectection = step8_ulDectection;
	}

	public String getStep8_Time() {
		return step8_Time;
	}

	public void setStep8_Time(String step8_Time) {
		this.step8_Time = step8_Time;
	}

	public String getStep9_Item_1() {
		return step9_Item_1;
	}

	public void setStep9_Item_1(String step9_Item_1) {
		this.step9_Item_1 = step9_Item_1;
	}

	public String getStep9_Supplier_1() {
		return step9_Supplier_1;
	}

	public void setStep9_Supplier_1(String step9_Supplier_1) {
		this.step9_Supplier_1 = step9_Supplier_1;
	}

	public String getStep9_LotNum() {
		return step9_LotNum;
	}

	public void setStep9_LotNum(String step9_LotNum) {
		this.step9_LotNum = step9_LotNum;
	}

	public boolean isStep9_CheckedBox_1() {
		return step9_CheckedBox_1;
	}

	public void setStep9_CheckedBox_1(boolean step9_CheckedBox_1) {
		this.step9_CheckedBox_1 = step9_CheckedBox_1;
	}

	public String getStep9_mlAmount() {
		return step9_mlAmount;
	}

	public void setStep9_mlAmount(String step9_mlAmount) {
		this.step9_mlAmount = step9_mlAmount;
	}

	public String getStep9_mlAmount_1() {
		return step9_mlAmount_1;
	}

	public void setStep9_mlAmount_1(String step9_mlAmount_1) {
		this.step9_mlAmount_1 = step9_mlAmount_1;
	}

	public boolean isStep9_CheckedBox_2() {
		return step9_CheckedBox_2;
	}

	public void setStep9_CheckedBox_2(boolean step9_CheckedBox_2) {
		this.step9_CheckedBox_2 = step9_CheckedBox_2;
	}

	public boolean isStep9_CheckedBox_3() {
		return step9_CheckedBox_3;
	}

	public void setStep9_CheckedBox_3(boolean step9_CheckedBox_3) {
		this.step9_CheckedBox_3 = step9_CheckedBox_3;
	}

	public boolean isStep9_CheckedBox_4() {
		return step9_CheckedBox_4;
	}

	public void setStep9_CheckedBox_4(boolean step9_CheckedBox_4) {
		this.step9_CheckedBox_4 = step9_CheckedBox_4;
	}

	public String getStep10_Item_1() {
		return step10_Item_1;
	}

	public void setStep10_Item_1(String step10_Item_1) {
		this.step10_Item_1 = step10_Item_1;
	}

	public String getStep10_Item_2() {
		return step10_Item_2;
	}

	public void setStep10_Item_2(String step10_Item_2) {
		this.step10_Item_2 = step10_Item_2;
	}

	public String getStep10_Supplier_1() {
		return step10_Supplier_1;
	}

	public void setStep10_Supplier_1(String step10_Supplier_1) {
		this.step10_Supplier_1 = step10_Supplier_1;
	}

	public String getStep10_Supplier_2() {
		return step10_Supplier_2;
	}

	public void setStep10_Supplier_2(String step10_Supplier_2) {
		this.step10_Supplier_2 = step10_Supplier_2;
	}

	public String getStep10_LotNum() {
		return step10_LotNum;
	}

	public void setStep10_LotNum(String step10_LotNum) {
		this.step10_LotNum = step10_LotNum;
	}

	public String getStep10_LotNum_1() {
		return step10_LotNum_1;
	}

	public void setStep10_LotNum_1(String step10_LotNum_1) {
		this.step10_LotNum_1 = step10_LotNum_1;
	}

	public String getStep10_ulDetection() {
		return step10_ulDetection;
	}

	public void setStep10_ulDetection(String step10_ulDetection) {
		this.step10_ulDetection = step10_ulDetection;
	}

	public String getStep10_ulDiluent() {
		return step10_ulDiluent;
	}

	public void setStep10_ulDiluent(String step10_ulDiluent) {
		this.step10_ulDiluent = step10_ulDiluent;
	}

	public String getStep10_Results() {
		return step10_Results;
	}

	public void setStep10_Results(String step10_Results) {
		this.step10_Results = step10_Results;
	}

	public String getStep10_ulDectection1() {
		return step10_ulDectection1;
	}

	public void setStep10_ulDectection1(String step10_ulDectection1) {
		this.step10_ulDectection1 = step10_ulDectection1;
	}

	public String getStep10_addingAmount() {
		return step10_addingAmount;
	}

	public void setStep10_addingAmount(String step10_addingAmount) {
		this.step10_addingAmount = step10_addingAmount;
	}

	public String getStep10_ulDiluent1() {
		return step10_ulDiluent1;
	}

	public void setStep10_ulDiluent1(String step10_ulDiluent1) {
		this.step10_ulDiluent1 = step10_ulDiluent1;
	}

	public String getStep10_Results_1() {
		return step10_Results_1;
	}

	public void setStep10_Results_1(String step10_Results_1) {
		this.step10_Results_1 = step10_Results_1;
	}

	public boolean isStep10_CheckedBox_1() {
		return step10_CheckedBox_1;
	}

	public void setStep10_CheckedBox_1(boolean step10_CheckedBox_1) {
		this.step10_CheckedBox_1 = step10_CheckedBox_1;
	}

	public boolean isStep10_CheckedBox_2() {
		return step10_CheckedBox_2;
	}

	public void setStep10_CheckedBox_2(boolean step10_CheckedBox_2) {
		this.step10_CheckedBox_2 = step10_CheckedBox_2;
	}

	public String getStep10_Time() {
		return step10_Time;
	}

	public void setStep10_Time(String step10_Time) {
		this.step10_Time = step10_Time;
	}

	public String getStep10_ItemName() {
		return step10_ItemName;
	}

	public void setStep10_ItemName(String step10_ItemName) {
		this.step10_ItemName = step10_ItemName;
	}

	public String getStep10_Supplier() {
		return step10_Supplier;
	}

	public void setStep10_Supplier(String step10_Supplier) {
		this.step10_Supplier = step10_Supplier;
	}

	public String getStep10_mlChemicalInput() {
		return step10_mlChemicalInput;
	}

	public void setStep10_mlChemicalInput(String step10_mlChemicalInput) {
		this.step10_mlChemicalInput = step10_mlChemicalInput;
	}

	public String getStep10_mlChemicalInput_2() {
		return step10_mlChemicalInput_2;
	}

	public void setStep10_mlChemicalInput_2(String step10_mlChemicalInput_2) {
		this.step10_mlChemicalInput_2 = step10_mlChemicalInput_2;
	}

	public boolean isStep10_CheckedBox_3() {
		return step10_CheckedBox_3;
	}

	public void setStep10_CheckedBox_3(boolean step10_CheckedBox_3) {
		this.step10_CheckedBox_3 = step10_CheckedBox_3;
	}

	public boolean isStep10_CheckedBox_4() {
		return step10_CheckedBox_4;
	}

	public void setStep10_CheckedBox_4(boolean step10_CheckedBox_4) {
		this.step10_CheckedBox_4 = step10_CheckedBox_4;
	}

	public String getStep11_Item_1() {
		return step11_Item_1;
	}

	public void setStep11_Item_1(String step11_Item_1) {
		this.step11_Item_1 = step11_Item_1;
	}

	public String getStep11_Supplier_1() {
		return step11_Supplier_1;
	}

	public void setStep11_Supplier_1(String step11_Supplier_1) {
		this.step11_Supplier_1 = step11_Supplier_1;
	}

	public String getStep11_LotNum() {
		return step11_LotNum;
	}

	public void setStep11_LotNum(String step11_LotNum) {
		this.step11_LotNum = step11_LotNum;
	}

	public boolean isStep11_CheckedBox_1() {
		return step11_CheckedBox_1;
	}

	public void setStep11_CheckedBox_1(boolean step11_CheckedBox_1) {
		this.step11_CheckedBox_1 = step11_CheckedBox_1;
	}

	public String getStep12_Item_1() {
		return step12_Item_1;
	}

	public void setStep12_Item_1(String step12_Item_1) {
		this.step12_Item_1 = step12_Item_1;
	}

	public String getStep12_Supplier_1() {
		return step12_Supplier_1;
	}

	public void setStep12_Supplier_1(String step12_Supplier_1) {
		this.step12_Supplier_1 = step12_Supplier_1;
	}

	public String getStep12_LotNum() {
		return step12_LotNum;
	}

	public void setStep12_LotNum(String step12_LotNum) {
		this.step12_LotNum = step12_LotNum;
	}

	public boolean isStep12_CheckedBox_1() {
		return step12_CheckedBox_1;
	}

	public void setStep12_CheckedBox_1(boolean step12_CheckedBox_1) {
		this.step12_CheckedBox_1 = step12_CheckedBox_1;
	}

	public boolean isStep12_CheckedBox_2() {
		return step12_CheckedBox_2;
	}

	public void setStep12_CheckedBox_2(boolean step12_CheckedBox_2) {
		this.step12_CheckedBox_2 = step12_CheckedBox_2;
	}

	public String getStep12_Time() {
		return step12_Time;
	}

	public void setStep12_Time(String step12_Time) {
		this.step12_Time = step12_Time;
	}

	public String getStep13_Item_1() {
		return step13_Item_1;
	}

	public void setStep13_Item_1(String step13_Item_1) {
		this.step13_Item_1 = step13_Item_1;
	}

	public String getStep13_Supplier_1() {
		return step13_Supplier_1;
	}

	public void setStep13_Supplier_1(String step13_Supplier_1) {
		this.step13_Supplier_1 = step13_Supplier_1;
	}

	public String getStep13_LotNum() {
		return step13_LotNum;
	}

	public void setStep13_LotNum(String step13_LotNum) {
		this.step13_LotNum = step13_LotNum;
	}

	public boolean isStep13_CheckedBox_1() {
		return step13_CheckedBox_1;
	}

	public void setStep13_CheckedBox_1(boolean step13_CheckedBox_1) {
		this.step13_CheckedBox_1 = step13_CheckedBox_1;
	}

	public boolean isStep13_CheckedBox_2() {
		return step13_CheckedBox_2;
	}

	public void setStep13_CheckedBox_2(boolean step13_CheckedBox_2) {
		this.step13_CheckedBox_2 = step13_CheckedBox_2;
	}

	public String getReviewComment() {
		return reviewComment;
	}

	public void setReviewComment(String reviewComment) {
		this.reviewComment = reviewComment;
	}

	public String getQcComment() {
		return qcComment;
	}

	public void setQcComment(String qcComment) {
		this.qcComment = qcComment;
	}

	public String getStep7_Time() {
		return step7_Time;
	}

	public void setStep7_Time(String step7_Time) {
		this.step7_Time = step7_Time;
	}

	public String getStep12_TimeInput() {
		return step12_TimeInput;
	}

	public void setStep12_TimeInput(String step12_TimeInput) {
		this.step12_TimeInput = step12_TimeInput;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public String diffCompare(Worksheet obj) {
		DiffBuilder db = new DiffBuilder(this, obj, ToStringStyle.SHORT_PREFIX_STYLE, true)
				.append("templateCategory", this.getTemplateCategory(), obj.getTemplateCategory())
				.append("reviewComment", this.getReviewComment(), obj.getReviewComment())
				.append("qcComment", this.getQcComment(), obj.getQcComment())
				.append("assayNumber", this.getAssayNumber(), obj.getAssayNumber())
				.append("scientist", this.getScientist(), obj.getScientist())
				.append("date", this.getDate(), obj.getDate()).append("status", this.getStatus(), obj.getStatus())
				.append("step2_CoatingBuffer", this.getStep2_CoatingBuffer(), obj.getStep2_CoatingBuffer())
				.append("step2_Supplier_1", this.getStep2_Supplier_1(), obj.getStep2_Supplier_1())
				.append("step2_CoatingBufferLotNumber", this.getStep2_CoatingBufferLotNumber(),
						obj.getStep2_CoatingBufferLotNumber())
				.append("step2_CoatingMaterial1", this.getStep2_CoatingMaterial1(), obj.getStep2_CoatingMaterial1())
				.append("step2_Supplier_2", this.getStep2_Supplier_2(), obj.getStep2_Supplier_2())
				.append("step2_Concentration_1", this.getStep2_Concentration_1(), obj.getStep2_Concentration_1())
				.append("step2_CoatingMaterial1LotNumber", this.getStep2_CoatingMaterial1LotNumber(),
						obj.getStep2_CoatingMaterial1LotNumber())
				.append("step2_ulCoatingMaterial", this.getStep2_ulCoatingMaterial(), obj.getStep2_ulCoatingMaterial())
				.append("step2_ulCoatingBuffer", this.getStep2_ulCoatingBuffer(), obj.getStep2_ulCoatingBuffer())
				.append("step2_ulCoatingMaterial_ulCoatingBuffer", this.getStep2_ulCoatingMaterial_ulCoatingBuffer(),
						obj.getStep2_ulCoatingMaterial_ulCoatingBuffer())
				.append("step2_CheckedBox_1", this.isStep2_CheckedBox_1(), obj.isStep2_CheckedBox_1())
				.append("step2_CheckedBox_2", this.isStep2_CheckedBox_2(), obj.isStep2_CheckedBox_2())
				.append("step2_Time", this.getStep2_Time(), obj.getStep2_Time())
				.append("step2_Item_1", this.getStep2_Item_1(), obj.getStep2_Item_1())
				.append("step2_LotNum", this.getStep2_LotNum(), obj.getStep2_LotNum())
				.append("step2_ConcentrationAmount", this.getStep2_ConcentrationAmount(),
						obj.getStep2_ConcentrationAmount())
				.append("step2_Item_2", this.getStep2_Item_2(), obj.getStep2_Item_2())
				.append("step2_LotNum_1", this.getStep2_LotNum_1(), obj.getStep2_LotNum_1())
				.append("step2_Results_0", this.getStep2_Results_0(), obj.getStep2_Results_0())
				.append("step2_ulCoatingMaterial_0", this.getStep2_ulCoatingMaterial_0(),
						obj.getStep2_ulCoatingMaterial_0())
				.append("step2_CoatingMaterial_0", this.getStep2_CoatingMaterial_0(), obj.getStep2_CoatingMaterial_0())
				.append("step2_ulCoatingBuffer_1", this.getStep2_ulCoatingBuffer_1(), obj.getStep2_ulCoatingBuffer_1())
				.append("step2_CoatingMaterial_2", this.getStep2_CoatingMaterial_2(), obj.getStep2_CoatingMaterial_2())
				.append("step2_CoatingMaterial_3", this.getStep2_CoatingMaterial_2(), obj.getStep2_CoatingMaterial_2())
				.append("step2_CoatingMaterial_4", this.getStep2_CoatingMaterial_4(), obj.getStep2_CoatingMaterial_4())
				.append("step2_ulCoatingMaterial_1", this.getStep2_ulCoatingMaterial_1(),
						obj.getStep2_ulCoatingMaterial_1())
				.append("step2_ulCoatingMaterial_2", this.getStep2_ulCoatingMaterial_2(),
						obj.getStep2_ulCoatingMaterial_2())
				.append("step2_CoatingMaterial_5", this.getStep2_CoatingMaterial_5(), obj.getStep2_CoatingMaterial_5())
				.append("step2_CoatingMaterial_6", this.getStep2_CoatingMaterial_6(), obj.getStep2_CoatingMaterial_6())
				.append("step2_ulCoatingBuffer_2", this.getStep2_ulCoatingBuffer_2(), obj.getStep2_ulCoatingBuffer_2())
				.append("step2_ConcentrationAmount_1", this.getStep2_ConcentrationAmount_1(),
						obj.getStep2_ConcentrationAmount_1())
				.append("step2_ConcentrationLotNum", this.getStep2_ConcentrationLotNum(),
						obj.getStep2_ConcentrationLotNum())
				.append("step2_ulCoatingMaterial_3", this.getStep2_ulCoatingMaterial_3(),
						obj.getStep2_ulCoatingMaterial_3())
				.append("step2_CoatingMaterial_7", this.getStep2_CoatingMaterial_7(), obj.getStep2_CoatingMaterial_7())
				.append("step2_Results", this.getStep2_Results(), obj.getStep2_Results())
				.append("step2_Item_3", this.getStep2_Item_3(), obj.getStep2_Item_3())
				.append("step2_Supplier_3", this.getStep2_Supplier_3(), obj.getStep2_Supplier_3())
				.append("step2_ulCoatingMaterial_4", this.getStep2_ulCoatingMaterial_4(),
						obj.getStep2_ulCoatingMaterial_4())
				.append("step2_CoatingMaterial_8", this.getStep2_CoatingMaterial_8(), obj.getStep2_CoatingMaterial_8())
				.append("step2_ulCoatingBuffer_3", this.getStep2_ulCoatingBuffer_3(), obj.getStep2_ulCoatingBuffer_3())
				.append("step2_Results_2", this.getStep2_Results_2(), obj.getStep2_Results_2())
				.append("step2_Supplier_9", this.getStep2_Supplier_9(), obj.getStep2_Supplier_9())
				.append("step2_Item_9", this.getStep2_Item_9(), obj.getStep2_Item_9())
				.append("step2_Concentration_2", this.getStep2_Concentration_2(), obj.getStep2_Concentration_2())
				.append("step2_LotNum_2", this.getStep2_LotNum_2(), obj.getStep2_LotNum_2())
				.append("step2_CoatingMaterial_9", this.getStep2_CoatingMaterial_9(), obj.getStep2_CoatingMaterial_9())
				.append("step2_ulCoatingBuffer_4", this.getStep2_ulCoatingBuffer_4(), obj.getStep2_ulCoatingBuffer_4())
				.append("step2_Results_3", this.getStep2_Results_3(), obj.getStep2_Results_3())
				.append("step2_CoatingMaterial_10", this.getStep2_CoatingMaterial_10(),
						obj.getStep2_CoatingMaterial_10())
				.append("step2_CoatingMaterial_11", this.getStep2_CoatingMaterial_11(),
						obj.getStep2_CoatingMaterial_11())
				.append("step2_ulCoatingBuffer_5", this.getStep2_ulCoatingBuffer_5(), obj.getStep2_ulCoatingBuffer_5())
				.append("step2_Results_4", this.getStep2_Results_4(), obj.getStep2_Results_4())
				.append("step2_Item_4", this.getStep2_Item_4(), obj.getStep2_Item_4())
				.append("step2_Supplier_4", this.getStep2_Supplier_4(), obj.getStep2_Supplier_4())
				.append("step2_Concentration_3", this.getStep2_Concentration_3(), obj.getStep2_Concentration_3())
				.append("step2_Item_5", this.getStep2_Item_5(), obj.getStep2_Item_5())
				.append("step2_Supplier_5", this.getStep2_Supplier_5(), obj.getStep2_Supplier_5())
				.append("step2_Concentration_7", this.getStep2_Concentration_7(), obj.getStep2_Concentration_7())
				.append("step2_LotNum_3", this.getStep2_LotNum_3(), obj.getStep2_LotNum_3())
				.append("step2_CoatingMaterial_25 ", this.getStep2_CoatingMaterial_25(),
						obj.getStep2_CoatingMaterial_25())
				.append("step2_ulCoatingBuffer_14", this.getStep2_ulCoatingBuffer_14(),
						obj.getStep2_ulCoatingBuffer_14())
				.append("step2_Results_13", this.getStep2_Results_13(), obj.getStep2_Results_13())
				.append("step2_CoatingMaterial_26", this.getStep2_CoatingMaterial_26(),
						obj.getStep2_CoatingMaterial_26())
				.append("step2_CoatingMaterial_27", this.getStep2_CoatingMaterial_27(),
						obj.getStep2_CoatingMaterial_27())
				.append("step2_CoatingBuffer_15", this.getStep2_CoatingBuffer_15(), obj.getStep2_CoatingBuffer_15())
				.append("step2_Results_14", this.getStep2_Results_14(), obj.getStep2_Results_14())
				.append("step2_CoatingMaterial_12", this.getStep2_CoatingMaterial_12(),
						obj.getStep2_CoatingMaterial_12())
				.append("step2_ulCoatingBuffer_6", this.getStep2_ulCoatingBuffer_6(), obj.getStep2_ulCoatingBuffer_6())
				.append("step2_Results_5", this.getStep2_Results_5(), obj.getStep2_Results_5())
				.append("step2_CoatingMaterial_13", this.getStep2_CoatingMaterial_13(),
						obj.getStep2_CoatingMaterial_13())
				.append("step2_CoatingMaterial_14", this.getStep2_CoatingMaterial_14(),
						obj.getStep2_CoatingMaterial_14())
				.append("step2_CoatingBuffer_7", this.getStep2_CoatingBuffer_7(), obj.getStep2_CoatingBuffer_7())
				.append("step2_Results_6", this.getStep2_Results_6(), obj.getStep2_Results_6())
				.append("step2_Item_6", this.getStep2_Item_6(), obj.getStep2_Item_6())
				.append("step2_Supplier_6", this.getStep2_Supplier_6(), obj.getStep2_Supplier_6())
				.append("step2_Concentration_4", this.getStep2_Concentration_4(), obj.getStep2_Concentration_4())
				.append("step2_LotNum_4", this.getStep2_LotNum_4(), obj.getStep2_LotNum_4())
				.append("step2_CoatingMaterial_15", this.getStep2_CoatingMaterial_15(),
						obj.getStep2_CoatingMaterial_15())
				.append("step2_ulCoatingBuffer_7", this.getStep2_ulCoatingBuffer_7(), obj.getStep2_ulCoatingBuffer_7())
				.append("step2_Results_7", this.getStep2_Results_7(), obj.getStep2_Results_7())
				.append("step2_CoatingMaterial_16", this.getStep2_CoatingMaterial_16(),
						obj.getStep2_CoatingMaterial_16())
				.append("step2_CoatingMaterial_17", this.getStep2_CoatingMaterial_17(),
						obj.getStep2_CoatingMaterial_17())
				.append("step2_CoatingBuffer_8", this.getStep2_CoatingBuffer_8(), obj.getStep2_CoatingBuffer_8())
				.append("step2_Results_8", this.getStep2_Results_8(), obj.getStep2_Results_8())
				.append("step2_Item_7", this.getStep2_Item_7(), obj.getStep2_Item_7())
				.append("step2_Supplier_7", this.getStep2_Supplier_7(), obj.getStep2_Supplier_7())
				.append("step2_Concentration_5", this.getStep2_Concentration_5(), obj.getStep2_Concentration_5())
				.append("step2_LotNum_7", this.getStep2_LotNum_7(), obj.getStep2_LotNum_7())
				.append("step4_LotNum_5", this.getStep2_LotNum_5(), obj.getStep2_LotNum_5())
				.append("step2_CoatingMaterial_18", this.getStep2_CoatingMaterial_18(),
						obj.getStep2_CoatingMaterial_18())
				.append("step2_CoatingBuffer_10", this.getStep2_CoatingBuffer_10(), obj.getStep2_CoatingBuffer_10())
				.append("step2_Results_9", this.getStep2_Results_9(), obj.getStep2_Results_9())
				.append("step2_CoatingMaterial_19", this.getStep2_CoatingMaterial_19(),
						obj.getStep2_CoatingMaterial_19())
				.append("step2_CoatingMaterial_20", this.getStep2_CoatingMaterial_20(),
						obj.getStep2_CoatingMaterial_20())
				.append("step2_CoatingBuffer_11", this.getStep2_CoatingBuffer_11(), obj.getStep2_CoatingBuffer_11())
				.append("step2_Results_10", this.getStep2_Results_10(), obj.getStep2_Results_10())
				.append("step2_Item_8", this.getStep2_Item_8(), obj.getStep2_Item_8())
				.append("step2_Supplier_8", this.getStep2_Supplier_8(), obj.getStep2_Supplier_8())
				.append("step2_LotNum_6", this.getStep2_LotNum_6(), obj.getStep2_LotNum_6())
				.append("step2_CoatingMaterial_21", this.getStep2_CoatingMaterial_21(),
						obj.getStep2_CoatingMaterial_21())
				.append("step2_Results_11", this.getStep2_Results_11(), obj.getStep2_Results_11())
				.append("step2_CoatingMaterial_1", this.getStep2_CoatingMaterial_1(), obj.getStep2_CoatingMaterial_1())
				.append("step2_ulCoatingBuffer_0", this.getStep2_ulCoatingBuffer_0(), obj.getStep2_ulCoatingBuffer_0())
				.append("step2_addingAmount", this.getStep2_addingAmount(), obj.getStep2_addingAmount())
				.append("step2_Results_1", this.getStep2_Results_1(), obj.getStep2_Results_1())
				.append("step2_addingAmount_1", this.getStep2_addingAmount_1(), obj.getStep2_addingAmount_1())
				.append("step2_CoatingBuffer_2", this.getStep2_CoatingBuffer_2(), obj.getStep2_CoatingBuffer_2())
				.append("step2_CoatingBuffer_3", this.getStep2_CoatingBuffer_3(), obj.getStep2_CoatingBuffer_3())
				.append("step2_addingAmount_2", this.getStep2_addingAmount_2(), obj.getStep2_addingAmount_2())
				.append("step2_ulCoatingMaterial_8", this.getStep2_ulCoatingMaterial_8(),
						obj.getStep2_ulCoatingMaterial_8())
				.append("step2_addingAmount_8", this.getStep2_addingAmount_8(), obj.getStep2_addingAmount_8())
				.append("step2_ulCoatingBuffer_10", this.getStep2_ulCoatingBuffer_10(),
						obj.getStep2_ulCoatingBuffer_10())
				.append("step2_LotNum_5", this.getStep2_LotNum_5(), obj.getStep2_LotNum_5())
				.append("step2_ulCoatingMaterial_5", this.getStep2_ulCoatingMaterial_5(),
						obj.getStep2_ulCoatingMaterial_5())
				.append("step2_ulCoatingMaterial_6", this.getStep2_ulCoatingMaterial_6(),
						obj.getStep2_ulCoatingMaterial_6())
				.append("step2_addingAmount_3", this.getStep2_addingAmount_3(), obj.getStep2_addingAmount_3())
				.append("step2_ulCoatingMaterial_7", this.getStep2_ulCoatingMaterial_7(),
						obj.getStep2_ulCoatingMaterial_7())
				.append("step2_addingAmount_4", this.getStep2_addingAmount_4(), obj.getStep2_addingAmount_4())
				.append("step2_ulCoatingBuffer_8", this.getStep2_ulCoatingBuffer_8(), obj.getStep2_ulCoatingBuffer_8())
				.append("step3_Item_1", this.getStep3_Item_1(), obj.getStep3_Item_1())
				.append("step3_Supplier_1", this.getStep3_Supplier_1(), obj.getStep3_Supplier_1())
				.append("step3_LotNum", this.getStep3_LotNum(), obj.getStep3_LotNum())
				.append("step3_CheckedBox_1", this.isStep3_CheckedBox_1(), obj.isStep3_CheckedBox_1())
				.append("step3_CheckedBox_2", this.isStep3_CheckedBox_2(), obj.isStep3_CheckedBox_2())
				.append("step3_Time", this.getStep3_Time(), obj.getStep3_Time())
				.append("step4_Item_1", this.getStep4_Item_1(), obj.getStep4_Item_1())
				.append("step4_Supplier_1", this.getStep4_Supplier_1(), obj.getStep4_Supplier_1())
				.append("step4_LotNum", this.getStep4_LotNum(), obj.getStep4_LotNum())
				.append("step4_CheckedBox_1", this.isStep4_CheckedBox_1(), obj.isStep4_CheckedBox_1())
				.append("step4_CheckedBox_2", this.isStep4_CheckedBox_2(), obj.isStep4_CheckedBox_2())
				.append("step4_Time", this.getStep4_Time(), obj.getStep4_Time())
				.append("step5_Item_1", this.getStep5_Item_1(), obj.getStep5_Item_1())
				.append("step5_Supplier_1", this.getStep5_Supplier_1(), obj.getStep5_Supplier_1())
				.append("step5_LotNum", this.getStep5_LotNum(), obj.getStep5_LotNum())
				.append("step5_CloneId_1 ", this.getStep5_CloneId_1(), obj.getStep5_CloneId_1())
				.append("step5_ulClone_1", this.getStep5_ulClone_1(), obj.getStep5_ulClone_1())
				.append("step5_ulClone_2", this.getStep5_ulClone_2(), obj.getStep5_ulClone_2())
				.append("step5_ulDiluent", this.getStep5_ulDiluent(), obj.getStep5_ulDiluent())
				.append("step5_CheckedBox_1", this.isStep5_CheckedBox_1(), obj.isStep5_CheckedBox_1())
				.append("step5_CheckedBox_2", this.isStep5_CheckedBox_2(), obj.isStep5_CheckedBox_2())
				.append("step5_Time", this.getStep5_Time(), obj.getStep5_Time())
				.append("step6_Item_1", this.getStep6_Item_1(), obj.getStep6_Item_1())
				.append("step6_Supplier_1", this.getStep6_Supplier_1(), obj.getStep6_Supplier_1())
				.append("step6_LotNum", this.getStep6_LotNum(), obj.getStep6_LotNum())
				.append("step6_Item_2", this.getStep6_Item_2(), obj.getStep6_Item_2())
				.append("step6_Supplier_2", this.getStep6_Supplier_2(), obj.getStep6_Supplier_2())
				.append("step6_Concentration_1", this.getStep6_Concentration_1(), obj.getStep6_Concentration_1())
				.append("step6_LotNum_1", this.getStep6_LotNum_1(), obj.getStep6_LotNum_1())
				.append("step6_ulAvastin", this.getStep6_ulAvastin(), obj.getStep6_ulAvastin())
				.append("step6_ulSuperblock", this.getStep6_ulSuperblock(), obj.getStep6_ulSuperblock())
				.append("step6_Results", this.getStep6_Results(), obj.getStep6_Results())
				.append("step6_ulAvastin1", this.getStep6_ulAvastin1(), obj.getStep6_ulAvastin1())
				.append("step6_addingAmount", this.getStep6_addingAmount(), obj.getStep6_addingAmount())
				.append("step6_ulSuperblock2", this.getStep6_ulSuperblock2(), obj.getStep6_ulSuperblock2())
				.append("step6_Results_2", this.getStep6_Results_2(), obj.getStep6_Results_2())
				.append("step6_Item_3", this.getStep6_Item_3(), obj.getStep6_Item_3())
				.append("step6_Concentration_2", this.getStep6_Concentration_2(), obj.getStep6_Concentration_2())
				.append("step6_Concentration_LotNum", this.getStep6_Concentration_LotNum(),
						obj.getStep6_Concentration_LotNum())
				.append("step6_ulBiosimilar", this.getStep6_ulBiosimilar(), obj.getStep6_ulBiosimilar())
				.append("step6_ulSuperblock3", this.getStep6_ulSuperblock3(), obj.getStep6_ulSuperblock3())
				.append("step6_Results_3", this.getStep6_Results_3(), obj.getStep6_Results_3())
				.append("step6_ulBiosimilar2", this.getStep6_ulBiosimilar2(), obj.getStep6_ulBiosimilar2())
				.append("step6_addingAmount_2", this.getStep6_addingAmount_2(), obj.getStep6_addingAmount_2())
				.append("step6_Superblock4", this.getStep6_Superblock4(), obj.getStep6_Superblock4())
				.append("step6_Results_4", this.getStep6_Results_4(), obj.getStep6_Results_4())
				.append("step6_Item_4", this.getStep6_Item_4(), obj.getStep6_Item_4())
				.append("step6_Supplier_4", this.getStep6_Supplier_4(), obj.getStep6_Supplier_4())
				.append("step6_Concentration_3", this.getStep6_Concentration_3(), obj.getStep6_Concentration_3())
				.append("step6_LotNum_2", this.getStep6_LotNum_2(), obj.getStep6_LotNum_2())
				.append("step6_ulStandard", this.getStep6_ulStandard(), obj.getStep6_ulStandard())
				.append("step6_ulSuperblock5", this.getStep6_ulSuperblock5(), obj.getStep6_ulSuperblock5())
				.append("step6_Results_5", this.getStep6_Results_5(), obj.getStep6_Results_5())
				.append("step6_ulStandard2", this.getStep6_ulStandard2(), obj.getStep6_ulStandard2())
				.append("step6_addingAmount_3", this.getStep6_addingAmount_3(), obj.getStep6_addingAmount_3())
				.append("step6_ulSuperblock6", this.getStep6_ulSuperblock6(), obj.getStep6_ulSuperblock6())
				.append("step6_Results_6", this.getStep6_Results_6(), obj.getStep6_Results_6())
				.append("step6_Supplier_3", this.getStep6_Supplier_3(), obj.getStep6_Supplier_3())
				.append("step6_CheckedBox_1", this.isStep6_CheckedBox_1(), obj.isStep6_CheckedBox_1())
				.append("step6_CheckedBox_2", this.isStep6_CheckedBox_2(), obj.isStep6_CheckedBox_2())
				.append("step6_Time", this.getStep6_Time(), obj.getStep6_Time())
				.append("step6_Concentration", this.getStep6_Concentration(), obj.getStep6_Concentration())
				.append("step6_ulAvastin_1", this.getStep6_ulAvastin_1(), obj.getStep6_ulAvastin_1())
				.append("step6_ulSuperblock_1", this.getStep6_ulSuperblock_1(), obj.getStep6_ulSuperblock_1())
				.append("step6_Results_1", this.getStep6_Results_1(), obj.getStep6_Results_1())
				.append("step6_LotNum_3", this.getStep6_LotNum_3(), obj.getStep6_LotNum_3())
				.append("step6_ulNistmAb", this.getStep6_ulNistmAb(), obj.getStep6_ulNistmAb())
				.append("step6_ulSuperblock_2", this.getStep6_ulSuperblock_2(), obj.getStep6_ulSuperblock_2())
				.append("step6_ulNistmAb_2", this.getStep6_ulNistmAb_2(), obj.getStep6_ulNistmAb_2())
				.append("step6_addingAmount_1", this.getStep6_addingAmount_1(), obj.getStep6_addingAmount_1())
				.append("step6_ulSuperblock_3", this.getStep6_ulSuperblock_3(), obj.getStep6_ulSuperblock_3())
				.append("step6_LotNum_4", this.getStep6_LotNum_4(), obj.getStep6_LotNum_4())
				.append("step6_ulSuperblock_4", this.getStep6_ulSuperblock_4(), obj.getStep6_ulSuperblock_4())
				.append("step6_ulBeavatas", this.getStep6_ulBeavatas(), obj.getStep6_ulBeavatas())
				.append("step6_ulBeavatas_1", this.getStep6_ulBeavatas_1(), obj.getStep6_ulBeavatas_1())
				.append("step6_ulSuperblock_5", this.getStep6_ulSuperblock_5(), obj.getStep6_ulSuperblock_5())
				.append("step7_Item_1", this.getStep7_Item_1(), obj.getStep7_Item_1())
				.append("step7_Supplier_1", this.getStep7_Supplier_1(), obj.getStep7_Supplier_1())
				.append("step7_CoatingMaterial0", this.getStep7_CoatingMaterial0(), obj.getStep7_CoatingMaterial0())
				.append("step7_CheckedBox_1", this.isStep7_CheckedBox_1(), obj.isStep7_CheckedBox_1())
				.append("step7_LotNum", this.getStep7_LotNum(), obj.getStep7_LotNum())
				.append("step7_LotNum_1", this.getStep7_LotNum_1(), obj.getStep7_LotNum_1())
				.append("step7_Item_2", this.getStep7_Item_2(), obj.getStep7_Item_2())
				.append("step7_Supplier_2", this.getStep7_Supplier_2(), obj.getStep7_Supplier_2())
				.append("step7_LotNum_2", this.getStep7_LotNum_2(), obj.getStep7_LotNum_2())
				.append("step7_ulConjugate", this.getStep7_ulConjugate(), obj.getStep7_ulConjugate())
				.append("step7_ulDiluent", this.getStep7_ulDiluent(), obj.getStep7_ulDiluent())
				.append("step7_ul", this.getStep7_ul(), obj.getStep7_ul())
				.append("step7_Results", this.getStep7_Results(), obj.getStep7_Results())
				.append("step7_addingAmount ", this.getStep7_addingAmount(), obj.getStep7_addingAmount())
				.append("step7_ulDiluent_1", this.getStep7_ulDiluent_1(), obj.getStep7_ulDiluent_1())
				.append("step7_Results_1", this.getStep7_Results_1(), obj.getStep7_Results_1())
				.append("step7_CheckedBox_2", this.isStep7_CheckedBox_2(), obj.isStep7_CheckedBox_2())
				.append("step7_Concentration_1", this.getStep7_Concentration_1(), obj.getStep7_Concentration_1())
				.append("step7_Time", this.getStep7_Time(), obj.getStep7_Time())
				.append("step8_Item_1", this.getStep8_Item_1(), obj.getStep8_Item_1())
				.append("step8_Supplier_1", this.getStep8_Supplier_1(), obj.getStep8_Supplier_1())
				.append("step8_LotNum", this.getStep8_LotNum(), obj.getStep8_LotNum())
				.append("step8_ulConjugate", this.getStep8_ulConjugate(), obj.getStep8_ulConjugate())
				.append("step8_Item_2", this.getStep8_Item_2(), obj.getStep8_Item_2())
				.append("step8_Supplier_2", this.getStep8_Supplier_2(), obj.getStep8_Supplier_2())
				.append("step8_LotNum_1", this.getStep8_LotNum_1(), obj.getStep8_LotNum_1())
				.append("step8_Results", this.getStep8_Results(), obj.getStep8_Results())
				.append("step8_ulAmount", this.getStep8_ulAmount(), obj.getStep8_ulAmount())
				.append("step8_ulDiluent", this.getStep8_ulDiluent(), obj.getStep8_ulDiluent())
				.append("step8_ulDiluent_1", this.getStep8_ulDiluent_1(), obj.getStep8_ulDiluent_1())
				.append("step8_Results_1", this.getStep8_Results_1(), obj.getStep8_Results_1())
				.append("step8_addingAmount", this.getStep8_addingAmount(), obj.getStep8_addingAmount())
				.append("step8_CheckedBox_1", this.isStep8_CheckedBox_1(), obj.isStep8_CheckedBox_1())
				.append("step8_CheckedBox_2", this.isStep8_CheckedBox_2(), obj.isStep8_CheckedBox_2())
				.append("step8_Time", this.getStep8_Time(), obj.getStep8_Time())
				.append("step8_Concentration_1", this.getStep8_Concentration_1(), obj.getStep8_Concentration_1())
				.append("step8_ulDectection", this.getStep8_ulDectection(), obj.getStep8_ulDectection())
				.append("step9_Item_1", this.getStep9_Item_1(), obj.getStep9_Item_1())
				.append("step9_Supplier_1", this.getStep9_Supplier_1(), obj.getStep9_Supplier_1())
				.append("step9_LotNum", this.getStep9_LotNum(), obj.getStep9_LotNum())
				.append("step9_CheckedBox_1", this.isStep9_CheckedBox_1(), obj.isStep9_CheckedBox_1())
				.append("step9_mlAmount", this.getStep9_mlAmount(), obj.getStep9_mlAmount())
				.append("step9_mlAmount_1", this.getStep9_mlAmount_1(), obj.getStep9_mlAmount_1())
				.append("step9_CheckedBox_2", this.isStep9_CheckedBox_2(), obj.isStep9_CheckedBox_2())
				.append("step9_CheckedBox_3", this.isStep9_CheckedBox_3(), obj.isStep9_CheckedBox_3())
				.append("step9_CheckedBox_4", this.isStep9_CheckedBox_4(), obj.isStep9_CheckedBox_4())
				.append("step10_Item_1", this.getStep10_Item_1(), obj.getStep10_Item_1())
				.append("step10_Item_2", this.getStep10_Item_2(), obj.getStep10_Item_2())
				.append("step10_Supplier_1", this.getStep10_Supplier_1(), obj.getStep10_Supplier_1())
				.append("step10_Supplier_2", this.getStep10_Supplier_2(), obj.getStep10_Supplier_2())
				.append("step10_LotNum", this.getStep10_LotNum(), obj.getStep10_LotNum())
				.append("step10_LotNum_1", this.getStep10_LotNum_1(), obj.getStep10_LotNum_1())
				.append("step10_ulDetection", this.getStep10_ulDetection(), obj.getStep10_ulDetection())
				.append("step10_ulDiluent", this.getStep10_ulDiluent(), obj.getStep10_ulDiluent())
				.append("step10_Results", this.getStep10_Results(), obj.getStep10_Results())
				.append("step10_ulDectection1", this.getStep10_ulDectection1(), obj.getStep10_ulDectection1())
				.append("step10_addingAmount", this.getStep10_addingAmount(), obj.getStep10_addingAmount())
				.append("step10_ulDiluent1", this.getStep10_ulDiluent1(), obj.getStep10_ulDiluent1())
				.append("step10_Results_1", this.getStep10_Results_1(), obj.getStep10_Results_1())
				.append("step10_CheckedBox_1", this.isStep10_CheckedBox_1(), obj.isStep10_CheckedBox_1())
				.append("step10_CheckedBox_2", this.isStep10_CheckedBox_2(), obj.isStep10_CheckedBox_2())
				.append("step10_Time", this.getStep10_Time(), obj.getStep10_Time())
				.append("step10_ItemName", this.getStep10_ItemName(), obj.getStep10_ItemName())
				.append("step10_Supplier", this.getStep10_Supplier(), obj.getStep10_Supplier())
				.append("step10_mlChemicalInput", this.getStep10_mlChemicalInput(), obj.getStep10_mlChemicalInput())
				.append("step10_mlChemicalInput_2", this.getStep10_mlChemicalInput_2(),
						obj.getStep10_mlChemicalInput_2())
				.append("step10_CheckedBox_3", this.isStep10_CheckedBox_3(), obj.isStep10_CheckedBox_3())
				.append("step10_CheckedBox_4", this.isStep10_CheckedBox_4(), obj.isStep10_CheckedBox_4())
				.append("step11_Item_1", this.getStep11_Item_1(), obj.getStep11_Item_1())
				.append("step11_Supplier_1", this.getStep11_Supplier_1(), obj.getStep11_Supplier_1())
				.append("step11_LotNum", this.getStep11_LotNum(), obj.getStep11_LotNum())
				.append("step11_CheckedBox_1", this.isStep11_CheckedBox_1(), obj.isStep11_CheckedBox_1())
				.append("step12_Item_1", this.getStep12_Item_1(), obj.getStep12_Item_1())
				.append("step12_Supplier_1", this.getStep12_Supplier_1(), obj.getStep12_Supplier_1())
				.append("step12_LotNum", this.getStep12_LotNum(), obj.getStep12_LotNum())
				.append("step12_Time", this.getStep12_Time(), obj.getStep12_Time())
				.append("step12_CheckedBox_1", this.isStep12_CheckedBox_1(), obj.isStep12_CheckedBox_1())
				.append("step12_CheckedBox_2", this.isStep12_CheckedBox_2(), obj.isStep12_CheckedBox_2())
				.append("step12_TimeInput", this.getStep12_TimeInput(), obj.getStep12_TimeInput())
				.append("step13_Item_1", this.getStep13_Item_1(), obj.getStep13_Item_1())
				.append("step13_Supplier_1", this.getStep13_Supplier_1(), obj.getStep13_Supplier_1())
				.append("step13_LotNum", this.getStep13_LotNum(), obj.getStep13_LotNum())
				.append("step13_CheckedBox_1", this.isStep13_CheckedBox_1(), obj.isStep13_CheckedBox_1())
				.append("step13_CheckedBox_2", this.isStep13_CheckedBox_2(), obj.isStep13_CheckedBox_2());

		return db.build().toString();
	}

}
