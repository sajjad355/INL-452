package com.internal.service.somruinternal.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.internal.service.somruinternal.model2.BlockingStep;
import com.internal.service.somruinternal.model2.BlockingStepInstance;
import com.internal.service.somruinternal.model2.CaptureStep;
import com.internal.service.somruinternal.model2.CaptureStepInstance;
import com.internal.service.somruinternal.model2.CoatingStep;
import com.internal.service.somruinternal.model2.CoatingStepInstance;
import com.internal.service.somruinternal.model2.DetectionStep;
import com.internal.service.somruinternal.model2.DetectionStepInstance;
import com.internal.service.somruinternal.model2.EquipmentV2;
import com.internal.service.somruinternal.model2.StopStep;
import com.internal.service.somruinternal.model2.StopStepInstance;
import com.internal.service.somruinternal.model2.SubstrateStep;
import com.internal.service.somruinternal.model2.SubstrateStepInstance;
import com.internal.service.somruinternal.model2.TestStep;
import com.internal.service.somruinternal.model2.TestStepInstance;
import com.internal.service.somruinternal.model2.WashStep;
import com.internal.service.somruinternal.model2.WashStepInstance;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY, property = "type", visible = true)
@JsonSubTypes({
    @JsonSubTypes.Type(value = StopStep.class, name = "stop"),
    @JsonSubTypes.Type(value = WashStep.class, name = "wash"),
    @JsonSubTypes.Type(value = CoatingStep.class, name = "coating"),
    @JsonSubTypes.Type(value = BlockingStep.class, name = "blocking"),
    @JsonSubTypes.Type(value = CaptureStep.class, name = "capture"),
    @JsonSubTypes.Type(value = TestStep.class, name = "test"),
    @JsonSubTypes.Type(value = DetectionStep.class, name = "detection"),
    @JsonSubTypes.Type(value = SubstrateStep.class, name = "substrate"),

    @JsonSubTypes.Type(value = StopStepInstance.class, name = "stop instance"),
    @JsonSubTypes.Type(value = WashStepInstance.class, name = "wash instance"),
    @JsonSubTypes.Type(value = CoatingStepInstance.class, name = "coating instance"),
    @JsonSubTypes.Type(value = BlockingStepInstance.class, name = "blocking instance"),
    @JsonSubTypes.Type(value = CaptureStepInstance.class, name = "capture instance"),
    @JsonSubTypes.Type(value = TestStepInstance.class, name = "test instance"),
    @JsonSubTypes.Type(value = DetectionStepInstance.class, name = "detection instance"),
    @JsonSubTypes.Type(value = SubstrateStepInstance.class, name = "substrate instance")
})
public abstract class Step {

    @JsonIgnore
    @JsonProperty("type")
    private String type;

    private long position;

    @JsonProperty("equipments")
    private List<EquipmentV2> equipments;
    
    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    @JsonIgnore
    @JsonProperty("type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

	public List<EquipmentV2> getEquipments() {
		return equipments;
	}

	public void setEquipments(List<EquipmentV2> equipments) {
		this.equipments = equipments;
	}
}
