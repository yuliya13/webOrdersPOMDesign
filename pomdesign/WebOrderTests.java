package pomdesign;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import io.github.bonigarcia.wdm.WebDriverManager;
import pages.AllOrdersPage;
import pages.OrderPage;
import pages.ProductsPage;
import pages.WebOrdersLoginPage;

public class WebOrderTests {
	WebDriver driver;
	ProductsPage productsPage;
	WebOrdersLoginPage loginPage;
	AllOrdersPage allOrdersPage;
	OrderPage orderpage;
	String userid = "Tester";
	String password="test";
	Faker data;
	List<WebElement>tabledata;
	String todaysdate="07/10/2018";
	
	@BeforeClass
	public void setUp() {
	WebDriverManager.chromedriver().setup();
	driver = new ChromeDriver();
	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	
	}
	
	@BeforeMethod
	public void setUpApp() {
		driver.get("http://secure.smartbearsoftware.com/samples/TestComplete12/WebOrders/Login.aspx");
		loginPage = new WebOrdersLoginPage(driver);
	}
	
	//@Test(description="Verify labels and tab links are displayed")
	public void labelsVerifications() {
		assertEquals(driver.getTitle(), "Web Orders Login");
		loginPage.login(userid, password);
		
		allOrdersPage = new AllOrdersPage(driver);
		assertTrue(allOrdersPage.webOrders.isDisplayed());
		assertTrue(allOrdersPage.allProducts.isDisplayed());
		assertTrue(allOrdersPage.listOfAllOrders.isDisplayed());
		assertEquals(allOrdersPage.welcomeMsg.getText().replace(" | Logout", ""), "Welcome, "+userid+"!");
		assertTrue(allOrdersPage.allOrders.isDisplayed());
	}
	
	//@Test(description="Verify default Products and prices")
	public void availableProductsTest() {
			assertEquals(driver.getTitle(), "Web Orders Login");
			loginPage.login(userid, password);
			allOrdersPage = new AllOrdersPage(driver);
			allOrdersPage.allProducts.click();
			productsPage=new ProductsPage(driver);
			List<String>expProducts = Arrays.asList("MyMoney", "FamilyAlbum", "ScreenSaver");
			List<String>actualProducts=new ArrayList<>();
			productsPage.productNames.forEach(elem->actualProducts.add(elem.getText()));
			assertEquals(actualProducts, expProducts);
			
			for(WebElement row:productsPage.productsRows) {
					System.out.println(row.getText());
					String[] prodData = row.getText().split(" ");
					switch(prodData[0]) {
					case "MyMoney":
						assertEquals(prodData[1], "$100");
						assertEquals(prodData[2], "8%");
						break;
						
					case "FamilyAlbum":
						assertEquals(prodData[1], "$80");
						assertEquals(prodData[2], "15%");
						break;
						
					case "ScreenSaver":
						assertEquals(prodData[1], "$20");
						assertEquals(prodData[2], "10%");
						break;
					}
				}
	}
	@Test(description="homework")
	public void ordersTest() {
		loginPage.login(userid, password);
		allOrdersPage=new AllOrdersPage(driver);
		allOrdersPage.orderTab.click();	
		orderpage=new OrderPage(driver);
		data=new Faker();
		Select select = new Select(orderpage.product);
		select.selectByIndex(data.number().numberBetween(1, 3));
		String product=select.getFirstSelectedOption().getText();
		
		String quantityType = data.number().digit();
		String custName = data.name().fullName();
		String streetType = data.address().streetAddress();
		String cityType = data.address().city();
		String stateType = data.address().state();
		String zipType=data.address().zipCode().substring(0,5);
		String cardNumberType = data.business().creditCardNumber().replace("-", "");
		String expDate=""+data.number().numberBetween(10, 12)+"/"+data.number().numberBetween(18, 25);
		
		orderpage.quantity.sendKeys(quantityType);
		orderpage.name.sendKeys(custName);
		orderpage.street.sendKeys(streetType);
		orderpage.city.sendKeys(cityType);
		orderpage.state.sendKeys(stateType);
		orderpage.zip.sendKeys(zipType);
	
		String xpath = "(//input[@name='ctl00$MainContent$fmwOrder$cardList'])["+data.number().numberBetween(1, 3)+"]";	
		driver.findElement(By.xpath(xpath)).click();
		String card = driver.findElement(By.xpath(xpath)).getAttribute("value").toString();
		
		orderpage.cardNumber.sendKeys(cardNumberType);
		orderpage.expireDate.sendKeys(expDate);
		
		orderpage.process.click();
		allOrdersPage.allOrders.click();
		
		tabledata=driver.findElements(By.xpath("//table/tbody/tr[2]/td"));
		List<String> actualResult=new ArrayList<String>();
		for (int i = 2; i < tabledata.size(); i++) {
			
		String tablexpath="//table/tbody/tr[2]/td["+i+"]";
		
		String tdData = driver.findElement(By.xpath(tablexpath)).getText();
		
        actualResult.add(tdData);
	}
		List<String>expectedResult=Arrays.asList(custName, product, quantityType, todaysdate, streetType, cityType, stateType, zipType, card, cardNumberType, expDate);
	
		
		System.out.println("ActualResult" +actualResult);
		System.out.println("Expected Result" + expectedResult);
		
		assertEquals(actualResult, expectedResult);
	
	}

//	@AfterMethod
//	public void tearDown() {
//		allOrdersPage.logout();
//	}
//	
//	@AfterClass
//	public void TearDown() {
//		driver.quit();
//	}

}
