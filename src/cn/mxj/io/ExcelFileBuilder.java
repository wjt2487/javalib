/**
 * 
 */
package cn.mxj.io;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * excel 格式的文件生成器
 * 
 * @author fl
 * 
 */
public class ExcelFileBuilder {

	public ExcelFileBuilder() {
		this.workbook = new HSSFWorkbook();
		this.sheet = workbook.createSheet();
	}

	public ExcelFileBuilder(String sheetName) {
		this.workbook = new HSSFWorkbook();
		this.sheet = workbook.createSheet(sheetName);
	}

	protected HSSFWorkbook workbook;

	protected HSSFSheet sheet;

	public HSSFRow appendRow(Object[] rowFields, int rowIndex) {
		return appendRow(rowFields, rowIndex, (short) 0);
	}

	public HSSFRow appendRow(Object[] rowFields, int rowIndex, short cellIndex) {
		HSSFRow row = sheet.createRow(rowIndex);
		for (Object cellData : rowFields) {
			HSSFCell cell = createCell(row, cellIndex++, cellData);
			onCellCreated(cell);
		}
		onRowCreated(row);
		return row;
	}

	public HSSFRow appendRow(ArrayList<Object> rowFields, int rowIndex) {
		return appendRow(rowFields, rowIndex, (short) 0);
	}

	public HSSFRow appendRow(ArrayList<Object> rowFields, int rowIndex,
			short cellIndex) {
		HSSFRow row = sheet.createRow(rowIndex);
		for (Object cellData : rowFields) {
			HSSFCell cell = createCell(row, cellIndex++, cellData);
			onCellCreated(cell);
		}
		onRowCreated(row);
		return row;
	}

	protected HSSFCell createCell(HSSFRow row, short index, Object data) {
		HSSFCell cell = row.createCell(index);
		if (data instanceof Number) {
			cell.setCellValue(((Number) data).doubleValue());
		} else if (data instanceof Date) {
			cell.setCellValue((Date) data);
		} else if (data != null) {
			cell.setCellValue(new HSSFRichTextString(data.toString()));
		} else {
			cell.setCellValue(new HSSFRichTextString(""));
		}
		return cell;
	}

	protected void onRowCreated(HSSFRow row) {
	}

	protected void onCellCreated(HSSFCell cell) {
	}

	/**
	 * 写入外部文件
	 * 
	 * @param filename
	 */
	public boolean writeToFile(String filename) {
		try {
			FileUtil.createFolders(filename, true);
			FileOutputStream fileOut = new FileOutputStream(filename);
			workbook.write(fileOut);
			fileOut.close();
			return true;
		} catch (Exception ex) {
			return false;
		}
	}
}
