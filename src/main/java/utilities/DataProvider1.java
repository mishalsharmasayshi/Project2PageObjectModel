package utilities;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Hashtable;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


import pages.Page;

public class DataProvider1 extends Page {

	

	@DataProvider(name = "dp1")
	public Object[][] getData(Method m) { // Object[][]
		String sheetName = m.getName();

		Object[][] obj = new Object[eu.getRowCount(sheetName) - 1][1];
		Hashtable<String, String> hMap = null;
		for (int i = 1; i < eu.getRowCount(sheetName); i++) { // for the row count
			hMap = new Hashtable<String, String>();
			for (int j = 0; j < eu.getColumnCount(sheetName); j++) {
				hMap.put(eu.getCellValue(sheetName, 0, j), eu.getCellValue(sheetName, i, j)); // excel starts with 0.
			}
			obj[i - 1][0] = hMap;

		}
		return obj;
	}

}
