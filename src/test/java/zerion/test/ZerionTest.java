package zerion.test;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class ZerionTest {
	WebDriver driver;
	WebDriverWait wait;

	// ADMIN LOGIN CREDENTIALS - CAN ALSO BE ADDED THROUGH EXTERNAL EXCEL FILE
	String username = "siddharth";
	String password = "password123";
	String formName = "Demo IForm";

	// ENTER THE DETAILS FOR NEW USER
	String newUserFirstName = "Alex";
	String newUserLastName = "Sandro";
	String newUserIDnumber = "5972145";

	@org.testng.annotations.BeforeSuite
	public void BeforeTest() throws InterruptedException {
		System.out.println("BEFORE SUITE");
		// I'M USING CHROME DRIVER, CAN BE CHANGED TO GECKODRIVER (FIREFOX) IF
		// NEEDED
		// SET THE SYSTEM PROPERTIES BEFORE - DRIVER PATH NEEDS TO BE CHANGED
		// ACCORDING TO YOUR DRIVER LOCATION
		System.setProperty("webdriver.chrome.driver",
				"F:/Sid/Eclipse/Selenium WebDrivers (IE, Chrome)/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}

	@Test(priority = 1)
	public void loginTest() throws InterruptedException {
		System.out.println("Inside Login Test");

		// GET THE URL
		driver.get("https://app.iformbuilder.com/exzact/dataViews.php");
		wait = new WebDriverWait(driver, 5);

		/**
		 * WAIT UNTIL LOGIN BUTTON APPEARS ON DOM, THEN ENTER CREDENTIALS AND
		 * LOGIN
		 */
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"login\"]/input")));
		driver.findElement(By.xpath("//*[@id=\"inner\"]/form/p/input")).sendKeys(username);
		driver.findElement(By.xpath("//*[@id=\"inner\"]/form/div[2]/span[1]/input")).sendKeys(password);
		driver.findElement(By.xpath("//*[@id=\"login\"]/input")).click();

		/**
		 * VERIFY THE LOGOUT BUTTON IS PRESENT ON SCREEN TO CONFIRM LOGIN IS
		 * SUCCESSFUL
		 */
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"header_rt\"]/li[3]/a")));
		if (driver.findElement(By.xpath("//*[@id=\"header_rt\"]/li[3]/a")).isDisplayed()) {
			System.out.println("Login Successful");
		} else {
			System.out.println("Login Unsuccessful");
		}
	}

	@Test(priority = 2)
	public void verifyCorrectUserTest() throws InterruptedException {
		System.out.println("Inside verifying correct user Test");

		/**
		 * VERIFY CORRECT USERNAME IS DISPLAYED AS LOGIN
		 */
		String welcomeText = driver.findElement(By.xpath("//*[@id=\"header_rt\"]/li[1]")).getText();
		if (welcomeText.contains(username)) {
			System.out.println("Correct User Login Verified.");
		} else {
			System.out.println("User Login Verification Failed.");
		}
	}

	@Test(priority = 3)
	public void createNewRecordTest() throws InterruptedException {
		System.out.println("Inside creating new record Test");

		// SEARCH FOR THE FORM NAME WHERE WE WANT TO ENTER NEW RECORD
		driver.findElement(By.xpath("//*[@id=\"datatable\"]/div[2]/div/div[8]/div/input[1]")).sendKeys(formName);
		;

		// SELECT THE SEARCH FILTER TYPE TO "FORM LABEL"
		WebElement mySelectElement = driver
				.findElement(By.xpath("//*[@id=\"datatable\"]/div[2]/div/div[8]/div/select"));
		Select dropdown = new Select(mySelectElement);
		dropdown.selectByIndex(1);

		// CLICK ON SEARCH BUTTON
		driver.findElement(By.xpath("//*[@id=\"datatable\"]/div[2]/div/div[8]/div/input[3]")).click();
		;

		/***
		 * USED THREAD SLEEP HERE BECAUSE TABLE AND ROWS ARE ALREADY PRESENT ON
		 * THE SCREEN AND WE ARE WAITING FOR UPDATE TO THIS TABLE ONLY, SO
		 * IMPLICIT AND EXPLICIT WAITS BOTH FAIL IN THIS CASE. I'M FULLY AWARE
		 * THREAD.SLEEP IS NOT THE BEST PRACTICE
		 */
		Thread.sleep(3000);
		driver.findElement(By.xpath("//*[@id=\"flex1\"]/tbody/tr/td[2]/div/a")).click();

		// OPEN THIS FORM BY CLICKING ON THE ID OF FORM SEARCHED FOR
		driver.findElement(By.xpath("//*[@id=\"fbutton_Create_New_Record\"]/div/span/div")).click();

		// FILL THE FORM WITH NEW RECORD DETAILS AND SAVE THE RECORD
		driver.findElement(By.xpath("//*[@id=\"p3638919_rec0_first_name\"]")).sendKeys(newUserFirstName);
		driver.findElement(By.xpath("//*[@id=\"p3638919_rec0_last_name\"]")).sendKeys(newUserLastName);
		driver.findElement(By.xpath("//*[@id=\"p3638919_rec0_id_number\"]")).sendKeys(newUserIDnumber);
		driver.findElement(By.xpath("//*[@id=\"custombtn_list\"]/ul/li[1]/div[1]/a")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"header_rt\"]/li[3]/a")));
	}

	@org.testng.annotations.AfterSuite
	public void AfterSuite() {
		System.out.println("AFTER SUITE");
		driver.quit();
	}

}
