package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountRegistationPage extends BasePage{
	
	WebDriver driver;
	public AccountRegistationPage(WebDriver driver)
	{
		super(driver);
	}
	
	@FindBy(xpath="//input[@id='input-firstname']")
	WebElement txtfirstName;
	
	@FindBy(xpath="//input[@id='input-lastname']")
	WebElement txtlastName;
	
	@FindBy(xpath="//input[@id='input-email']")
	WebElement txtemail;
	
	@FindBy(xpath="//input[@id='input-telephone']")
	WebElement txttelephone;
		
	@FindBy(xpath="//input[@id='input-password']")
	WebElement txtpassword;
	
	@FindBy(xpath="//input[@id='input-confirm']")
	WebElement txtconfirmpassword;
	
	@FindBy(xpath="//input[@name='agree']")
	WebElement chkdpolicy;
	
	@FindBy(xpath="//input[@value='Continue']")
	WebElement btncontinue;
	
	@FindBy(xpath="//h1[normalize-space()='Your Account Has Been Created!']")
	WebElement msgConfirmation;
	
	public void setFirstName(String fname)
	{
		txtfirstName.sendKeys(fname);
	}
	
	public void setlasName(String lname)
	{
		txtlastName.sendKeys(lname);
	}	
	
	public void setEmail(String email)
	{
		txtemail.sendKeys(email);
	}	
	
	public void setTelephone(String telephn)
	{
		txttelephone.sendKeys(telephn);
	}	
		
	public void setPassword(String pwd)
	{
		txtpassword.sendKeys(pwd);
	}
	
	public void setConfirmPwd(String password)
	{
		txtconfirmpassword.sendKeys(password);
	}	
	
	public void setcheckpolicy()
	{
		chkdpolicy.click();
	}	
	
	public void setBtnContinue()
	{
		btncontinue.click();
	}	
	public String getMsgConfirmation()
	{
		try {
			return (msgConfirmation.getText());
		}
		catch(Exception e )
		{
			return (e.getMessage());
		} 
		
	}	
	
}
