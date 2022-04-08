package data;

import com.mifmif.common.regex.Generex;

public class Field {
	private String name;
	private String value;
	private Generex gen;

	public Field(String name, String value, String regex) {
		this.name = name;
		this.value = value;
		this.gen = new Generex(regex);
	}

	public String random() {
		this.value = gen.random();
		return value;
	}

	public String getValue() {
		return value;
	}

	public String getName() {
		return name;
	}
}
