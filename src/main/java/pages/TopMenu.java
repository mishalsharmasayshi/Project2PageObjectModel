package pages;

import org.openqa.selenium.WebDriver;

import pages.crmPages.Accounts;
import pages.crmPages.Leads;
import pages.crmPages.ZohoCRMHomepage;

public class TopMenu {
	WebDriver driver;
    TopMenu(WebDriver driver){
    	this.driver=driver;
    }
	public ZohoCRMHomepage goToHome() {
		Page.click("home_Link_CSS");
		return new ZohoCRMHomepage();
	}
	
	public Leads goToLeads() {
		Page.click("leads_Link_CSS");
		return new Leads();
	}
	
	public Accounts goToAccounts() {
		Page.click("accounts_Link_CSS");
		return new Accounts();
	}
//	
//	public void goTohome() {
//		Page.click(Page.OR.getProperty(""));
//		return new HomePage();
//	}
//	
//	public void goTohome() {
//		Page.click(Page.OR.getProperty(""));
//		return new HomePage();
//	}
//	
//	public void goTohome() {
//		Page.click(Page.OR.getProperty(""));
//		return new HomePage();
//	}
//	
	
	
}
