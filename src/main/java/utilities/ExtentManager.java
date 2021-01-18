package utilities;

import java.io.File;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
	static String path = System.getProperty("user.dir") + File.separator + "ExtentReports" + File.separator + "extent.html";
	static ExtentReports extent;

	public static ExtentReports createExtentInstance() {
		ExtentSparkReporter esr = new ExtentSparkReporter(path);
		esr.config().setDocumentTitle("Extent Report for Project2");
		esr.config().setEncoding("utf-8");
		esr.config().setTheme(Theme.STANDARD);
		esr.config().setReportName("Banking Extent Report for proj2 Page Object framework");
		extent=new ExtentReports();
		extent.attachReporter(esr);
		extent.setSystemInfo("Report Generator", "Mishal");
		extent.setSystemInfo("Build_No", "22322");
		extent.setSystemInfo("Organisation Name", "WoltersKluwer");
		
		return extent;
	}

}
