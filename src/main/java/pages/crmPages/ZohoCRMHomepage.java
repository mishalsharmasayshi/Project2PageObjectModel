package pages.crmPages;

import pages.Page;

public class ZohoCRMHomepage extends Page {

	public void signOut() throws InterruptedException {
		tm.goToAccounts();
		Thread.sleep(4000);
		tm.goToLeads();
		Thread.sleep(4000);
		tm.goToHome();
		Thread.sleep(4000);
		click("profileButton_XPATH");
		click("signoutButton_XPATH");
		click("reSignIn_XPATH");
		
	}
}
