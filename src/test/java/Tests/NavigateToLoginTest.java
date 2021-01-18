package Tests;

import org.testng.annotations.Test;

import base.Base;
import pages.LandingPage;

public class NavigateToLoginTest extends Base {
     @Test
	public void navigateToLogin() {
		LandingPage lp = new LandingPage();
		lp.login();
		}
	
}
