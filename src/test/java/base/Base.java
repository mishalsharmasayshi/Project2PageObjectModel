package base;

import org.testng.annotations.AfterSuite;

import pages.Page; 
public class Base {
    @AfterSuite
	public void tearDown() {
		Page.quit();
	}
}
