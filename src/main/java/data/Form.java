package data;

import java.util.ArrayList;
import java.util.List;

public class Form {
	private List<Field> fields;
	
	public Form(List<Field> fields) {
		this.fields = fields;
	}
	
	public Form(String[] titles, String[] regexes) {
		if (titles.length !=  regexes.length) {
			System.err.println("invalid form template");
			return;
		}
		fields = new ArrayList<Field>();
		for (int i = 0; i < titles.length; i++) {
			fields.add(new Field(titles[i], "", regexes[i]));
		}
	}
	
	public Field getField(String name) {
		for (int i = 0; i < fields.size(); i++) {
			if (name.equals(fields.get(i).getName())) {
				return fields.get(i);
			}
		}
		System.err.println("field not found:" + name);
		return null;
	}
}
