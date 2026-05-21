package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import Utilities.DataProviders;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

/*Data is valid - login success - test pass - logout
 * Data is valid - login failed - test failed
 * 
 * Data is invalid - login success - test failed -logout
 * data is invalid - login failed -test pass
 */
// TC is having positive and negative tcs

public class TC003_LoginDDT extends BaseClass{
	
	@Test(dataProvider="LoginData", dataProviderClass=DataProviders.class, groups="Datadriven")  // getting data from different class //dataProviderClass=DataProviders.class has to be mention if data providers are created in another class or another package if you check Dataproviders.java in Utilities folder, there mentioned only data provider="login", here we are using that provided data   
	public void verify_loginDDT(String email, String pwd, String exptdresult) throws InterruptedException
	{
		
		logger.info("*** Started TC003_LoginDDT***"); 
		//going to home page
		try
		{
			driver.get(p.getProperty("appURL"));  //chatgpt
			
			HomePage hp = new HomePage(driver);
			hp.clickMyaccount();
			hp.clicklogin();
			
			//going to Login page 
			LoginPage lp= new LoginPage(driver);
			lp.setEmail(email);
			lp.setPassword(pwd);    
			lp.clickLogin();
			
			//Going to My Account Page
			MyAccountPage macc=new MyAccountPage(driver);
			boolean targetPage =macc.isMyAccountExists();
			//Assert.assertTrue(targetPage); 
			
			/*Data is valid - login success - test pass - logout
			 * Data is valid - login failed - test failed
			 * 
			 * Data is invalid - login success - test failed -logout
			 * data is invalid - login failed -test pass
			 */
			if (exptdresult.equalsIgnoreCase("Valid"))
			{
				if(targetPage==true)
				{
					Assert.assertTrue(true);
					macc.clickLogout();
					driver.get(p.getProperty("appURL"));
				}
				else
				{
					Assert.assertTrue(false);
				}
			}
			else if (exptdresult.equalsIgnoreCase("Invalid"))
			{
				if(targetPage==true)
				{
					macc.clickLogout();
					Assert.assertTrue(false);
				}
				else
				{
					Assert.assertTrue(true);
				}	
				
			}
		}
		catch(Exception e)
		{
			Assert.fail();
		}
		Thread.sleep(2000);
		logger.info("*** Finished TC003_LoginDDT***"); 
	}

}
