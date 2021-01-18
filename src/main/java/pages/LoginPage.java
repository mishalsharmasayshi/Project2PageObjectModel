package pages;

import org.openqa.selenium.By;

public class LoginPage extends Page {
    
	public HomePage enterCredentials(String userName,String Password) {
		sendKeys("userName_XPATH",userName);
        click("nextBtn_ID");
        sendKeys("password_CSS",Password);
        click("signInBtn_CSS");
        return new HomePage();
	}
	
}