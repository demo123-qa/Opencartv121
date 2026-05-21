package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;
import org.apache.commons.lang3.RandomStringUtils;


public class BaseClass {
	public static WebDriver driver;   //static bez we are using webdriver objects two times at the same time of execution, one is for calling webdrive(chrome or egde) another object creation it at extent report, captureScreen calling that menthod is written in this baseclass and at a time calling two objects conflict will occure to avoid that make webdriver as static(though we are creating object value will not change for webdriver bez it is static   
	public Logger logger; //Log4j2 
	public Properties p;

    @BeforeClass(groups= {"Sanity","Regression","Master"})
    @Parameters({"os", "browser"})
    public void setup(String os, String br) throws IOException
    {
    	logger = LogManager.getLogger(this.getClass()); 
    	
    	//Loading config properties file
    	FileReader file= new FileReader("./src//test//resources//config.properties");
    	p= new Properties();
    	p.load(file);
    	
    	if(p.getProperty("execution_env").equalsIgnoreCase("remote"))  // this is for grid execution
    	{
    	    DesiredCapabilities capabilities = new DesiredCapabilities();

    	    //os
    	    if(os.equalsIgnoreCase("windows"))
    	    {
    	        capabilities.setPlatform(Platform.WINDOWS);
    	    }
    	    else if (os.equalsIgnoreCase("mac"))
    	    {
    	        capabilities.setPlatform(Platform.MAC);
    	    }
    	    else
    	    {
    	        System.out.println("No matching os");
    	        return; // return will exit the loop
    	    }

    	    //browser
    	    switch(br.toLowerCase())
    	    {
    	        case "chrome": capabilities.setBrowserName("chrome"); break;
    	        case "edge": capabilities.setBrowserName("MicrosoftEdge"); break;
    	        default: System.out.println("No matching browser"); return;
    	    } 
    	    driver= new RemoteWebDriver(new URL("http://localhost:4444"),capabilities);
    	}
    	else if(p.getProperty("execution_env").equalsIgnoreCase("local"))
    	{
        	switch(br.toLowerCase())
        	{
        		case "chrome": driver=new ChromeDriver(); break;
        		case "edge": driver=new EdgeDriver(); break;
        		case "firefox": driver=new FirefoxDriver(); break;
        		default: System.out.println("No matching browser"); return;
        	}

    	}
        //driver = new ChromeDriver();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get(p.getProperty("appURL"));   //reading URL from properties file "https://tutorialsninja.com/demo/"
    }

    @AfterClass(groups= {"Sanity","Regression","Master"})
    public void teardown()
    {
        if(driver != null)
        {
            driver.quit();
        }
    }
    // Random string Generator

    public String randomString()
    {
    	String generatedString = RandomStringUtils.randomAlphabetic(5);
        return generatedString;
    }
    public String randomNumber()
    {
    	String generatedNumber = RandomStringUtils.randomNumeric(10);
        return generatedNumber;
    }
    public String randomAlphanumeric()
    {
    	String generatedString = RandomStringUtils.randomAlphabetic(5);
    	String generatedNumber = RandomStringUtils.randomNumeric(5);
        return (generatedString +"@"+ generatedNumber);
    }
    public String captureScreen(String tname) throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;   
        File sourceFile =takesScreenshot.getScreenshotAs(OutputType.FILE);  //we have to copy source file to target file
        
        String targetFilePath = System.getProperty("user.dir")+ "\\screenshots\\"+ tname + "_" + timeStamp + ".png";
        File targetFile = new File(targetFilePath);
        
        sourceFile.renameTo(targetFile);   //to copy source file to target file we have to write this line
        
        return targetFilePath;  //after completion of copying returning targetFilepath
    }
}
