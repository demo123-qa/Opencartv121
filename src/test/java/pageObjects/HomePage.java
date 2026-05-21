package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage{
	
	WebDriver driver;
	public HomePage(WebDriver driver)
	{
		super(driver);
	}
	@FindBy(xpath="//span[normalize-space()='My Account']")
	WebElement linkMyaccount;
	//linkMyaccount=mywait.until(ExpectedConditions.elementToBeClickable(linkMyaccount));
	
	
	@FindBy(xpath="//a[normalize-space()='Register']")
	WebElement linkRegister; 
	
	@FindBy(xpath="//a[normalize-space()='Login']")   //login link added in step-5
	WebElement linklogin; 	
	
	
	
	public void clickMyaccount()
	{
		linkMyaccount.click();
	} 
	
	public void clickregister()
	{
		linkRegister.click();
	}
	public void clicklogin()
	{
		linklogin.click();
	}
}
