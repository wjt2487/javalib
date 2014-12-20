package cn.mxj.exception;

import cn.mxj.business.IEnumBase;

/**
 * the level of exception
 * 
 * @author fl
 * 
 */
public enum ExceptionLevel implements IEnumBase {

	CanIgnore(1, "忽略"), Normal(2, "正常"), Important(3, "严重"), Deadly(4, "致命");

	private int value;

	private String name;

	private ExceptionLevel(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public int getValue() {
		return this.value;
	}
}
