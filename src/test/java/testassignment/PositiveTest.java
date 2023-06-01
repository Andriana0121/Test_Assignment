package testassignment;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class PositiveTest {
    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        // Set the path to the chromedriver executable
        System.setProperty("webdriver.chrome.driver", "src//main//resources//chromedriver.exe");

        // Create a new instance of the Chrome driver
        driver = new ChromeDriver();

        // Maximize the browser window
        driver.manage().window().maximize();

        // Navigate to the website
        driver.get("https://rozetka.com.ua/");
    }

    /*@Parameters({"username", "password"})
    @Test(priority = 1, groups = "positiveTest" )
    public void testLogin(String username, String password) throws InterruptedException {

        // Perform login
        WebElement accoutIcon = driver.findElement(By.cssSelector("rz-user [aria-hidden]"));
        accoutIcon.click();
        Thread.sleep(3000);

        //Enter login and password
        WebElement loginField = driver.findElement(By.cssSelector("input#auth_email"));
        loginField.click();
        loginField.sendKeys(username);

        WebElement passwordField = driver.findElement(By.xpath("//*[@id=\"auth_pass\"]"));
        passwordField.sendKeys(password);

        WebElement loginButton = driver.findElement(By.cssSelector(".button.button--large.button--green.auth-modal__submit.ng-star-inserted"));
        loginButton.click();
        Thread.sleep(3000);

        // Perform click on captcha
        //WebDriverWait wait = new WebDriverWait(driver, 20);
        //WebElement recaptchaCheckbox = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".recaptcha-checkbox-checkmark")));
        //recaptchaCheckbox.click();

        //loginButton.click();
        //Thread.sleep(3000);

        // Assert login success
        WebElement userMenu = driver.findElement(By.xpath("//header/div/div/ul/li[3]/rz-user/a"));
        Assert.assertTrue(userMenu.isDisplayed(), "Login failed");
        Thread.sleep(3000);

    }*/

    @Parameters({"expectedMessage"})
    @Test(priority = 2, groups = "positiveTest")
    public void AddToCart(String expectedMessage) throws InterruptedException {
        // Choose a product from the catalog
        WebElement productLink = driver.findElement(By.xpath("//button[@id='fat-menu']"));
        productLink.click();
        Thread.sleep(3000);

        // Find category "Ноутбуки та комп’ютери"
        WebElement catalogLink = driver.findElement(By.xpath("//a[@class='menu-categories__link js-menu-categories__link menu-categories__link_state_hovered']"));

        // Create an instance of the Actions class
        Actions actions = new Actions(driver);

        // Perform the mouseover action on the "Ноутбуки та комп’ютери"
        actions.moveToElement(catalogLink).perform();
        Thread.sleep(3000);

        // Find and click on the desired Asus option within the expanded menu
        WebElement asusElement = driver.findElement(By.linkText("Asus"));
        asusElement.click();
        Thread.sleep(5000);

        // Add the product to the cart
        WebElement addToCartButton = driver.findElement(By.xpath("//li[1]/rz-catalog-tile/app-goods-tile-default/div/div[2]/div[4]/div[2]/app-buy-button/button"));
        addToCartButton.click();
        Thread.sleep(3000);


        // Verify the text of the success message
        WebElement successMessagePopup = driver.findElement(By.xpath("//div[@class='notification__wrapper notification__wrapper--success']"));
        Assert.assertTrue(successMessagePopup.isDisplayed(), "Success message is not displayed");

        // Get the text of the success message using JavaScript
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        String actualMessage = (String) jsExecutor.executeScript("return arguments[0].textContent;", successMessagePopup);

        // Verify if expected message is equal to actual Message
        Assert.assertEquals(actualMessage, expectedMessage, "Actual message is not equal to expected message");

        // Go to the basket
        WebElement basketLink = driver.findElement(By.xpath("//ul/li[7]/rz-cart/button"));
        basketLink.click();
        Thread.sleep(3000);

        // Verify if the product is displayed in the basket
        WebElement productInBasket = driver.findElement(By.xpath("//rz-cart-product/div/div[1]/div/a"));
        Assert.assertTrue(productInBasket.isDisplayed(), "Product is not displayed in the basket");
        Thread.sleep(7000);
    }

    @Test(priority = 3)
    public void testOrderForm() throws InterruptedException {
        // Purchase order
        WebElement purchaseOrder = driver.findElement(By.xpath("//rz-shopping-cart/div/div[1]/div/a"));
        purchaseOrder.click();
        Thread.sleep(25000);

        // Complete the form
        WebElement surname = driver.findElement(By.xpath("//rz-checkout-orders/rz-checkout-orders-content/div/form/div/main/rz-checkout-contact-info/fieldset/rz-contact-info-toggle/div/div/rz-name-surname-group/div[1]/input"));
        surname.sendKeys("Tester");

        WebElement name = driver.findElement(By.xpath("//rz-name-surname-group/div[2]/input"));
        name.sendKeys("Test");

        WebElement phoneNumber = driver.findElement(By.xpath("//*[@id=\"checkoutUserPhone\"]"));
        phoneNumber.sendKeys("5609343584");

        WebElement email = driver.findElement(By.xpath("//rz-checkout-user-email-wrapper/input"));
        email.sendKeys("abcd@mailinator.com");

        WebElement save = driver.findElement(By.xpath("//rz-checkout-user-email-wrapper/input"));
        save.click();

        WebElement delivery = driver.findElement(By.xpath("//rz-checkout-order-delivery/div/div[1]/label"));
        delivery.click();

        WebElement recipientSurname = driver.findElement(By.xpath("//*[@id=\"recipientSurname\"]"));
        recipientSurname.sendKeys("Tester");

        WebElement recipientName = driver.findElement(By.xpath("//*[@id=\"recipientName\"]"));
        recipientName.sendKeys("Test");

        WebElement recipientPatronymic = driver.findElement(By.xpath("//*[@id=\"recipientPatronymic\"]"));
        recipientPatronymic.sendKeys("Testers");

        Thread.sleep(3000);
    }

    @AfterClass
    public void tearDown() {
        // Close the browser
        driver.quit();
    }
}

