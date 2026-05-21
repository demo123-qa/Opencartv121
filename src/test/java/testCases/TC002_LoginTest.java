package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass {
	@Test(groups={"Sanity","Master"})
	public void verify_login()
	{
		logger.info("****TC002_LoginTest Started****");
		try {
			//going to home page
			HomePage hp = new HomePage(driver);
			hp.clickMyaccount();
			hp.clicklogin();
			
			//going to Login page 
			LoginPage lp= new LoginPage(driver);
			lp.setEmail(p.getProperty("email"));
			lp.setPassword(p.getProperty("password"));    //email and password not java variables those are belongs to config properties that's why specified with " "
			lp.clickLogin();
			
			//Going to My Account Page
			MyAccountPage macc=new MyAccountPage(driver);
			boolean targetPage =macc.isMyAccountExists();
			Assert.assertTrue(targetPage);    //Assert.assertEquals(targetPage, true "Login Failed(to know login failed we wrote this)");   // we can write both the ways to check my account page is accessible or not 
		}
		catch (Exception e)
		{
			Assert.fail();
		}
		logger.info("*** Finishing TC002_LoginTest Started***");
	}
}
