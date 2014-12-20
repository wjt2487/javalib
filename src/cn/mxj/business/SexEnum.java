package cn.mxj.business;

public enum SexEnum implements IEnumBase {

	Unknown(0, "未知"), Male(1, "男"), Female(2, "女");

	private int value;

	private String name;

	private SexEnum(int value, String name) {
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
