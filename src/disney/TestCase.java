package disney;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;


public class TestCase {

	private WebDriver driver;
	private String baseUrl;
	
	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		baseUrl = "https://www.statefarm.com/";
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	}
	
	@After
	public void tearDown() throws Exception {
		if (driver != null) {
            driver.quit(); driver = null;
        }
	}
	
	@Test
    // Sample Test - Statefarm: Get a Life Quote
    public void testSTLifeQuote() throws Exception {
        
        String[] args = {
        	"California",
        	"Male", "3/26/1982", "5'5 165",
        	"No", "Good", "$50,000", "Yes"
        };
        
        int i = 0;
        String stateName = args[i++];
        
        /* 1. Launch the application with https://www.statefarm.com */
        driver.get(baseUrl);
        Thread.sleep(2000);
        
        /* 2. Move and Click on Finances */
        Actions builder = new Actions(driver);
        builder.moveToElement(getElementBy("id", "finances-menuitem", driver)).perform();
        
        /* 3. Choose Life Insurance from menu and Click on Life Insurance */
        getElementBy("linktext", "Life Insurance", driver).click();
        
        mouseScrollDown(250, driver);
        
        /* 4. Select State from dropdown menu in get a life qoute */
        new Select(getElementBy("id", "sState", driver)).selectByVisibleText(stateName);
        
        /* 5-1. then click go */
        getElementBy("css", "div.controls > input[name=\"submit\"]", driver).click();
        
        /* 5-2. Click Continue */
        getElementBy("id", "coppaIndicatorButtonID", driver).click();
        Thread.sleep(2500);
        
        mouseScrollDown(300, driver);
        
        //Extract testing data: personal information
        String gender = args[i++];
        String[] mm_dd_yyyy = args[i++].split("/");
        String[] height_weight = args[i++].split("[ ]+");
        String[] foot_inch = height_weight[0].split("'");
        String iftobacco = args[i++];
        String health_category = args[i++];
        String coverageCost = args[i++];
        String ifCurrCustomer = args[i++];
        
        /* 6. Click female */
        if (gender.equalsIgnoreCase("Male")) {
        	getElementBy("id", "gender10", driver).click();
        }
        else {
        	getElementBy("id", "gender11", driver).click();
        }
        
        /* 7. Click and write date of birth mm/dd/yyyy */
        enterText("xpath", "//*[@id='age1Id']/div[1]/input", mm_dd_yyyy[0], driver);
        enterText("xpath", "//*[@id='age1Id']/div[3]/input", mm_dd_yyyy[1], driver);
        enterText("xpath", "//*[@id='age1Id']/div[5]/input", mm_dd_yyyy[2], driver);
        
        /* 8. Enter height and weight */
        enterText("id", "ft1-sfxid_3", foot_inch[0], driver);
        enterText("id", "in1-sfxid_4", foot_inch[1], driver);
        enterText("id", "lbs1-sfxid_5", height_weight[1], driver);
        
        /* 9. Move cursor to the next filed and then click yes or no */
        if (iftobacco.equalsIgnoreCase("Yes")) {
        	getElementBy("id", "tobaccoNicotine10", driver).click();
        }
        else {
        	getElementBy("id", "tobaccoNicotine11", driver).click();
        }
        
        /* 10. Move cursor to the health category message and then click on option */
        switch(health_category.toLowerCase()) {
            case "excellent":
            	getElementBy("id", "cust1RateYourHealth0", driver).click();
                break;
            case "very good":
            	getElementBy("id", "cust1RateYourHealth1", driver).click();
                break;
            case "good":
            	getElementBy("id", "cust1RateYourHealth2", driver).click();;
                break;
            case "average":
            default:
            	getElementBy("id", "cust1RateYourHealth3", driver).click();
                break;
        }
        
        /* 11. Move cursor to the coverage step, click and select option */
        new Select(getElementBy("id", "coverageList1Id-sfxid_10", driver)).selectByVisibleText(coverageCost); 
        
//        /* 12. Move cursor to the current state farm field and the click yes or no */
//        if (ifCurrCustomer.equalsIgnoreCase("Yes")) {
//        	getElementBy("id", "custAlreadyWithSF10", driver).click();
//        }
//        else {
//        	getElementBy("id", "custAlreadyWithSF11", driver).click();
//        }
//        Thread.sleep(2500);
//        
//        mouseScrollDown(200, driver);
//        Thread.sleep(1000);
        
        /* 13. Click Get Quote button */
        getElementBy("id", "getQuoteBtnID", driver).click();;
        Thread.sleep(5000);
    }
	
	public static WebElement getElementBy(String locator, String value, WebDriver driver)  {
        
    	locator = locator.toLowerCase();
    	WebElement elm = null;
        try {
        	switch(locator) {
        	case "id":
        		elm = driver.findElement(By.id(value));
        		break;
        	case "name":
        		elm = driver.findElement(By.name(value));
        		break;
        	case "classname":
        		elm = driver.findElement(By.className(value));
        		break;
        	case "tagname":
        		elm = driver.findElement(By.tagName(value));
        		break;
        	case "linktext":
        		elm = driver.findElement(By.linkText(value));
        		break;
        	case "css":
        		elm = driver.findElement(By.cssSelector(value));
        		break;
        	case "xpath":
        		elm = driver.findElement(By.xpath(value));
        		break;
        	default:
        		System.out.println("Unknown locator type '"+ locator +"'");
        		break;
        	}
        } catch (Exception e) {
        	System.out.println(e.getMessage());
        }
        return elm;
    }
    
	public static void mouseScrollDown(int px, WebDriver driver) {
		JavascriptExecutor jsExe = (JavascriptExecutor) driver;
    	jsExe.executeScript("window.scrollBy(0, "+ px +")");
	}
	
	public static void enterText(String locatorType, String locatorValue, String text, WebDriver driver) {
        
        WebElement elm = getElementBy(locatorType, locatorValue, driver);
        elm.clear();
        elm.sendKeys(text);
    }
}
