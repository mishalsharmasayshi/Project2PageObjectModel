package pages;

import org.openqa.selenium.By;

public class LandingPage extends Page{

	public LoginPage login() {
			click("loginLink_XPATH");
			return new LoginPage();
			
	}
	
}
