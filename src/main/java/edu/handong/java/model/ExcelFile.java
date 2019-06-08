package edu.handong.java.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelFile {
	private ArrayList<String> rawData;
	
	public ExcelFile(InputStream in, int cellCount)
	{
		rawData = new ArrayList<String>();
		
		try {
	        Workbook wb = WorkbookFactory.create(in);
	        Sheet sheet = wb.getSheetAt(0);

	        Iterator<Row> it = sheet.rowIterator();
	        boolean headerJump = false;
	        while (it.hasNext())
	        {
	        	Row row = it.next();
	        	if ((int)row.getLastCellNum() != cellCount)
	        	{
	        		continue;
	        	}
	        	if (!headerJump)
	        	{
	        		headerJump = true;
	        		continue;
	        	}
	        	
	        	String rowString = "";
	        	
	        	for(int cn=0; cn<row.getLastCellNum(); cn++) {
	        		Cell cell = row.getCell(cn);
	        		String value = "";
	        		DataFormatter formatter = new DataFormatter();
	        		
                    value += formatter.formatCellValue(cell);
                    System.out.println(value);
                    value = value.replaceAll("\n", "");
                    value = value.replaceAll("\"", "\"\"");
                    value = value.replaceAll("\'", "\'\'");
			        rowString += ", \"" + value + "\"";
        	    }
	        	rawData.add(rowString);
	        }
	        
	    } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public ArrayList<String> getRawData()
	{
		return rawData;
	}
}
