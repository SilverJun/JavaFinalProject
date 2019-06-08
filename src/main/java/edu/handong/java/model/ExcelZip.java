package edu.handong.java.model;

import java.io.File;
import java.util.ArrayList;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Enumeration;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipFile;

import edu.handong.java.utils.NoExcelFileExist;
import edu.handong.java.utils.Util;

public class ExcelZip extends Thread {
	private ArrayList<ExcelFile> excels;
	private File file;

	public ExcelZip(String path)
	{
		file = new File(path);
	}
	
	public void run()
	{
		excels = new ArrayList<ExcelFile>(2);
		try 
		{
			ZipFile zipFile;
			try {
				zipFile = new ZipFile(file, "EUC-KR", true);
				
				Enumeration<? extends ZipArchiveEntry> entries = zipFile.getEntries();

			    while(entries.hasMoreElements()){
			    	ZipArchiveEntry entry = entries.nextElement();
			        
			    	String fileName = entry.getName();
			    	if (Util.getExtension(fileName).equals("xlsx"))
			    	{
				    	InputStream stream = zipFile.getInputStream(entry);
				        
				        int cellCount = 0;
				        int type = 0;
				        
				        if (fileName.contains("(요약문)")) cellCount = 7;
				        else if (fileName.contains("(표.그림)")) {cellCount = 5; type=1;}	
				        
				        //System.out.println(file.getName()+fileName);
				        
				        ExcelFile myExcel = new ExcelFile(stream, cellCount);
				        excels.add(type, myExcel);
			    	}
			    }
			    if (excels.isEmpty())
			    {
			    	throw new NoExcelFileExist("");
			    }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		catch(NoExcelFileExist e)
		{
			System.out.println(e.getMessage());
			System.exit(-1);
		}
	}
	
	public File getFile()
	{
		return file;
	}
	
	public ArrayList<ExcelFile> getExcelFiles()
	{
		return excels;
	}
}
