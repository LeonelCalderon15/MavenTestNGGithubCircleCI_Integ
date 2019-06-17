package TestNG;

import java.util.concurrent.TimeUnit;

import java.util.*;
import org.apache.tools.ant.taskdefs.Java;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.annotations.*;
import org.apache.poi.xssf.usermodel.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import com.opencsv.CSVReader;
import MavenTestNGGithubCircleCI_Integ.MavenTestNGGithubCircleCI_Integ.PageObject_EurHistoricalData;

public class MavenTestNG_Class  {	
   
   private WebDriver driver;
	
   @Parameters("browser")
   
   @BeforeTest
   public void launchapp() {
	  String key_driver = "webdriver.chrome.driver";
	  String loc_driver =  "C:\\Users\\Vatbox Dev\\Documents\\Eclipse IDE Jars, Exe, Zip\\Browser Driver\\chromedriver_win32\\chromedriver.exe";
	  System.setProperty(key_driver, loc_driver);      
      driver = new ChromeDriver();
	  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
      // Go to https://www.investing.com/currencies/eur-usd-historical-data
      driver.navigate().to("https://www.investing.com/currencies/eur-usd-historical-data");
      driver.manage().window().maximize();
      //account login
      String email_account = "ExamApplication12345@gmail.com";
      String password_accout = "P@$$word_1234";
      
      PageObject_EurHistoricalData.signin_link(driver).click();
      driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS); 
      
      PageObject_EurHistoricalData.email_login_form(driver).clear();
      PageObject_EurHistoricalData.email_login_form(driver).sendKeys(email_account);
       
      PageObject_EurHistoricalData.password_login_form(driver).clear();
      PageObject_EurHistoricalData.password_login_form(driver).sendKeys(password_accout);

      PageObject_EurHistoricalData.signin_button_form(driver).click();  
   }
   
   @Test
   public void change_time_frame_and_download() throws InterruptedException, IOException {	   
      Select timeframe_dropdown = new Select(PageObject_EurHistoricalData.timeframe_dropdown(driver));	  
      WebElement option = timeframe_dropdown.getFirstSelectedOption();
      String defaultItem = option.getText();
      System.out.println("Default filter is " + defaultItem);   
       //If filter is set to “Daily” set to monthly. If filter is set to “Monthly” change to daily.
	  if(defaultItem.equals("Daily")) {		 	
	      timeframe_dropdown.selectByValue("Monthly");
	      try {
	    	    Thread.sleep(2000);
	    	} catch(InterruptedException e) {	   
	    		
	    	}
	    } 
	  else if(defaultItem.equals("Monthly"))  {
	      timeframe_dropdown.selectByValue("Daily");
	      try {
	    	    Thread.sleep(2000);
	    	} catch(InterruptedException e) {
	    	    
	    	}
	    }
	  //Download data. Check if the download is finished before processing the CSV as excel file WITHOUT using DELAY  
	  PageObject_EurHistoricalData.download_button_eurousd(driver).click();
	  
	  File file = new File("C:\\Users\\Vatbox Dev\\Downloads\\EUR_USD Historical Data.csv");
	  
	  for(int j = 0; j < 200000 ; j++)
	    {
		  if (file.exists() == true) {
		  System.out.println(file.exists());   
		  j = 300000;  
			  
		  }
		  else {
		   
		  }
	        
	    }	  
	  //Processing the Excel File
	  CSVReader eur_usd_dload_excel = new CSVReader(new FileReader("C:\\Users\\Vatbox Dev\\Downloads\\EUR_USD Historical Data.csv"));
	  //eur_usd_historical_data_dload_excel eur_usd_dload_excel = new eur_usd_historical_data_dload_excel("C:\\Users\\PC\\Downloads\\EUR_USD Historical Data.csv","Sheet1");
	//  double[] price_array = eur_usd_dload_excel.readNext();
	  double price_average_first_ten;
      double sum = 0;
      int no_of_price_to_average = 10;
      
      double[] price_double_array = new double[no_of_price_to_average];
      
	  String [] csvCell;
	  String [] price_array_string = new String[10];
	  String price_header_string = "";
	
	    		
	   csvCell = eur_usd_dload_excel.readNext();
	   price_header_string = csvCell[1];
	   
	
	   for(int i = 0; i < 10 ; i++)
          { 
		     csvCell = eur_usd_dload_excel.readNext();
		     price_array_string[i] = csvCell[1];
		     price_double_array[i] = Double.parseDouble(price_array_string[i]);
		     System.out.println(price_double_array[i]);    
	      }
	//getting the sum of the first 10 price  
       for(int i = 0; i < 10 ; i++)
          {  		
        sum = sum + price_double_array[i];
          }
  	//getting the average
       price_average_first_ten = sum / no_of_price_to_average;
  	
       if(price_average_first_ten < 1.2) {		 	
             System.out.println("Average Value is equal to " + price_average_first_ten + " and it is less than 1.2"); 
             System.out.println("Test is PASSED"); 
        } 
       else {         
        }
	  // Delete the CSV File after getting the average of the first 10 price number so that duplicating the file
	  // after running the test again will be avoided.
       
       File file_delete = new File("C:\\Users\\Vatbox Dev\\Downloads\\EUR_USD Historical Data.csv");
       
       if (!file_delete.exists()) {
	       
	   } else {	     
	       file_delete.deleteOnExit();
	   }          
   }
   @AfterTest
   public void terminatetest() {
   driver.close();
   }
}