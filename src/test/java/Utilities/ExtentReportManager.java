package Utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;



public class ExtentReportManager implements ITestListener {

    public ExtentSparkReporter sparkReporter;
    public ExtentReports extent;
    public ExtentTest test;

    String repName;

    public void onStart(ITestContext testContext) {
    	
    	/*SimpleDateFormat df =new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
    	Date dt = new Date();
    	String currentdatetimestamp = df.format(dt);
    	*/        // this is also date formate code but instead of writing 3lines of code, we can write one line code for date
    	
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()); //time stamp code in single line
       
        repName = "Test-Report-" + timeStamp + ".html";
        sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName);  //specify new location of the report

        sparkReporter.config().setDocumentTitle("opencart Automation Report");  //title of the report
        sparkReporter.config().setReportName("opencart Functional Testing");  //name of the report
        sparkReporter.config().setTheme(Theme.DARK);

        extent = new ExtentReports(); 

        extent.attachReporter(sparkReporter);

        extent.setSystemInfo("Application", "opencart");
        extent.setSystemInfo("Module", "Admin");
        extent.setSystemInfo("Sub Module", "Customers");
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
        extent.setSystemInfo("Environemnt", "QA");

        String os = testContext.getCurrentXmlTest().getParameter("os");  //this is master.xml file parameter data we are getting 
        extent.setSystemInfo("Operating System", os);

        String browser = testContext.getCurrentXmlTest().getParameter("browser");  //this is master.xml file parameter data we are getting
        extent.setSystemInfo("Browser", browser);

        List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();//getIncludedGroups() is specify what are the included tests we want to get the data on that only displaying 

        if(!includedGroups.isEmpty()) {
            extent.setSystemInfo("Groups", includedGroups.toString());   // grouping list(sanity, regression, mater)
        }
    }
    public void onTestSuccess(ITestResult result) {

        test = extent.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups()); // to display groups in report
        test.log(Status.PASS,result.getName() +" got successfully executed");
    }
    
    public void onTestFailure(ITestResult result) {

        test = extent.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.FAIL, result.getName() + " got failed");
        test.log(Status.INFO, result.getThrowable().getMessage());

        try {    //if screenshot will not available will get filenotfound exception, so better to use try catch block 

            String imgPath = new BaseClass().captureScreen(result.getName());   //capturing the failure 
            test.addScreenCaptureFromPath(imgPath);    //attaching screenshot to extent report 

        }
        catch (IOException e1) {
            e1.printStackTrace();   // this will show what exception we got (filenotfound like that)
        }
    }
    public void onTestSkipped(ITestResult result) {

        test = extent.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.SKIP,result.getName() +" got skipped");  
        test.log(Status.INFO,result.getThrowable().getMessage()); //why TC is skipped info will provide
    }
    
    public void onFinish(ITestContext testContext) {

        extent.flush();  // this it last line, it will consolidate data and in extent report 

        String pathOfExtentReport = System.getProperty("user.dir")+"\\reports\\"+ repName;   // after completion of report generation, this code will automatically open in your desktop
        File extentReport = new File(pathOfExtentReport);  // if extent report not available then it will throw an error 
        try {
            Desktop.getDesktop().browse(extentReport.toURI());  // Desktop pre defined class this line is automatically opn on browser

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}