package Tests;

import java.util.Hashtable;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;

import base.Base;
import pages.HomePage;
import pages.LandingPage;
import pages.LoginPage;
import pages.Page;
import pages.crmPages.ZohoCRMHomepage;
import utilities.DataProvider1;

public class LoginTest extends Base{
	@Test(dataProviderClass=DataProvider1.class,dataProvider="dp1")
	public void LoginTest1(Hashtable<String,String> hMap) throws InterruptedException {
		
		LoginPage loPage = new LoginPage();
		HomePage hPage = loPage.enterCredentials(hMap.get("Username"),hMap.get("Password"));
		//hPage.isCRMPresent();
		ZohoCRMHomepage zCrmPage = hPage.clickCrm();
		zCrmPage.signOut();
		
	}
	
	
}
