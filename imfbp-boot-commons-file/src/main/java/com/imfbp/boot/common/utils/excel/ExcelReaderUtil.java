package com.imfbp.boot.common.utils.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 读取Excel文件对象
 * @author QuanJianjun
 */
public class ExcelReaderUtil {
	
	private static Logger logger = Logger.getLogger("MyCLASS.class");
	// 工作薄，也就是一个excle文件
	private Workbook wb = null;// book [includes sheet]
	// 一个excle文件可以有多个sheet
	private Sheet sheet = null;
	// 代表了表的第一行，也就是列名
	private Row row = null;
	// 一个excel有多个sheet，这是其中一个
	private int sheetNum = 0; 
	// 一个sheet中可以有多行，这里应该是给行数的定义
	private int rowNum = 0;
	// 文件输入流
	private FileInputStream fis = null;
	// 指定文件
	private File file = null;
	
	public ExcelReaderUtil() {
		
	}

	/**
	 * 
	 * @param path
	 * @throws Exception
	 */
	public ExcelReaderUtil(String path) throws Exception{
		File file = new File(path);
		this.file = file;
		create();
	}
	
	/**
	 * 
	 * @param file
	 * @throws Exception
	 */
	public ExcelReaderUtil(File file) throws Exception {
		this.file = file;
		create();
	}
	
	/**
	 * 
	 * @param file
	 * @throws Exception
	 */
	public ExcelReaderUtil(InputStream in,String fileName) throws Exception {
		create(in,fileName);
	}

	/**
	 * 创建 excelType
	 * @throws Exception
	 */
	public void create(){
		FileInputStream is = null;
	    try {
			String fileName = file.getName();
			String prefix=fileName.substring(fileName.lastIndexOf(".")+1);
			if (prefix.equals("xls")) {// 2003
				is = new FileInputStream(file);
				wb = new HSSFWorkbook(is);
				this.fis = is;
			} else if (prefix.equals("xlsx")){// 2007
				is = new FileInputStream(file);
				wb = new XSSFWorkbook(is);
				this.fis = is;
			}
		} catch (IOException e) {
			logger.error("创建excel workbook对象失败！",e);
		}finally{
			if(is!=null){
				try {
					is.close();
					is = null;
				} catch (IOException e) {
					logger.error("关闭FileInputStream失败！",e);
				}
			}
		}
	}
	
	/**
	 * 创建 excelType
	 * @throws Exception
	 */
	public void create(InputStream is,String fileName){
	    try {
			String prefix=fileName.substring(fileName.lastIndexOf(".")+1);
			if (prefix.equals("xls")) {// 2003
				wb = new HSSFWorkbook(is);
			} else if (prefix.equals("xlsx")){// 2007
				wb = new XSSFWorkbook(is);
			}
		} catch (IOException e) {
			logger.error("创建excel workbook对象失败！",e);
		}finally{
			if(is!=null){
				try {
					is.close();
					is = null;
				} catch (IOException e) {
					logger.error("关闭FileInputStream失败！",e);
				}
			}
		}
	}
	
	/**
	 * 返回sheet表数目
	 * @return int
	 */
	public int getSheetCount() {
		int sheetCount = -1;
		if(wb!=null){
			sheetCount = wb.getNumberOfSheets();
		}
		return sheetCount;
	}
	
	

	/**
	 * sheetNum下的记录行数
	 * @return int
	 */
	public int getRowCount() {
		return getRowCount(this.sheetNum);
	}
	
	/**
	 * 读取指定sheetNum的rowCount
	 * @param sheetNum
	 * @return int
	 */
	public int getRowCount(int sheetNum) {
		Sheet sheet = wb.getSheetAt(sheetNum);
		int rowCount = -1;
		rowCount = sheet.getLastRowNum();
		return rowCount;
	}
	
	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public void setSheetNum(int sheetNum) {
		this.sheetNum = sheetNum;
	}

	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * 得到指定行的内容
	 * @param lineNum
	 * @return String[]
	 */
	public String[] readExcelLine(int lineNum) {
		return readExcelLine(this.sheetNum, lineNum);
	}

	/**
	 * 指定工作表和行数的内容
	 * @param sheetNum
	 * @param lineNum
	 * @return String[]
	 */
	public String[] readExcelLine(int sheetNum, int lineNum) {
		if (sheetNum < 0 || lineNum < 0)
			return null;
		String[] strExcelLine = null;
			sheet = wb.getSheetAt(sheetNum);
			row = sheet.getRow(lineNum);
			int cellCount = row.getLastCellNum();
			strExcelLine = new String[cellCount];
			for (int i = 0; i < cellCount; i++) {
				strExcelLine[i] = readStringExcelCell(lineNum, i);
			}
		
		return strExcelLine;
	}

	/**
	 * 读取指定列的内容
	 * 
	 * @param cellNum
	 * @return String
	 */
	public String readStringExcelCell(int cellNum) {
		return readStringExcelCell(this.rowNum, cellNum);
	}

	/**
	 * 指定行和列编号的内容
	 * 
	 * @param rowNum
	 * @param cellNum
	 * @return String
	 */
	public String readStringExcelCell(int rowNum, int cellNum) {
		return readStringExcelCell(this.sheetNum, rowNum, cellNum);
	}

	/**
	 * 指定工作表、行、列下的内容
	 * 
	 * @param sheetNum
	 * @param rowNum
	 * @param cellNum
	 * @return String
	 */
	public String readStringExcelCell(int sheetNum, int rowNum, int cellNum) {
		if (sheetNum < 0 || rowNum < 0)
			return "";
		String strExcelCell = "";
		try {
			sheet = wb.getSheetAt(sheetNum);
			row = sheet.getRow(rowNum);

			if (row.getCell((short) cellNum) != null) { // add this condition
				// judge
				switch (row.getCell((short) cellNum).getCellType()) {
				case Cell.CELL_TYPE_FORMULA:
					strExcelCell = "FORMULA ";
					break;
				case Cell.CELL_TYPE_NUMERIC: {
					strExcelCell = String.valueOf(row.getCell((short) cellNum)
							.getNumericCellValue());
				}
					break;
				case Cell.CELL_TYPE_STRING:
					strExcelCell = row.getCell((short) cellNum)
							.getStringCellValue();
					break;
				case Cell.CELL_TYPE_BLANK:
					strExcelCell = "";
					break;
				default:
					strExcelCell = "";
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strExcelCell;
	}

//	public static void main(String args[]) {
//		File file = new File("D:\\市场数据导入模版.xls");
//		try {
//			ExcelReaderUtil excelReaderUtil = new ExcelReaderUtil(file);
//			excelReaderUtil.setSheetNum(0);
//			String[] rowArr = excelReaderUtil.readExcelLine(0);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	public FileInputStream getFis() {
		return fis;
	}

	public void setFis(FileInputStream fis) {
		this.fis = fis;
	}
}