package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass{
  
    @Test(groups={"Regression", "Master"})
    public void verify_account_registration()
    {
        // Home Page
    	logger.info("*****Starting TC001_AccountRegistrationTest******");
        HomePage hp = new HomePage(driver);
        try {
        	
	        hp.clickMyaccount();
	        logger.info("Clicked on Myaccount");
	        
	        hp.clickregister();
	        logger.info("Clicked on register");
	
	        // Registration Page
	        AccountRegistationPage regpage = new AccountRegistationPage(driver);
	        logger.info("Providing customer details");
	        
	        regpage.setFirstName(randomString().toUpperCase());
	        regpage.setlasName(randomString().toUpperCase());
	        regpage.setEmail(randomString() +"@gmail.com");
	        regpage.setTelephone(randomNumber());
	        
	        String password=randomAlphanumeric();
	        
	        regpage.setPassword(password);
	        regpage.setConfirmPwd(password);
	        regpage.setcheckpolicy();
	        regpage.setBtnContinue();

	        // Validation
	        logger.info("validating expected message..");
	        String confmsg = regpage.getMsgConfirmation();
	       
	        if(confmsg.equals("Your Account Has Been Created!"))
	        {
	        	Assert.assertTrue(true);
	        }	
	        //Assert.assertEquals(confmsg, "Your Account Has Been Created!");
	        else
	        {
	        	Assert.assertTrue(false);
	        	logger.error("Test Failed");
	        	logger.debug("Debug logs");	        	
	        }
        }
        catch(Exception e)
        {

        	Assert.fail();
        }
        logger.info("****Finished TC001_AccountRegistrationTest");
        
    }

   
}