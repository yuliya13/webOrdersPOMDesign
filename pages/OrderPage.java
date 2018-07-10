package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OrderPage {
	
	public OrderPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//a[@href='Process.aspx']")
	public WebElement orders;
	
//	public void Orders() {
//		orders.click();
//	}
	
	@FindBy(xpath="//select[@id='ctl00_MainContent_fmwOrder_ddlProduct']")
	public WebElement product;
	
	@FindBy(id="ctl00_MainContent_fmwOrder_txtQuantity")
	public WebElement quantity;
	
	@FindBy(id="ctl00_MainContent_fmwOrder_txtName")
	public WebElement name;
	
	@FindBy(id="ctl00_MainContent_fmwOrder_TextBox2")
	public WebElement street;
	
	@FindBy(id="ctl00_MainContent_fmwOrder_TextBox3")
	public WebElement city;
	
	@FindBy(id="ctl00_MainContent_fmwOrder_TextBox4")
	public WebElement state;
	
	@FindBy(id="ctl00_MainContent_fmwOrder_TextBox5")
	public WebElement zip;
	
	@FindBy(id="ctl00_MainContent_fmwOrder_TextBox6")
	public WebElement cardNumber;
	
	@FindBy(id="ctl00_MainContent_fmwOrder_TextBox1")
	public WebElement expireDate;
	
	@FindBy(xpath="//a[@id='ctl00_MainContent_fmwOrder_InsertButton']")
	public WebElement process;

	
	public void process() {
		process.click();
}
}
