package data;

import com.mifmif.common.regex.Generex;

public class Field {
	private String name;
	private Object value;
	private Generex gen;

	public Field(String name, Object value, String regex) {
		this.name = name;
		this.value = value;
		this.gen = new Generex(regex);
	}

	public String random() {
		return gen.random();
	}

	public Object getValue() {
		return value;
	}

	public String getName() {
		return name;
	}
}
