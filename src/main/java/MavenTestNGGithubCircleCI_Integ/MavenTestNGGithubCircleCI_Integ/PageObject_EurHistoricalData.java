package MavenTestNGGithubCircleCI_Integ.MavenTestNGGithubCircleCI_Integ;

import org.openqa.selenium.*;

public class PageObject_EurHistoricalData {
	
	private static WebElement element = null;
	
	//Sign In Link    
	public static WebElement signin_link(WebDriver driver) {
		  element = driver.findElement(By.xpath("/html/body/div[5]/header/div[1]/div/div[4]/span[1]/div/a[1]"));
		  return element;	       
		   }
	
	public static WebElement frame_form(WebDriver driver) {
		  element = driver.findElement(By.id("loginPopup"));
		  return element;	       
		   }
	
	public static WebElement email_login_form(WebDriver driver) {
		  element = driver.findElement(By.id("loginFormUser_email"));
		  return element;	       
		   }
	
	public static WebElement password_login_form(WebDriver driver) {
		  element = driver.findElement(By.id("loginForm_password"));
		  return element;	       
		   }
	
	
	public static WebElement signin_button_form(WebDriver driver) {
		  element = driver.findElement(By.xpath("//a[@class='newButton orange'][contains(text(),'Sign In')]"));
		  return element;	       
		   }
	
	//Time Frame Drop Down
	public static WebElement timeframe_dropdown(WebDriver driver) {
	      element = driver.findElement(By.id("data_interval"));
	      return element;	
	   }     
	
	 //DownLoad Button     
	public static WebElement download_button_eurousd(WebDriver driver) {
		  element = driver.findElement(By.xpath("/html/body/div[5]/section/div[8]/div[4]/div/a"));
		  return element;	       
	   }		
}	