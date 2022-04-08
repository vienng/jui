package ui;

import java.io.IOException;

import com.opencsv.exceptions.CsvException;

import data.DataDriven;

public class Main {
	
	public static void main(String[] arg) throws IOException, CsvException {
		String filePath = Main.class.getClassLoader().getResource("test.csv").getPath();
		
		DataDriven dtp = new DataDriven().parse(filePath);
		System.out.println(dtp.getData(0).getField("id").getValue());
		
		DataDriven dtr = new DataDriven().withGenRegex(new String[] {"id", "name"}, new String[] {"[0-9]{3}", "^*.$"});
		System.out.println(dtr.getData(0).getField("id").random());
	}

}
