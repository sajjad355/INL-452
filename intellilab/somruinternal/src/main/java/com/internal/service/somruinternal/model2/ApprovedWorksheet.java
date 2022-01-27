package com.internal.service.somruinternal.model2;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "approvedWorksheet_part_1")
@SecondaryTable(name = "approvedWorksheet_part_2")
@EntityListeners(AuditingEntityListener.class)
public class ApprovedWorksheet {

    public ApprovedWorksheet() {
        super();
    }

    @Id
    private long worksheetID;
    @Column(nullable = true, length = 100)
    private int templateCategory;

    //============================= Section One ================================
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

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step2_ulCoatingMaterial;

    @Column(nullable = true, length = 100)
    private String step2_ulCoatingBuffer;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
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

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step2_ulCoatingMaterial_0;

    @Column(nullable = true, length = 100)
    private String step2_CoatingMaterial_0;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step2_ulCoatingBuffer_1;

    @Column(nullable = true, length = 100)
    private String step2_CoatingMaterial_2;

    @Column(nullable = true, length = 100)
    private String step2_CoatingMaterial_3;

    @Column(nullable = true, length = 100)
    private String step2_CoatingMaterial_4;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step2_ulCoatingMaterial_1;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step2_ulCoatingMaterial_2;

    @Column(nullable = true, length = 100)
    private String step2_CoatingMaterial_5;

    @Column(nullable = true, length = 100)
    private String step2_CoatingMaterial_6;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step2_ulCoatingBuffer_2;

    @Column(nullable = true, length = 100)
    private String step2_ConcentrationAmount_1;
    @Column(nullable = true, length = 100)
    private String step2_ConcentrationLotNum;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step2_ulCoatingMaterial_3;

    @Column(nullable = true, length = 100)
    private String step2_CoatingMaterial_7;

    @Column(nullable = true, length = 100)
    private String step2_Results;

    @Column(nullable = true, length = 100)
    private String step2_Item_3;
    @Column(nullable = true, length = 100)
    private String step2_Supplier_3;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step2_ulCoatingMaterial_4;

    @Column(nullable = true, length = 100)
    private String step2_CoatingMaterial_8;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
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

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step2_ulCoatingBuffer_4;

    @Column(nullable = true, length = 100)
    private String step2_Results_3;

    @Column(nullable = true, length = 100)
    private String step2_CoatingMaterial_10;

    @Column(nullable = true, length = 100)
    private String step2_CoatingMaterial_11;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
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

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step2_ulCoatingBuffer_14;

    @Column(nullable = true, length = 100)
    private String step2_Results_13;

    @Column(nullable = true, length = 100)
    private String step2_CoatingMaterial_26;

    @Column(nullable = true, length = 100)
    private String step2_CoatingMaterial_27;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step2_CoatingBuffer_15;

    @Column(nullable = true, length = 100)
    private String step2_Results_14;
    @Column(nullable = true, length = 100)
    private String step2_CoatingMaterial_12;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step2_ulCoatingBuffer_6;

    @Column(nullable = true, length = 100)
    private String step2_Results_5;

    @Column(nullable = true, length = 100)
    private String step2_CoatingMaterial_13;

    @Column(nullable = true, length = 100)
    private String step2_CoatingMaterial_14;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
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

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step2_ulCoatingBuffer_7;

    @Column(nullable = true, length = 100)
    private String step2_Results_7;
    @Column(nullable = true, length = 100)
    private String step2_CoatingMaterial_16;

    @Column(nullable = true, length = 100)
    private String step2_CoatingMaterial_17;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
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

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
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

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
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

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step2_ulCoatingMaterial_8;

    @Column(nullable = true, length = 100)
    private String step2_addingAmount_8;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step2_ulCoatingBuffer_10;
    @Column(nullable = true, length = 100)
    private String step2_LotNum_5;
    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step2_ulCoatingMaterial_5;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step2_ulCoatingMaterial_6;

    @Column(nullable = true, length = 100)
    private String step2_addingAmount_3;
    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step2_ulCoatingMaterial_7;

    @Column(nullable = true, length = 100)
    private String step2_addingAmount_4;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step2_ulCoatingBuffer_8;

//=================================== END =====================================
    //============================= Section Three =================================
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

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step3_Time;

//=================================== END =====================================
    //============================= Section Four ================================
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

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step4_Time;

//=================================== END =====================================
    //============================= Section Five ==================================
    @Column(nullable = true, length = 100)
    private String step5_Item_1;
    @Column(nullable = true, length = 100)
    private String step5_Supplier_1;
    @Column(nullable = true, length = 100)
    private String step5_LotNum;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step5_CloneId_1;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step5_ulClone_1;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step5_ulClone_2;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step5_ulDiluent;
    @Column(nullable = true, length = 100)
    private boolean step5_CheckedBox_1;
    @Column(nullable = true, length = 100)
    private boolean step5_CheckedBox_2;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step5_Time;

//=================================== END ====================================
    //============================= Section Six ==================================
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

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step6_Concentration_1;
    @Column(nullable = true, length = 100)
    private String step6_LotNum_1;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step6_ulAvastin;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step6_ulSuperblock;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step6_Results;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step6_ulAvastin1;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step6_addingAmount;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step6_ulSuperblock2;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step6_Results_2;
    @Column(nullable = true, length = 100)
    private String step6_Item_3;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step6_Concentration_2;
    @Column(nullable = true, length = 100)
    private String step6_Concentration_LotNum;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step6_ulBiosimilar;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step6_ulSuperblock3;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step6_Results_3;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step6_ulBiosimilar2;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step6_addingAmount_2;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step6_Superblock4;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step6_Results_4;
    @Column(nullable = true, length = 100)
    private String step6_Item_4;
    @Column(nullable = true, length = 100)
    private String step6_Supplier_4;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step6_Concentration_3;
    @Column(nullable = true, length = 100)
    private String step6_LotNum_2;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step6_ulStandard;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step6_ulSuperblock5;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step6_Results_5;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step6_ulStandard2;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step6_addingAmount_3;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step6_ulSuperblock6;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step6_Results_6;
    @Column(nullable = true, length = 100)
    private String step6_Supplier_3;
    @Column(nullable = true, length = 100)
    private boolean step6_CheckedBox_1;
    @Column(nullable = true, length = 100)
    private boolean step6_CheckedBox_2;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step6_Time;
    @Column(nullable = true, length = 100)
    private String step6_Concentration;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step6_ulAvastin_1;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step6_ulSuperblock_1;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step6_Results_1;

    @Column(nullable = true, length = 100)
    private String step6_LotNum_3;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step6_ulNistmAb;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step6_ulSuperblock_2;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step6_ulNistmAb_2;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step6_addingAmount_1;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step6_ulSuperblock_3;

    @Column(nullable = true, length = 100)
    private String step6_LotNum_4;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step6_ulSuperblock_4;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step6_ulBeavatas;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step6_ulBeavatas_1;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step6_ulSuperblock_5;

//=================================== END =====================================
    //============================= Section Seven =================================
    @Column(nullable = true, length = 100)
    private String step7_Item_1;
    @Column(nullable = true, length = 100)
    private String step7_Supplier_1;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
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

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step7_ulConjugate;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step7_ulDiluent;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step7_ul;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step7_Results;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step7_addingAmount;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step7_ulDiluent_1;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step7_Results_1;
    @Column(nullable = true, length = 100)
    private boolean step7_CheckedBox_2;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step7_Concentration_1;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step7_Time;

//=================================== END =====================================
    //============================= Section Eight ===============================
    @Column(nullable = true, length = 100)
    private String step8_Item_1;
    @Column(nullable = true, length = 100)
    private String step8_Supplier_1;
    @Column(nullable = true, length = 100)
    private String step8_LotNum;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step8_ulConjugate;
    @Column(nullable = true, length = 100)
    private String step8_Item_2;
    @Column(nullable = true, length = 100)
    private String step8_Supplier_2;
    @Column(nullable = true, length = 100)
    private String step8_LotNum_1;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step8_Results;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step8_ulAmount;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step8_ulDiluent;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step8_ulDiluent_1;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step8_Results_1;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step8_addingAmount;
    @Column(nullable = true, length = 100)
    private boolean step8_CheckedBox_1;
    @Column(nullable = true, length = 100)
    private boolean step8_CheckedBox_2;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step8_Time;
    @Column(nullable = true, length = 100)
    private String step8_Concentration_1;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step8_ulDectection;

//=================================== END =====================================
    //============================= Section Nine ================================
    @Column(nullable = true, length = 100)
    private String step9_Item_1;
    @Column(nullable = true, length = 100)
    private String step9_Supplier_1;
    @Column(nullable = true, length = 100)
    private String step9_LotNum;
    @Column(nullable = true, length = 100)
    private boolean step9_CheckedBox_1;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step9_mlAmount;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step9_mlAmount_1;
    @Column(nullable = true, length = 100)
    private boolean step9_CheckedBox_2;
    @Column(nullable = true, length = 100)
    private boolean step9_CheckedBox_3;
    @Column(nullable = true, length = 100)
    private boolean step9_CheckedBox_4;

//=================================== END ====================================
    //============================= Section Ten ==================================
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

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step10_ulDetection;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step10_ulDiluent;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step10_Results;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step10_ulDectection1;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step10_addingAmount;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step10_ulDiluent1;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step10_Results_1;
    @Column(nullable = true, length = 100)
    private boolean step10_CheckedBox_1;
    @Column(nullable = true, length = 100)
    private boolean step10_CheckedBox_2;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step10_Time;

    @Column(nullable = true, length = 100)
    private String step10_ItemName;
    @Column(nullable = true, length = 100)
    private String step10_Supplier;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step10_mlChemicalInput;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step10_mlChemicalInput_2;
    @Column(nullable = true, length = 100)
    private boolean step10_CheckedBox_3;
    @Column(nullable = true, length = 100)
    private boolean step10_CheckedBox_4;

//=================================== END =====================================
    //============================= Section Eleven ================================
    @Column(nullable = true, length = 100)
    private String step11_Item_1;
    @Column(nullable = true, length = 100)
    private String step11_Supplier_1;
    @Column(nullable = true, length = 100)
    private String step11_LotNum;
    @Column(nullable = true, length = 100)
    private boolean step11_CheckedBox_1;

//=================================== END =====================================
    //============================= Section Twelve ================================
    @Column(nullable = true, length = 100)
    private String step12_Item_1;
    @Column(nullable = true, length = 100)
    private String step12_Supplier_1;
    @Column(nullable = true, length = 100)
    private String step12_LotNum;

    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step12_Time;
    @Column(nullable = true, length = 100)
    private boolean step12_CheckedBox_1;
    @Column(nullable = true, length = 100)
    private boolean step12_CheckedBox_2;
    @Column(table = "approvedWorksheet_part_2", nullable = true, length = 100)
    private String step12_TimeInput;

//=================================== END =====================================
    //============================= Section Thirteen ==============================
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

    //=================================== END =====================================
    //=========================All Getter Method ==================================
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
    public String getStep2_Concentration_6() {
      return step2_Concentration_6;
    }

    public void setStep2_Concentration_6(String step2_Concentration_6) {
      this.step2_Concentration_6 = step2_Concentration_6;
    }
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
    public String getStep2_CoatingBuffer_12() {
      return step2_CoatingBuffer_12;
    }

    public void setStep2_CoatingBuffer_12(String step2_CoatingBuffer_12) {
      this.step2_CoatingBuffer_12 = step2_CoatingBuffer_12;
    }
     */
    public String getStep2_Results_11() {
        return step2_Results_11;
    }

    public void setStep2_Results_11(String step2_Results_11) {
        this.step2_Results_11 = step2_Results_11;
    }

    /*
    public String getStep2_CoatingMaterial_22() {
      return step2_CoatingMaterial_22;
    }

    public void setStep2_CoatingMaterial_22(String step2_CoatingMaterial_22) {
      this.step2_CoatingMaterial_22 = step2_CoatingMaterial_22;
    }

    public String getStep2_CoatingMaterial_23() {
      return step2_CoatingMaterial_23;
    }

    public void setStep2_CoatingMaterial_23(String step2_CoatingMaterial_23) {
      this.step2_CoatingMaterial_23 = step2_CoatingMaterial_23;
    }

    public String getStep2_CoatingMaterial_24() {
      return step2_CoatingMaterial_24;
    }

    public void setStep2_CoatingMaterial_24(String step2_CoatingMaterial_24) {
      this.step2_CoatingMaterial_24 = step2_CoatingMaterial_24;
    }

    public String getStep2_CoatingBuffer_13() {
      return step2_CoatingBuffer_13;
    }

    public void setStep2_CoatingBuffer_13(String step2_CoatingBuffer_13) {
      this.step2_CoatingBuffer_13 = step2_CoatingBuffer_13;
    }

    public String getStep2_Results_12() {
      return step2_Results_12;
    }

    public void setStep2_Results_12(String step2_Results_12) {
      this.step2_Results_12 = step2_Results_12;
    }
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
    //===================================END=======================================
}
