package com.assignment.springboot.mongo.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Main {

	public static void main(String[] args) throws ParseException {
		List<String> headers = Arrays.asList("MRN", "DATE");
		List<Sample> file1Samples = readSamples("./src/main/resources/file1.xlsx", headers);
		List<Sample> file2Samples = readSamples("./src/main/resources/file2.xlsx", headers);
		List<Sample> matchedSamples = compare(file1Samples, file2Samples);
		writeSamples(matchedSamples);

	}

	private static void writeSamples(List<Sample>samples) {
		// Blank workbook
		XSSFWorkbook workbook = new XSSFWorkbook();

		// Create a blank sheet
		XSSFSheet sheet = workbook.createSheet("Matched Mrns");

		// This data needs to be written (Object[])
		Map<String, Object[]> data = new TreeMap<String, Object[]>();
		data.put("1", new Object[] { "MRN", "DATE"});
		
		for(int i=0,j=1;i<samples.size();i++) {
			data.put(String.valueOf(++j), new Object[] { samples.get(i).getMrn(), samples.get(i).getDate() });
		}
		// Iterate over data and write to sheet
		Set<String> keyset = data.keySet();

		int rownum = 0;
		for (String key : keyset) {
			// create a row of excelsheet
			Row row = sheet.createRow(rownum++);

			// get object array of prerticuler key
			Object[] objArr = data.get(key);

			int cellnum = 0;

			for (Object obj : objArr) {
				Cell cell = row.createCell(cellnum++);
				if (obj instanceof String) {
					cell.setCellValue((String) obj);
				} else if (obj instanceof Integer) {
					cell.setCellValue((Integer) obj);
				}
			}
		}
		try {
			// Write the workbook in file system
			FileOutputStream out = new FileOutputStream(
					new File("./src/main/resources/matchedMrns.xlsx"));
			workbook.write(out);
			out.close();
			System.out.println("written successfully on disk.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static List<Sample> compare(List<Sample> file1Samples, List<Sample> file2Samples) {
		List<Sample> commonSamples = new ArrayList<>();

		for (Sample file1Sample : file1Samples) {
			for (Sample file2Sample : file2Samples) {
				if (file1Sample.getMrn().equals(file2Sample.getMrn())) {
					commonSamples.add(file2Sample);
				}
			}
		}
		return commonSamples;

	}

	public static List<Sample> readSamples(String filePath, List<String> headers) {
		try {
			FileInputStream file = new FileInputStream(new File(filePath));
			DataFormatter dataFormatter = new DataFormatter();
			// Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			// Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(0);

			// Iterate through each rows one by one
			Iterator<Row> rowIterator = sheet.iterator();
			List<Sample> samples = new ArrayList<>();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				Sample s = new Sample();
				// For each row, iterate through all the columns
				Iterator<Cell> cellIterator = row.cellIterator();

				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					String cellValue = dataFormatter.formatCellValue(cell);
					if (headers.contains(cellValue)) {
						break;
					}
					if (cell.getColumnIndex() == 0) {
						s.setMrn(cellValue);
					} else if (cell.getColumnIndex() == 1) {
						s.setDate(cellValue);
					}
				}
				if (s.getMrn() != null || s.getDate() != null) {
					samples.add(s);
				}
			}
			file.close();
			return samples;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
