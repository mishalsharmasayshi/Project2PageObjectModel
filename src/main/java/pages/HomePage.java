package pages;

import org.openqa.selenium.By;

import pages.crmPages.ZohoCRMHomepage;

public class HomePage extends Page {

	 public void isCRMPresent() {
		 if(isElementPresent("crm_CSS"))
			 System.out.println("Element is present");
		 else
			 System.out.println("Element is not present");
	 }
	 
	 public ZohoCRMHomepage clickCrm() {
		 click("crm_CSS");
		 return  new ZohoCRMHomepage();
	 }
}
