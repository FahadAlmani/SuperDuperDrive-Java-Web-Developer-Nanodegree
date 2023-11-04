package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
import java.time.Duration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		System.setProperty("webdriver.chrome.driver", "src/test/java/chromedriver.exe");
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password) {
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));

		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		/* Check that the sign up was successful. 
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depening on the rest of your code.
		*/
	}


	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password) {
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
	 * rest of your code.
	 * This test is provided by Udacity to perform some basic sanity testing of
	 * your code to ensure that it meets certain rubric criteria.
	 * <p>
	 * If this test is failing, please ensure that you are handling redirecting users
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric:
	 * https://review.udacity.com/#!/rubrics/2724/view
	 */
	@Test
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection", "Test", "RT", "123");

		// Check if we have been redirected to the log in page.
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
	 * rest of your code.
	 * This test is provided by Udacity to perform some basic sanity testing of
	 * your code to ensure that it meets certain rubric criteria.
	 * <p>
	 * If this test is failing, please ensure that you are handling bad URLs
	 * gracefully, for example with a custom error page.
	 * <p>
	 * Read more about custom error pages at:
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL", "Test", "UT", "123");
		doLogIn("UT", "123");

		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
	 * rest of your code.
	 * This test is provided by Udacity to perform some basic sanity testing of
	 * your code to ensure that it meets certain rubric criteria.
	 * <p>
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code.
	 * <p>
	 * Read more about file size limits here:
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File", "Test", "LFT", "123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

	}

	public void addNote(String title, String description) throws InterruptedException {
		doMockSignUp("Note", "Test", "note", "123");
		doLogIn("note", "123");

		WebElement noteTap = driver.findElement(By.id("nav-notes-tab"));
		noteTap.click();

		Thread.sleep(200);

		WebElement newNoteButton = driver.findElement(By.id("add-note"));
		newNoteButton.click();

		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteModal")));

		WebElement noteTitleField = driver.findElement(By.id("note-title"));
		noteTitleField.sendKeys(title);

		WebElement noteDescriptionField = driver.findElement(By.id("note-description"));
		noteDescriptionField.sendKeys(description);

		WebElement noteSubmitButton = driver.findElement(By.id("noteSubmitButton"));
		noteSubmitButton.click();
	}

	@Test
	public void deleteNote() {
		try {
			addNote("deleteNote", "test");

			WebElement noteTap = driver.findElement(By.id("nav-notes-tab"));
			noteTap.click();

			Thread.sleep(200);

			WebElement deleteNoteNoteButton = driver.findElement(By.id("deleteNote"));
			deleteNoteNoteButton.click();

		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

	}

	@Test
	public void updateNote() {
		try {
			addNote("updateNote", "test");

			WebElement noteTap = driver.findElement(By.id("nav-notes-tab"));
			noteTap.click();

			Thread.sleep(200);

			WebElement editNoteButton = driver.findElement(By.id("editNote"));
			editNoteButton.click();

			WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

			webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteModal")));

			WebElement noteTitleField = driver.findElement(By.id("note-title"));
			noteTitleField.sendKeys("Test");

			WebElement noteDescriptionField = driver.findElement(By.id("note-description"));
			noteDescriptionField.sendKeys("Test");

			WebElement noteSubmitButton = driver.findElement(By.id("noteSubmitButton"));
			noteSubmitButton.click();

		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

	}


	public void addCredentials(String url, String username, String password) throws InterruptedException {
		doMockSignUp("Credential", "Test", "credential", "123");
		doLogIn("credential", "123");

		WebElement credentialsTap = driver.findElement(By.id("nav-credentials-tab"));
		credentialsTap.click();

		Thread.sleep(200);

		WebElement newCredentialButton = driver.findElement(By.id("add-credential"));
		newCredentialButton.click();

		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialModal")));

		WebElement credentialUrlField = driver.findElement(By.id("credential-url"));
		credentialUrlField.sendKeys(url);

		WebElement credentialUsernameField = driver.findElement(By.id("credential-username"));
		credentialUsernameField.sendKeys(username);

		WebElement credentialPasswordField = driver.findElement(By.id("credential-password"));
		credentialPasswordField.sendKeys(password);

		WebElement noteSubmitButton = driver.findElement(By.id("credentialSubmitButton"));
		noteSubmitButton.click();
	}

	@Test
	public void deleteCredentials() {
		try {
			String URL = "www.delete.com";
			String USERNAME = "credentialsTest";
			String PASSWORD = "credentialsTest";
			addCredentials(URL, USERNAME, PASSWORD);

			WebElement credentialsTap = driver.findElement(By.id("nav-credentials-tab"));
			credentialsTap.click();

			Thread.sleep(200);

			WebElement deleteCredentialButton = driver.findElement(By.id("deleteCredential"));
			deleteCredentialButton.click();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void updateCredentials() throws InterruptedException {
		String URL = "www.update.com";
		String USERNAME = "credentialsTest";
		String PASSWORD = "credentialsTest";
		addCredentials(URL,USERNAME,PASSWORD);

		WebElement credentialsTap = driver.findElement(By.id("nav-credentials-tab"));
		credentialsTap.click();

		Thread.sleep(200);

		WebElement editCredentialButton = driver.findElement(By.id("editCredential"));
		editCredentialButton.click();

		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialModal")));

		WebElement credentialUrlField = driver.findElement(By.id("credential-url"));
		credentialUrlField.sendKeys("www.url.com");

		WebElement credentialUsernameField = driver.findElement(By.id("credential-username"));
		credentialUsernameField.sendKeys("username");

		WebElement credentialPasswordField = driver.findElement(By.id("credential-password"));
		credentialPasswordField.sendKeys("password");

		WebElement noteSubmitButton = driver.findElement(By.id("credentialSubmitButton"));
		noteSubmitButton.click();
	}
}
