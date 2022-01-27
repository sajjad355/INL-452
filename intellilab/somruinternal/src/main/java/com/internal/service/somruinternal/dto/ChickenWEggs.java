package com.internal.service.somruinternal.dto;

import java.util.List;

import com.internal.service.somruinternal.model.Chicken;
import com.internal.service.somruinternal.model.Egg;

public class ChickenWEggs {
	public ChickenWEggs(Chicken chick, List<Egg> eggs) {
		super();
		this.chick = chick;
		this.eggs = eggs;
	}
	
	public Chicken chick;
	public List<Egg> eggs;
	
	public Chicken getChick() {
		return chick;
	}
	public void setChick(Chicken chick) {
		this.chick = chick;
	}
	public List<Egg> getEggs() {
		return eggs;
	}
	public void setEggs(List<Egg> eggs) {
		this.eggs = eggs;
	}
}
