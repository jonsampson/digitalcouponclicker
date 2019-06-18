package digitalcouponclicker;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ChromeDriverCouponClicker {
	
	private static ChromeDriverService service;
	
	private static final String CHROME_DRIVER_FILENAME = "chromedriver-2.35";
	
	public static void createAndStartService() throws IOException {
		URL classpathURL = ChromeDriverCouponClicker.class.getClassLoader().getResource(CHROME_DRIVER_FILENAME);
		
		service = new ChromeDriverService.Builder()
				.usingDriverExecutable(new File(classpathURL.getFile()))
				.usingAnyFreePort()
				.build();
		service.start();
	}
		
	public static ChromeDriver chromeDriver() throws IOException {
		createAndStartService();
		return new ChromeDriver(service);
	}
	
	public static void main(String[] args) throws InterruptedException, IOException {
		if(args == null || args.length != 2) {
			System.out.println("java ChromeDriverCouponClicker [%publix user name%] [%publix password%]");
		}
		// create a Chrome Web Driver
		WebDriver driver = chromeDriver();
		// open the browser and go to publix.com's digital coupons
		driver.get("https://www.publix.com/savings/coupons/digital-coupons#!/list");
		
		WebElement loginLink = (new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.className("pblx-login"))));
		
		Actions actions = new Actions(driver);
		actions.moveToElement(loginLink);
		
		loginLink.click();
		
		WebElement emailAddressTextField = (new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[id$=tmpUserNameInput"))));
		
		emailAddressTextField.sendKeys(args[0]);
		
//		WebElement fakePasswordField = driver.findElement(By.cssSelector("input[id=fakePassword]"));
//		fakePasswordField.click();
		
		WebElement passwordField = driver.findElement(By.cssSelector("input[id$=passwordInput]"));
		passwordField.sendKeys(args[1]);

		WebElement loginButton = driver.findElement(By.cssSelector("span[id$=submitButton]"));
		loginButton.click();
		
		WebElement loadAllCouponsButton = (new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("button[class$=btnLoadCoupons]"))));
		actions.moveToElement(loadAllCouponsButton);
		loadAllCouponsButton.click();
		
		Thread.sleep(10000);
		
		List<WebElement> couponPlusButtons = driver.findElements(By.cssSelector("button.dc-card-clip"));
		for(WebElement couponPlusButton : couponPlusButtons) {
			if(!couponPlusButton.isDisplayed()) {
				actions.moveToElement(couponPlusButton);
			}
			try {
				couponPlusButton.findElement(By.cssSelector("img[src$='clipped.svg']"));
			}
			catch(Exception e) {
				couponPlusButton.click();
				Thread.sleep(750);
			}
		}
		
		Thread.sleep(10000);
		
		driver.quit();
		
		service.stop();
	}

}
