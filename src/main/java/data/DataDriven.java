package data;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

public class DataDriven {
	private List<Form> records;

	public DataDriven() {
		records = new ArrayList<Form>();
	}

	public DataDriven parse(String csvFilePath) throws IOException, CsvException {
		FileReader file = new FileReader(csvFilePath);
		CSVReader reader = new CSVReader(file);
		List<String[]> rows = reader.readAll();
		reader.close();

		if (rows.size() <= 1) {
			System.out.println("[warn] file is empty!");
			return this;
		}

		String[] headers = rows.get(0);
		for (int rowIdx = 1; rowIdx < rows.size(); rowIdx++) {

			List<Field> fields = new ArrayList<Field>(rows.get(rowIdx).length);
			for (int i = 0; i < rows.get(rowIdx).length; i++) {
				String[] cells = rows.get(rowIdx);

				fields.add(i, new Field(headers[i], cells[i], ""));
			}
			
			records.add(new Form(fields));
		}
		return this;
	}

	public DataDriven withGenRegex(String[] headers, String[] regexes) {
		records = new ArrayList<Form>(1);
		records.add(new Form(headers, regexes));
		return this;
	}

	public Form getData(int index) {
		return records.get(index);
	}
	
	public Field getField(String name) {
		if (records.isEmpty()) {
			return new Form(null).getField(name);
		}
		return records.get(0).getField(name);
	}
}