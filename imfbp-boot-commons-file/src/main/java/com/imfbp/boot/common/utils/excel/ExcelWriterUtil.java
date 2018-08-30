package com.imfbp.boot.common.utils.excel;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

import javax.imageio.ImageIO;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * 生成导出Excel文件对象
 * 
 * @author samlee
 * 
 */
@SuppressWarnings("static-access")
public class ExcelWriterUtil {
	// 定制浮点数格式
	private static String NUMBER_FORMAT = "#,##0.00";
	// 定制日期格式
	private static String DATE_FORMAT = "m/d/yy"; // "m/d/yy h:mm"

	private OutputStream out = null;

	private HSSFWorkbook workbook = null;

	private HSSFSheet sheet = null;

	private HSSFRow row = null;

	private Boolean isBold;// 字体是否加粗

	private Boolean isBottom = false;// 是否底部对齐居中

	private Boolean iscenter = false;// 是否居中

	private HSSFCellStyle cellStyle = null;

	public HSSFCellStyle getCellStyle() {
		return cellStyle;
	}

	public void setCellStyle(HSSFCellStyle cellStyle) {
		this.cellStyle = cellStyle;
	}

	public Boolean getIscenter() {
		return iscenter;
	}

	public void setIscenter(Boolean iscenter) {
		this.iscenter = iscenter;
	}

	private int width = 0;

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public Boolean getIsBottom() {
		return isBottom;
	}

	public void setIsBottom(Boolean isBottom) {
		this.isBottom = isBottom;
	}

	public Boolean getIsBold() {
		return isBold;
	}

	public void setIsBold(Boolean isBold) {
		this.isBold = isBold;
	}

	public HSSFSheet getSheet() {
		return sheet;
	}

	public void setSheet(HSSFSheet sheet) {
		this.sheet = sheet;
	}

	public ExcelWriterUtil() {
	}

	/**
	 * @Description:新建Workbook
	 * @param out
	 * @author dcm
	 */
	public ExcelWriterUtil(OutputStream out) {
		this.out = out;
		this.workbook = new HSSFWorkbook();
		this.sheet = this.sheetWriter("sheet1");
		this.cellStyle = workbook.createCellStyle(); // 建立新的cell样式
	}
	
	/**
	 * 
	 * @param filePath excel模版文件的路径
	 * @param out
	 */
	public ExcelWriterUtil(String filePath, OutputStream out) {
		try {
			this.out = out;
			this.workbook = new HSSFWorkbook(new POIFSFileSystem(new FileInputStream(new File(filePath))));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Description:修改Workbook
	 * @param fs
	 *            输入流
	 * @param downLoadName
	 *            需要导出的名字
	 * @param sheetName
	 *            需要读取sheet的名字
	 * @author dcm
	 */
	public ExcelWriterUtil(String InFilePath, String OutFilePath,
			String sheetName) throws IOException {
		this.out = new FileOutputStream(OutFilePath);
		this.workbook = new HSSFWorkbook(new POIFSFileSystem(
				new FileInputStream(InFilePath)));
		this.sheet = this.sheetWriterUtil(sheetName);
		this.cellStyle = workbook.createCellStyle(); // 建立新的cell样式
	}

	/**
	 * @Description:新建sheet
	 * @param sheetName
	 * @author dcm
	 */
	public HSSFSheet sheetWriter(String sheetName) {
		return this.workbook.createSheet(sheetName);
	}

	/**
	 * @Description:修改sheet
	 * @param sheetName
	 * @author dcm
	 */
	public HSSFSheet sheetWriterUtil(String sheetName) {
		return this.workbook.getSheet(sheetName);
	}

	/**
	 * 增加一行
	 * 
	 * @param index
	 *            行号
	 */
	public void createRow(int index) {
		this.row = this.sheet.createRow(index);
	}

	/**
	 * @Description: 获得某行
	 * @author dcm
	 */
	public void getRow(int index) {
		this.row = this.sheet.getRow(index);
	}

	/**
	 * @Description:设置行高
	 * @param heighe
	 *            高度（像素） void
	 * @throws
	 * @author dcm
	 */
	public void getRowHeight(int heighe) {
		this.row.setHeight((short) (15.625 * heighe));
	}

	/**
	 * 获取单元格的值
	 * 
	 * @param index
	 *            列号
	 */
	public String getCell(int index) {
		HSSFCell cell = this.row.getCell(index);
		String strExcelCell = "";
		if (cell != null) {
			switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_FORMULA:
				strExcelCell = "FORMULA ";
				break;
			case HSSFCell.CELL_TYPE_NUMERIC: {
				strExcelCell = String.valueOf(cell.getNumericCellValue());
			}
				break;
			case HSSFCell.CELL_TYPE_STRING:
				strExcelCell = cell.getStringCellValue();
				break;
			case HSSFCell.CELL_TYPE_BLANK:
				strExcelCell = "";
				break;
			default:
				strExcelCell = "";
				break;
			}
		}
		return strExcelCell;
	}

	/**
	 * 增加一列标题
	 * 
	 * @param startIndex
	 *            开始行号
	 * @param endIndex
	 *            结束行号
	 * @param start
	 *            开始列号
	 * @param end
	 *            结束列号
	 */
	public void setCell(int startIndex, int endIndex, int start, int end,
			String value, int size) {
		HSSFCell cell = this.row.createCell(start);
		cell.setCellType(cell.CELL_TYPE_STRING);
		HSSFFont font = workbook.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) size);// 设置字体大小
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setBorderBottom(cellStyle.BORDER_THIN); // 下边框
		this.row.setHeight((short) 400);// 设置表格高度

		cellStyle.setFont(font);
		cellStyle.setAlignment(cellStyle.ALIGN_CENTER);// 水平居中
		cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);// 垂直居中
		cell.setCellStyle(cellStyle);
		this.sheet.addMergedRegion(new CellRangeAddress(startIndex, endIndex,
				start, end));
		cell.setCellValue(value);
	}

	/**
	 * 设置单元格
	 * 
	 * @param index
	 *            列号
	 * @param value
	 *            单元格填充值
	 */
	public void setCell(int index, int value) {
		HSSFCell cell = this.row.createCell(index);
		cell.setCellType(cell.CELL_TYPE_NUMERIC);
		HSSFCellStyle cellStyle = workbook.createCellStyle(); // 建立新的cell样式
		cellStyle.setBorderBottom(cellStyle.BORDER_THIN); // 下边框
		cellStyle.setBorderLeft(cellStyle.BORDER_THIN);// 左边框
		cellStyle.setBorderTop(cellStyle.BORDER_THIN);// 上边框
		cellStyle.setBorderRight(cellStyle.BORDER_THIN);// 右边框
		cellStyle.setAlignment(cellStyle.ALIGN_RIGHT);
		cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);// 垂直居中
		cell.setCellStyle(cellStyle);
		cell.setCellValue(value);
	}

	/**
	 * 设置单元格
	 * 
	 * @param index
	 *            列号
	 * @param value
	 *            单元格填充值
	 */
	public void setCell(int index, double value) {
		HSSFCell cell = this.row.createCell(index);
		cell.setCellType(cell.CELL_TYPE_NUMERIC);
		cell.setCellValue(value);
		HSSFDataFormat format = workbook.createDataFormat();
		HSSFCellStyle cellStyle = workbook.createCellStyle(); // 建立新的cell样式
		cellStyle.setBorderBottom(cellStyle.BORDER_THIN); // 下边框
		cellStyle.setBorderLeft(cellStyle.BORDER_THIN);// 左边框
		cellStyle.setBorderTop(cellStyle.BORDER_THIN);// 上边框
		cellStyle.setBorderRight(cellStyle.BORDER_THIN);// 右边框
		cellStyle.setAlignment(cellStyle.ALIGN_RIGHT);
		cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);// 垂直居中
		cellStyle.setDataFormat(format.getFormat(NUMBER_FORMAT)); // 设置cell样式为定制的浮点数格式
		cell.setCellStyle(cellStyle); // 设置该cell浮点数的显示格式
	}

	/**
	 * 设置单元格宽度
	 * 
	 * @param index
	 *            列号
	 * @param value
	 *            单元格填充值
	 * @param width
	 *            单元格宽度
	 */
	public void setCell(int index, String value, int width) {
		HSSFCell cell = this.row.createCell(index);
		cell.setCellType(cell.CELL_TYPE_STRING);
		Font font = workbook.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 10);// 设置字体大小
		if (isBold != null && isBold == true)
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		if (width != 0) {
			this.sheet.setColumnWidth(index, width * 256);
		}
		HSSFCellStyle cellStyle = workbook.createCellStyle(); // 建立新的cell样式
		if (iscenter != null && iscenter == true) {
			cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
		} else {
			cellStyle.setAlignment(cellStyle.ALIGN_LEFT);
		}
		cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);// 垂直居中
		cellStyle.setAlignment(cellStyle.ALIGN_CENTER);// 水平居中
		cellStyle.setBorderBottom(cellStyle.BORDER_THIN); // 下边框
		cellStyle.setBorderLeft(cellStyle.BORDER_THIN);// 左边框
		cellStyle.setBorderTop(cellStyle.BORDER_THIN);// 上边框
		cellStyle.setBorderRight(cellStyle.BORDER_THIN);// 右边框

		cellStyle.setWrapText(true);// 设置自动换行
		cellStyle.setFont(font);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(value);
	}

	/**
	 * 设置单元格宽度、高度
	 * 
	 * @param index
	 *            列号
	 * @param value
	 *            单元格填充值
	 * @param width
	 *            单元格宽度
	 * @param height
	 *            单元格高度
	 */
	public void setCell(int index, String value, int width, int height) {
		HSSFCell cell = this.row.createCell(index);
		cell.setCellType(cell.CELL_TYPE_STRING);
		Font font = workbook.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 10);// 设置字体大小
		if (isBold != null && isBold == true)
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		if (height != 0) {
			this.row.setHeight((short) height);
		}
		if (width != 0) {
			this.sheet.setColumnWidth(index, width * 256);
		}
		HSSFCellStyle cellStyle = workbook.createCellStyle(); // 建立新的cell样式
		if (iscenter != null && iscenter == true) {
			cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
		} else {
			cellStyle.setAlignment(cellStyle.ALIGN_LEFT);
		}
		cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);// 垂直居中
		cellStyle.setBorderBottom(cellStyle.BORDER_THIN); // 下边框
		cellStyle.setBorderLeft(cellStyle.BORDER_THIN);// 左边框
		cellStyle.setBorderTop(cellStyle.BORDER_THIN);// 上边框
		cellStyle.setBorderRight(cellStyle.BORDER_THIN);// 右边框
		cellStyle.setWrapText(true);// 设置自动换行
		cellStyle.setFont(font);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(value);
	}

	/**
	 * 设置单元格
	 * 
	 * @param index
	 *            列号
	 * @param value
	 *            单元格填充值
	 */
	public void setCell(int index, String value) {
		HSSFCell cell = this.row.createCell(index);
		cell.setCellType(cell.CELL_TYPE_STRING);
		Font font = workbook.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 10);// 设置字体大小
		if (isBold != null && isBold == true)
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

		cellStyle.setBorderBottom(cellStyle.BORDER_THIN); // 下边框
		cellStyle.setBorderLeft(cellStyle.BORDER_THIN);// 左边框
		cellStyle.setBorderTop(cellStyle.BORDER_THIN);// 上边框
		cellStyle.setBorderRight(cellStyle.BORDER_THIN);// 右边框
		cellStyle.setAlignment(cellStyle.ALIGN_LEFT);
		if (width != 0) {
			this.sheet.setColumnWidth(index, width * 256);
		}
		if (isBottom)
			cellStyle.setAlignment(HSSFCellStyle.VERTICAL_BOTTOM);// 垂直底部
		else
			cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);// 垂直居中
		cellStyle.setWrapText(true);// 设置自动换行
		cellStyle.setFont(font);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(value);
	}

	/**
	 * 设置单元格
	 * 
	 * @param index
	 *            列号
	 * @param value
	 *            单元格填充值
	 */
	public void setCell_border_bottom(int index, String value) {
		HSSFCell cell = this.row.createCell(index);
		cell.setCellType(cell.CELL_TYPE_STRING);
		Font font = workbook.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 10);// 设置字体大小
		if (isBold != null && isBold == true)
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		HSSFCellStyle cellStyle = workbook.createCellStyle(); // 建立新的cell样式
		cellStyle.setBorderBottom(cellStyle.BORDER_THIN); // 下边框
		cellStyle.setAlignment(cellStyle.ALIGN_LEFT);
		if (width != 0) {
			this.sheet.setColumnWidth(index, width * 256);
		}
		if (isBottom)
			cellStyle.setAlignment(HSSFCellStyle.VERTICAL_BOTTOM);// 垂直底部
		else
			cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);// 垂直居中
		cellStyle.setWrapText(true);// 设置自动换行
		cellStyle.setFont(font);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(value);
	}

	/**
	 * 设置单元格
	 * 
	 * @param index
	 *            列号
	 * @param value
	 *            单元格填充值
	 */
	public void setCell(int index, Calendar value) {
		HSSFCell cell = this.row.createCell(index);
		cell.setCellValue(value.getTime());
		HSSFCellStyle cellStyle = workbook.createCellStyle(); // 建立新的cell样式
		cellStyle.setBorderBottom(cellStyle.BORDER_THIN); // 下边框
		cellStyle.setBorderLeft(cellStyle.BORDER_THIN);// 左边框
		cellStyle.setBorderTop(cellStyle.BORDER_THIN);// 上边框
		cellStyle.setBorderRight(cellStyle.BORDER_THIN);// 右边框
		cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);// 垂直居中
		cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat(DATE_FORMAT)); // 设置cell样式为定制的日期格式
		cell.setCellStyle(cellStyle); // 设置该cell日期的显示格式
	}

	/**
	 * @Description:插入图片
	 * @param StartRow
	 *            第几行开始
	 * @param startCol
	 *            第几列开始
	 * @param endRow
	 *            第几行结束
	 * @param endCol
	 *            第几列结束
	 * @param path
	 * @param isResize
	 *            是否绝对值
	 * @throws IOException
	 *             void
	 * @throws
	 * @author dcm
	 */
	public void setPatriarch(int StartRow, int startCol, int endRow,
			int endCol, int dx1, int dy1, int dx2, int dy2, String path)
			throws IOException {
		try {
			ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
			File file = new File(path);
			if (file.exists()) {
				BufferedImage bufferImg = ImageIO.read(file);
				ImageIO.write(bufferImg, "jpg", byteArrayOut);
				HSSFPatriarch patriarch = this.sheet.createDrawingPatriarch();
				HSSFClientAnchor anchor = new HSSFClientAnchor(dx1, dy1, dx2,
						dy2, (short) startCol, StartRow, (short) endCol, endRow);
				patriarch.createPicture(anchor, this.workbook.addPicture(
						byteArrayOut.toByteArray(),
						HSSFWorkbook.PICTURE_TYPE_JPEG));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * @Description:插入图片
	 * @param StartRow
	 *            第几行开始
	 * @param startCol
	 *            第几列开始
	 * @param endRow
	 *            第几行结束
	 * @param endCol
	 *            第几列结束
	 * @param path
	 * @param sheet
	 * @param isResize
	 *            是否绝对值
	 * @throws IOException
	 *             void
	 * @throws
	 * @author dcm
	 */
	public void setPatriarch(int StartRow, int startCol, int endRow,
			int endCol, int dx1, int dy1, int dx2, int dy2, String path, HSSFSheet sheet)
					throws IOException {
		try {
			ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
			File file = new File(path);
			if (file.exists()) {
				BufferedImage bufferImg = ImageIO.read(file);
				ImageIO.write(bufferImg, "jpg", byteArrayOut);
				HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
				HSSFClientAnchor anchor = new HSSFClientAnchor(dx1, dy1, dx2,
						dy2, (short) startCol, StartRow, (short) endCol, endRow);
				patriarch.createPicture(anchor, this.workbook.addPicture(
						byteArrayOut.toByteArray(),
						HSSFWorkbook.PICTURE_TYPE_JPEG));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @Description:合并单元格
	 * @param startIndex
	 *            开始行号
	 * @param endIndex
	 *            结束行号
	 * @param start
	 *            开始列号
	 * @param end
	 *            结束列号
	 * @author dcm
	 */
	public void addMergedRegion(int startIndex, int endIndex, int start, int end) {
		this.sheet.addMergedRegion(new CellRangeAddress(startIndex, endIndex,
				start, end));
	}

	/**
	 * @Description:删除行
	 * @param row
	 *            void
	 * @throws
	 * @author dcm
	 */
	public void removeRow(int row) {
		HSSFRow row1 = sheet.getRow(row);
		this.sheet.removeRow(row1);
	}

	/**
	 * 导出Excel文件
	 * 
	 * @throws IOException
	 */
	public void export() throws FileNotFoundException, IOException {
		try {
			workbook.write(out);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			throw new IOException(" 生成导出Excel文件出错! ", e);
		} catch (IOException e) {
			throw new IOException(" 写入Excel文件出错! ", e);
		}
	}

	public HSSFWorkbook getWorkbook() {
		return workbook;
	}

	public void setWorkbook(HSSFWorkbook workbook) {
		this.workbook = workbook;
	}

	public static void main(String[] args) {
		ExcelWriterUtil e = new ExcelWriterUtil();
		String filePath = "E:/auditList.xls";
		String filePathString = "E:/test.xls";
		String picturePath = "E:/Tulips.jpg";
		try {
			e = new ExcelWriterUtil(filePath, filePathString, "Sheet1");
			e.setPatriarch(2, 9, 12, 11, 0, 0, 0, 0, picturePath);
			e.export();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println(1 % 3);
		// File f = new File("D:\\test.xls");
		// ExcelWriterUtil e = new ExcelWriterUtil();
		//
		// try {
		// e = new ExcelWriterUtil(new FileOutputStream(f));
		// e.createRow(0);
		// e.setCell(0,2,0,0,"sadfasdfads",10);
		// } catch (FileNotFoundException e1) {
		// e1.printStackTrace();
		// }
		//
		// e.createRow(0);
		// e.setCell(0, "试题编码 ");
		// e.setCell(1, "题型");
		// e.setCell(2, "分值");
		// e.setCell(3, "难度");
		// e.setCell(4, "级别");
		// e.setCell(5, "知识点");
		// e.sheet = e.sheetWriter("sheet2");
		// e.createRow(1);
		// e.setCell(0, "t1");
		// e.setCell(1, 1);
		// e.setCell(2, 3.0);
		// e.setCell(3, 1);
		// e.setCell(4, "重要");
		// e.setCell(5, "专业");
		// // try {
		// // e.setPatriarch(2, 3, 5, 6, "E:\\36.jpg");
		// // } catch (IOException e1) {
		// // e1.printStackTrace();
		// // }
		//
		// try {
		// e.export();
		// System.out.println(" 导出Excel文件[成功] ");
		// } catch (IOException ex) {
		// System.out.println(" 导出Excel文件[失败] ");
		// ex.printStackTrace();
		// }
	}

}