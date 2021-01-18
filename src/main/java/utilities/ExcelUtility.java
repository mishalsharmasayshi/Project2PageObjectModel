package utilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {

	File file;
	String path;
	XSSFWorkbook wb;
	XSSFSheet wSheet;
	static int rowIndexCount = 0;
	ArrayList<String> rowCells = new ArrayList<String>();

	public ExcelUtility(String path) {
		this.path=path;
		this.file = new File(path);
		try {
			wb = new XSSFWorkbook(file);
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public XSSFSheet getSheet(String sheetName) {
		if (!isSheetPresent(sheetName)) {
			System.out.println("Sheet not present");
			return null;
		} else {

			return wb.getSheetAt(wb.getSheetIndex(sheetName));
		}
	}

	public ArrayList<String> getTestCaseRow(String sheetName, int columnNo, String columnValue) {
		if (getSheet(sheetName) == null) {
			System.out.println("Sheet missing");

		} else {
			if (!(isSheetEmpty(getSheet(sheetName)))) {
				wSheet = getSheet(sheetName);
				for (Row r : wSheet) {
					Cell c = r.getCell(columnNo, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
					if (returnCellValueOnCellType(c).equalsIgnoreCase(columnValue)) {
						for (int i = 0; i < r.getLastCellNum(); i++) {
							Cell cInternal = r.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
							rowCells.add(returnCellValueOnCellType(cInternal));
						}
						break;
					}

				}
				System.out.println("The TC  " + columnValue + " is not present in Column  " + columnNo);
			} else {
				System.out.println("The Sheet " + sheetName + " is present but empty");

			}
		}
		return rowCells;

	}

	public void printRowList(ArrayList<String> alist) {
		int i = 0;
		for (String a : alist) {
			System.out.println("Value for column " + i++ + " is " + a);

		}
	}

	public String getCellValue(String sheetName, int rowNo, int columnNo) {
		Cell cellT = getSheet(sheetName).getRow(rowNo).getCell(columnNo, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
		return returnCellValueOnCellType(cellT);

	}

	public int getColumnIndex(String sheetName, String columnName) {
		if (getSheet(sheetName) == null) { // Checks if the sheet is present or not by calling
			System.out.println("Sheet not present"); // getSheet methods which in turn calls
			return -1; // isSheetMethods and returns null if sheet not present.
		} else { // the code entering else part means sheet is present.

			if (!(isSheetEmpty(getSheet(sheetName)))) { // checks if the sheet has rows with values or not by calling
														// isSheetEmpty method
				int columnNum = 0; // the code here means sheet has data
				wSheet = getSheet(sheetName);
				Row r = wSheet.getRow(rowIndexCount); // gets the first row which has data. rowIndexCount is global
														// static varable set in isSheetEmpty iteration
				for (int i = 0; i < r.getLastCellNum(); i++) { // traverse through the row ,cell by cell
					Cell c = r.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK); // read each cell and null as
																						// blanks.
					if (returnCellValueOnCellType(c).equalsIgnoreCase(columnName)) { // called method to get string
																						// value
						columnNum = i;
						return i;
					}
				}
				System.out.println("Column " + columnName + " is not present in the excel");
				return -1; // if for loop ends means the column name is not in the sheet.

			} else {
				System.out.println("The Sheet " + sheetName + " is present but empty");
				return -1; // if sheet is empty means its present but has not row with data.
			}
		}
	}

	public boolean isRowEmpty(XSSFSheet wSheet, Row r) {

		if (r == null || r.getLastCellNum() < 0) {
			System.out.println("The row is blank ");
			return true;
		} else {
			for (Cell c : r) { // traverses each cell of the row and checks if any cell is not null or type is
								// not blank
				if (c != null && c.getCellType() != CellType.BLANK) // meaning it has data and if found breaks the loop
																	// returning false.
					System.out.println("Row is not blank traversing through cells");
				return false;
			}
			return true; // if the loop is done then it returns true , meaning the row is empty.
		}
	}

	public boolean isSheetEmpty(XSSFSheet wSheet) {
		for (int i = wSheet.getTopRow(); i < wSheet.getLastRowNum(); i++) { // gets top row of sheet and traverses till
																			// last.
			if (!(isRowEmpty(wSheet, wSheet.getRow(i)))) { // calls isRowEmpty() with every row
				System.out.println("The first non empty row in the sheet is : " + i);// for the first row which returns
																						// false on isRowEmpty() , it
																						// sets
				this.rowIndexCount = i; // the rowIndexCount to tha value.
				return false; // then returns false, meaning the sheet is not empty.
			}
		}
		return true; // if loops ends meaning every row is empty so returns true.
	}

	public int getRowCount(String sheetName) {
		if (!isSheetPresent(sheetName)) {
			System.out.println("Sheet not present");
			return -1;
		} else {
			wSheet = getSheet(sheetName);
			return wSheet.getLastRowNum() + 1;

		}

	}

	public int getColumnCount(String sheetName) {
		if (getSheet(sheetName) == null) {
			System.out.println("Sheet missing");
			return -1;
		} else {
			wSheet = getSheet(sheetName);
			Row r = wSheet.getRow(wSheet.getTopRow());
			return r.getLastCellNum();
		}

	}

	public String returnCellValueOnCellType(Cell cellT) {
		String tempCellValue = "";
		if (cellT.getCellType() != CellType.BLANK) {
			if (cellT.getCellType() == CellType.NUMERIC) {
				return String.valueOf(cellT.getNumericCellValue());
			} else if (cellT.getCellType() == CellType.STRING) {
				return cellT.getStringCellValue();
			} else if (cellT.getCellType() == CellType.BOOLEAN) {
				return String.valueOf(cellT.getBooleanCellValue());
			} else if (cellT.getCellType() == CellType.FORMULA) {
				return String.valueOf(cellT.getCellFormula());
			}

		} else {
			System.out.println("Cell is blank");
			tempCellValue = "";
			return tempCellValue;

		}
		System.out.println("Cell type match not found");
		return tempCellValue;
	}

	public boolean isSheetPresent(String sheetName) {

		if (wb.getSheet(sheetName) != null)
			return true;
		else
			return false;

	}

	public boolean isSheetPresentUsingIndex(String sheetName) {
		System.out.println("Sheet Index " + wb.getSheetIndex(sheetName));
		if (wb.getSheetIndex(sheetName) != -1)
			return true;
		else
			return false;

	}

	public boolean addSheet(String sheetName) {

		if (isSheetPresent(sheetName)) {
			System.out.println("Sheet already present");
			return false;
		}

		FileOutputStream fs;
		try {
			wb.createSheet(sheetName);
			fs = new FileOutputStream(file);

			System.out.println("Index of new sheet" + sheetName + " is " + wb.getSheetIndex(sheetName));
			wb.write(fs);
			fs.close();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public boolean removeSheet(String sheetName) {

		if (!isSheetPresent(sheetName)) {
			System.out.println("Sheet not present");
			return false;
		}

		FileOutputStream fs;
		try {
			int sIndex = wb.getSheetIndex(sheetName);
			wb.removeSheetAt(sIndex);
			fs = new FileOutputStream(file);

			System.out.println("Sheet at index " + sIndex + " named" + sheetName + " is removed");
			wb.write(fs);
			fs.close();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;

	}

}
