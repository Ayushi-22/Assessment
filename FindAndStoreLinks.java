package links;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class FindAndStoreLinks {

	private static WebDriver driver;

	public static boolean isValidURL(String url) {
		try {
			(new java.net.URL(url)).openStream().close();
			return true;
		} catch (Exception ex) {
		}
		return false;
	}

	public static void main(String[] args) {

		Scanner link = new Scanner(System.in);
		System.out.println("Enter Link :");

		// String input
		String url = link.nextLine();

		if (isValidURL(url)) {
			System.out.println("It is a Valid URL");
		} else {
			System.out.println("It is Not a Valid URL");
		}

		Scanner validInt = new Scanner(System.in);
		System.out.println("Enter number betwwen 1 to 20 :");
		Integer num = validInt.nextInt();

		if (num > 0 && num < 21) {
			System.out.println("Entered Integer is Valid");
		} else {
			System.out.println("Entered Integer is Not Valid");
		}

		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\14379\\Downloads\\chromedriver_win32\\chromedriver.exe");

		List<String> allLinks = new ArrayList<String>();
		allLinks.add(url);

		for (int i = 0; i < num; i++) {
			String currentURL = allLinks.get(i);

			driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.get(url);

			List<String> newlyFoundedLinks = getPageEmbadedLinks(currentURL);

			Set<String> uniqueLinks = new HashSet<String>(newlyFoundedLinks);
			allLinks.addAll(uniqueLinks);

			System.out.println("URL: " + currentURL);
			System.out.println("Total Links: " + newlyFoundedLinks.size());
			System.out.println("Unique Links: " + uniqueLinks.size());

			driver.close();
			driver.quit();

		}
	}

	public static List<String> getPageEmbadedLinks(String url) {

		List<String> newlyFoundedLinks = new ArrayList<String>();

		List<WebElement> allLinks = driver.findElements(By.tagName("a"));

		for (WebElement element : allLinks) {
			String newlyUrl = element.getAttribute("href");

			if (newlyUrl != null) {
				newlyFoundedLinks.add(newlyUrl);
			}
		}

		return newlyFoundedLinks;
	}

}
