	package pomdesign;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import pages.WebOrdersLoginPage;

public class WebOrdersLoginTests {
	String url ="http://secure.smartbearsoftware.com/samples/TestComplete12/WebOrders/Login.aspx";
	WebDriver driver;
	
	@BeforeClass
	public void setUp() {
	WebDriverManager.chromedriver().setup();
	driver = new ChromeDriver();
	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
	@Ignore
	@Test
	public void positiveLoginTest() {
		driver.get(url);
		driver.findElement(By.id("ctl00_MainContent_username")).sendKeys("Tester");
		driver.findElement(By.id("ctl00_MainContent_password")).sendKeys("Test");
		driver.findElement(By.id("ctl00_MainContent_login_button")).click();	
	}
	
	@Test
	public void positiveLoginUsingPom() {
		driver.get(url);
		WebOrdersLoginPage loginPage = new WebOrdersLoginPage(driver);
		loginPage.userName.sendKeys("Tester");
		loginPage.password.sendKeys("test");
		loginPage.loginButton.click();
		
	}
	
	@Test
	public void invalidUsernameTest() {
		driver.get(url);
		WebOrdersLoginPage loginPage = new WebOrdersLoginPage(driver);
		loginPage.userName.sendKeys("edeffs");
		loginPage.password.sendKeys("test");
		loginPage.loginButton.click();
		String err = loginPage.errorMessage.getText();
		assertEquals(err, "Invalid Login or Password.");
	}
	

}
