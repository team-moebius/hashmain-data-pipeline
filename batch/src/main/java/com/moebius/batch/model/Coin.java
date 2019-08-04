package com.moebius.batch.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Coin {
	private String name;
	private String balance;

	abstract String getOwner();
}
