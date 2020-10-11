package com.armut.common;

public enum ActivityStatusEnum {

	SUCCESS("1", "1"), FAILED("0", "0")

	;

	private ActivityStatusEnum(String key, String value) {
		this.key = key;
		this.value = value;
	}

	private String key;
	private String value;

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}
}
