package data;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

public class DataDriven {
	private List<Form> formDatas;

	public DataDriven() {
		formDatas = new ArrayList<Form>();
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

			formDatas.add(new Form(fields));
		}
		return this;
	}

	public DataDriven withGenRegex(String[] headers, String[] regexes) {
		formDatas = new ArrayList<Form>(1);
		formDatas.add(new Form(headers, regexes));
		return this;
	}

	public Form getData(int index) {
		return formDatas.get(index);
	}

	public Form randomData() {
		if (formDatas.isEmpty()) {
			return null;
		}
		Form form = formDatas.get(0);
		form.getFields().forEach((field) -> {
			field.random();
		});
		return form;
	}
	
	public List<Form> getAllData() {
		return formDatas;
	}
}