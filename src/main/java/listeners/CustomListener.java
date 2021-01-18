package listeners;

import java.util.Arrays;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import pages.Page;

public class CustomListener extends Page implements ITestListener{

	public void onTestStart(ITestResult result) {
		ExtentTest test = rep.createTest(result.getMethod().getMethodName());
		testThread.set(test);
		ITestListener.super.onTestStart(result);
	}

	public void onTestSuccess(ITestResult result) {
		String text = "Test "+result.getMethod().getMethodName()+" has Passed";
		Markup passMarkup =MarkupHelper.createLabel(text, ExtentColor.GREEN);
		testThread.get().pass(passMarkup);
		ITestListener.super.onTestSuccess(result);
	}

	public void onTestFailure(ITestResult result) {
		String failureException = Arrays.toString(result.getThrowable().getStackTrace());
		String markupText = "<details  style="+"text-align:left >" + "<summary style="+"text-align:left>" + "<b>" + "<font color=" + "red>" + "Exception"
				+ "Occured. Click to see : " + "</font>" + "</b>" + "</summary>"
				+ failureException.replaceAll(",", "<br>") + "</details>";
		Markup failMkp = MarkupHelper.createLabel(markupText, ExtentColor.WHITE);
		testThread.get().fail(failMkp);
		ITestListener.super.onTestFailure(result);
	}

	public void onTestSkipped(ITestResult result) {
		String throwableString= Arrays.toString(result.getThrowable().getStackTrace());
		Markup skipLog = MarkupHelper.createLabel("<details  style="+"text-align:left font color ="+"yellow>" + "<summary style="+"text-align:left font color ="+"blue>"+"<b>"+ "Click to see the exception"
		 +"</b>"+"</summary>"+throwableString.replaceAll(",", "<br>")+"</details>", ExtentColor.WHITE);
	   testThread.get().skip(skipLog);
		ITestListener.super.onTestSkipped(result);
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
	}

	public void onTestFailedWithTimeout(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailedWithTimeout(result);
	}

	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		ITestListener.super.onStart(context);
	}

	public void onFinish(ITestContext context) {
		if(rep!=null) {
			rep.flush();
		}
		ITestListener.super.onFinish(context);
	}

}
