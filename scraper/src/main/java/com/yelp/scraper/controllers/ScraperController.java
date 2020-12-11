package com.yelp.scraper.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.yelp.scraper.model.Review;
import com.yelp.scraper.service.ScraperService;

@RestController
public class ScraperController {
	
	@Autowired
	ScraperService service;
	
	
	@RequestMapping(value = "/scrape", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
	public List<Review> scrape() {
		return service.scrape();
	}

}
