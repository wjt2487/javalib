/**
 * 
 */
package cn.mxj.io;

import java.io.File;
import java.util.Date;

import jxl.BooleanCell;
import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;

/**
 * Excel 文件阅读器
 * 
 * @author fl
 * 
 */
public class ExcelReader {

	/**
	 * 创建一个阅读器
	 * 
	 * @param filename
	 *            需要读取的 Excel 文件的完整路径
	 */
	public ExcelReader(String filename) {
		this.filename = filename;
	}

	protected String filename;

	protected Workbook workbook;

	protected Sheet currentSheet;

	protected boolean fileOpened;

	public boolean isFileOpened() {
		return this.fileOpened;
	}

	public int getRowsCount() {
		if (this.currentSheet != null) {
			return this.currentSheet.getRows();
		} else {
			return 0;
		}
	}

	public int getColumnsCount() {
		if (this.currentSheet != null) {
			return this.currentSheet.getColumns();
		} else {
			return 0;
		}
	}

	public boolean open() {
		try {
			this.workbook = Workbook.getWorkbook(new File(filename));
			this.currentSheet = workbook.getSheet(0);
			this.fileOpened = true;
		} catch (Exception ex) {
			this.fileOpened = false;
			AppLogger.getInstance().exception(ex);
		}
		return this.fileOpened;
	}

	public void close() {
		if (this.workbook != null) {
			this.workbook.close();
		}
	}

	public void setCurrentSheet(int index) {
		if (fileOpened && index >= 0 && index < workbook.getSheets().length) {
			this.currentSheet = workbook.getSheet(index);
		}
	}

	public Cell getCell(int columnIndex, int rowIndex) {
		try {
			return this.currentSheet.getCell(columnIndex, rowIndex);
		} catch (Exception ex) {
			return null;
		}
	}

	public String getString(int columnIndex, int rowIndex) {
		Cell c = this.getCell(columnIndex, rowIndex);
		return (c == null) ? "" : c.getContents();
	}

	public Date getDate(int columnIndex, int rowIndex) {
		Cell c = this.getCell(columnIndex, rowIndex);
		if (c != null && c.getType() == CellType.DATE) {
			return ((DateCell) c).getDate();
		} else {
			return null;
		}
	}

	public boolean getBoolean(int columnIndex, int rowIndex) {
		Cell c = this.getCell(columnIndex, rowIndex);
		if (c != null && c.getType() == CellType.BOOLEAN) {
			return ((BooleanCell) c).getValue();
		} else {
			return false;
		}
	}

	public double getNumber(int columnIndex, int rowIndex) {
		Cell c = this.getCell(columnIndex, rowIndex);
		if (c != null && c.getType() == CellType.NUMBER) {
			return ((NumberCell) c).getValue();
		} else {
			return 0;
		}
	}

}
