package com.yelp.scraper.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yelp.scraper.model.Avatar;
import com.yelp.scraper.model.Review;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ScraperService {
	
	private static final String URL_MANAM = "https://www.yelp.com/biz/manam-comfort-filipino-makati";
	
	@Autowired
	FaceDetectionService fdService;
	
	
	public List<Review> scrape() {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\admin\\Desktop\\workspace\\drivers\\chromedriver.exe");
		final ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--headless");
		ChromeDriver driver = new ChromeDriver(chromeOptions);
		driver.get(URL_MANAM);		
		
		WebElement wrapper = driver.findElement(By.xpath("//div[@class='lemon--div__373c0__1mboc css-79elbk border-color--default__373c0__3-ifU']"));
		List<WebElement> ul = wrapper.findElements(By.tagName("ul"));
		List<WebElement> reviewList = ul.get(1).findElements(By.tagName("li"));
		
		List<Review> reviewListObject = new ArrayList<>();
		
		for (WebElement wElement: reviewList) {
			WebElement imgElement = wElement.findElement(By.xpath(".//div/div/div/div/div/div/div/div/div/a/img"));
			String imgUrl = imgElement.getAttribute("src");
			System.out.println("img: " + imgUrl);
			Review review = new Review();
			Avatar avatar = new Avatar();
			try {
				avatar = fdService.detectFacesUri(imgUrl);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			review.setAvatar(avatar);
			review.setReview(wElement.getText());
			reviewListObject.add(review);
		}
		driver.close();
			
		return reviewListObject;
		
	}

}
